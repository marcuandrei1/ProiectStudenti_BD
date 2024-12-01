import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class PaginaHome {

    public JButton CreateButton(String nume, JPanel panel) {
        JButton buton = new JButton(nume);
        buton.setBackground(Color.LIGHT_GRAY);
        buton.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(buton);
        return buton;
    }


    public PaginaHome(){
        JFrame frame = new JFrame();
        frame.setTitle("PaginaHome");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);

        /// Panelul care contine butoanele
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton butonAutentificare = CreateButton("Autentificare", panel);

        panel.add(Box.createRigidArea(new Dimension(0, 20)));           // trebuie intre elementele intre care vreau spatiu

        JButton butonLogin = CreateButton("Login", panel);

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
        frame.add(panel);

    }

}
