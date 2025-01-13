import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PaginaHomeStudent extends JPanel {
    private Student student;
    private JPanel panel;
    public  JPanel InterfataInscriere(){
        panel = new JPanel();
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
    public JPanel InterfataVizualizareNote(){
        String[] numeColoane = {"Materie", "Nota Curs", "Nota Seminar", "Nota Laborator", "Nota Finala"};

        // Creăm modelul tabelului (inițial gol)
        DefaultTableModel tableModel = new DefaultTableModel(numeColoane, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable catalogTable = new JTable(tableModel);
        catalogTable.setFillsViewportHeight(true);
        catalogTable.setPreferredScrollableViewportSize(new Dimension(400, 200));
        //catalogTable.setRowHeight(18); // Setează înălțimea rândului

        // Adăugăm tabelul într-un JScrollPane pentru scroll
        JScrollPane scrollPane = new JScrollPane(catalogTable);

        // Panel pentru afișare
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);
        try {

            ResultSet rs=student.VizualizareNote();
            DefaultTableModel model = (DefaultTableModel) catalogTable.getModel();
            model.setRowCount(0);
            while (rs.next()){
                model.addRow(new Object[]{rs.getString("Nume"), rs.getString("notaCurs"), rs.getString("notaSeminar"),  rs.getString("notaLaborator"), rs.getString("notaFinala")});
            }
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null,
                    "Studentul nu este inscris la nici o disciplina");
        }

        return panel;
    }
    public JPanel InterfataVizualizareGrupuri(){
        panel = new JPanel();
        panel.setLayout(new MigLayout("wrap, inset 0,center","[center]","[]10[]10[]"));
        List<JButton> groups=new ArrayList<>();
        List<Integer> groupsID=new ArrayList<>();
        try{
            String url="jdbc:mysql://139.144.67.202:3306/lms?user=lms&password=WHlQjrrRDs5t";
            Connection conn= DriverManager.getConnection(url);

            PreparedStatement stmt=conn.prepareStatement("SELECT  distinct  numeGrup,idGrupStudiu from grupstudiu join mesaj using (idGrupStudiu) where mesaj.idStudent=(select idStudent from student where Username=?) and mesaj='';");
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
            b.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                        JFrame chatFrame=new JFrame();
                        chatFrame.setTitle(b.getText());
                        chatFrame.setMinimumSize(new Dimension(438, 707));
                        int idGrupStudiu=groupsID.get(groups.indexOf(b));
                        chatFrame.add(new ChatWindow(student,idGrupStudiu,chatFrame,PaginaHomeStudent.this));
                }
            });
        }
        JButton inscriereGrup=new JButton("Inscrire la un nou grup");
        inscriereGrup.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(panel!=null){
                    PaginaHomeStudent.this.remove(panel);
                }
                PaginaHomeStudent.this.add(InterfataInscriereGrupuri(),BorderLayout.CENTER);
                PaginaHomeStudent.this.revalidate();
                PaginaHomeStudent.this.repaint();

            }
        });
        panel.add(inscriereGrup);
        return panel;
    }
    public JPanel InterfataInscriereGrupuri(){
        panel=new JPanel();

        JPanel sugestiiPanel=new JPanel();
        sugestiiPanel.setLayout(new MigLayout("wrap, inset 0","","[]10[]")); // Vertical stacking of labels
        sugestiiPanel.setBorder(BorderFactory.createTitledBorder("Grupuri la care te poti inscrie"));
        sugestiiPanel.setOpaque(true);
        try {
            String url = "jdbc:mysql://139.144.67.202:3306/lms?user=lms&password=WHlQjrrRDs5t";
            Connection conn = DriverManager.getConnection(url);
            PreparedStatement stmt = conn.prepareStatement("SELECT grupstudiu.numeGrup,disciplina.Nume from grupstudiu join disciplina using(idDisciplina) where idGrupStudiu not in\n" +
                    "(SELECT distinct idGrupStudiu from mesaj  where mesaj.idStudent= (SELECT idStudent from student where Username=?) and mesaj='') and disciplina.idDisciplina in (SELECT idDisciplina from nota where nota.idStudent=(SELECT idStudent from student where Username=?));");
            stmt.setString(1, student.getUsername());
            stmt.setString(2,student.getUsername());
            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) {
                JOptionPane.showMessageDialog(null, "NU exista sugestii disponibile. Incearca sa creezi unul nou.");
            } else {
                List<JPanel> rows=new ArrayList<>();
                List<String> numeGrupuri=new ArrayList<>();
                do {
                    JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                    rowPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                    sugestiiPanel.add(rowPanel);

                    JLabel nameLabel = new JLabel(
                            String.format("Nume: %s, Disciplina: %s",
                                    rs.getString("numeGrup"), rs.getString("Nume")));
                    nameLabel.setForeground(Color.BLACK);
                    nameLabel.setFont(new Font("Arial", Font.PLAIN, 15));
                    rowPanel.add(nameLabel);


                    JButton butonAcceptat = new JButton("Inscrie-te");
                    butonAcceptat.setBackground(Color.GREEN);
                    butonAcceptat.setForeground(Color.WHITE);
                    rows.add(rowPanel);
                    numeGrupuri.add(rs.getString("numeGrup"));
                    butonAcceptat.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            for(int i=0;i<rows.size();i++){
                                if(rows.get(i).getComponent(1).equals(butonAcceptat)){
                                    try {
                                       student.InscriereGrup(numeGrupuri.get(i),conn);
                                        if(rows.get(i)!=null){
                                            panel.remove(rows.get(i));
                                        }
                                        PaginaHomeStudent.this.revalidate();
                                        PaginaHomeStudent.this.repaint();

                                        JOptionPane.showMessageDialog(null,
                                                "Inscrierea a fost realizata cu succes. Accesati vizualizare grupuri pentru a continua.");
                                    } catch (SQLException ex) {
                                        ex.printStackTrace();
                                        JOptionPane.showMessageDialog(null, "NU ai reusit sa te inscrii.");
                                    }
                                }
                            }
                        }
                    });
                    rowPanel.add(butonAcceptat);
                } while (rs.next());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        JPanel cautarePanel=new JPanel();
;       cautarePanel.setLayout(new MigLayout("wrap,center"));

        JTextField searchField=new JTextField();
        JPanel l = FunctiiUtile.creareText(searchField,"Cautare grup:  ","Introduce-ti Numele");
        searchField.setFont(new Font("Arial", Font.PLAIN, 15));
        JButton searchButton=new JButton("Search");
        cautarePanel.add(l);
        cautarePanel.add(searchButton);
        panel.setLayout(new MigLayout("inset 0"));
        panel.add(sugestiiPanel,"w 50%,h 100%");
        panel.add(cautarePanel,"w 50%,h 100%");

        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String url = "jdbc:mysql://139.144.67.202:3306/lms?user=lms&password=WHlQjrrRDs5t";
                    Connection conn = DriverManager.getConnection(url);

                    PreparedStatement stmt = conn.prepareStatement("SELECT grupstudiu.numeGrup,disciplina.Nume from grupstudiu join disciplina using(idDisciplina) where idGrupStudiu not in\n" +
                            "(SELECT distinct idGrupStudiu from mesaj  where mesaj.idStudent= (SELECT idStudent from student where Username=?) and mesaj='');");
                    stmt.setString(1, student.getUsername());
                    ResultSet rs = stmt.executeQuery();

                    int ok=0;
                    while (rs.next()){
                            if(rs.getString("NumeGrup").equals(searchField.getText())){
                                student.InscriereGrup(rs.getString("NumeGrup"),conn);
                                ok=1;
                                JOptionPane.showMessageDialog(null, "Ai reusit sa te inscrii cu succes.");
                                break;
                            }
                    }
                    if(ok==0){
                        JOptionPane.showMessageDialog(null, "NU exista grupul cautat de catre tine.");
                    }
                }
                catch (Exception ex){
                    ex.printStackTrace();
                }
                searchField.setText("");
            }
        });
        return panel;
    }
    private JPanel InterfataCreareGrupuri(){
        panel = new JPanel();
        panel.setLayout(new MigLayout("wrap, inset 0,center","","[]10[]10[]"));

        JTextField numeGrup=new JTextField();
        JPanel l=FunctiiUtile.creareText(numeGrup,"Nume Grup:","Introduce-ti numele grupului");
        numeGrup.setFont(new Font("Arial", Font.PLAIN, 15));
        panel.add(l,"w 280,h ::40");

        JComboBox<String> c=new JComboBox<>();
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



        JButton b=new JButton("Create");
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    student.CreareeGrup(c.getSelectedItem().toString(),numeGrup.getText());
                    JOptionPane.showMessageDialog(null,
                            "Utilizatorul " + student.getNume()+ " " +student.getPrenume()  + " a reusit sa creeze grupul de studiu la "+c.getSelectedItem().toString()+ ".");
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null,
                            " Grupul de studiu nu a reusit sa se creeze.");
                }
            }
        });
        panel.add(b);

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

        JButton butonBack = new JButton("Log Out");
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

        Dimension buttonSize = new Dimension(150, 27);


        JButton inscriere=new JButton("Inscriere");//buton inscriere
        inscriere.setBackground(Color.DARK_GRAY);
        inscriere.setForeground(Color.WHITE);
        inscriere.setMinimumSize(buttonSize);
        inscriere.setPreferredSize(buttonSize);
        inscriere.setMaximumSize(buttonSize);
        inscriere.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(panel!=null){
                    PaginaHomeStudent.this.remove(panel);
                }
                PaginaHomeStudent.this.add(InterfataInscriere(),BorderLayout.CENTER);
                PaginaHomeStudent.this.revalidate();
                PaginaHomeStudent.this.repaint();
            }
        });

        JButton vizualizareNote=new JButton("Vizualizare Note");//buton vizulizare note
        vizualizareNote.setBackground(Color.DARK_GRAY);
        vizualizareNote.setForeground(Color.WHITE);
        vizualizareNote.setMinimumSize(buttonSize);
        vizualizareNote.setPreferredSize(buttonSize);
        vizualizareNote.setMaximumSize(buttonSize);
        vizualizareNote.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(panel!=null){
                    PaginaHomeStudent.this.remove(panel);
                }
                PaginaHomeStudent.this.add(InterfataVizualizareNote(),BorderLayout.CENTER);
                PaginaHomeStudent.this.revalidate();
                PaginaHomeStudent.this.repaint();
            }
        });

        JButton vizualizareGrupuri =new JButton("Vizualizare Grupuri");//buton vizulizare grupuri
        vizualizareGrupuri.setBackground(Color.DARK_GRAY);
        vizualizareGrupuri.setForeground(Color.WHITE);
        vizualizareGrupuri.setMinimumSize(buttonSize);
        vizualizareGrupuri.setPreferredSize(buttonSize);
        vizualizareGrupuri.setMaximumSize(buttonSize);
        vizualizareGrupuri.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(panel!=null){
                    PaginaHomeStudent.this.remove(panel);
                }
                PaginaHomeStudent.this.add(InterfataVizualizareGrupuri(),BorderLayout.CENTER);
                PaginaHomeStudent.this.revalidate();
                PaginaHomeStudent.this.repaint();
            }
        });

        JButton creareGrupuri =new JButton("Creare Grup");//buton creare grup
        creareGrupuri.setBackground(Color.DARK_GRAY);
        creareGrupuri.setForeground(Color.WHITE);
        creareGrupuri.setMinimumSize(buttonSize);
        creareGrupuri.setPreferredSize(buttonSize);
        creareGrupuri.setMaximumSize(buttonSize);
        creareGrupuri.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(panel!=null){
                    PaginaHomeStudent.this.remove(panel);
                }
                PaginaHomeStudent.this.add(InterfataCreareGrupuri(),BorderLayout.CENTER);
                PaginaHomeStudent.this.revalidate();
                PaginaHomeStudent.this.repaint();
            }
        });

        JButton orar=new JButton("Vizualizare Orar");
        orar.setBackground(Color.DARK_GRAY);
        orar.setForeground(Color.WHITE);
        orar.setMinimumSize(buttonSize);
        orar.setPreferredSize(buttonSize);
        orar.setMaximumSize(buttonSize);
        orar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(panel!=null){
                    PaginaHomeStudent.this.remove(panel);
                }
                panel=new Calendar(student);
                PaginaHomeStudent.this.add(panel,BorderLayout.CENTER);
                //PaginaHomeStudent.this.setBorder(BorderFactory.createTitledBorder("Grupuri la care te poti inscrie"));
                PaginaHomeStudent.this.revalidate();
                PaginaHomeStudent.this.repaint();
            }
        });

        JPanel butonsPanel=new JPanel();
        butonsPanel.setLayout(new BoxLayout(butonsPanel,BoxLayout.Y_AXIS));
        butonsPanel.setOpaque(false);

        butonsPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        butonsPanel.add(inscriere);
        butonsPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        butonsPanel.add(vizualizareNote);
        butonsPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        butonsPanel.add(creareGrupuri);
        butonsPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        butonsPanel.add(vizualizareGrupuri);
        butonsPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        butonsPanel.add(orar);
        this.add(butonsPanel, BorderLayout.EAST);
    }
}
