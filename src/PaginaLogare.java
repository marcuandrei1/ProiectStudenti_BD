import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.*;

public class PaginaLogare extends JPanel {

    public  void verificareUsername(String s) throws IOException {
        if (s.equals("Introduce-ti Username"))
            throw new IOException("Username invalid");
    }

    public static void verificarePassword(String s) throws IOException {
        if (s.equals("Introduce-ti Password"))
            throw new IOException("Password invalid");
    }

    private static Utilizator getUser(String username,String password) throws IOException{
        Utilizator user = null;
        try{
            String url="jdbc:mysql://139.144.67.202:3306/lms?user=lms&password=WHlQjrrRDs5t";
            Connection conn= DriverManager.getConnection(url);

            CallableStatement statement=conn.prepareCall("{call selectTypeOfUtilizator(?,?,?)}");

            statement.setString(1,username);
            statement.setString(2,password);
            statement.registerOutParameter(3,java.sql.Types.VARCHAR);
            ResultSet rs=statement.executeQuery();

            PreparedStatement stmtAdresa=conn.prepareStatement("SELECT * FROM adresa join utilizator using(idAdresa) where username=? and password=?");
            stmtAdresa.setString(1,username);
            stmtAdresa.setString(2,password);
            ResultSet rs1=stmtAdresa.executeQuery();
            String adresa = "";
            if(rs1.next()){
                adresa=adresa.concat("Strada: ").concat(rs1.getString("strada"));
                adresa = adresa.concat(" Nr: ").concat(rs1.getString("numar"));
            }
            else{
                throw new IOException("User invalid! (adresa)");
            }

            if(rs.next()) {
                //verificare ce este in functie de tip
                if(statement.getString(3).equals("Administrator")){
                    user = new Administrator(rs.getString("username"), rs.getString("password"), rs.getString("CNP"), rs.getString("nume"), rs.getString("prenume"), rs.getString("numarTelefon"), rs.getString("email"), rs.getString("IBAN"), rs.getInt("nrContract"),adresa);
                }
                else if(statement.getString(3).equals("Student")){
                    user = new Student(rs.getString("username"), rs.getString("password"), rs.getString("CNP"), rs.getString("nume"), rs.getString("prenume"), rs.getString("numarTelefon"), rs.getString("email"), rs.getString("IBAN"), rs.getInt("nrContract"),adresa,rs.getInt("anStudiu"),rs.getInt("nrOreObligatorii"));
                }
                else if(statement.getString(3).equals("Profesor")){
                    user = new Profesor(rs.getString("username"), rs.getString("password"), rs.getString("CNP"), rs.getString("nume"), rs.getString("prenume"), rs.getString("numarTelefon"), rs.getString("email"), rs.getString("IBAN"), rs.getInt("nrContract"),adresa,rs.getInt("nrMinOre"),rs.getInt("nrMaxOre"),rs.getString("departament"));
                }
            }
            else {
                throw new IOException("User invalid");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        if(user == null){
            throw new IOException("User invalid");
        }
        return user;                // daca user e null atunci returneaza null
    }
    public PaginaLogare(JFrame frame) {
        JTextField username = new JTextField();
        JTextField password = new JTextField();

        this.setLayout(new GridBagLayout());

        JPanel dataPanel =new JPanel();
        dataPanel.setLayout(new BoxLayout(dataPanel, BoxLayout.Y_AXIS));

        JPanel l1 = new JPanel();
        JPanel l2 = new JPanel();

        l1 = FunctiiUtile.creareText(username,"Username:  ","Introduce-ti Username");
        l2 = FunctiiUtile.creareText(password,"Parola:  ","Introduce-ti Password");
        username.setFont(new Font("Arial", Font.PLAIN, 15));
        password.setFont(new Font("Arial", Font.PLAIN, 15));

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
                    verificarePassword(password.getText());
                    Utilizator user = getUser(username.getText(),password.getText());
                    frame.getContentPane().removeAll();
                    frame.setTitle("PaginaHome");
                    if(user instanceof Administrator){
                        frame.getContentPane().add(new PaginaHomeAdministrator(frame, (Administrator) user));
                    }
                    else if(user instanceof Student){
                       frame.getContentPane().add(new PaginaHomeStudent(frame, (Student) user));
                    }
                    else{
                        frame.getContentPane().add(new PaginaHomeProfesor(frame, (Profesor) user));
                    }
                    frame.revalidate();
                    frame.repaint();
                }catch(IOException exception){
                    System.out.println(exception.getMessage());     // in functie de eroare (adresa sau user) ne da eroarea
                }
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
