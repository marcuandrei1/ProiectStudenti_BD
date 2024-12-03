import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PaginaHome extends JPanel {
    public PaginaHome(JFrame frame) {

        this.setLayout(new GridBagLayout());

        JButton profile = new JButtonCircle("Abc");
        profile.setBackground(Color.BLUE);
        profile.setForeground(Color.WHITE);

        this.add(profile);

        //l.setLayout(new BorderLayout());
       // l.add(logOut);

        profile.addActionListener(new ActionListener() {
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
