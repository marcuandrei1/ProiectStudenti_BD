import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class PaginaHome extends JPanel {



    public PaginaHome(JFrame frame) {

        frame.setTitle("PaginaHome");
        /// Panelul (adica clasa noastra) care contine butoanele
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton butonAutentificare = FunctiiUtile.CreateButton("Autentificare", this);
        butonAutentificare.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                frame.setTitle("PaginaAutentificare");
                frame.getContentPane().add(new PaginaAutentificare());
                frame.revalidate();
                frame.repaint();
            }
        });

        this.add(Box.createRigidArea(new Dimension(0, 20)));           // trebuie intre elementele intre care vreau spatiu
        JButton butonLogin = FunctiiUtile.CreateButton("Login", this);
        butonLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                frame.setTitle("PaginaLogare");
                frame.getContentPane().add(new PaginaLogare());             // seteaza ca content un nou panel (PaginaLogare care extends JPanel)
                frame.revalidate();
                frame.repaint();
            }
        });

        frame.setLayout(new GridBagLayout());
        frame.add(this);
    }
}
