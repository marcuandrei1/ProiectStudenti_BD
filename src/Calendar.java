import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class Calendar extends JPanel{
    private JPanel calendarPanel;
    private JLabel monthLabel;
    private LocalDate currentDate;
    private Utilizator user;

    public Calendar(Utilizator user) {
        currentDate = LocalDate.now();
        this.setLayout(new BorderLayout());
        this.user=user;
        createAndShowGUI();
    }

    private void createAndShowGUI() {

        // Top panel for navigation
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new MigLayout("inset 0","grow"));
        JButton prevButton = new JButton("<");
        JButton nextButton = new JButton(">");
        monthLabel = new JLabel("", SwingConstants.CENTER);
        monthLabel.setFont(new Font("Arial", Font.BOLD, 30));
        updateMonthLabel();

        prevButton.addActionListener(e -> navigateMonth(-1));
        nextButton.addActionListener(e -> navigateMonth(1));

        topPanel.add(prevButton,"align left");
        topPanel.add(monthLabel,"align center");
        topPanel.add(nextButton,"align right");

        // Calendar panel
        calendarPanel = new JPanel();
        calendarPanel.setLayout(new GridLayout(0, 7)); // 7 columns for days of the week
        populateCalendar();

        this.add(topPanel, BorderLayout.NORTH);
        this.add(calendarPanel, BorderLayout.CENTER);

    }

    private void updateMonthLabel() {
        monthLabel.setText(currentDate.getMonth() + " " + currentDate.getYear());
    }

    private void navigateMonth(int increment) {
        currentDate = currentDate.plusMonths(increment);
        updateMonthLabel();
        populateCalendar();
    }

    private void populateCalendar() {
        calendarPanel.removeAll();

        // Day names
        String[] days = {"lu.", "ma.", "mie.", "joi", "vi.", "sâ", "du"};
        for (String day : days) {
            JLabel label = new JLabel(day, SwingConstants.CENTER);
            label.setFont(new Font("Arial", Font.BOLD, 14));
            calendarPanel.add(label);
        }

        // Calculate the first day and total days of the month
        YearMonth yearMonth = YearMonth.of(currentDate.getYear(), currentDate.getMonth());
        LocalDate firstDayOfMonth = yearMonth.atDay(1);
        int totalDays = yearMonth.lengthOfMonth();
        int startDayOfWeek = firstDayOfMonth.getDayOfWeek().getValue(); // 0 = Sunday
        System.out.println(startDayOfWeek);
        // Blank spaces for days before the first day of the month
        for (int i = 1; i < startDayOfWeek; i++) {
            calendarPanel.add(new JLabel());
        }
        // Add day buttons
        for (int day = 1; day <= totalDays; day++) {
            JButton dayButton = new JButton(String.valueOf(day));
            dayButton.setFont(new Font("Arial", Font.PLAIN, 16));
            dayButton.setBorder(new LineBorder(Color.LIGHT_GRAY));

            LocalDate today=LocalDate.now();
            if(day==today.getDayOfMonth() && today.getMonth()==yearMonth.getMonth() && today.getYear()==yearMonth.getYear()) {
                dayButton.setBackground(Color.CYAN);
            }
            else dayButton.setBackground(Color.WHITE);

            LocalDate selectedDate = firstDayOfMonth.plusDays(day -1);
            int finalDay = day;
            dayButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFrame frame = new JFrame();
                    frame.setMinimumSize(new Dimension(400, 500));
                    frame.setLocationRelativeTo(Calendar.this);

                    String []zileleSaptamanii={"Luni","Marti","Miercuri","Joi","Vineri","Sâmbătă","Duminica"};
                    String ziuaCurenta=zileleSaptamanii[selectedDate.getDayOfWeek().getValue()-1];
                    if(finalDay==today.getDayOfMonth() && today.getMonth()==yearMonth.getMonth() && today.getYear()==yearMonth.getYear()) {
                        frame.setTitle("Azi");
                    }
                    else frame.setTitle(ziuaCurenta+" "+ finalDay);


                    CreateDialogUI(frame,ziuaCurenta);
                    frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                    frame.setVisible(true);
                }
            });

            calendarPanel.add(dayButton);
        }

        // Fill remaining cells with blank labels to maintain the grid structure
        int remainingCells = 42 - (startDayOfWeek + totalDays); // 42 = 7 days * 6 weeks
        for (int i = 0; i < remainingCells; i++) {
            calendarPanel.add(new JLabel());
        }

        calendarPanel.revalidate();
        calendarPanel.repaint();
    }

    public void CreateDialogUI(JFrame frame,String ziuaCurenta) {
        frame.setLayout(new BorderLayout());
        JPanel schedulePanel=new JPanel();
        schedulePanel.setLayout(new MigLayout("wrap,inset 5","","[]10[]"));

        boolean isDialogEmpty=true;
        SimpleDateFormat df = new SimpleDateFormat(" HH:mm");
        List<String> data=new ArrayList<>();
        try{
            String url="jdbc:mysql://139.144.67.202:3306/lms?user=lms&password=WHlQjrrRDs5t";
            Connection conn= DriverManager.getConnection(url);


            PreparedStatement stmt=conn.prepareStatement("SELECT ziua,ora,Nume,'Curs'  from curs join nota on curs.idDisciplina=nota.idDisciplina and curs.idProfesor=nota.idProfesor join disciplina on curs.idDisciplina = disciplina.idDisciplina\n" +
                    "                                 where idStudent=(SELECT idStudent from student where Username=?) UNION  ALL\n" +
                    "SELECT ziua,ora,Nume,'Seminar' from seminar join nota on seminar.idDisciplina=nota.idDisciplina and seminar.idProfesor=nota.idProfesor join disciplina on seminar.idDisciplina = disciplina.idDisciplina\n" +
                    "                                where idStudent=(SELECT idStudent from student where Username=?) UNION ALL\n" +
                    "SELECT ziua,ora,Nume,'Laborator' from laborator join nota on laborator.idDisciplina=nota.idDisciplina and laborator.idProfesor=nota.idProfesor join disciplina on laborator.idDisciplina = disciplina.idDisciplina\n" +
                    "                                where idStudent=(SELECT idStudent from student where Username=?)\n" +
                    "ORDER BY ora;");
            stmt.setString(1,user.getUsername());
            stmt.setString(2,user.getUsername());
            stmt.setString(3,user.getUsername());
            ResultSet rs=stmt.executeQuery();
            while(rs.next()) {
                if(rs.getString("ziua").equals(ziuaCurenta)){
                    Timestamp date = rs.getTimestamp("Ora");
                    String dateString=df.format(date);
                    JLabel txt=new JLabel(rs.getString(4)+" de "+rs.getString("Nume")+" la ora "+dateString);
                    data.add(txt.getText());
                    txt.setFont(new Font("Arial", Font.PLAIN, 16));
                    schedulePanel.add(txt);
                    isDialogEmpty=false;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        JPanel butonsPanel=new JPanel(new MigLayout("inset 5","[left,grow][right]"));

        JButton adaugare=new JButton("Adauga");
        adaugare.setBackground(Color.DARK_GRAY);
        adaugare.setForeground(Color.WHITE);
        butonsPanel.add(adaugare);
        adaugare.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JDialog adaugareDialog = new JDialog();
                adaugareDialog.setLayout(new MigLayout("wrap, inset 5","[]10[]"));
                adaugareDialog.setMinimumSize(new Dimension(200, 300));
                adaugareDialog.setLocationRelativeTo(frame);
                adaugareDialog.setModal(true);
                adaugareDialog.setTitle("Adauga");
                //TODO:in functie daca esti prof sau nu.Proful poate introduce cursuri,seminari,laburi in calendar pe cand elevul doar activitati
                //TODO:trebuie complet separate nici query nu e la fel
                JComboBox<String> activityType=new JComboBox<>();
                activityType.addItem("Curs");
                activityType.addItem("Laborator");
                activityType.addItem("Seminar");
                activityType.addItem("Activitate de grup");

                JComboBox<String> disciplina=new JComboBox<>();
                try {
                    String url = "jdbc:mysql://139.144.67.202:3306/lms?user=lms&password=WHlQjrrRDs5t";
                    Connection conn = DriverManager.getConnection(url);

                    PreparedStatement stmt=conn.prepareStatement("SELECT disciplina.Nume from  disciplina join nota using (idDisciplina) where idStudent=(SELECT idStudent from student where Username=?);");
                    stmt.setString(1,user.getUsername());

                    ResultSet rs=stmt.executeQuery();
                    while(rs.next()) {
                        disciplina.addItem(rs.getString("Nume"));
                    }
                    conn.close();
                }
                catch (Exception ex){
                    ex.printStackTrace();
                }

                JTextField activityHour = new JTextField();
                JPanel l = FunctiiUtile.creareText(activityHour,"Ora:  ","HH:MM");
                activityHour.setFont(new Font("Arial", Font.PLAIN, 15));
                try{
                    verificareOra(activityHour.getText(),activityHour);
                    String url = "jdbc:mysql://139.144.67.202:3306/lms?user=lms&password=WHlQjrrRDs5t";
                    Connection conn = DriverManager.getConnection(url);
                    PreparedStatement stmt=conn.prepareStatement("");
                }catch (IOException | SQLException io){
                    io.printStackTrace();
                }
                String width="w 170!";
                adaugareDialog.add(activityType,width);
                adaugareDialog.add(disciplina,width);
                adaugareDialog.add(l,width);
                adaugareDialog.setVisible(true);

            }
        });

        if(!isDialogEmpty){

            JButton downloadButton = new JButton("Descarcare");
            downloadButton.setBackground(Color.DARK_GRAY);
            downloadButton.setForeground(Color.WHITE);
            butonsPanel.add(downloadButton);

            downloadButton.addActionListener(e -> {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Salveaza Orar");
                fileChooser.setSelectedFile(new File("Orar.txt"));

                int userSelection = fileChooser.showSaveDialog(null);
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File fileToSave = fileChooser.getSelectedFile();
                    exportToCSV(fileToSave, data);
                }
            });
        }

        frame.add(schedulePanel,BorderLayout.NORTH);
        frame.add(butonsPanel,BorderLayout.SOUTH);
    }


    private void exportToCSV(File file, List<String> data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            // Scrie datele tabelului
            for (int i = 0; i < data.size(); i++) {
                    writer.write(data.get(i));
                writer.newLine();
            }


        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Eroare la salvarea programului pe ziua aleasa!", "Eroare", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void verificareOra(String s,JTextField text)throws IOException{
            if (s.equals("HH:MM")){
                Color lightRed = new Color(255, 102, 102);
                text.setBackground(lightRed);
                throw new IOException("Ora invalida!");
            }
            String regex = "/^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$/";
            if(!s.matches(regex)) {
                Color lightRed = new Color(255, 102, 102);
                text.setBackground(lightRed);
                throw new IOException("Ora invalida!");
            }
            text.setBackground(Color.WHITE);
    }
}
