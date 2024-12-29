import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PaginaHomeStudent extends JPanel {
    private Student student;

    private  JPanel InterfataInscriere(){
        JPanel panel = new JPanel();
        JComboBox<String> c=new JComboBox<>();
        JButton b=new JButton("Submit");
        b.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try {
                    student.InscriereStudent(Objects.requireNonNull(c.getSelectedItem()).toString());
                    JOptionPane.showMessageDialog(null,
                            "Utilizatorul " + student.getNume()+ " " +student.getPrenume()  + " a reusit sa se inscrie la "+c.getSelectedItem().toString()+ ".");
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null,
                            "Nu exista profesor la cursul de "+c.getSelectedItem().toString()+ ".");
                }
            }
        });
        try{
            String url="jdbc:mysql://139.144.67.202:3306/lms?user=lms&password=WHlQjrrRDs5t";
            Connection conn= DriverManager.getConnection(url);

            PreparedStatement stmt=conn.prepareStatement("select Nume from disciplina");
            ResultSet rs=stmt.executeQuery();

            while(rs.next()){
                c.addItem(rs.getString(1));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        panel.add(c);
        panel.add(b);
        return panel;
    }
    private JPanel InterfataVizualizareNote(){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Vertical stacking of labels
        panel.setBorder(BorderFactory.createTitledBorder("Notele la toate disciplinele"));
        panel.setOpaque(true);
        try {
            ResultSet rs=student.VizualizareNote();
            while (rs.next()){
                JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                rowPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                panel.add(rowPanel);
                JLabel nameLabel = new JLabel(
                        String.format("%s, %s, %s, %s, %s",
                                rs.getString("Nume"), rs.getString("notaSeminar"),rs.getString("notaCurs"),
                                rs.getString("notaLaborator"),rs.getString("notaFinala")));
                nameLabel.setForeground(Color.BLACK);
                nameLabel.setFont(new Font("Arial", Font.PLAIN, 15));
                rowPanel.add(nameLabel);
            }
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null,
                    "Studentul nu este inscris la nici o disciplina");
        }

        return panel;
    }
    private JPanel InterfataVizualizareGrupuri(){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        List<JButton> groups=new ArrayList<>();
        List<Integer> groupsID=new ArrayList<>();
        try{
            String url="jdbc:mysql://139.144.67.202:3306/lms?user=lms&password=WHlQjrrRDs5t";
            Connection conn= DriverManager.getConnection(url);

            PreparedStatement stmt=conn.prepareStatement("SELECT  numeGrup,idGrupStudiu from grupstudiu join `grup/student` using (idGrupStudiu) where idStudent=(select idStudent from student where Username=?);");
            stmt.setString(1,student.getUsername());
            ResultSet rs=stmt.executeQuery();
            while(rs.next()){
                groups.add(new JButton(rs.getString(1)));
                groupsID.add(rs.getInt(2));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        for(JButton b:groups){
            //creare butoane pentru a te conecta unui anumit grup din cele la care esti inscris
            b.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(b);
            panel.add(Box.createVerticalStrut(10));
            b.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                        JFrame chatFrame=new JFrame();
                        chatFrame.setTitle(b.getText());
                        chatFrame.setMinimumSize(new Dimension(438, 707));
                        chatFrame.add(new ChatWindow(student,groupsID.get(groups.indexOf(b)),chatFrame));
                        chatFrame.setVisible(true);

                }
            });
        }
        return panel;
    }
    public PaginaHomeStudent(JFrame frame, Student student ) {

        this.student = student;
        this.setLayout(new BorderLayout());

        JButton profile = new JButtonCircle("Profil");
        profile.setBackground(Color.DARK_GRAY);
        profile.setForeground(Color.WHITE);

        JPanel topRightPanel = new JPanel();
        topRightPanel.setLayout(new FlowLayout(FlowLayout.RIGHT)); // Align content to the right
        topRightPanel.setOpaque(false); // Transparent background
        topRightPanel.add(profile);

        JButton butonBack = new JButton("Înapoi");
        butonBack.setBackground(Color.DARK_GRAY);
        butonBack.setForeground(Color.WHITE);

        JPanel topLeftPanel = new JPanel();
        topLeftPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); // Align content to the left
        topLeftPanel.setOpaque(false); // Transparent background
        topLeftPanel.add(butonBack);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false); // Transparent background
        topPanel.add(topLeftPanel, BorderLayout.WEST); // Left-aligned panel
        topPanel.add(topRightPanel, BorderLayout.EAST);

        this.add(topPanel, BorderLayout.NORTH);

        butonBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                frame.setTitle("PaginaInitiala");
                frame.getContentPane().add(new PaginaInitiala(frame));
                frame.revalidate();
                frame.repaint();
            }
        });

        profile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JPanel userDataPanel = new JPanel();
                topRightPanel.removeAll();
                userDataPanel.setLayout(new BoxLayout(userDataPanel, BoxLayout.Y_AXIS));

                userDataPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
                userDataPanel.setOpaque(true);

                JButton butonProfilX = new JButtonCircle("X");
                butonProfilX.setBackground(Color.DARK_GRAY);
                butonProfilX.setForeground(Color.WHITE);

                butonProfilX.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        frame.getContentPane().removeAll();
                        frame.setTitle("PaginaHome");
                        frame.getContentPane().add(new PaginaHomeStudent(frame, student));
                        frame.revalidate();
                        frame.repaint();
                    }
                });        /// cand apasam butonul de X din label-ul Profil


                JLabel userNameLabel = new JLabel("User: " + student.getUsername());
                userNameLabel.setForeground(Color.black);
                userNameLabel.setFont(new Font("Arial", Font.PLAIN, 15));

                JLabel numeLabel = new JLabel("Nume: " + student.getNume());
                numeLabel.setForeground(Color.black);
                numeLabel.setFont(new Font("Arial", Font.PLAIN, 15));

                JLabel prenumeLabel = new JLabel("Prenume: " + student.getPrenume());
                prenumeLabel.setForeground(Color.black);
                prenumeLabel.setFont(new Font("Arial", Font.PLAIN, 15));

                JLabel CNPJLabel = new JLabel("CNP: " + student.getCNP());
                CNPJLabel.setForeground(Color.black);
                CNPJLabel.setFont(new Font("Arial", Font.PLAIN, 15));

                JLabel emailLabel = new JLabel("Email: " + student.getEmail());
                emailLabel.setForeground(Color.black);
                emailLabel.setFont(new Font("Arial", Font.PLAIN, 15));

                JLabel adresaLabel = new JLabel("Adresa: " + student.getAdresa());
                adresaLabel.setForeground(Color.black);
                adresaLabel.setFont(new Font("Arial", Font.PLAIN, 15));

                JLabel nrTelefonLabel = new JLabel("Nr Telefon: " + student.getNumarTelefon());
                nrTelefonLabel.setForeground(Color.black);
                nrTelefonLabel.setFont(new Font("Arial", Font.PLAIN, 15));

                JLabel nrContractLabel = new JLabel("Nr Contract: " + student.getNrContract());
                nrContractLabel.setForeground(Color.black);
                nrContractLabel.setFont(new Font("Arial", Font.PLAIN, 15));



                userDataPanel.add(butonProfilX);
                userDataPanel.add(userNameLabel);
                userDataPanel.add(numeLabel);
                userDataPanel.add(prenumeLabel);
                userDataPanel.add(CNPJLabel);
                userDataPanel.add(emailLabel);
                userDataPanel.add(adresaLabel);
                userDataPanel.add(nrTelefonLabel);
                userDataPanel.add(nrContractLabel);


                topRightPanel.add(userDataPanel); // Replace the button with the user data
                topRightPanel.revalidate();
                topRightPanel.repaint();
            }
        });
        //pana aici este chestia de profile




        JButton inscriere=new JButton("Inscriere");//buton inscriere
        inscriere.setBackground(Color.DARK_GRAY);
        inscriere.setForeground(Color.WHITE);
        inscriere.addActionListener(new ActionListener() {
            private int clicks=0;
            public void actionPerformed(ActionEvent e) {
                if(clicks%2==0){
                    PaginaHomeStudent.this.add(InterfataInscriere(),BorderLayout.CENTER);
                    PaginaHomeStudent.this.revalidate();
                    PaginaHomeStudent.this.repaint();
                    clicks++;
                }
                else{
                    frame.getContentPane().removeAll();
                    frame.setTitle("PaginaHome");
                    frame.getContentPane().add(new PaginaHomeStudent(frame, student));
                    frame.revalidate();
                    frame.repaint();
                    clicks++;
                }
            }
        });

        JButton vizualizareNote=new JButton("Vizualizare Note");//buton vizulizare note
        vizualizareNote.setBackground(Color.DARK_GRAY);
        vizualizareNote.setForeground(Color.WHITE);
        vizualizareNote.addActionListener(new ActionListener() {
            private int clicks=0;
            public void actionPerformed(ActionEvent e) {
                if(clicks%2==0){
                    PaginaHomeStudent.this.add(InterfataVizualizareNote(),BorderLayout.CENTER);
                    PaginaHomeStudent.this.revalidate();
                    PaginaHomeStudent.this.repaint();
                    clicks++;
                }
                else{
                    frame.getContentPane().removeAll();
                    frame.setTitle("PaginaHome");
                    frame.getContentPane().add(new PaginaHomeStudent(frame, student));
                    frame.revalidate();
                    frame.repaint();
                    clicks++;
                }
            }
        });

        JButton vizualizareGrupuri =new JButton("Vizualizare Grupuri");//buton vizulizare grupuri
        vizualizareGrupuri.setBackground(Color.DARK_GRAY);
        vizualizareGrupuri.setForeground(Color.WHITE);
        vizualizareGrupuri.addActionListener(new ActionListener() {
            private int clicks=0;
            public void actionPerformed(ActionEvent e) {
                if(clicks%2==0){
                    PaginaHomeStudent.this.add(InterfataVizualizareGrupuri(),BorderLayout.CENTER);
                    PaginaHomeStudent.this.revalidate();
                    PaginaHomeStudent.this.repaint();
                    clicks++;
                }
                else{
                    frame.getContentPane().removeAll();
                    frame.setTitle("PaginaHome");
                    frame.getContentPane().add(new PaginaHomeStudent(frame, student));
                    frame.revalidate();
                    frame.repaint();
                    clicks++;
                }
            }
        });


        JPanel butonsPanel=new JPanel();
        butonsPanel.setLayout(new BoxLayout(butonsPanel,BoxLayout.Y_AXIS));
        butonsPanel.add(inscriere);
        butonsPanel.add(vizualizareNote);
        butonsPanel.add(vizualizareGrupuri);
        this.add(butonsPanel, BorderLayout.EAST);
    }
}
