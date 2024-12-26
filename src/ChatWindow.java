import javax.swing.*;
import java.awt.*;

public class ChatWindow extends JPanel {
    private Student student;
    private int idGrupStudiu;

    public ChatWindow(Student student,int idGrupStudiu) {
        this.student = student;
        this.idGrupStudiu = idGrupStudiu;
        this.setLayout(new BorderLayout());

        JTextField chatbox = new JTextField();
        JPanel l=FunctiiUtile.creareText(chatbox,"","Introduce-ti mesajul");
        chatbox.setFont(new Font("Arial", Font.PLAIN, 15));
        this.add(l,BorderLayout.SOUTH);
    }
    //introdus buton de submit,poate si enter sa mearga?
    //cine apasa pe buton ii apare sus mesajul,pe dreapta daca ii atau,pe stanga in rest
    //de fiecare data cand se da load la pagina trebuie afisate toate mesajele scrise pana acum
}
