import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class BoxTextInformatii {

    public static JPanel creareText(JTextField txt, String numeTitlu, String placeholder){
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
}
