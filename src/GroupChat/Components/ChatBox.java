package GroupChat.Components;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;

import GroupChat.AutoWrapText;
import GroupChat.model.ModelMessage;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import net.miginfocom.swing.MigLayout;

public class ChatBox extends JComponent {

    private final BoxType boxType;
    private final ModelMessage message;

    public ChatBox(BoxType boxType, ModelMessage message) {
        this.boxType = boxType;
        this.message = message;
        init();
    }

    private void init() {
        initBox();
    }

    private void initBox() {
        String rightToLeft = (boxType == BoxType.RIGHT) ? ",rtl" : "";
        setLayout(new MigLayout("inset 5" + rightToLeft,"", "[top]"));
        JTextPane text = new JTextPane();
        text.setEditorKit(new AutoWrapText());
        text.setText(message.getMessage());
        text.setBackground(new Color(0, 0, 0, 0));
        text.setForeground(new Color(242, 242, 242));
        text.setSelectionColor(new Color(200, 200, 200, 100));
        text.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
        text.setOpaque(false);
        text.setEditable(false);
        JLabel labelDate = new JLabel(message.getName() + " | " + message.getDate());
        labelDate.setForeground(new Color(127, 127, 127));
        add(text, "gapy 20, wrap");
        add(labelDate, "gapx 0,span 2");
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int width = getWidth();
        int height = getHeight();
        if (boxType == BoxType.LEFT) {
            Area area = new Area(new RoundRectangle2D.Double(0, 25, width - 25, height - 25 - 16 - 10, 5, 5));
            g2.setPaint(new GradientPaint(0, 0, new Color(255, 94, 98, 240), width, 0, new Color(255, 153, 102, 240)));
            g2.fill(area);
        } else {
            Area area = new Area(new RoundRectangle2D.Double(25, 25, width - 25, height - 25 - 16 - 10, 5, 5));
            g2.setColor(new Color(255, 255, 255, 20));
            g2.fill(area);
        }
        g2.dispose();
        super.paintComponent(g);
    }

    public BoxType getBoxType() {
        return boxType;
    }

    public ModelMessage getMessage() {
        return message;
    }

    public static enum BoxType {
        LEFT, RIGHT
    }
}
