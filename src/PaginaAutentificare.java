import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.*;


public class PaginaAutentificare extends JPanel {


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

           PreparedStatement statement=conn.prepareStatement("SELECT email FROM utilizator WHERE email=?");
           statement.setString(1,s);
            ResultSet rs=statement.executeQuery();
            if (!(!rs.isBeforeFirst() && rs.getRow()==0))
                throw new IOException(s);
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


            PreparedStatement statement=conn.prepareStatement("SELECT username FROM utilizator WHERE username=?");
            statement.setString(1,s);
            ResultSet rs=statement.executeQuery();
            if (!(!rs.isBeforeFirst() && rs.getRow()==0))
                throw new IOException(s);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void verificarePassword(String s) throws IOException {
        //TODO:maybe check for a strong password?
        if (s.equals("Introduce-ti Password"))
            throw new IOException(s);
    }

    public PaginaAutentificare(JFrame frame) {
        JTextField textCNP = new JTextField();
        JTextField textNume = new JTextField();
        JTextField textPrenume = new JTextField();
        JTextField textAdresa = new JTextField();
        JTextField textNrTel = new JTextField();
        JTextField textEmail = new JTextField();
        JTextField textContIBAN = new JTextField();
        JTextField textNrContract = new JTextField();
        JTextField textUsername = new JTextField();
        JTextField textPassword = new JTextField();


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
        textCNP.setFont(new Font("Arial", Font.PLAIN, 15));
        textNume.setFont(new Font("Arial", Font.PLAIN, 15));
        textPrenume.setFont(new Font("Arial", Font.PLAIN, 15));
        textAdresa.setFont(new Font("Arial", Font.PLAIN, 15));
        textNrTel.setFont(new Font("Arial", Font.PLAIN, 15));
        textEmail.setFont(new Font("Arial", Font.PLAIN, 15));
        textContIBAN.setFont(new Font("Arial", Font.PLAIN, 15));
        textNrContract.setFont(new Font("Arial", Font.PLAIN, 15));
        textUsername.setFont(new Font("Arial", Font.PLAIN, 15));
        textPassword.setFont(new Font("Arial", Font.PLAIN, 15));
        this.setLayout(new GridBagLayout());
        data.setLayout(new BoxLayout(data, BoxLayout.Y_AXIS));
        for(int i=0;i<10;i++){
            data.add(l[i]);
            data.add(Box.createVerticalStrut(10));      /// lasa spatiu intre liniile JPanel-ului
        }

        JButton butonSubmit = FunctiiUtile.CreateButton("Submit", data);
        butonSubmit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int ok=1;
                //TODO:you can check if email or username are duplicates with the same query,you dont need more
                try{
                    verificareCNP(textCNP.getText());
                    System.out.println("CNP valid");
                }catch(IOException exception){
                    System.out.println(exception.getMessage());
                    ok=0;
                }
                try{
                    verificareNume(textNume.getText());
                    System.out.println("Nume valid");
                }catch(IOException exception){
                    System.out.println("Nume invalid");
                    ok=0;
                }
                try{
                    verificarePrenume(textPrenume.getText());
                    System.out.println("Prenume valid");
                }catch(IOException exception){
                    System.out.println("Prenume invalid");
                    ok=0;
                }
                try{
                    verificareAdresa(textAdresa.getText());
                    System.out.println("Adresa valid");

                }catch(IOException exception){
                    System.out.println("Adresa invalid");
                    ok=0;
                }
                try{
                    verificareNrtel(textNrTel.getText());
                    System.out.println("Numar Telefon valid");
                }catch(IOException exception){
                    System.out.println("Numar Telefon invalid");
                    ok=0;
                }
                try{
                    verificareEmail(textEmail.getText());
                    System.out.println("Email valid");
                }catch(IOException exception){
                    System.out.println("Email invalid");
                    ok=0;
                }
                try{
                    verificareIban(textContIBAN.getText());
                    System.out.println("ContIBAN valid");
                }catch(IOException exception){
                    System.out.println("ContIBAN invalid");
                    ok=0;
                }
                try{
                    verificareNrContract(textNrContract.getText());
                    System.out.println("NrContract valid");
                }catch(IOException exception){
                    System.out.println("NrContract invalid");
                    ok=0;
                }
                try{
                    verificareUsername(textUsername.getText());
                    System.out.println("Username valid");
                }catch(IOException exception){
                    System.out.println("Username invalid");
                    ok=0;
                }
                try{
                    verificarePassword(textPassword.getText());
                    System.out.println("Password valid");
                }catch(IOException exception){
                    System.out.println("Password invalid");
                    ok=0;
                }
                if(ok==1){//if all data is correct
                    try{
                        String url="jdbc:mysql://139.144.67.202:3306/lms?user=lms&password=WHlQjrrRDs5t";
                        Connection conn= DriverManager.getConnection(url);
                        PreparedStatement stmt = conn.prepareStatement("SELECT idAdresa FROM adresa WHERE idAdresa = (SELECT max(idAdresa) FROM adresa)");
                        ResultSet ls = stmt.executeQuery();

                        int idAdresa = 1; // Default value if no records are found
                        if (ls.next()) {
                            idAdresa = ls.getInt("idAdresa") + 1; // Incrementing the max idAdresa
                        }

                        String[] parts = textAdresa.getText().split(" ", 2);

                        // Step 4: Insert into 'adresa' table using prepared statement
                        PreparedStatement insertAdresaStmt = conn.prepareStatement("INSERT INTO adresa(idAdresa, strada, numar) VALUES (?, ?, ?)");
                        insertAdresaStmt.setInt(1, idAdresa); // Set idAdresa
                        insertAdresaStmt.setString(2, parts[0]); // Set strada
                        insertAdresaStmt.setInt(3, Integer.parseInt(parts[1])); // Set numar
                        insertAdresaStmt.executeUpdate();

                        // Step 5: Insert into 'utilizator' table using prepared statement
                        PreparedStatement insertUtilizatorStmt = conn.prepareStatement("INSERT INTO utilizator(Username, password, CNP, nume, prenume, idAdresa, numarTelefon, email, IBAN, nrContract) " +
                                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                        insertUtilizatorStmt.setString(1, textUsername.getText()); // Set Username
                        insertUtilizatorStmt.setString(2, textPassword.getText()); // Set Password
                        insertUtilizatorStmt.setString(3, textCNP.getText()); // Set CNP
                        insertUtilizatorStmt.setString(4, textNume.getText()); // Set Nume
                        insertUtilizatorStmt.setString(5, textPrenume.getText()); // Set Prenume
                        insertUtilizatorStmt.setInt(6, idAdresa); // Set idAdresa (from previous step)
                        insertUtilizatorStmt.setString(7, textNrTel.getText()); // Set numarTelefon
                        insertUtilizatorStmt.setString(8, textEmail.getText()); // Set email
                        insertUtilizatorStmt.setString(9, textContIBAN.getText()); // Set IBAN
                        insertUtilizatorStmt.setString(10, textNrContract.getText()); // Set nrContract
                        insertUtilizatorStmt.executeUpdate();

                    } catch (SQLException en) {
                        System.out.println(en.getMessage());
                    }
                    frame.getContentPane().removeAll();
                    frame.setTitle("PaginaInitiala");
                    frame.getContentPane().add(new PaginaInitiala(frame));
                    frame.revalidate();
                    frame.repaint();
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
        this.add(data);
        GridBagConstraints gbc=new GridBagConstraints();
        gbc.gridx=0;
        gbc.gridy=0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill=GridBagConstraints.BOTH;
        this.add(panelBack,gbc);


    }
}
