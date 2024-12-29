import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatWindow extends JPanel {
    private Student student;
    private int idGrupStudiu;

    public ChatWindow(Student student,int idGrupStudiu) {
        this.student = student;
        this.idGrupStudiu = idGrupStudiu;
        this.setLayout(new BorderLayout());


        //creare chatbox space
        JPanel chatbox=new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField chatfield = new JTextField();
        JPanel l=FunctiiUtile.creareText(chatfield,"","Introduce-ti mesajul");
        chatfield.setFont(new Font("Arial", Font.PLAIN, 15));
        chatfield.setPreferredSize(new Dimension(300,30));
        JButton submit=new JButton("SEND");
        chatbox.add(l);
        chatbox.add(submit);
        this.add(chatbox,BorderLayout.SOUTH);

        //trmitere mesaje
        submit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

    }
    //introdus buton de submit,poate si enter sa mearga?
    //cine apasa pe buton ii apare sus mesajul,pe dreapta daca ii atau,pe stanga in rest
    //de fiecare data cand se da load la pagina trebuie afisate toate mesajele scrise pana acum
}
