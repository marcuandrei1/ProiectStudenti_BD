import javax.swing.*;

public class PaginaHomeStudent extends PaginaHome{
    private Student student;
    public PaginaHomeStudent(JFrame frame,Student student) {
        super(frame, student);
        this.student = student;
        System.out.println("f");
    }
}
