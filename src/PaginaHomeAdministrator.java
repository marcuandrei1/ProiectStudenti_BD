import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.*;

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











        JButton butonCereri = new JButton("Cereri înscriere");
        butonCereri.setBackground(Color.DARK_GRAY);
        butonCereri.setForeground(Color.WHITE);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new FlowLayout(FlowLayout.RIGHT)); // Align content to the right
        rightPanel.setOpaque(false); // Transparent background
        rightPanel.add(butonCereri);

        this.add(rightPanel, BorderLayout.CENTER);

        butonCereri.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Create a new panel for the "Cereri înscriere" list
                JPanel listaCereri = new JPanel();
                listaCereri.setLayout(new BoxLayout(listaCereri, BoxLayout.Y_AXIS)); // Vertical stacking of labels
                listaCereri.setBorder(BorderFactory.createTitledBorder("Cereri de înscriere"));
                listaCereri.setOpaque(true);

                // Add the panel to the frame before loading data
                PaginaHomeAdministrator.this.add(new JScrollPane(listaCereri), BorderLayout.CENTER);
                PaginaHomeAdministrator.this.revalidate();
                PaginaHomeAdministrator.this.repaint();

                // Use SwingWorker to perform the database query in a background thread
                new SwingWorker<Void, JLabel>() {
                    @Override
                    protected Void doInBackground() {
                        try {
                            String url = "jdbc:mysql://139.144.67.202:3306/lms?user=lms&password=WHlQjrrRDs5t";
                            Connection conn = DriverManager.getConnection(url);

                            PreparedStatement statement = conn.prepareStatement(
                                    "SELECT Username, nume, prenume, CNP, email, numarTelefon FROM utilizator WHERE Aprobare='Unknown'");
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
                                            ///call functie delete
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
                    protected void process(java.util.List<JLabel> chunks) {
                        // Add each label to the panel on the EDT
                        for (JLabel label : chunks) {
                            listaCereri.add(label);
                        }
                        listaCereri.revalidate();
                        listaCereri.repaint();
                    }
                }.execute();
            }
        });
    }
}
