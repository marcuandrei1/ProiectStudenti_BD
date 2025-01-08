
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.*;
import java.sql.*;
import java.util.HashSet;
import java.util.Set;



public class PaginaHomeProfesor extends JPanel {
    private Profesor profesor;


    public  void verificareDisciplina(String s) throws IOException {
        if (s.equals("Introduce-ti disciplina!"))
            throw new IOException("Username invalid");
    }


    private void exportToCSV(File file, JTable table) {
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
            JOptionPane.showMessageDialog(null, "Eroare la salvarea catalogului!", "Eroare", JOptionPane.ERROR_MESSAGE);
        }
    }




    public PaginaHomeProfesor(JFrame frame, Profesor profesor) {

        this.profesor = profesor;
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
                        frame.getContentPane().add(new PaginaHomeProfesor(frame, profesor));
                        frame.revalidate();
                        frame.repaint();
                    }
                });        /// cand apasam butonul de X din label-ul Profil


                JLabel userNameLabel = new JLabel("User: " + profesor.getUsername());
                userNameLabel.setForeground(Color.black);
                userNameLabel.setFont(new Font("Arial", Font.PLAIN, 15));

                JLabel numeLabel = new JLabel("Nume: " + profesor.getNume());
                numeLabel.setForeground(Color.black);
                numeLabel.setFont(new Font("Arial", Font.PLAIN, 15));

                JLabel prenumeLabel = new JLabel("Prenume: " + profesor.getPrenume());
                prenumeLabel.setForeground(Color.black);
                prenumeLabel.setFont(new Font("Arial", Font.PLAIN, 15));

                JLabel CNPJLabel = new JLabel("CNP: " + profesor.getCNP());
                CNPJLabel.setForeground(Color.black);
                CNPJLabel.setFont(new Font("Arial", Font.PLAIN, 15));

                JLabel emailLabel = new JLabel("Email: " + profesor.getEmail());
                emailLabel.setForeground(Color.black);
                emailLabel.setFont(new Font("Arial", Font.PLAIN, 15));

                JLabel adresaLabel = new JLabel("Adresa: " + profesor.getAdresa());
                adresaLabel.setForeground(Color.black);
                adresaLabel.setFont(new Font("Arial", Font.PLAIN, 15));

                JLabel nrTelefonLabel = new JLabel("Nr Telefon: " + profesor.getNumarTelefon());
                nrTelefonLabel.setForeground(Color.black);
                nrTelefonLabel.setFont(new Font("Arial", Font.PLAIN, 15));

                JLabel nrContractLabel = new JLabel("Nr Contract: " + profesor.getNrContract());
                nrContractLabel.setForeground(Color.black);
                nrContractLabel.setFont(new Font("Arial", Font.PLAIN, 15));

                JLabel nrMinOreLabel = new JLabel("Nr minim de ore: " + profesor.getNrMinOre());
                nrMinOreLabel.setForeground(Color.black);
                nrMinOreLabel.setFont(new Font("Arial", Font.PLAIN, 15));

                JLabel nrMaxOreLabel = new JLabel("Nr maxim de ore: " + profesor.getNrMaxOre());
                nrMaxOreLabel.setForeground(Color.black);
                nrMaxOreLabel.setFont(new Font("Arial", Font.PLAIN, 15));

                JLabel departamentLabel = new JLabel("Departament: " + profesor.getDepartament());
                departamentLabel.setForeground(Color.black);
                departamentLabel.setFont(new Font("Arial", Font.PLAIN, 15));

                userDataPanel.add(butonProfilX);
                userDataPanel.add(userNameLabel);
                userDataPanel.add(numeLabel);
                userDataPanel.add(prenumeLabel);
                userDataPanel.add(CNPJLabel);
                userDataPanel.add(emailLabel);
                userDataPanel.add(adresaLabel);
                userDataPanel.add(nrTelefonLabel);
                userDataPanel.add(nrContractLabel);
                userDataPanel.add(nrMinOreLabel);
                userDataPanel.add(nrMaxOreLabel);
                userDataPanel.add(departamentLabel);

                topRightPanel.add(userDataPanel); // Replace the button with the user data
                topRightPanel.revalidate();
                topRightPanel.repaint();
            }
        });


        /// Un label mare in care se afla un label left
        // Middle Panel
        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.Y_AXIS));
        middlePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); /// avem padding

        /// Label-ul pentru Inscriere Curs
        JPanel borderedPanel = new JPanel();
        borderedPanel.setLayout(new BoxLayout(borderedPanel, BoxLayout.Y_AXIS));
        borderedPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY, 2), "Inscriere Curs")); // Add border with title
        borderedPanel.setMaximumSize(new Dimension(500, 100));
        borderedPanel.setBackground(Color.WHITE);
        borderedPanel.setVisible(false);
        borderedPanel.setAlignmentX(Component.LEFT_ALIGNMENT);


        /// Componentele pentru inscriereCursLabel
        JLabel inscriereCursLabel = new JLabel("Inscriere Curs");
        inscriereCursLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        inscriereCursLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        inscriereCursLabel.setVisible(false);                                           /// e ascuns

        JTextField inscriereCursField = new JTextField(10);
        inscriereCursField.setText("Introduce-ti disciplina!");
        inscriereCursField.setPreferredSize(new Dimension(400, 25));
        inscriereCursField.setMaximumSize(new Dimension(400, 25));
        inscriereCursField.setAlignmentX(Component.CENTER_ALIGNMENT);
        inscriereCursField.setVisible(false);                                           /// tot ascuns

        JButton submitButton = new JButton("Submit");
        submitButton.setBackground(Color.DARK_GRAY);
        submitButton.setForeground(Color.WHITE);
        submitButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        submitButton.setVisible(false);                                                 /// tot ascuns


        JButton inscriereCursButton = new JButton("Inscriere Curs");
        inscriereCursButton.setBackground(Color.DARK_GRAY);
        inscriereCursButton.setForeground(Color.WHITE);
        inscriereCursButton.setAlignmentX(Component.LEFT_ALIGNMENT);

        /// cand apas pe buton sa imi apara label-urile, cu TOGGLE
        inscriereCursButton.addActionListener(e -> {

            // Check if the components are already visible
            boolean isVisible = inscriereCursField.isVisible();
            if (isVisible) {
                inscriereCursField.setText("Introduce-ti disciplina!");
                inscriereCursField.setForeground(Color.BLACK);
            }
            // Toggle visibility
            inscriereCursField.setVisible(!isVisible);
            submitButton.setVisible(!isVisible); // Also toggle the submit button visibility
            borderedPanel.setVisible(!isVisible); // Toggle the bordered panel visibility

            // Revalidate and repaint the panel to update the layout
            middlePanel.revalidate();
            middlePanel.repaint();

        });

        /// adaug componentele la borderedPanel
        borderedPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Spacing
        borderedPanel.add(inscriereCursLabel);
        borderedPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Spacing
        borderedPanel.add(inscriereCursField);
        borderedPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Spacing
        borderedPanel.add(submitButton);

        /// doar inscriereCursButton e vizibil
        middlePanel.add(inscriereCursButton);
        middlePanel.add(Box.createRigidArea(new Dimension(0, 20))); // Spacing
        middlePanel.add(borderedPanel);

        this.add(middlePanel, BorderLayout.CENTER);

        inscriereCursField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {                             /// cand dau click pe field, daca textul e la fel cu placeholder-ul atunci initializam cu ""
                if (inscriereCursField.getText().equals("Introduce-ti disciplina!") || inscriereCursField.getText().equals("Disciplina invalida!") || inscriereCursField.getText().equals("Done!")) {
                    inscriereCursField.setText("");
                    inscriereCursField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {           /// daca nu am introdus nimic atunci va ramane ce e ca placeholder
                if (inscriereCursButton.getText().isEmpty()) {
                    inscriereCursField.setText("Introduce-ti disciplina!");
                    inscriereCursField.setForeground(Color.GRAY);
                }
            }
        });


        /// la apasarea tastei submit sa imi bage in baza de date conexiunea dintre profesor si disciplina

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    String url = "jdbc:mysql://139.144.67.202:3306/lms?user=lms&password=WHlQjrrRDs5t";
                    Connection conn = DriverManager.getConnection(url);


                    // Trebuie gasit id-ul profesorului meu
                    PreparedStatement stmtSelectId = conn.prepareStatement("SELECT idProfesor FROM profesor join utilizator using (username) WHERE username = ? AND password = ?");
                    stmtSelectId.setString(1, profesor.getUsername());
                    stmtSelectId.setString(2, profesor.getPassword());
                    ResultSet rezultat = stmtSelectId.executeQuery();
                    int idProfesor = 0;
                    if (rezultat.next()) {                    // verifica daca a returnat vreun rezultat query-ul
                        idProfesor = rezultat.getInt("idProfesor");
                    }

                    System.out.println(idProfesor);


                    String materie = inscriereCursField.getText();
                    int idMaterie = 0;
                    if (materie != "Introduce-ti disciplina!") {         // daca chiar s-a introdus o materie

                        // Trebuie sa gasim materia dupa id
                        PreparedStatement stmtidMaterie = conn.prepareStatement("SELECT idDisciplina FROM disciplina where Nume= ?");
                        stmtidMaterie.setString(1, materie);
                        ResultSet rezultatMaterie = stmtidMaterie.executeQuery();
                        if (rezultatMaterie.next()) {
                            idMaterie = rezultatMaterie.getInt("idDisciplina");
                            inscriereCursField.setText("Done!");
                            inscriereCursField.setForeground(Color.GREEN);
                        } else {
                            inscriereCursField.setText("Disciplina invalida!");
                            inscriereCursField.setForeground(Color.RED);
                        }
                        System.out.println(idMaterie);
                    }

                    /// Inserarea in tabela curs
                    PreparedStatement insertCursStmt = conn.prepareStatement(
                            "INSERT INTO curs (idProfesor, idDisciplina,nrMaxStudenti, Procent, `interval`, ziua, ora, numarStudenti)  " +
                                    "VALUES (?, ?,0,0,'saptamanal','luni','12:30:20',0)");
                    //insertCursStmt.setInt(1,);
                    insertCursStmt.setInt(1, idProfesor);
                    insertCursStmt.setInt(2, idMaterie);
                    insertCursStmt.executeUpdate();

                    /// Inserarea si in tabela seminar
                    PreparedStatement insertSeminarStmt = conn.prepareStatement(
                            "INSERT INTO seminar (idProfesor, idDisciplina, nrMaxStudenti, procent, `interval`, ziua, ora)  " +
                                    "VALUES (?, ?,0,0,'saptamanal','luni','12:30:20')");
                    insertSeminarStmt.setInt(1, idProfesor);
                    insertSeminarStmt.setInt(2, idMaterie);
                    insertSeminarStmt.executeUpdate();

                    /// Inserarea si in tabela seminar
                    PreparedStatement insertLabStmt = conn.prepareStatement(
                            "INSERT INTO laborator (idProfesor, idDisciplina, nrMaxStudenti, procent, `interval`, ziua, ora)  " +
                                    "VALUES (?, ?,0,0,'saptamanal','luni','12:30:20')");
                    insertLabStmt.setInt(1, idProfesor);
                    insertLabStmt.setInt(2, idMaterie);
                    insertLabStmt.executeUpdate();


                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });


        /// TODO: de setat procente (in curs procent, laborator procent si seminar procent)
        /// - un comboBox cu toate materiile pe care le preda profesorul
        /// - odata selectata materia sa iti afiseze cele 3 rubrici (curs, laborator, seminar) in care sa introduci procentele

        /// Panel-ul pentru notare procente
        JPanel borderedPanelProcente = new JPanel();
        borderedPanelProcente.setLayout(new BoxLayout(borderedPanelProcente, BoxLayout.Y_AXIS));
        borderedPanelProcente.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY, 2), "Notare procente")); // Add border with title
        borderedPanelProcente.setMaximumSize(new Dimension(500, 300));
        borderedPanelProcente.setBackground(Color.WHITE);
        borderedPanelProcente.setAlignmentX(Component.LEFT_ALIGNMENT);
        borderedPanelProcente.setVisible(false);


        JButton NotareProcenteButton = new JButton("Notare procente");
        NotareProcenteButton.setBackground(Color.DARK_GRAY);
        NotareProcenteButton.setForeground(Color.WHITE);
        NotareProcenteButton.setAlignmentX(Component.LEFT_ALIGNMENT);


        /// TODO: Sa avem un comboBox in care sa se afiseze materiile la care este inscris profesorul

        JComboBox materiiProfesorComboBox = new JComboBox();
        materiiProfesorComboBox.setPreferredSize(new Dimension(150, 25));
        materiiProfesorComboBox.setMaximumSize(new Dimension(250, 25));
        materiiProfesorComboBox.setMinimumSize(new Dimension(150, 25));
        materiiProfesorComboBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        materiiProfesorComboBox.setVisible(false);


        /// TOGGLE la NotareProcenteButton
        NotareProcenteButton.addActionListener(e -> {
            // Check if the components are already visible
            boolean isVisible = materiiProfesorComboBox.isVisible();

            // Toggle visibility
            materiiProfesorComboBox.setVisible(!isVisible);
            borderedPanelProcente.setVisible(!isVisible); // Toggle the bordered panel visibility

            // Revalidate and repaint the panel to update the layout
            middlePanel.revalidate();
            middlePanel.repaint();

        });

        /// Cand se apasa butonul de Notare Procente
        NotareProcenteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                borderedPanelProcente.setVisible(true);

                try {
                    String url = "jdbc:mysql://139.144.67.202:3306/lms?user=lms&password=WHlQjrrRDs5t";
                    Connection conn = DriverManager.getConnection(url);
                    PreparedStatement selectMateriiProf = conn.prepareStatement("SELECT disciplina.nume FROM disciplina JOIN curs USING (idDisciplina) JOIN profesor USING (idProfesor) WHERE profesor.username = ?");
                    selectMateriiProf.setString(1, profesor.getUsername());
                    ResultSet rezultat = selectMateriiProf.executeQuery();


                    Set<String> materiiAdaugate = new HashSet<>();
                    for (int i = 0; i < materiiProfesorComboBox.getItemCount(); i++) {
                        materiiAdaugate.add(materiiProfesorComboBox.getItemAt(i).toString());
                    }

                    while (rezultat.next()) {
                        String numeMaterie = rezultat.getString("nume");
                        if (!materiiAdaugate.contains(numeMaterie)) {
                            materiiProfesorComboBox.addItem(numeMaterie);
                            materiiAdaugate.add(numeMaterie);
                        }
                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });


        /// Adaugam componentele la panel-urile care trebuie
        middlePanel.add(Box.createRigidArea(new Dimension(0, 20))); // Spacing intre butonul inscriereCurs si NotareProcente
        borderedPanelProcente.add(Box.createRigidArea(new Dimension(0, 10))); // Spacing
        borderedPanelProcente.add(materiiProfesorComboBox);

        /// Creez campurile pentru adaugarea procentelor de note

        JLabel procentCursLabel = new JLabel("Procente curs: ");
        JLabel procentSeminarLabel = new JLabel("Procente seminar: ");
        JLabel procentLaboratorLabel = new JLabel("Procente laborator: ");
        JTextField procentCursField = new JTextField(10);
        procentCursField.setMaximumSize(new Dimension(100, 30));
        JTextField procentSeminarField = new JTextField(10);
        procentSeminarField.setMaximumSize(new Dimension(100, 30));
        JTextField procentLaboratorField = new JTextField(10);
        procentLaboratorField.setMaximumSize(new Dimension(100, 30));
        JButton butonSubmit1 = new JButton("Submit1");
        JButton butonSubmit2 = new JButton("Submit2");
        JButton butonSubmit3 = new JButton("Submit3");


        borderedPanelProcente.add(Box.createRigidArea(new Dimension(0, 20))); // Spacing
        borderedPanelProcente.add(procentCursLabel);
        borderedPanelProcente.add(procentCursField);
        borderedPanelProcente.add(butonSubmit1);

        borderedPanelProcente.add(procentSeminarLabel);
        borderedPanelProcente.add(procentSeminarField);
        borderedPanelProcente.add(butonSubmit2);

        borderedPanelProcente.add(procentLaboratorLabel);
        borderedPanelProcente.add(procentLaboratorField);
        borderedPanelProcente.add(butonSubmit3);

        ///TODO: Adaugare procente in baza de date

        /// Pentru butonSubmit1

        procentCursField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                // Clear the text when the field gains focus
                procentCursField.setText("");
                procentCursField.setForeground(Color.BLACK); // Reset the text color to default
            }

            @Override
            public void focusLost(FocusEvent e) {
                // Optionally do something when the field loses focus
            }
        });

        butonSubmit1.addActionListener(e -> {
            if (Integer.parseInt(procentCursField.getText()) <= 100) {
                try {
                    String url = "jdbc:mysql://139.144.67.202:3306/lms?user=lms&password=WHlQjrrRDs5t";
                    Connection conn = DriverManager.getConnection(url);

                    PreparedStatement selectIdProfesor = conn.prepareStatement("SELECT idProfesor FROM profesor WHERE username = ?");
                    selectIdProfesor.setString(1, profesor.getUsername());
                    ResultSet rezultat0 = selectIdProfesor.executeQuery();
                    String idProfesor = "";
                    if (rezultat0.next()) {
                        idProfesor = rezultat0.getString(1);
                    }


                    /// Selectam idMaterie
                    PreparedStatement selectIdMaterie = conn.prepareStatement("SELECT idDisciplina FROM disciplina WHERE Nume = ?");
                    selectIdMaterie.setString(1, materiiProfesorComboBox.getSelectedItem().toString());
                    ResultSet rezultat1 = selectIdMaterie.executeQuery();
                    String idMaterie = "";
                    if (rezultat1.next()) {
                        idMaterie = rezultat1.getString(1);
                    }


                    PreparedStatement updateProcentCurs = conn.prepareStatement("UPDATE curs SET Procent = ? WHERE idProfesor = ? AND idDisciplina = ?");
                    updateProcentCurs.setInt(1, Integer.parseInt(procentCursField.getText()));
                    updateProcentCurs.setString(2, idProfesor);
                    updateProcentCurs.setString(3, idMaterie);
                    int rowsUpdated = updateProcentCurs.executeUpdate();
                    if (rowsUpdated > 0) {
                        System.out.println("Procent updated successfully!");
                    } else {
                        System.out.println("No rows updated. Check if the curs record exists.");
                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

                procentCursField.setText("Done!");
                procentCursField.setForeground(Color.green);
            } else {
                procentCursField.setText("Procent invalid!");
                procentCursField.setForeground(Color.RED);
            }
            ;
        });

        /// Pentru butonSubmit2

        procentSeminarField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                // Clear the text when the field gains focus
                procentSeminarField.setText("");
                procentSeminarField.setForeground(Color.BLACK); // Reset the text color to default
            }

            @Override
            public void focusLost(FocusEvent e) {
                // Optionally do something when the field loses focus
            }
        });

        butonSubmit2.addActionListener(e -> {
            if (Integer.parseInt(procentSeminarField.getText()) <= 100) {
                try {
                    String url = "jdbc:mysql://139.144.67.202:3306/lms?user=lms&password=WHlQjrrRDs5t";
                    Connection conn = DriverManager.getConnection(url);

                    PreparedStatement selectIdProfesor = conn.prepareStatement("SELECT idProfesor FROM profesor WHERE username = ?");
                    selectIdProfesor.setString(1, profesor.getUsername());
                    ResultSet rezultat0 = selectIdProfesor.executeQuery();
                    String idProfesor = "";
                    if (rezultat0.next()) {
                        idProfesor = rezultat0.getString(1);
                    }


                    /// Selectam idMaterie
                    PreparedStatement selectIdMaterie = conn.prepareStatement("SELECT idDisciplina FROM disciplina WHERE Nume = ?");
                    selectIdMaterie.setString(1, materiiProfesorComboBox.getSelectedItem().toString());
                    ResultSet rezultat1 = selectIdMaterie.executeQuery();
                    String idMaterie = "";
                    if (rezultat1.next()) {
                        idMaterie = rezultat1.getString(1);
                    }


                    PreparedStatement updateProcentSeminar = conn.prepareStatement("UPDATE seminar SET procent = ? WHERE idProfesor = ? AND idDisciplina = ?");
                    updateProcentSeminar.setInt(1, Integer.parseInt(procentSeminarField.getText()));
                    updateProcentSeminar.setString(2, idProfesor);
                    updateProcentSeminar.setString(3, idMaterie);
                    int rowsUpdated = updateProcentSeminar.executeUpdate();
                    if (rowsUpdated > 0) {
                        System.out.println("Procent updated successfully!");
                    } else {
                        System.out.println("No rows updated. Check if the seminar record exists.");
                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

                procentSeminarField.setText("Done!");
                procentSeminarField.setForeground(Color.green);
            } else {
                procentCursField.setText("Procent invalid!");
                procentCursField.setForeground(Color.RED);
            }
            ;
        });

        /// Pentru butonSubmit3

        procentLaboratorField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                // Clear the text when the field gains focus
                procentLaboratorField.setText("");
                procentLaboratorField.setForeground(Color.BLACK); // Reset the text color to default
            }

            @Override
            public void focusLost(FocusEvent e) {
                // Optionally do something when the field loses focus
            }
        });

        butonSubmit3.addActionListener(e -> {
            if (Integer.parseInt(procentLaboratorField.getText()) <= 100) {
                try {
                    String url = "jdbc:mysql://139.144.67.202:3306/lms?user=lms&password=WHlQjrrRDs5t";
                    Connection conn = DriverManager.getConnection(url);

                    PreparedStatement selectIdProfesor = conn.prepareStatement("SELECT idProfesor FROM profesor WHERE username = ?");
                    selectIdProfesor.setString(1, profesor.getUsername());
                    ResultSet rezultat0 = selectIdProfesor.executeQuery();
                    String idProfesor = "";
                    if (rezultat0.next()) {
                        idProfesor = rezultat0.getString(1);
                    }


                    /// Selectam idMaterie
                    PreparedStatement selectIdMaterie = conn.prepareStatement("SELECT idDisciplina FROM disciplina WHERE Nume = ?");
                    selectIdMaterie.setString(1, materiiProfesorComboBox.getSelectedItem().toString());
                    ResultSet rezultat1 = selectIdMaterie.executeQuery();
                    String idMaterie = "";
                    if (rezultat1.next()) {
                        idMaterie = rezultat1.getString(1);
                    }


                    PreparedStatement updateProcentLaborator = conn.prepareStatement(
                            "UPDATE laborator " +
                                    "SET procent = ? " +
                                    "WHERE idProfesor = ? AND idDisciplina = ?");
                    updateProcentLaborator.setInt(1, Integer.parseInt(procentLaboratorField.getText()));
                    updateProcentLaborator.setString(2, idProfesor);
                    updateProcentLaborator.setString(3, idMaterie);
                    int rowsUpdated = updateProcentLaborator.executeUpdate();
                    if (rowsUpdated > 0) {
                        System.out.println("Procent updated successfully!");
                    } else {
                        System.out.println("No rows updated. Check if the seminar record exists.");
                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

                procentLaboratorField.setText("Done!");
                procentLaboratorField.setForeground(Color.green);
            } else {
                procentLaboratorField.setText("Procent invalid!");
                procentLaboratorField.setForeground(Color.RED);
            }
            ;
        });

        /// Adaugam componentele la label-urile care trebuie

        middlePanel.add(NotareProcenteButton);
        middlePanel.add(Box.createRigidArea(new Dimension(0, 20))); // Spacing
        middlePanel.add(borderedPanelProcente);


        // TODO: notele la studenti, lista de studenti si descarcare catalog (studentii cu notele lor)

        ///  Pentru sectiunea de note pentru studenti

        JButton NotePentruStudentiButton = new JButton("Notare note");
        NotePentruStudentiButton.setBackground(Color.DARK_GRAY);
        NotePentruStudentiButton.setForeground(Color.WHITE);
        NotePentruStudentiButton.setAlignmentX(Component.LEFT_ALIGNMENT);

        /// Panelul pentru "note pentru studenti"
        JPanel borderedPanelNote = new JPanel();
        borderedPanelNote.setLayout(new BoxLayout(borderedPanelNote, BoxLayout.Y_AXIS));
        borderedPanelNote.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY, 2), "Notare note")); // Add border with title
        borderedPanelNote.setMaximumSize(new Dimension(500, 300));
        borderedPanelNote.setBackground(Color.WHITE);
        borderedPanelNote.setAlignmentX(Component.LEFT_ALIGNMENT);
        borderedPanelNote.setVisible(false);


        JComboBox materiiProfesorComboBox1 = new JComboBox();
        materiiProfesorComboBox1.setPreferredSize(new Dimension(150, 25));
        materiiProfesorComboBox1.setMaximumSize(new Dimension(250, 25));
        materiiProfesorComboBox1.setMinimumSize(new Dimension(150, 25));
        materiiProfesorComboBox1.setAlignmentX(Component.LEFT_ALIGNMENT);
        materiiProfesorComboBox1.setVisible(false);
        borderedPanelNote.add(Box.createRigidArea(new Dimension(0, 20)));
        borderedPanelNote.add(materiiProfesorComboBox1);

        JComboBox studentiMaterieComboBox = new JComboBox();
        studentiMaterieComboBox.setPreferredSize(new Dimension(150, 25));
        studentiMaterieComboBox.setMaximumSize(new Dimension(250, 25));
        studentiMaterieComboBox.setMinimumSize(new Dimension(150, 25));
        studentiMaterieComboBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        studentiMaterieComboBox.setVisible(false);

        borderedPanelNote.add(Box.createRigidArea(new Dimension(0, 20)));
        borderedPanelNote.add(studentiMaterieComboBox);


        /// TOGGLE la butonul NotePentruStudentiButton
        NotePentruStudentiButton.addActionListener(e -> {
            // Check if the components are already visible
            boolean isVisible = borderedPanelNote.isVisible();

            // Toggle visibility
            materiiProfesorComboBox1.setVisible(!isVisible);
            studentiMaterieComboBox.setVisible(!isVisible);
            borderedPanelNote.setVisible(!isVisible); // Toggle the bordered panel visibility

            // Revalidate and repaint the panel to update the layout
            middlePanel.revalidate();
            middlePanel.repaint();

        });

        /// Un comboBox cu materiile predate, dupaia cu studentii de la materia respectiva si profesorul acesta ca sa le pot da note
        NotePentruStudentiButton.addActionListener(e -> {
            try {
                String url = "jdbc:mysql://139.144.67.202:3306/lms?user=lms&password=WHlQjrrRDs5t";
                Connection conn = DriverManager.getConnection(url);
                PreparedStatement selectMateriiProf = conn.prepareStatement("SELECT disciplina.nume FROM disciplina JOIN curs USING (idDisciplina) JOIN profesor USING (idProfesor) WHERE profesor.username = ?");
                selectMateriiProf.setString(1, profesor.getUsername());
                ResultSet rezultat = selectMateriiProf.executeQuery();

                Set<String> materiiAdaugate = new HashSet<>();
                for (int i = 0; i < materiiProfesorComboBox1.getItemCount(); i++) {
                    materiiAdaugate.add(materiiProfesorComboBox1.getItemAt(i).toString());
                }

                while (rezultat.next()) {
                    String numeMaterie = rezultat.getString("nume");
                    if (!materiiAdaugate.contains(numeMaterie)) {
                        materiiProfesorComboBox1.addItem(numeMaterie);
                        materiiAdaugate.add(numeMaterie);
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            /// Cream comboBox-ul cu studentii de la materia selectata
            materiiProfesorComboBox1.addActionListener(t -> {
                try {
                    // Golim ComboBox-ul pentru studenți înainte de actualizare
                    studentiMaterieComboBox.removeAllItems();

                    // Verificăm dacă există o selecție validă
                    String materieSelectata = (String) materiiProfesorComboBox1.getSelectedItem();
                    if (materieSelectata != null && !materieSelectata.isEmpty()) {
                        // Conexiunea la baza de date
                        String url = "jdbc:mysql://139.144.67.202:3306/lms?user=lms&password=WHlQjrrRDs5t";
                        try (Connection conn = DriverManager.getConnection(url);
                             PreparedStatement selectStudentProf = conn.prepareStatement(
                                     "SELECT student.Username " +
                                             "FROM student " +
                                             "JOIN nota USING (idStudent) " +
                                             "JOIN disciplina USING (idDisciplina) " +
                                             "JOIN profesor USING (idProfesor) " +
                                             "WHERE profesor.username = ? AND disciplina.Nume = ?"
                             )) {
                            // Setăm parametrii interogării
                            selectStudentProf.setString(1, profesor.getUsername());
                            selectStudentProf.setString(2, materieSelectata);

                            try (ResultSet rezultat = selectStudentProf.executeQuery()) {
                                // Populăm ComboBox-ul cu studenți
                                while (rezultat.next()) {
                                    String usernameStudent = rezultat.getString("Username");
                                    studentiMaterieComboBox.addItem(usernameStudent);
                                }
                            }
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            });

            /// Field-urile cu note pentru curs, lab si seminar
            JLabel notaCursLabel = new JLabel("Nota curs: ");
            JLabel notaSeminarLabel = new JLabel("Nota seminar: ");
            JLabel notaLaboratorLabel = new JLabel("Nota laborator: ");
            JTextField notaCursField = new JTextField(10);
            notaCursField.setMaximumSize(new Dimension(100, 30));
            JTextField notaSeminarField = new JTextField(10);
            notaSeminarField.setMaximumSize(new Dimension(100, 30));
            JTextField notaLaboratorField = new JTextField(10);
            notaLaboratorField.setMaximumSize(new Dimension(100, 30));
            JButton butonSubmitNote = new JButton("Submit");

            borderedPanelNote.add(notaCursLabel);
            borderedPanelNote.add(notaCursField);
            borderedPanelNote.add(notaSeminarLabel);
            borderedPanelNote.add(notaSeminarField);
            borderedPanelNote.add(notaLaboratorLabel);
            borderedPanelNote.add(notaLaboratorField);
            borderedPanelNote.add(butonSubmitNote);

            /// Introducerea notelor in baza de date
            butonSubmitNote.addActionListener(b -> {
                String studentSelectat = (String) studentiMaterieComboBox.getSelectedItem();
                if (studentSelectat != null && !studentSelectat.isEmpty()) {
                    try {
                        // Conexiunea la baza de date
                        String url = "jdbc:mysql://139.144.67.202:3306/lms?user=lms&password=WHlQjrrRDs5t";
                        try (Connection conn = DriverManager.getConnection(url)) {

                            // Declarații pentru identificatori
                            int idStudent = 0, idProfesor = 0, idDisciplina = 0;

                            // Găsim `idStudent`
                            try (PreparedStatement interogare = conn.prepareStatement("SELECT idStudent FROM student WHERE Username = ?")) {
                                interogare.setString(1, studentSelectat);
                                try (ResultSet rs = interogare.executeQuery()) {
                                    if (rs.next()) {
                                        idStudent = rs.getInt("idStudent");
                                    } else {
                                        System.out.println("Student not found!");
                                        return;
                                    }
                                }
                            }

                            // Găsim `idProfesor`
                            try (PreparedStatement interogare = conn.prepareStatement("SELECT idProfesor FROM profesor WHERE Username = ?")) {
                                interogare.setString(1, profesor.getUsername());
                                try (ResultSet rs = interogare.executeQuery()) {
                                    if (rs.next()) {
                                        idProfesor = rs.getInt("idProfesor");
                                    } else {
                                        System.out.println("Profesor not found!");
                                        return;
                                    }
                                }
                            }

                            // Găsim `idDisciplina`
                            try (PreparedStatement interogare = conn.prepareStatement("SELECT idDisciplina FROM disciplina WHERE Nume = ?")) {
                                interogare.setString(1, materiiProfesorComboBox1.getSelectedItem().toString());
                                try (ResultSet rs = interogare.executeQuery()) {
                                    if (rs.next()) {
                                        idDisciplina = rs.getInt("idDisciplina");
                                    } else {
                                        System.out.println("Disciplina not found!");
                                        return;
                                    }
                                }
                            }

                            // Validare valori introduse
                            int notaCurs, notaSeminar, notaLaborator;
                            try {
                                notaCurs = Integer.parseInt(notaCursField.getText());
                                notaSeminar = Integer.parseInt(notaSeminarField.getText());
                                notaLaborator = Integer.parseInt(notaLaboratorField.getText());
                            } catch (NumberFormatException ex) {
                                System.out.println("Invalid input for grades!");
                                return;
                            }

                            /// Calculez nota finala

                            String sql = "{CALL nota_finala(?, ?, ?, ?, ?, ?)}";
                            CallableStatement stmt = conn.prepareCall(sql);
                            stmt.setFloat(1, notaCurs);
                            stmt.setFloat(2, notaLaborator);
                            stmt.setFloat(3, notaSeminar);
                            stmt.setFloat(4, idDisciplina);
                            stmt.setFloat(5, idProfesor);
                            // Register the output parameter
                            stmt.registerOutParameter(6, Types.FLOAT);

                            // Execute the procedure
                            stmt.execute();

                            // Get the result from the output parameter
                            float notaFinala = stmt.getFloat(6);

                            // Actualizare note
                            String query = "UPDATE nota SET notaCurs = ?, notaSeminar = ?, notaLaborator = ?, notaFinala = ? " +
                                    "WHERE idStudent = ? AND idDisciplina = ? AND idProfesor = ?";
                            try (PreparedStatement introducereNote = conn.prepareStatement(query)) {
                                introducereNote.setInt(1, notaCurs);
                                introducereNote.setInt(2, notaSeminar);
                                introducereNote.setInt(3, notaLaborator);
                                introducereNote.setInt(4, (int) Math.round(notaFinala));
                                introducereNote.setInt(5, idStudent);
                                introducereNote.setInt(6, idDisciplina);
                                introducereNote.setInt(7, idProfesor);

                                int rowsUpdated = introducereNote.executeUpdate();
                                if (rowsUpdated > 0) {
                                    System.out.println("Note updated successfully!");
                                } else {
                                    System.out.println("No rows updated. Check if the record exists.");
                                }
                            }
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    System.out.println("Please select a student!");
                }

                /// clear fields
                notaCursField.setText("Done");
                notaCursField.setForeground(Color.GREEN);

                notaSeminarField.setText("Done");
                notaSeminarField.setForeground(Color.GREEN);

                notaLaboratorField.setText("Done");
                notaLaboratorField.setForeground(Color.GREEN);




            });

            /// Verificarea pentru field-uri cand scriu in ele
            JTextField[] fieldAux = new JTextField[3];
            fieldAux[0] = notaCursField;
            fieldAux[1] = notaSeminarField;
            fieldAux[2] = notaLaboratorField;

            for (int i = 0; i < fieldAux.length; i++) {
                final int index = i; // Copie locală a lui i
                fieldAux[index].addFocusListener(new FocusListener() {
                    @Override
                    public void focusGained(FocusEvent e) {
                        // Clear the text when the field gains focus
                        fieldAux[index].setText(""); // Folosește index-ul local
                        fieldAux[index].setForeground(Color.BLACK); // Reset the text color to default
                    }

                    @Override
                    public void focusLost(FocusEvent e) {
                        // Optionally do something when the field loses focus
                    }
                });
            }

        });


        /// Adaugarea la panel-ul principal
        middlePanel.add(NotePentruStudentiButton);
        middlePanel.add(Box.createRigidArea(new Dimension(0, 20))); // Spacing
        middlePanel.add(borderedPanelNote);


        /// TODO: vizualizare liste studenti, fac direct catalogul (materie->nume->note (curs, seminar si laborator))
        middlePanel.add(Box.createRigidArea(new Dimension(0, 20)));
        JButton catalogStudentiButton = new JButton("Catalog Studenti");
        middlePanel.add(catalogStudentiButton);

        String[] numeColoane = {"Materie", "Nume Student", "Nota Curs", "Nota Seminar", "Nota Laborator", "Nota Finala"};
        // Creăm modelul tabelului (inițial gol)
        DefaultTableModel tableModel = new DefaultTableModel(numeColoane, 0);
        JTable catalogTable = new JTable(tableModel);
        catalogTable.setFillsViewportHeight(true);
        catalogTable.setPreferredScrollableViewportSize(new Dimension(400, 200));
        //catalogTable.setRowHeight(18); // Setează înălțimea rândului

        // Adăugăm tabelul într-un JScrollPane pentru scroll
        JScrollPane scrollPane = new JScrollPane(catalogTable);

        // Panel pentru afișare
        JPanel catalogPanel = new JPanel();
        catalogPanel.setLayout(new BorderLayout());
        catalogPanel.add(scrollPane, BorderLayout.CENTER);
        catalogPanel.setVisible(false);


        /// TOGGLE la catalogStudentiButton
        catalogStudentiButton.addActionListener(e -> {
            // Check if the components are already visible
            boolean isVisible = catalogPanel.isVisible();

            // Toggle visibility
            catalogPanel.setVisible(!isVisible);

            // Revalidate and repaint the panel to update the layout
            middlePanel.revalidate();
            middlePanel.repaint();

        });

        ///  afisarea catalogului
        catalogStudentiButton.addActionListener(e -> {
            try {
                String url = "jdbc:mysql://139.144.67.202:3306/lms?user=lms&password=WHlQjrrRDs5t";
                Connection conn = DriverManager.getConnection(url);

                // Interogarea pentru datele catalogului
                String query = "SELECT disciplina.Nume AS Materie, student.Username AS NumeStudent, " +
                        "nota.notaCurs, nota.notaSeminar, nota.notaLaborator, nota.notaFinala " +
                        "FROM nota " +
                        "JOIN student USING (idStudent) " +
                        "JOIN disciplina USING (idDisciplina) " +
                        "JOIN profesor USING (idProfesor) " +
                        "WHERE profesor.Username = ?";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, profesor.getUsername());

                ResultSet rs = stmt.executeQuery();

                // Ștergem datele existente din tabel
                DefaultTableModel model = (DefaultTableModel) catalogTable.getModel();
                model.setRowCount(0);

                // Adăugăm datele noi
                while (rs.next()) {
                    String materie = rs.getString("Materie");
                    String numeStudent = rs.getString("NumeStudent");
                    int notaCurs = rs.getInt("notaCurs");
                    int notaSeminar = rs.getInt("notaSeminar");
                    int notaLaborator = rs.getInt("notaLaborator");
                    int notaFinala = rs.getInt("notaFinala");

                    model.addRow(new Object[]{materie, numeStudent, notaCurs, notaSeminar, notaLaborator, notaFinala});
                }
                conn.close();
            } catch (SQLException h) {
                h.printStackTrace();
            }

            // Revalidăm și repictăm layout-ul
            middlePanel.revalidate();
            middlePanel.repaint();
        });

        JButton downloadButton = new JButton("Descarcă Catalog");
        middlePanel.add(downloadButton);

        downloadButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save Catalog");
            fileChooser.setSelectedFile(new File("Catalog.csv"));

            int userSelection = fileChooser.showSaveDialog(null);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                exportToCSV(fileToSave, catalogTable);
            }
        });

        /// Adaug la panelul principal
        middlePanel.add(catalogPanel);

    }
}
