
import java.awt.*;
import java.sql.*;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try{
            String url="jdbc:mysql://139.144.67.202:3306/lms?user=lms&password=WHlQjrrRDs5t";
            Connection conn= DriverManager.getConnection(url);

            Statement s=conn.createStatement();
            ResultSet rs=s.executeQuery("SELECT * FROM adresa");

            while(rs.next()){
                System.out.println(rs.getString("strada"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        ///  Frame-ul nostru
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(800, 600));
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

       JPanel panel = new PaginaInitiala(frame);

        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }
}