import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.*;
import java.util.List;

public class PaginaHomeAdministrator extends JPanel {
    private Administrator administrator;
    public PaginaHomeAdministrator(JFrame frame, Administrator administrator) {






        this.administrator = administrator;
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
        rightPanel.add(butonAddAdmin);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Optional spacing between buttons
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
        butonDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });
        butonModificari.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });

    }
}

///to do:
///- adauga, sterge, modifica informatii in baza de date

