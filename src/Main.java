
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

        PaginaHome home = new PaginaHome();


        /// de facut aici chestiile ce tin de frame
    }
}