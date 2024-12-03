import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.*;


public class PaginaAutentificare extends JPanel {

    private JTextField textCNP = new JTextField();
    private JTextField textNume = new JTextField();
    private JTextField textPrenume = new JTextField();
    private JTextField textAdresa = new JTextField();
    private JTextField textNrTel = new JTextField();
    private JTextField textEmail = new JTextField();
    private JTextField textContIBAN = new JTextField();
    private JTextField textNrContract = new JTextField();
    private JTextField textUsername = new JTextField();
    private JTextField textPassword = new JTextField();

    public static void verificareCNP(String s) throws IOException {
        if (s.length()!=13 || !s.matches("\\d+"))
            throw new IOException(s);
    }
    public static void verificareNume(String s) throws IOException {
        if (s.equals("Introduce-ti numele"))
            throw new IOException(s);
    }
    public static void verificarePrenume(String s) throws IOException {
        if (s.equals("Introduce-ti prenumele"))
            throw new IOException(s);
    }
    public static void verificareAdresa(String s) throws IOException {
        if (s.equals("Introduce-ti adresa"))
            throw new IOException(s);
        String regex = "^[A-Za-z\\s]+\\s*\\d+[A-Za-z]*$";
        if(!s.matches(regex))
            throw new IOException(s);
    }
    public static void verificareNrtel(String s) throws IOException {
        if (s.length()!=10 || !s.matches("\\d+"))
            throw new IOException(s);
    }
    public static void verificareEmail(String s) throws IOException {
        if (s.equals("Introduce-ti Email"))
            throw new IOException(s);
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if(!s.matches(regex))
            throw new IOException(s);

        try{
            String url="jdbc:mysql://139.144.67.202:3306/lms?user=lms&password=WHlQjrrRDs5t";
            Connection conn= DriverManager.getConnection(url);

            Statement statement=conn.createStatement();
            ResultSet rs=statement.executeQuery("SELECT email FROM utilizator");

            while(rs.next()){
                if (!rs.getString("email").equals(s))
                    throw new IOException(s);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void verificareIban(String s) throws IOException {
        if (s.equals("Introduce-ti cont IBAN") || s.length() < 15 || s.length() > 34) {
            throw new IOException(s); // IBAN is too short or too long
        }

        // Remove spaces and make uppercase
        s = s.replaceAll("\\s+", "").toUpperCase();

        // Check the length for the specific country
        String countryCode = s.substring(0, 2);
        Integer expectedLength = 24;//pt RO
        if (expectedLength == null || s.length() != expectedLength) {
            throw new IOException(s); // Invalid country code or incorrect length
        }

        // Rearrange IBAN: Move the first four characters to the end
        String rearranged = s.substring(4) + s.substring(0, 4);

        // Convert letters to numbers (A=10, B=11, ..., Z=35)
        StringBuilder numericIBAN = new StringBuilder();
        for (char c : rearranged.toCharArray()) {
            if (Character.isLetter(c)) {
                numericIBAN.append(c - 'A' + 10);
            } else {
                numericIBAN.append(c);
            }
        }

        // Perform Modulo 97 operation
        BigInteger ibanNumber = new BigInteger(numericIBAN.toString());
        if (ibanNumber.mod(BigInteger.valueOf(97)).intValue() != 1)
            throw new IOException(s);
    }
    public static void verificareNrContract(String s) throws IOException {
        if (s.equals("Introduce-ti numar de contract") || !s.matches("\\d+"))
            throw new IOException(s);
    }
    public static void verificareUsername(String s) throws IOException {
        if (s.equals("Introduce-ti Username"))
            throw new IOException(s);
        try{
            String url="jdbc:mysql://139.144.67.202:3306/lms?user=lms&password=WHlQjrrRDs5t";
            Connection conn= DriverManager.getConnection(url);

            Statement statement=conn.createStatement();
            ResultSet rs=statement.executeQuery("SELECT Username FROM utilizator");

            while(rs.next()){
                if (rs.getString("Username").equals(s))
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

    public PaginaAutentificare(JFrame frame) {
        JPanel[] l = new JPanel[10];
        JPanel data=new JPanel();
        l[0] = FunctiiUtile.creareText(textCNP,"CNP:  ","Introduce-ti CNP");
        l[1] = FunctiiUtile.creareText(textNume,"Nume:  ","Introduce-ti numele");
        l[2] = FunctiiUtile.creareText(textPrenume,"Prenume:  ","Introduce-ti prenumele");
        l[3] = FunctiiUtile.creareText(textAdresa,"Adresa:  ","Introduce-ti adresa");
        l[4] = FunctiiUtile.creareText(textNrTel,"Nr. Telefon:  ","Introduce-ti numarul de telefon");
        l[5] = FunctiiUtile.creareText(textEmail,"Email:  ","Introduce-ti Email");
        l[6] = FunctiiUtile.creareText(textContIBAN,"IBAN:  ","Introduce-ti cont IBAN");
        l[7] = FunctiiUtile.creareText(textNrContract,"Nr. Contract:  ","Introduce-ti numar de contract");
        l[8] = FunctiiUtile.creareText(textUsername,"Username:  ","Introduce-ti Username");
        l[9] = FunctiiUtile.creareText(textPassword,"Password:  ","Introduce-ti Password");

        this.setLayout(new GridBagLayout());
        data.setLayout(new BoxLayout(data, BoxLayout.Y_AXIS));
        for(int i=0;i<10;i++){
            data.add(l[i]);
            data.add(Box.createVerticalStrut(10));      /// lasa spatiu intre liniile JPanel-ului
        }

        JButton butonSubmit = FunctiiUtile.CreateButton("Submit", data);
        butonSubmit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //TODO:un singur try nu merita 100 de mii,si refresh pagina daca ii gresit si spus unde
                try{
                    verificareCNP(textCNP.getText());
                    System.out.println("CNP valid");
                }catch(IOException exception){
                    System.out.println("CNP invalid");
                }
                try{
                    verificareNume(textNume.getText());
                    System.out.println("Nume valid");
                }catch(IOException exception){
                    System.out.println("Nume invalid");
                }
                try{
                    verificarePrenume(textPrenume.getText());
                    System.out.println("Prenume valid");
                }catch(IOException exception){
                    System.out.println("Prenume invalid");
                }
                try{
                    verificareAdresa(textAdresa.getText());
                    System.out.println("Adresa valid");
                }catch(IOException exception){
                    System.out.println("Adresa invalid");
                }
                try{
                    verificareNrtel(textNrTel.getText());
                    System.out.println("Numar Telefon valid");
                }catch(IOException exception){
                    System.out.println("Numar Telefon invalid");
                }
                try{
                    verificareEmail(textEmail.getText());
                    System.out.println("Email valid");
                }catch(IOException exception){
                    System.out.println("Email invalid");
                }
                try{
                    verificareIban(textContIBAN.getText());
                    System.out.println("ContIBAN valid");
                }catch(IOException exception){
                    System.out.println("ContIBAN invalid");
                }
                try{
                    verificareNrContract(textNrContract.getText());
                    System.out.println("NrContract valid");
                }catch(IOException exception){
                    System.out.println("NrContract invalid");
                }
                try{
                    verificareUsername(textUsername.getText());
                    System.out.println("Username valid");
                }catch(IOException exception){
                    System.out.println("Username invalid");
                }
                try{
                    verificarePassword(textPassword.getText());
                    System.out.println("Password valid");
                }catch(IOException exception){
                    System.out.println("Password invalid");
                }

                frame.getContentPane().removeAll();
                frame.setTitle("PaginaInitiala");
                frame.getContentPane().add(new PaginaInitiala(frame));
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
        this.add(data);
        GridBagConstraints gbc=new GridBagConstraints();
        gbc.gridx=0;
        gbc.gridy=0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill=GridBagConstraints.BOTH;
        this.add(panelBack,gbc);


    }

    public String getCNP() {
        return textCNP.getText();
    }
}
