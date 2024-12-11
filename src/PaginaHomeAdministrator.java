import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.*;

public class PaginaHomeAdministrator extends PaginaHome{
    private Administrator administrator;
    public PaginaHomeAdministrator(JFrame frame, Administrator administrator) {
        super(frame, administrator);
        this.administrator = administrator;
        System.out.println("f");


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
