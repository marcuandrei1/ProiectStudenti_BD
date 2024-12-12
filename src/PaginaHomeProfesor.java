import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;
import java.sql.*;

public class PaginaHomeProfesor extends JPanel {
    private Profesor profesor;


    public  void verificareDisciplina(String s) throws IOException {
        if (s.equals("Introduce-ti disciplina!"))
            throw new IOException("Username invalid");
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


        JPanel borderedPanel = new JPanel();
        borderedPanel.setLayout(new BoxLayout(borderedPanel, BoxLayout.Y_AXIS));
        borderedPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY, 2), "Inscriere Curs")); // Add border with title
        borderedPanel.setMaximumSize(new Dimension(500, 200));
        borderedPanel.setBackground(Color.WHITE);
        borderedPanel.setVisible(false);


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

        /// cand apas pe buton sa imi aparea label-urile, cu TOGGLE
        inscriereCursButton.addActionListener(e -> {

            // Check if the components are already visible
            boolean isVisible = inscriereCursField.isVisible();
            if(isVisible) {
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


        /// TODO: la apasarea tastei submit sa imi bage in baza de date conexiunea dintre profesor si disciplina
        /// TODO: DONE

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
                    int iD = 0;
                    if(rezultat.next()){                    // verifica daca a returnat vreun rezultat query-ul
                        iD = rezultat.getInt("idProfesor");
                    }

                    System.out.println(iD);



                    String materie = inscriereCursField.getText();
                    int idMaterie = 0;
                    if(materie != "Introduce-ti disciplina!") {         // daca chiar s-a introdus o materie

                        // Trebuie sa gasim materia dupa id
                        PreparedStatement stmtidMaterie = conn.prepareStatement("SELECT idDisciplina FROM disciplina where Nume= ?");
                        stmtidMaterie.setString(1, materie);
                        ResultSet rezultatMaterie = stmtidMaterie.executeQuery();
                        if (rezultatMaterie.next()) {
                            idMaterie = rezultatMaterie.getInt("idDisciplina");
                            inscriereCursField.setText("Done!");
                            inscriereCursField.setForeground(Color.GREEN);
                        }
                        else{
                           inscriereCursField.setText("Disciplina invalida!");
                           inscriereCursField.setForeground(Color.RED);
                        }
                        System.out.println(idMaterie);
                    }

                    // Insert statement in prof/disciplina table
                    PreparedStatement insertProfDisciplinaStmt = conn.prepareStatement("INSERT INTO `prof/disciplina`(idProfesor, idDisciplina, nrStudenti)  VALUES (?, ?, ?)");
                    insertProfDisciplinaStmt.setInt(1,iD);
                    insertProfDisciplinaStmt.setInt(2, idMaterie);
                    insertProfDisciplinaStmt.setInt(3, 30);
                    insertProfDisciplinaStmt.executeUpdate();

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
}
