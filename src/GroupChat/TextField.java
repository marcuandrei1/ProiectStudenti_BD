package GroupChat;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

public class TextField extends JTextPane {

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
        repaint();
    }

    private String hint = "";
    private float animate;
    public TextField() {
        setOpaque(false);
        setBorder(new EmptyBorder(9, 1, 9, 1));
        setBackground(new Color(0, 0, 0, 0));
        setForeground(new Color(255, 255, 255));
        setSelectionColor(new Color(200, 200, 200, 100));
        autoWrapText();

    }

    private void autoWrapText() {
        setEditorKit(new AutoWrapText());
    }


    @Override
    public void paint(Graphics g) {
        if (this.getText().equals("")) {
            Graphics2D g2 = (Graphics2D) g.create();
            int h = getHeight();
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            Insets ins = getInsets();
            FontMetrics fm = g.getFontMetrics();
            g2.setColor(new Color(170, 170, 170));
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f - animate));
            g2.drawString(hint, ins.left + (animate * 30), h / 2 + fm.getAscent() / 2 - 1);
            g2.dispose();
        }
        super.paint(g);
    }
}
