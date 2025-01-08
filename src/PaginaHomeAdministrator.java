import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.*;
import java.util.List;

public class PaginaHomeAdministrator extends JPanel {
    private Administrator administrator;

    private static Utilizator getUser(String username,String password) throws IOException{
        Utilizator user = null;
        try{
            String url="jdbc:mysql://139.144.67.202:3306/lms?user=lms&password=WHlQjrrRDs5t";
            Connection conn= DriverManager.getConnection(url);

            CallableStatement statement=conn.prepareCall("{call selectTypeOfUtilizator(?,?,?)}");

            statement.setString(1,username);
            statement.setString(2,password);
            statement.registerOutParameter(3,java.sql.Types.VARCHAR);
            ResultSet rs=statement.executeQuery();

            PreparedStatement stmtAdresa=conn.prepareStatement("SELECT * FROM adresa join utilizator using(idAdresa) where username=? and password=?");
            stmtAdresa.setString(1,username);
            stmtAdresa.setString(2,password);
            ResultSet rs1=stmtAdresa.executeQuery();
            String adresa = "";
            if(rs1.next()){
                adresa=adresa.concat("Strada: ").concat(rs1.getString("strada"));
                adresa = adresa.concat(" Nr: ").concat(rs1.getString("numar"));
            }
            else{
                throw new IOException("User invalid! (adresa)");
            }

            if(rs.next()) {
                //verificare ce este in functie de tip
                if(statement.getString(3).equals("Administrator")){
                    user = new Administrator(rs.getString("username"), rs.getString("password"), rs.getString("CNP"), rs.getString("nume"), rs.getString("prenume"), rs.getString("numarTelefon"), rs.getString("email"), rs.getString("IBAN"), rs.getInt("nrContract"),adresa);
                }
                else if(statement.getString(3).equals("Student")){
                    user = new Student(rs.getString("username"), rs.getString("password"), rs.getString("CNP"), rs.getString("nume"), rs.getString("prenume"), rs.getString("numarTelefon"), rs.getString("email"), rs.getString("IBAN"), rs.getInt("nrContract"),adresa,rs.getInt("anStudiu"),rs.getInt("nrOreObligatorii"));
                }
                else if(statement.getString(3).equals("Profesor")){
                    user = new Profesor(rs.getString("username"), rs.getString("password"), rs.getString("CNP"), rs.getString("nume"), rs.getString("prenume"), rs.getString("numarTelefon"), rs.getString("email"), rs.getString("IBAN"), rs.getInt("nrContract"),adresa,rs.getInt("nrMinOre"),rs.getInt("nrMaxOre"),rs.getString("departament"));
                }
            }
            else {
                throw new IOException("User invalid");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        if(user == null){
            throw new IOException("User invalid");
        }
        return user;                // daca user e null atunci returneaza null
    }

    public PaginaHomeAdministrator(JFrame frame, Administrator administrator) {
        this.administrator = administrator;
        String adminUsername = administrator.getUsername();

        //ifsuper or not
        boolean ifSuper=false;
        try{
            String url = "jdbc:mysql://139.144.67.202:3306/lms?user=lms&password=WHlQjrrRDs5t";
            Connection conn = DriverManager.getConnection(url);
            PreparedStatement statement = conn.prepareStatement(
                    "SELECT ifSuper FROM administrator WHERE Username = ?");
            statement.setString(1, adminUsername);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                // Get the value of ifSuper
                ifSuper = rs.getBoolean("ifSuper"); // or rs.getBoolean(1)
            }
        }catch (SQLException ex) {
            System.out.println("Database error: " + ex.getMessage());
        }

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
                        frame.getContentPane().add(new PaginaHomeAdministrator(frame, administrator));
                        frame.revalidate();
                        frame.repaint();
                    }
                });        /// cand apasam butonul de X din label-ul Profil


                JLabel userNameLabel = new JLabel("User: " + administrator.getUsername());
                userNameLabel.setForeground(Color.black);
                userNameLabel.setFont(new Font("Arial", Font.PLAIN, 15));

                JLabel numeLabel = new JLabel("Nume: " + administrator.getNume());
                numeLabel.setForeground(Color.black);
                numeLabel.setFont(new Font("Arial", Font.PLAIN, 15));

                JLabel prenumeLabel = new JLabel("Prenume: " + administrator.getPrenume());
                prenumeLabel.setForeground(Color.black);
                prenumeLabel.setFont(new Font("Arial", Font.PLAIN, 15));

                JLabel CNPJLabel = new JLabel("CNP: " + administrator.getCNP());
                CNPJLabel.setForeground(Color.black);
                CNPJLabel.setFont(new Font("Arial", Font.PLAIN, 15));

                JLabel emailLabel = new JLabel("Email: " + administrator.getEmail());
                emailLabel.setForeground(Color.black);
                emailLabel.setFont(new Font("Arial", Font.PLAIN, 15));

                JLabel adresaLabel = new JLabel("Adresa: " + administrator.getAdresa());
                adresaLabel.setForeground(Color.black);
                adresaLabel.setFont(new Font("Arial", Font.PLAIN, 15));

                JLabel nrTelefonLabel = new JLabel("Nr Telefon: " + administrator.getNumarTelefon());
                nrTelefonLabel.setForeground(Color.black);
                nrTelefonLabel.setFont(new Font("Arial", Font.PLAIN, 15));

                JLabel nrContractLabel = new JLabel("Nr Contract: " + administrator.getNrContract());
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
        /// Pana aici crearea panel-ului de profil









        Dimension buttonSize = new Dimension(175, 30);

        JButton butonCereri = new JButton("Cereri înscriere");
        butonCereri.setMinimumSize(buttonSize);
        butonCereri.setMaximumSize(buttonSize);
        butonCereri.setPreferredSize(buttonSize);
        butonCereri.setBackground(Color.DARK_GRAY);
        butonCereri.setForeground(Color.WHITE);

        JButton butonAddStudent = new JButton("Adaugă Student");
        butonAddStudent.setMinimumSize(buttonSize);
        butonAddStudent.setMaximumSize(buttonSize);
        butonAddStudent.setPreferredSize(buttonSize);
        butonAddStudent.setBackground(Color.DARK_GRAY);
        butonAddStudent.setForeground(Color.WHITE);

        JButton butonAddProfesor = new JButton("Adaugă Profesor");
        butonAddProfesor.setMinimumSize(buttonSize);
        butonAddProfesor.setMaximumSize(buttonSize);
        butonAddProfesor.setPreferredSize(buttonSize);
        butonAddProfesor.setBackground(Color.DARK_GRAY);
        butonAddProfesor.setForeground(Color.WHITE);

        JButton butonAddAdmin = new JButton("Adaugă Administrator");
        butonAddAdmin.setMinimumSize(buttonSize);
        butonAddAdmin.setMaximumSize(buttonSize);
        butonAddAdmin.setPreferredSize(buttonSize);
        butonAddAdmin.setBackground(Color.DARK_GRAY);
        butonAddAdmin.setForeground(Color.WHITE);

        JButton butonDelete = new JButton("Șterge informații");
        butonDelete.setMinimumSize(buttonSize);
        butonDelete.setMaximumSize(buttonSize);
        butonDelete.setPreferredSize(buttonSize);
        butonDelete.setBackground(Color.DARK_GRAY);
        butonDelete.setForeground(Color.WHITE);

        JButton butonModificari = new JButton("Modifică informații");
        butonModificari.setMinimumSize(buttonSize);
        butonModificari.setMaximumSize(buttonSize);
        butonModificari.setPreferredSize(buttonSize);
        butonModificari.setBackground(Color.DARK_GRAY);
        butonModificari.setForeground(Color.WHITE);

        // Align the buttons to the right
        butonCereri.setAlignmentX(Component.RIGHT_ALIGNMENT);
        butonModificari.setAlignmentX(Component.RIGHT_ALIGNMENT);
        butonAddStudent.setAlignmentX(Component.RIGHT_ALIGNMENT);
        butonAddProfesor.setAlignmentX(Component.RIGHT_ALIGNMENT);
        butonAddAdmin.setAlignmentX(Component.RIGHT_ALIGNMENT);
        butonDelete.setAlignmentX(Component.RIGHT_ALIGNMENT);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS)); // Vertical stacking
        rightPanel.setOpaque(false); // Transparent background
        rightPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Optional spacing between buttons
        rightPanel.add(butonCereri);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Optional spacing between buttons
        rightPanel.add(butonAddStudent);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Optional spacing between buttons
        rightPanel.add(butonAddProfesor);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Optional spacing between buttons
        if (ifSuper) {
            rightPanel.add(butonAddAdmin);
            rightPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Optional spacing between buttons
        }
        rightPanel.add(butonDelete);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Optional spacing between buttons
        rightPanel.add(butonModificari);

        this.add(rightPanel, BorderLayout.EAST);

        butonCereri.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Create a new panel for the "Cereri înscriere" list
                JPanel listaCereri = new JPanel();
                listaCereri.setLayout(new BoxLayout(listaCereri, BoxLayout.Y_AXIS)); // Vertical stacking of labels
                listaCereri.setBorder(BorderFactory.createTitledBorder("Cereri de înscriere"));
                listaCereri.setOpaque(true);

                // Add the panel to the frame before loading data
                // Use SwingWorker to perform the database query in a background thread
                new SwingWorker<Void, JLabel>() {
                    @Override
                    protected Void doInBackground() {
                        try {
                            String url = "jdbc:mysql://139.144.67.202:3306/lms?user=lms&password=WHlQjrrRDs5t";
                            Connection conn = DriverManager.getConnection(url);
                            PreparedStatement statement = conn.prepareStatement(
                                    "SELECT Username, nume, prenume, CNP, email, numarTelefon, idAdresa FROM utilizator WHERE Aprobare='Unknown'");
                            ResultSet rs = statement.executeQuery();
                            if (!rs.isBeforeFirst()) {
                                // Publish a label indicating no data
                                publish(new JLabel("No users with 'Unknown' approval found."));
                            } else {
                                while (rs.next()) {
                                    String username = rs.getString("Username");
                                    String nume = rs.getString("nume");
                                    String prenume = rs.getString("prenume");
                                    String CNP = rs.getString("CNP");
                                    String email = rs.getString("email");
                                    String numarTelefon = rs.getString("numarTelefon");
                                    int idAdresa = rs.getInt("idAdresa");

                                    JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                                    rowPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                                    listaCereri.add(rowPanel);
                                    JLabel nameLabel = new JLabel(
                                            String.format("%s %s, %s, %s, %s",
                                                    nume, prenume, CNP, email, numarTelefon));
                                    nameLabel.setForeground(Color.BLACK);
                                    nameLabel.setFont(new Font("Arial", Font.PLAIN, 15));
                                    rowPanel.add(nameLabel);

                                    JButton butonAcceptat = new JButton("Acceptat");
                                    butonAcceptat.setBackground(Color.GREEN);
                                    butonAcceptat.setForeground(Color.WHITE);

                                    butonAcceptat.addActionListener(new ActionListener() {
                                        public void actionPerformed(ActionEvent e) {
                                            try {
                                                PreparedStatement updateStatement = conn.prepareStatement(
                                                        "UPDATE utilizator SET Aprobare = 'Aprobat' WHERE Username = ?");
                                                updateStatement.setString(1, username);
                                                int rowsUpdated = updateStatement.executeUpdate();
                                                if (rowsUpdated > 0) {
                                                    JOptionPane.showMessageDialog(null,
                                                            "User " + nume + " " + prenume + " has been approved.");
                                                    butonAcceptat.setEnabled(false); // Disable the button after approval
                                                    butonCereri.doClick();
                                                } else {
                                                    JOptionPane.showMessageDialog(null,
                                                            "Failed to approve user " + nume + " " + prenume + ".");
                                                }
                                            } catch (SQLException ex) {
                                                JOptionPane.showMessageDialog(null,
                                                        "Error while approving user: " + ex.getMessage());
                                            }
                                        }
                                    });
                                    JButton butonNeacceptat = new JButton("Neacceptat");
                                    butonNeacceptat.setBackground(Color.GREEN);
                                    butonNeacceptat.setForeground(Color.WHITE);

                                    butonNeacceptat.addActionListener(new ActionListener() {
                                        public void actionPerformed(ActionEvent e) {
                                            try{
                                                PreparedStatement updateStatement = conn.prepareStatement(
                                                        "DELETE from utilizator where Username = ?");
                                                updateStatement.setString(1, username);
                                                updateStatement.executeUpdate();
                                                PreparedStatement updateStatement2 = conn.prepareStatement(
                                                        "Delete from adresa where idAdresa = ?");
                                                updateStatement2.setInt(1, idAdresa);
                                                updateStatement2.executeUpdate();
                                                butonNeacceptat.setEnabled(false); // Disable the button after approval
                                            }catch (SQLException ex) {
                                                JOptionPane.showMessageDialog(null,
                                                        "Error while approving user: " + ex.getMessage());
                                            }

                                        }
                                    });
                                    rowPanel.add(butonAcceptat);
                                    rowPanel.add(butonNeacceptat);
                                }
                            }
                        } catch (SQLException ex) {
                            System.out.println("Database error: " + ex.getMessage());
                            publish(new JLabel("Error fetching data from the database."));
                        }
                        return null;
                    }

                    @Override
                    protected void process(List<JLabel> chunks) {
                        // Add each label to the panel on the EDT
                        for (JLabel label : chunks) {
                            listaCereri.add(label);
                            System.out.println(label.getText());
                        }
                        listaCereri.revalidate();
                        listaCereri.repaint();
                    }
                    @Override
                    protected void done(){
                        PaginaHomeAdministrator.this.add(new JScrollPane(listaCereri), BorderLayout.CENTER);
                        PaginaHomeAdministrator.this.revalidate();
                        PaginaHomeAdministrator.this.repaint();
                    }
                }.execute();
            }
        });


        butonAddStudent.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Create a new dialog to embed PaginaAutentificare
                JDialog addInfoDialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(PaginaHomeAdministrator.this), "Adaugă informații", true);
                addInfoDialog.setLayout(new BorderLayout());
                addInfoDialog.setSize(800, 600); // Set appropriate size
                addInfoDialog.setLocationRelativeTo(PaginaHomeAdministrator.this); // Center it relative to the main panel

                // Embed PaginaAutentificare content
                PaginaAutentificare paginaAutentificare = new PaginaAutentificare((JFrame) SwingUtilities.getWindowAncestor(PaginaHomeAdministrator.this),"Student");
                addInfoDialog.add(paginaAutentificare, BorderLayout.CENTER);

                // Add a close button at the bottom of the dialog
                JButton closeButton = new JButton("Close");
                closeButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        addInfoDialog.dispose(); // Close the dialog
                    }
                });
                JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                buttonPanel.add(closeButton);
                addInfoDialog.add(buttonPanel, BorderLayout.SOUTH);

                addInfoDialog.setVisible(true); // Show the dialog
            }
        });


        butonAddProfesor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Create a new dialog to embed PaginaAutentificare
                JDialog addInfoDialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(PaginaHomeAdministrator.this), "Adaugă informații", true);
                addInfoDialog.setLayout(new BorderLayout());
                addInfoDialog.setSize(800, 600); // Set appropriate size
                addInfoDialog.setLocationRelativeTo(PaginaHomeAdministrator.this); // Center it relative to the main panel

                // Embed PaginaAutentificare content
                PaginaAutentificare paginaAutentificare = new PaginaAutentificare((JFrame) SwingUtilities.getWindowAncestor(PaginaHomeAdministrator.this),"Profesor");
                addInfoDialog.add(paginaAutentificare, BorderLayout.CENTER);

                // Add a close button at the bottom of the dialog
                JButton closeButton = new JButton("Close");
                closeButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        addInfoDialog.dispose(); // Close the dialog
                    }
                });
                JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                buttonPanel.add(closeButton);
                addInfoDialog.add(buttonPanel, BorderLayout.SOUTH);

                addInfoDialog.setVisible(true); // Show the dialog
            }
        });


        butonAddAdmin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Create a new dialog to embed PaginaAutentificare
                JDialog addInfoDialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(PaginaHomeAdministrator.this), "Adaugă informații", true);
                addInfoDialog.setLayout(new BorderLayout());
                addInfoDialog.setSize(800, 600); // Set appropriate size
                addInfoDialog.setLocationRelativeTo(PaginaHomeAdministrator.this); // Center it relative to the main panel

                // Embed PaginaAutentificare content
                PaginaAutentificare paginaAutentificare = new PaginaAutentificare((JFrame) SwingUtilities.getWindowAncestor(PaginaHomeAdministrator.this),"Administrator");
                addInfoDialog.add(paginaAutentificare, BorderLayout.CENTER);

                // Add a close button at the bottom of the dialog
                JButton closeButton = new JButton("Close");
                closeButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        addInfoDialog.dispose(); // Close the dialog
                    }
                });
                JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                buttonPanel.add(closeButton);
                addInfoDialog.add(buttonPanel, BorderLayout.SOUTH);


                addInfoDialog.setVisible(true); // Show the dialog
            }
        });


        boolean finalIfSuper = ifSuper;
        butonDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Create a new dialog to embed PaginaAutentificare
                JDialog addInfoDialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(PaginaHomeAdministrator.this), "Adaugă informații", true);
                addInfoDialog.setLayout(new MigLayout("wrap, inset 0, align center","[grow]","[grow]"));
                addInfoDialog.setSize(400, 300); // Set appropriate size
                addInfoDialog.setLocationRelativeTo(PaginaHomeAdministrator.this); // Center it relative to the main panel

                //Content for add
                JTextField textUsername = new JTextField();
                JPanel l = FunctiiUtile.creareText(textUsername,"Username:  ","Introduce-ti Username");
                textUsername.setFont(new Font("Arial", Font.PLAIN, 15));




                addInfoDialog.add(l,"align center, push");


                JButton deleteUtilizator = new JButton("Șterge");
                deleteUtilizator.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String username=textUsername.getText();
                        String password="";
                        try{
                            // Database connection
                            String url = "jdbc:mysql://139.144.67.202:3306/lms?user=lms&password=WHlQjrrRDs5t";
                            Connection conn = DriverManager.getConnection(url);
                            PreparedStatement statement = conn.prepareStatement(
                                    "SELECT password FROM utilizator WHERE Username = ?");
                            statement.setString(1, username);
                            ResultSet rs = statement.executeQuery();
                            if (rs.next()){
                                password=rs.getString("password");
                            }
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }




                        Utilizator user = null;
                        try {
                            user = getUser(username,password);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                        if (!finalIfSuper){
                            if (user instanceof Administrator) {
                                JOptionPane.showMessageDialog(addInfoDialog, "Introduceți un nume de utilizator valid.", "Eroare", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                        }
                        if (username.isEmpty()) {
                            JOptionPane.showMessageDialog(addInfoDialog, "Introduceți un nume de utilizator valid.", "Eroare", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        try {
                            // Database connection
                            String url = "jdbc:mysql://139.144.67.202:3306/lms?user=lms&password=WHlQjrrRDs5t";
                            Connection conn = DriverManager.getConnection(url);

                            // Get the address ID for the user
                            PreparedStatement statement = conn.prepareStatement(
                                    "SELECT idAdresa FROM utilizator WHERE Username = ?");
                            statement.setString(1, username);
                            ResultSet rs = statement.executeQuery();

                            if (rs.next()) {
                                int idAdresa = rs.getInt("idAdresa");

                                // Delete the user
                                PreparedStatement deleteUserStmt = conn.prepareStatement(
                                        "DELETE FROM utilizator WHERE Username = ?");
                                deleteUserStmt.setString(1, username);
                                deleteUserStmt.executeUpdate();

                                // Delete the address
                                PreparedStatement deleteAddressStmt = conn.prepareStatement(
                                        "DELETE FROM adresa WHERE idAdresa = ?");
                                deleteAddressStmt.setInt(1, idAdresa);
                                deleteAddressStmt.executeUpdate();

                                JOptionPane.showMessageDialog(addInfoDialog, "Utilizatorul a fost șters cu succes.");
                            } else {
                                JOptionPane.showMessageDialog(addInfoDialog, "Numele de utilizator nu există.", "Eroare", JOptionPane.ERROR_MESSAGE);
                            }

                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(addInfoDialog, "Eroare la ștergerea utilizatorului: " + ex.getMessage(), "Eroare", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });


                // Add a close button at the bottom of the dialog
                JButton closeButton = new JButton("Close");
                closeButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        addInfoDialog.dispose(); // Close the dialog
                    }
                });
                JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                buttonPanel.add(closeButton);
                buttonPanel.add(deleteUtilizator);
                addInfoDialog.add(buttonPanel, BorderLayout.SOUTH);

                addInfoDialog.setVisible(true); // Show the dialog
            }
        });


        butonModificari.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Create a new dialog to embed PaginaAutentificare
                JDialog addInfoDialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(PaginaHomeAdministrator.this), "Adaugă informații", true);
                addInfoDialog.setLayout(new MigLayout("wrap, inset 0, align center","[grow]","[grow]"));
                addInfoDialog.setSize(400, 300); // Set appropriate size
                addInfoDialog.setLocationRelativeTo(PaginaHomeAdministrator.this); // Center it relative to the main panel

                //Content for add
                JTextField textUsername = new JTextField();
                JPanel l = FunctiiUtile.creareText(textUsername,"Username:  ","Introduce-ti Username");
                textUsername.setFont(new Font("Arial", Font.PLAIN, 15));




                addInfoDialog.add(l,"align center, push");

                JButton modifyButton = new JButton("Modifica");
                modifyButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String username=textUsername.getText();
                        String password="";
                        try{
                            // Database connection
                            String url = "jdbc:mysql://139.144.67.202:3306/lms?user=lms&password=WHlQjrrRDs5t";
                            Connection conn = DriverManager.getConnection(url);
                            PreparedStatement statement = conn.prepareStatement(
                                    "SELECT password FROM utilizator WHERE Username = ?");
                            statement.setString(1, username);
                            ResultSet rs = statement.executeQuery();
                            if (rs.next()){
                                password=rs.getString("password");
                            }
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }

                        Utilizator user = null;
                        try {
                            user = getUser(username,password);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                        if (!finalIfSuper){
                            if (user instanceof Administrator) {
                                JOptionPane.showMessageDialog(addInfoDialog, "Introduceți un nume de utilizator valid.", "Eroare", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                        }
                        try {

                            String usernameText = textUsername.getText();

                            // Fetch user data from the database
                            String url = "jdbc:mysql://139.144.67.202:3306/lms?user=lms&password=WHlQjrrRDs5t";
                            Connection conn = DriverManager.getConnection(url);

                            PreparedStatement stmt = conn.prepareStatement(
                                    "SELECT u.*, s.anStudiu, p.departament, a.strada, a.numar FROM utilizator u LEFT JOIN student s ON u.Username = s.Username LEFT JOIN profesor p ON u.Username = p.Username LEFT JOIN adresa a ON u.idAdresa = a.idAdresa WHERE u.Username = ?");
                            stmt.setString(1, usernameText);

                            ResultSet rs = stmt.executeQuery();
                            if (!rs.next()) {
                                JOptionPane.showMessageDialog(null, "User not found!");
                                return;
                            }

                            // Pre-fill form with existing data
                            JTextField textCNP = new JTextField(rs.getString("CNP"));
                            JTextField textNume = new JTextField(rs.getString("nume"));
                            JTextField textPrenume = new JTextField(rs.getString("prenume"));
                            JTextField textidAdresa = new JTextField(rs.getString("idAdresa"));
                            JTextField textNrTel = new JTextField(rs.getString("numarTelefon"));
                            JTextField textEmail = new JTextField(rs.getString("email"));
                            JTextField textContIBAN = new JTextField(rs.getString("IBAN"));
                            JTextField textNrContract = new JTextField(String.valueOf(rs.getInt("nrContract")));
                            JTextField textUsername = new JTextField(rs.getString("Username"));
                            JPasswordField textPassword = new JPasswordField(rs.getString("password"));
                            JTextField textAdresa = new JTextField(rs.getString("strada") + " " + rs.getString("numar"));

                            // Create dialog
                            JPanel form = new JPanel(new GridLayout(0, 2, 11, 11));
                            form.add(new JLabel("CNP:")); form.add(textCNP);
                            form.add(new JLabel("Nume:")); form.add(textNume);
                            form.add(new JLabel("Prenume:")); form.add(textPrenume);
                            form.add(new JLabel("idAdresa:")); form.add(textidAdresa);
                            form.add(new JLabel("Nr. Telefon:")); form.add(textNrTel);
                            form.add(new JLabel("Email:")); form.add(textEmail);
                            form.add(new JLabel("IBAN:")); form.add(textContIBAN);
                            form.add(new JLabel("Nr. Contract:")); form.add(textNrContract);
                            form.add(new JLabel("Username:")); form.add(textUsername);
                            form.add(new JLabel("Password:")); form.add(textPassword);
                            form.add(new JLabel("Adresa:")); form.add(textAdresa);

                            // Type-specific fields
                            String anStudiu = rs.getString("anStudiu");
                            String departament = rs.getString("departament");
                            JTextField textAnStudiu = new JTextField(anStudiu != null ? anStudiu : "");
                            JTextField textDepartament = new JTextField(departament != null ? departament : "");

                            if (anStudiu != null) {
                                form.add(new JLabel("An Studiu:")); form.add(textAnStudiu);
                            }
                            if (departament != null) {
                                form.add(new JLabel("Departament:")); form.add(textDepartament);
                            }

                            // Show dialog
                            int result = JOptionPane.showConfirmDialog(null, form,
                                    "Modifică Utilizator", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                            if (result == JOptionPane.OK_OPTION) {
                                // Validate and update database
                                ValidareUtilizator.verificareCNP(textCNP.getText(), textCNP);
                                ValidareUtilizator.verificareNume(textNume.getText(), textNume);
                                ValidareUtilizator.verificarePrenume(textPrenume.getText(), textPrenume);
                                ValidareUtilizator.verificareAdresa(textAdresa.getText(), textAdresa);
                                ValidareUtilizator.verificareNrtel(textNrTel.getText(), textNrTel);
                                ValidareUtilizator.verificareEmail(textEmail.getText(), textEmail);
                                ValidareUtilizator.verificareIban(textContIBAN.getText(), textContIBAN);
                                ValidareUtilizator.verificareNrContract(textNrContract.getText(), textNrContract);
                                ValidareUtilizator.verificareUsername(textUsername.getText(), textUsername);
                                ValidareUtilizator.verificarePassword(new String(textPassword.getPassword()), textPassword);

                                PreparedStatement updateStmt = conn.prepareStatement(
                                        "UPDATE utilizator SET CNP = ?, nume = ?, prenume = ?, numarTelefon = ?, email = ?, " +
                                                "IBAN = ?, nrContract = ?, password = ? WHERE Username = ?");
                                updateStmt.setString(1, textCNP.getText());
                                updateStmt.setString(2, textNume.getText());
                                updateStmt.setString(3, textPrenume.getText());
                                updateStmt.setString(4, textNrTel.getText());
                                updateStmt.setString(5, textEmail.getText());
                                updateStmt.setString(6, textContIBAN.getText());
                                updateStmt.setInt(7, Integer.parseInt(textNrContract.getText()));
                                updateStmt.setString(8, new String(textPassword.getPassword()));
                                updateStmt.setString(9, textUsername.getText());
                                updateStmt.executeUpdate();

                                String[] parts = textAdresa.getText().split(" ", 2);

                                PreparedStatement updateAdresaStmt = conn.prepareStatement(
                                        "UPDATE adresa SET strada = ?, numar = ? WHERE idAdresa = ?");
                                updateAdresaStmt.setString(1, parts[0]);
                                updateAdresaStmt.setInt(2, Integer.parseInt(parts[1]));
                                updateAdresaStmt.setString(3, textidAdresa.getText());
                                updateAdresaStmt.executeUpdate();

                                // Type-specific updates
                                if (!textAnStudiu.getText().isEmpty()) {
                                    PreparedStatement updateStudentStmt = conn.prepareStatement(
                                            "UPDATE student SET anStudiu = ? WHERE Username = ?");
                                    updateStudentStmt.setString(1, textAnStudiu.getText());
                                    updateStudentStmt.setString(2, textUsername.getText());
                                    updateStudentStmt.executeUpdate();
                                }
                                if (!textDepartament.getText().isEmpty()) {
                                    PreparedStatement updateProfesorStmt = conn.prepareStatement(
                                            "UPDATE profesor SET departament = ? WHERE Username = ?");
                                    updateProfesorStmt.setString(1, textDepartament.getText());
                                    updateProfesorStmt.setString(2, textUsername.getText());
                                    updateProfesorStmt.executeUpdate();
                                }

                                JOptionPane.showMessageDialog(null, "User updated successfully!");
                            }
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
                        }
                    }
                });



                // Add a close button at the bottom of the dialog
                JButton closeButton = new JButton("Close");
                closeButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        addInfoDialog.dispose(); // Close the dialog
                    }
                });
                JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                buttonPanel.add(closeButton);
                buttonPanel.add(modifyButton);
                addInfoDialog.add(buttonPanel, BorderLayout.SOUTH);

                addInfoDialog.setVisible(true); // Show the dialog
            }
        });

    }
}

///to do:
///- diferenta intre super si admin

