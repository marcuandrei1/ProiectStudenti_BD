import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class PaginaInitiala extends JPanel {



    public PaginaInitiala(JFrame frame) {

        frame.setTitle("PaginaInitiala");
        this.setLayout(new GridBagLayout());


        JPanel panelButtons = new JPanel();
        panelButtons.setLayout(new BoxLayout(panelButtons, BoxLayout.Y_AXIS));

        JPanel btnAutentificare= new JPanel();
        btnAutentificare.setLayout(new BoxLayout(btnAutentificare, BoxLayout.X_AXIS));

        btnAutentificare.add(Box.createHorizontalStrut(100));//pentru aliniere
        JButton butonAutentificare = FunctiiUtile.CreateButton("Autentificare", btnAutentificare);
        JComboBox<String> c1=new JComboBox<>(new String[]{"Student", "Profesor"});
        butonAutentificare.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                frame.setTitle("PaginaAutentificare");
                frame.getContentPane().add(new PaginaAutentificare(frame, (String) c1.getSelectedItem()));
                frame.revalidate();
                frame.repaint();
            }
        });
        btnAutentificare.add(Box.createHorizontalStrut(20));//pentru spatiu intre buton si combobox

        btnAutentificare.add(c1);
        panelButtons.add(btnAutentificare);

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
