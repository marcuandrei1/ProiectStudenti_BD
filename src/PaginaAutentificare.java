import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class PaginaAutentificare extends JPanel {

    public JTextField textCNP = new JTextField();
    public JTextField textNume = new JTextField();
    public JTextField textPrenume = new JTextField();
    public JTextField textAdresa = new JTextField();
    public JTextField textNrTel = new JTextField();
    public JTextField textEmail = new JTextField();
    public JTextField textContIBAN = new JTextField();
    public JTextField textNrContract = new JTextField();



    public PaginaAutentificare() {

        JPanel[] l = new JPanel[8];
        l[0] = BoxTextInformatii.creareText(textCNP,"CNP:  ","Introduce-ti CNP");
        l[1] = BoxTextInformatii.creareText(textNume,"Nume:  ","Introduce-ti numele");
        l[2] = BoxTextInformatii.creareText(textPrenume,"Prenume:  ","Introduce-ti prenumele");
        l[3] = BoxTextInformatii.creareText(textAdresa,"Adresa:  ","Introduce-ti adresa");
        l[4] = BoxTextInformatii.creareText(textNrTel,"Nr. Telefon:  ","Introduce-ti numarul de telefon");
        l[5] = BoxTextInformatii.creareText(textEmail,"Email:  ","Introduce-ti Email");
        l[6] = BoxTextInformatii.creareText(textContIBAN,"IBAN:  ","Introduce-ti cont IBAN");
        l[7] = BoxTextInformatii.creareText(textNrContract,"Nr. Contract:  ","Introduce-ti numar de contract");

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        for(int i=0;i<8;i++){
            this.add(l[i]);
            this.add(Box.createVerticalStrut(10));      /// lasa spatiu intre liniile JPanel-ului
        }
    }

    public String getCNP() {
        return textCNP.getText();
    }
}
