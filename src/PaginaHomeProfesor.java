import javax.swing.*;

public class PaginaHomeProfesor extends PaginaHome{
    private Profesor profesor;
    public PaginaHomeProfesor(JFrame frame, Profesor profesor) {
        super(frame, profesor);
        this.profesor = profesor;
    }
}
