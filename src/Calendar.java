import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
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

                    if(user instanceof Profesor){
                        CreateDialogUIProf(frame,ziuaCurenta);
                    }
                    else{
                        CreateDialogUIStudent(frame,ziuaCurenta);
                    }
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

    public void CreateDialogUIProf(JFrame frame,String ziuaCurenta) {
        frame.setLayout(new BorderLayout());
        JPanel schedulePanel=new JPanel();
        schedulePanel.setLayout(new MigLayout("wrap,inset 5","","[]10[]"));

        boolean isDialogEmpty=true;
        SimpleDateFormat df = new SimpleDateFormat(" HH:mm");

        String[] numeColoane = {"Tip", "Disciplina ", "Ora"};

        // Creăm modelul tabelului (inițial gol)
        DefaultTableModel tableModel = new DefaultTableModel(numeColoane, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable dataTable = new JTable(tableModel);
        dataTable.setFillsViewportHeight(true);
        dataTable.setPreferredScrollableViewportSize(new Dimension(400, 200));

        // Adăugăm tabelul într-un JScrollPane pentru scroll
        JScrollPane scrollPane = new JScrollPane(dataTable);
        schedulePanel.add(scrollPane);

        try{
            String url="jdbc:mysql://139.144.67.202:3306/lms?user=lms&password=WHlQjrrRDs5t";
            Connection conn= DriverManager.getConnection(url);


            PreparedStatement stmt=conn.prepareStatement("SELECT curs.ziua,curs.ora,disciplina.Nume,'Curs' as tip from curs join disciplina using (idDisciplina) where idProfesor=(SELECT idProfesor from profesor where Username=?) UNION ALL\n" +
                    "SELECT seminar.ziua,seminar.ora,disciplina.Nume,'Seminar' as tip from seminar join disciplina using (idDisciplina) where idProfesor=(SELECT idProfesor from profesor where Username=?) UNION ALL\n" +
                    "SELECT laborator.ziua,laborator.ora,disciplina.Nume,'Laborator' as tip from laborator join disciplina using (idDisciplina) where idProfesor=(SELECT idProfesor from profesor where Username=?)\n" +
                    "ORDER BY ora;");
            stmt.setString(1,user.getUsername());
            stmt.setString(2,user.getUsername());
            stmt.setString(3,user.getUsername());
            ResultSet rs=stmt.executeQuery();

            DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
            model.setRowCount(0);
            while(rs.next()) {
                if(rs.getString("ziua").equals(ziuaCurenta)){
                    Timestamp date = rs.getTimestamp("Ora");
                    String dateString=df.format(date);

                    model.addRow(new Object[]{rs.getString(4), rs.getString("Nume"), dateString});

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
                adaugareDialog.setLayout(new MigLayout("wrap, inset 5","","[]10[][grow,bottom]"));
                adaugareDialog.setMinimumSize(new Dimension(200, 220));
                adaugareDialog.setLocationRelativeTo(frame);
                adaugareDialog.setModal(true);
                adaugareDialog.setTitle("Adauga");

                JComboBox<String> activityType=new JComboBox<>();
                JComboBox<String> disciplina=new JComboBox<>();
                Map<String,List<String>> types=new HashMap<>();

                try {
                    String url = "jdbc:mysql://139.144.67.202:3306/lms?user=lms&password=WHlQjrrRDs5t";
                    Connection conn = DriverManager.getConnection(url);

                    PreparedStatement stmt=conn.prepareStatement("SELECT Nume, GROUP_CONCAT(DISTINCT activity_type ORDER BY activity_type SEPARATOR ', ') AS types FROM ( SELECT disciplina.Nume, 'Curs' AS activity_type FROM disciplina JOIN curs ON disciplina.idDisciplina = curs.idDisciplina WHERE curs.idProfesor = (SELECT idProfesor FROM profesor WHERE Username = ?) UNION ALL\n" +
                            "SELECT disciplina.Nume, 'Seminar' AS activity_type FROM disciplina JOIN seminar ON disciplina.idDisciplina = seminar.idDisciplina WHERE seminar.idProfesor = (SELECT idProfesor FROM profesor WHERE Username = ?) UNION ALL\n" +
                            "SELECT disciplina.Nume, 'Laborator' AS activity_type FROM disciplina JOIN laborator ON disciplina.idDisciplina = laborator.idDisciplina WHERE laborator.idProfesor = (SELECT idProfesor FROM profesor WHERE Username = ?) ) AS combined\n" +
                            "GROUP BY Nume;");
                    stmt.setString(1,user.getUsername());
                    stmt.setString(2,user.getUsername());
                    stmt.setString(3,user.getUsername());

                    ResultSet rs=stmt.executeQuery();
                    while(rs.next()) {
                        disciplina.addItem(rs.getString("Nume"));
                        types.put(rs.getString("Nume"), List.of(rs.getString("types").split(",")));
                    }
                    conn.close();
                }
                catch (Exception ex){
                    ex.printStackTrace();
                }
                activityType.removeAllItems();
                List<String> selectedTypes=new ArrayList<>(types.get(disciplina.getItemAt(0)));
                for(int i=0;i<selectedTypes.size();i++){
                    activityType.addItem(selectedTypes.get(i));
                }
                disciplina.addItemListener(new ItemListener() {
                    public void itemStateChanged(ItemEvent e) {
                        if(e.getStateChange()==ItemEvent.SELECTED){
                            activityType.removeAllItems();
                            String selectedItem=(String) disciplina.getSelectedItem();
                            List<String> selectedTypes=new ArrayList<>(types.get(selectedItem));
                            for(int i=0;i<selectedTypes.size();i++){
                                activityType.addItem(selectedTypes.get(i));
                            }
                        }
                    }
                });

                adaugareDialog.add(disciplina,"w 170!");
                adaugareDialog.add(activityType,"w 170!");
                HourUISubmit(frame,adaugareDialog,activityType,disciplina,ziuaCurenta);

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
                fileChooser.setSelectedFile(new File("Orar.csv"));

                int userSelection = fileChooser.showSaveDialog(null);
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File fileToSave = fileChooser.getSelectedFile();
                    exportToCSV(fileToSave,dataTable );
                }
            });
        }

        frame.add(schedulePanel,BorderLayout.NORTH);
        frame.add(butonsPanel,BorderLayout.SOUTH);
    }
    private void CreateDialogUIStudent(JFrame frame,String ziuaCurenta){
        frame.setLayout(new BorderLayout());
        JPanel schedulePanel=new JPanel();
        schedulePanel.setLayout(new MigLayout("wrap,inset 5","","[]10[]"));

        boolean isDialogEmpty=true;
        SimpleDateFormat df = new SimpleDateFormat(" HH:mm");

        String[] numeColoane = {"Tip", "Disciplina ", "Ora"};

        // Creăm modelul tabelului (inițial gol)
        DefaultTableModel tableModel = new DefaultTableModel(numeColoane, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable dataTable = new JTable(tableModel);
        dataTable.setFillsViewportHeight(true);
        dataTable.setPreferredScrollableViewportSize(new Dimension(400, 200));

        // Adăugăm tabelul într-un JScrollPane pentru scroll
        JScrollPane scrollPane = new JScrollPane(dataTable);
        schedulePanel.add(scrollPane);

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

            DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
            model.setRowCount(0);
            while(rs.next()) {
                if(rs.getString("ziua").equals(ziuaCurenta)){
                    Timestamp date = rs.getTimestamp("Ora");
                    String dateString=df.format(date);

                    model.addRow(new Object[]{rs.getString(4), rs.getString("Nume"), dateString});

                    isDialogEmpty=false;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }


        JPanel butonsPanel=new JPanel(new MigLayout("inset 5","[left,grow][right]"));

        JButton adaugare=new JButton("Adauga Activitate");
        adaugare.setBackground(Color.DARK_GRAY);
        adaugare.setForeground(Color.WHITE);
        butonsPanel.add(adaugare);
        adaugare.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JDialog adaugareDialog = new JDialog();
                adaugareDialog.setLayout(new MigLayout("wrap, inset 5","","[]10[][grow,bottom]"));
                adaugareDialog.setMinimumSize(new Dimension(270, 220));
                adaugareDialog.setLocationRelativeTo(frame);
                adaugareDialog.setModal(true);
                adaugareDialog.setTitle("Adauga Activitate");


                JComboBox<String> grupStudiu=new JComboBox<>();
                try {
                    String url = "jdbc:mysql://139.144.67.202:3306/lms?user=lms&password=WHlQjrrRDs5t";
                    Connection conn = DriverManager.getConnection(url);

                    PreparedStatement stmt=conn.prepareStatement("SELECT distinct grupstudiu.numeGrup from grupstudiu join mesaj using(idGrupStudiu) where mesaj.idStudent=(SELECT idStudent from student where Username=?);");
                    stmt.setString(1,user.getUsername());

                    ResultSet rs=stmt.executeQuery();
                    while(rs.next()) {
                        grupStudiu.addItem(rs.getString("numeGrup"));
                    }
                    conn.close();
                }
                catch (Exception ex){
                    ex.printStackTrace();
                }

                JTextField nrMinPartcipanti = new JTextField();
                JPanel l = FunctiiUtile.creareText(nrMinPartcipanti,"Participanti: ","Numarul minim");
                nrMinPartcipanti.setFont(new Font("Arial", Font.PLAIN, 15));

                JTextField responseTime = new JTextField();
                JPanel l2 = FunctiiUtile.creareText(responseTime,"Timp raspuns: ","Numar s/min/h/m/y)");
                nrMinPartcipanti.setFont(new Font("Arial", Font.PLAIN, 15));

                adaugareDialog.add(grupStudiu,"w 250!");
                adaugareDialog.add(l,"w 250!");
                adaugareDialog.add(l2,"w 250!");
                HourUISubmit(frame,adaugareDialog,null,grupStudiu,ziuaCurenta);
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
                fileChooser.setSelectedFile(new File("Orar.csv"));

                int userSelection = fileChooser.showSaveDialog(null);
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File fileToSave = fileChooser.getSelectedFile();
                    exportToCSV(fileToSave,dataTable );
                }
            });
        }

        frame.add(schedulePanel,BorderLayout.NORTH);
        frame.add(butonsPanel,BorderLayout.SOUTH);
    }
    private void HourUISubmit(JFrame frame,JDialog adaugareDialog,JComboBox<String>activtyType,JComboBox<String> disciplina,String ziuaCurenta){
        JTextField activityHour = new JTextField();
        JPanel l = FunctiiUtile.creareText(activityHour,"Ora activitati: ","HH:MM");
        activityHour.setFont(new Font("Arial", Font.PLAIN, 15));

        JButton submit=new JButton("Submit");
        submit.setBackground(Color.DARK_GRAY);
        submit.setForeground(Color.WHITE);
        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try{
                    verificareOra(activityHour.getText(),activityHour);
                    String url = "jdbc:mysql://139.144.67.202:3306/lms?user=lms&password=WHlQjrrRDs5t";
                    Connection conn = DriverManager.getConnection(url);
                    if(user instanceof Profesor){
                        PreparedStatement stmt;
                        switch (activtyType.getSelectedItem().toString()){
                            case "Curs":

                                stmt =conn.prepareStatement("UPDATE curs set ziua=?,ora=? where idProfesor=(SELECT idProfesor from profesor where Username=?) and idDisciplina=(SELECT idDisciplina from disciplina where Nume=?);");
                                stmt.setString(1,ziuaCurenta);
                                stmt.setTime(2,Time.valueOf(activityHour.getText()+":00"));
                                stmt.setString(3,user.getUsername());
                                stmt.setString(4,disciplina.getSelectedItem().toString());
                                stmt.executeUpdate();
                                break;
                            case "Seminar":
                                stmt =conn.prepareStatement("UPDATE seminar set ziua=?,ora=? where idProfesor=(SELECT idProfesor from profesor where Username=?) and idDisciplina=(SELECT idDisciplina from disciplina where Nume=?);");
                                stmt.setString(1,ziuaCurenta);
                                stmt.setTime(2,Time.valueOf(activityHour.getText()+":00"));
                                stmt.setString(3,user.getUsername());
                                stmt.setString(4,disciplina.getSelectedItem().toString());
                                stmt.executeUpdate();
                                break;
                            case "Laborator":
                                stmt =conn.prepareStatement("UPDATE laborator set ziua=?,ora=? where idProfesor=(SELECT idProfesor from profesor where Username=?) and idDisciplina=(SELECT idDisciplina from disciplina where Nume=?);");
                                stmt.setString(1,ziuaCurenta);
                                stmt.setTime(2,Time.valueOf(activityHour.getText()+":00"));
                                stmt.setString(3,user.getUsername());
                                stmt.setString(4,disciplina.getSelectedItem().toString());
                                stmt.executeUpdate();
                                break;
                            default:
                                break;
                        }
                        adaugareDialog.dispose();
                        frame.dispose();
                        JFrame frame = new JFrame();
                        frame.setMinimumSize(new Dimension(400, 500));
                        frame.setLocationRelativeTo(Calendar.this);
                        CreateDialogUIProf(frame,ziuaCurenta);
                        frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                        frame.setVisible(true);
                    }
                    else {

                        PreparedStatement stmt;
                        stmt =conn.prepareStatement("");
                        stmt.executeUpdate();

                        adaugareDialog.dispose();
                        frame.dispose();
                        JFrame frame = new JFrame();
                        frame.setMinimumSize(new Dimension(400, 500));
                        frame.setLocationRelativeTo(Calendar.this);
                        CreateDialogUIProf(frame,ziuaCurenta);
                        frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                        frame.setVisible(true);
                    }
                }catch (IOException | SQLException io){
                    io.printStackTrace();
                }
            }
        });
        if(user instanceof Profesor){
            adaugareDialog.add(l,"w 170!");
        }
        else adaugareDialog.add(l,"w 250!");
        adaugareDialog.add(submit,"align center");
    }
    private void exportToCSV(File file,JTable table) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            // Scrie header-ul tabelului
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            for (int i = 0; i < model.getColumnCount(); i++) {
                writer.write(model.getColumnName(i));
                if (i < model.getColumnCount() - 1) {
                    writer.write(",");
                }
            }
            writer.newLine();

            // Scrie datele tabelului
            for (int i = 0; i < model.getRowCount(); i++) {
                for (int j = 0; j < model.getColumnCount(); j++) {
                    writer.write(model.getValueAt(i, j).toString());
                    if (j < model.getColumnCount() - 1) {
                        writer.write(",");
                    }
                }
                writer.newLine();
            }

            System.out.println("Catalog salvat cu succes în: " + file.getAbsolutePath());

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
            String regex = "^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$";
            if(!s.matches(regex)) {
                Color lightRed = new Color(255, 102, 102);
                text.setBackground(lightRed);
                throw new IOException("Ora invalida!");
            }
            text.setBackground(Color.WHITE);
    }
    private void verificareRaspuns(String s,JTextField text)throws IOException{
        if (s.equals("HH:MM")){
            Color lightRed = new Color(255, 102, 102);
            text.setBackground(lightRed);
            throw new IOException("Ora invalida!");
        }
        String regex = "^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$";
        if(!s.matches(regex)) {
            Color lightRed = new Color(255, 102, 102);
            text.setBackground(lightRed);
            throw new IOException("Ora invalida!");
        }
        text.setBackground(Color.WHITE);
    }
}
