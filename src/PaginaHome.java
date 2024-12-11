import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PaginaHome extends JPanel {
    private Utilizator user;
    JPanel userDataPanel;
    JButton profile;
    JPanel topRightPanel;
    JFrame frame;

    public void afisareInformatiiUtilizator(){

        userDataPanel=  new JPanel();
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
                if(user instanceof Administrator){
                    frame.getContentPane().add(new PaginaHomeAdministrator(frame, (Administrator) user));
                }
                else if(user instanceof Student){
                    frame.getContentPane().add(new PaginaHomeStudent(frame, (Student) user));
                }
                else{
                    frame.getContentPane().add(new PaginaHomeProfesor(frame, (Profesor) user));
                }
                frame.revalidate();
                frame.repaint();
            }
        });        /// cand apasam butonul de X din label-ul Profil


        JLabel userNameLabel = new JLabel("User: " + user.getUsername());
        userNameLabel.setForeground(Color.black);
        userNameLabel.setFont(new Font("Arial", Font.PLAIN, 15));

        JLabel numeLabel = new JLabel("Nume: " + user.getNume());
        numeLabel.setForeground(Color.black);
        numeLabel.setFont(new Font("Arial", Font.PLAIN, 15));

        JLabel prenumeLabel = new JLabel("Prenume: " + user.getPrenume());
        prenumeLabel.setForeground(Color.black);
        prenumeLabel.setFont(new Font("Arial", Font.PLAIN, 15));

        JLabel CNPJLabel = new JLabel("CNP: " + user.getCNP());
        CNPJLabel.setForeground(Color.black);
        CNPJLabel.setFont(new Font("Arial", Font.PLAIN, 15));

        JLabel emailLabel = new JLabel("Email: " + user.getEmail());
        emailLabel.setForeground(Color.black);
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 15));

        JLabel adresaLabel = new JLabel("Adresa: "+ user.getAdresa());
        adresaLabel.setForeground(Color.black);
        adresaLabel.setFont(new Font("Arial", Font.PLAIN, 15));

        JLabel nrTelefonLabel = new JLabel("Nr Telefon: " + user.getNumarTelefon());
        nrTelefonLabel.setForeground(Color.black);
        nrTelefonLabel.setFont(new Font("Arial", Font.PLAIN, 15));

        JLabel nrContractLabel = new JLabel("Nr Contract: " + user.getNrContract());
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

    /// Constructorul
    public PaginaHome(JFrame frame,Utilizator user) {
        this.user=user;
        this.setLayout(new BorderLayout());

        profile = new JButtonCircle("Profil");
        profile.setBackground(Color.DARK_GRAY);
        profile.setForeground(Color.WHITE);

        topRightPanel = new JPanel();
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

        this.add(topPanel,BorderLayout.NORTH);


        butonBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                frame.setTitle("PaginaInitiala");
                frame.getContentPane().add(new PaginaInitiala(frame));
                frame.revalidate();
                frame.repaint();
            }
        });


    }
}
