import javax.swing.*;

public class PaginaHomeAdministrator extends PaginaHome{
    private Administrator administrator;
    public PaginaHomeAdministrator(JFrame frame, Administrator administrator) {
        super(frame, administrator);
        this.administrator = administrator;
        System.out.println("f");
    }
}
