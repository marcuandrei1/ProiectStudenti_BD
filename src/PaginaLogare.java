import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.*;

public class PaginaLogare extends JPanel {

    private JTextField username = new JTextField();
    private JTextField password = new JTextField();

    public static void verificareUsername(String s) throws IOException {
        if (s.equals("Introduce-ti Username"))
            throw new IOException(s);
        try{
            String url="jdbc:mysql://139.144.67.202:3306/lms?user=lms&password=WHlQjrrRDs5t";
            Connection conn= DriverManager.getConnection(url);

            Statement statement=conn.createStatement();
            ResultSet rs=statement.executeQuery("SELECT Username FROM utilizator");

            while(rs.next()){
                if (!rs.getString("Username").equals(s))
                    throw new IOException(s);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void verificarePassword(String s) throws IOException {
        if (s.equals("Introduce-ti Password"))
            throw new IOException(s);
    }


    public PaginaLogare(JFrame frame) {

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));



        JPanel l1 = new JPanel();
        JPanel l2 = new JPanel();

        l1 = FunctiiUtile.creareText(username,"Username:  ","Introduce-ti Username");
        l2 = FunctiiUtile.creareText(password,"Parola:  ","Introduce-ti Password");
        username.setFont(new Font("Arial", Font.PLAIN, 18));
        password.setFont(new Font("Arial", Font.PLAIN, 18));

        l1.setAlignmentX(Component.CENTER_ALIGNMENT);
        l2.setAlignmentX(Component.CENTER_ALIGNMENT);



        this.add(l1);
        this.add(Box.createVerticalStrut(10));
        this.add(l2);
        this.add(Box.createVerticalStrut(30));




        JButton home = new JButton("Home");
        home.setBackground(Color.LIGHT_GRAY);



        home.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                frame.setTitle("PaginaInitiala");
                frame.getContentPane().add(new PaginaInitiala(frame));
                frame.revalidate();
                frame.repaint();
            }
        });

        JButton submit = FunctiiUtile.CreateButton("Submit",this);

        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try{
                    verificareUsername(username.getText());
                    System.out.println("Username valid!");
                }catch(IOException exception){
                    System.out.println("Username invalid");
                }
                try{
                    verificarePassword(password.getText());
                    System.out.println("Password valid!");
                }catch(IOException exception){
                    System.out.println("Password invalid");
                }

//                frame.getContentPane().removeAll();
//                frame.setTitle("PaginaHome");
//                frame.getContentPane().add(new PaginaHome(frame));
//                frame.revalidate();
//                frame.repaint();
            }
        });



        home.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.add(home);
        this.add(Box.createVerticalStrut(20));
        this.add(submit);
        this.add(Box.createVerticalStrut(20));


    }

}
