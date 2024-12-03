import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PaginaAutentificare extends JPanel {

    private JTextField textCNP = new JTextField();
    private JTextField textNume = new JTextField();
    private JTextField textPrenume = new JTextField();
    private JTextField textAdresa = new JTextField();
    private JTextField textNrTel = new JTextField();
    private JTextField textEmail = new JTextField();
    private JTextField textContIBAN = new JTextField();
    private JTextField textNrContract = new JTextField();



    public PaginaAutentificare(JFrame frame) {

        JPanel[] l = new JPanel[8];
        l[0] = FunctiiUtile.creareText(textCNP,"CNP:  ","Introduce-ti CNP");
        l[1] = FunctiiUtile.creareText(textNume,"Nume:  ","Introduce-ti numele");
        l[2] = FunctiiUtile.creareText(textPrenume,"Prenume:  ","Introduce-ti prenumele");
        l[3] = FunctiiUtile.creareText(textAdresa,"Adresa:  ","Introduce-ti adresa");
        l[4] = FunctiiUtile.creareText(textNrTel,"Nr. Telefon:  ","Introduce-ti numarul de telefon");
        l[5] = FunctiiUtile.creareText(textEmail,"Email:  ","Introduce-ti Email");
        l[6] = FunctiiUtile.creareText(textContIBAN,"IBAN:  ","Introduce-ti cont IBAN");
        l[7] = FunctiiUtile.creareText(textNrContract,"Nr. Contract:  ","Introduce-ti numar de contract");

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        for(int i=0;i<8;i++){
            this.add(l[i]);
            this.add(Box.createVerticalStrut(10));      /// lasa spatiu intre liniile JPanel-ului
        }

        JButton butonSubmit = FunctiiUtile.CreateButton("Submit", this);
        butonSubmit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {


                frame.getContentPane().removeAll();
                frame.setTitle("PaginaInitiala");
                frame.getContentPane().add(new PaginaInitiala(frame));
                frame.revalidate();
                frame.repaint();
            }
        });
        JButton butonBack = FunctiiUtile.CreateButton("Înapoi", this);
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

    public String getCNP() {
        return textCNP.getText();
    }
}
