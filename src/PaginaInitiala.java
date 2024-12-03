import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class PaginaInitiala extends JPanel {



    public PaginaInitiala(JFrame frame) {

        frame.setTitle("PaginaInitiala");
        this.setLayout(new GridBagLayout());
        /// Panelul (adica clasa noastra) care contine butoanele

        JPanel panelButtons = new JPanel();
        panelButtons.setLayout(new BoxLayout(panelButtons, BoxLayout.Y_AXIS));
        panelButtons.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton butonAutentificare = FunctiiUtile.CreateButton("Autentificare", panelButtons);
        butonAutentificare.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                frame.setTitle("PaginaAutentificare");
                frame.getContentPane().add(new PaginaAutentificare(frame));
                frame.revalidate();
                frame.repaint();
            }
        });

        panelButtons.add(Box.createRigidArea(new Dimension(0, 20)));           // trebuie intre elementele intre care vreau spatiu
        JButton butonLogin = FunctiiUtile.CreateButton("Login", panelButtons);
        butonLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                frame.setTitle("PaginaLogare");
                frame.add(new PaginaLogare(frame),BorderLayout.CENTER);             // seteaza ca content un nou panel (PaginaLogare care extends JPanel)
                frame.revalidate();
                frame.repaint();
            }
        });
        this.add(panelButtons);

    }
}
