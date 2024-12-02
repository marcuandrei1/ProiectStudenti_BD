import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class PaginaAutentificare extends JPanel {
    JLabel titluText = new JLabel("CNP:");

    public JTextField textCNP = new JTextField();
    public JTextField textNume = new JTextField();
    public JTextField textPrenume = new JTextField();
    public JTextField textAdresa = new JTextField();
    public JTextField textNrTel = new JTextField();
    public JTextField textEmail = new JTextField();
    public JTextField textContIBAN = new JTextField();
    public JTextField textNrContract = new JTextField();

    public static JPanel creareText(JTextField txt,String numeTitlu,String placeholder){
        JPanel panelMic = new JPanel(new BorderLayout());
        JLabel label = new JLabel(numeTitlu);

        txt.setText(placeholder);
        txt.setForeground(Color.GRAY);

        txt.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {                             /// cand dau click pe field, daca textul e la fel cu placeholder-ul atunci initializam cu ""
                if(txt.getText().equals(placeholder)){
                    txt.setText("");
                    txt.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {           /// daca nu am introdus nimic atunci va ramane ce e ca placeholder
                if(txt.getText().isEmpty()){
                    txt.setText(placeholder);
                    txt.setForeground(Color.GRAY);
                }
            }
        });
        panelMic.add(label, BorderLayout.WEST);
        panelMic.add(txt, BorderLayout.CENTER);

        return panelMic;
    }

    public PaginaAutentificare() {

        JPanel[] l = new JPanel[8];
        l[0] = creareText(textCNP,"CNP:  ","Introduce-ti CNP");
        l[1] = creareText(textNume,"Nume:  ","Introduce-ti numele");
        l[2] = creareText(textPrenume,"Prenume:  ","Introduce-ti prenumele");
        l[3] = creareText(textAdresa,"Adresa:  ","Introduce-ti adresa");
        l[4] = creareText(textNrTel,"Nr. Telefon:  ","Introduce-ti numarul de telefon");
        l[5] = creareText(textEmail,"Email:  ","Introduce-ti Email");
        l[6] = creareText(textContIBAN,"IBAN:  ","Introduce-ti cont IBAN");
        l[7] = creareText(textNrContract,"Nr. Contract:  ","Introduce-ti numar de contract");

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
