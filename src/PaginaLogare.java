import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.*;

public class PaginaLogare extends JPanel {

    private JTextField username = new JTextField();
    private JTextField password = new JTextField();

    public  void verificareUsername(String s) throws IOException {
        if (s.equals("Introduce-ti Username"))
            throw new IOException(s);
        try{
            //TODO:as pune conexiunea intr o clasa noua
            String url="jdbc:mysql://139.144.67.202:3306/lms?user=lms&password=WHlQjrrRDs5t";
            Connection conn= DriverManager.getConnection(url);

            Statement statement=conn.createStatement();
            ResultSet rs=statement.executeQuery("SELECT Username FROM utilizator WHERE Username="+ username.getText()+";");

                if (!rs.getString("Username").equals(s))
                    throw new IOException(s);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void verificarePassword(String s) throws IOException {
        if (s.equals("Introduce-ti Password"))
            throw new IOException(s);
    }


    public PaginaLogare(JFrame frame) {

        this.setLayout(new GridBagLayout());

        JPanel dataPanel =new JPanel();
        dataPanel.setLayout(new BoxLayout(dataPanel, BoxLayout.Y_AXIS));



        JPanel l1 = new JPanel();
        JPanel l2 = new JPanel();

        l1 = FunctiiUtile.creareText(username,"Username:  ","Introduce-ti Username");
        l2 = FunctiiUtile.creareText(password,"Parola:  ","Introduce-ti Password");
        username.setFont(new Font("Arial", Font.PLAIN, 18));
        password.setFont(new Font("Arial", Font.PLAIN, 18));

        l1.setAlignmentX(Component.CENTER_ALIGNMENT);
        l2.setAlignmentX(Component.CENTER_ALIGNMENT);



        dataPanel.add(l1);
        dataPanel.add(Box.createVerticalStrut(10));
        dataPanel.add(l2);
        dataPanel.add(Box.createVerticalStrut(10));

        JButton submit = FunctiiUtile.CreateButton("Submit",dataPanel);

        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {


                try{
                    verificareUsername(username.getText());
                    System.out.println("Username valid!");
                    verificarePassword(password.getText());
                    System.out.println("Password valid!");
                }catch(IOException exception){
                    System.out.println("Username or Password invalid");
                }

                frame.getContentPane().removeAll();
                frame.setTitle("PaginaHome");
                frame.getContentPane().add(new PaginaHome(frame));
                frame.revalidate();
                frame.repaint();
            }
        });

        JPanel panelBack=new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton butonBack = new JButton("Inapoi");
        butonBack.setBackground(Color.LIGHT_GRAY);
        panelBack.add(butonBack);



        butonBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                frame.setTitle("PaginaInitiala");
                frame.getContentPane().add(new PaginaInitiala(frame));
                frame.revalidate();
                frame.repaint();
            }
        });
        this.add(dataPanel);
        GridBagConstraints gbc=new GridBagConstraints();
        gbc.gridx=0;
        gbc.gridy=0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill=GridBagConstraints.BOTH;
        this.add(panelBack,gbc);


    }

}
