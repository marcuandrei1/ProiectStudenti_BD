import javax.swing.*;
import java.awt.*;

public class PaginaLogare extends JPanel {

    public PaginaLogare() {

        JTextField text = new JTextField("Test",15);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(text);

    }

}
