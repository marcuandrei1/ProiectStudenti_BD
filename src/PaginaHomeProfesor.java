import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PaginaHomeProfesor extends PaginaHome{
    private Profesor profesor;


    /// Constructorul
    public PaginaHomeProfesor(JFrame frame, Profesor profesor) {
        super(frame, profesor);
        this.profesor = profesor;

        super.profile.addActionListener(new ActionListener (){
            public void actionPerformed(ActionEvent e) {

                afisareInformatiiUtilizator();

                JLabel nrMinOreLabel = new JLabel("Nr minim de ore: " + profesor.getNrMinOre());
                nrMinOreLabel.setForeground(Color.black);
                nrMinOreLabel.setFont(new Font("Arial", Font.PLAIN, 15));

                JLabel nrMaxOreLabel = new JLabel("Nr maxim de ore: " + profesor.getNrMaxOre());
                nrMaxOreLabel.setForeground(Color.black);
                nrMaxOreLabel.setFont(new Font("Arial", Font.PLAIN, 15));

                JLabel departamentLabel = new JLabel("Departament: " + profesor.getDepartament());
                departamentLabel.setForeground(Color.black);
                departamentLabel.setFont(new Font("Arial", Font.PLAIN, 15));


                userDataPanel.add(nrMinOreLabel);
                userDataPanel.add(nrMaxOreLabel);
                userDataPanel.add(departamentLabel);
            }
        });
    }
}
