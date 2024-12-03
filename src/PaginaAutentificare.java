import javax.swing.*;

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
    }

    public String getCNP() {
        return textCNP.getText();
    }
}
