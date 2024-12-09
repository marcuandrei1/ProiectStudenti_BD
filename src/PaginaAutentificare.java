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
            throw new IOException("CNP invalid!");
    }
    public static void verificareNume(String s) throws IOException {
        if (s.equals("Introduce-ti numele"))
            throw new IOException("Nume invalid!");
    }
    public static void verificarePrenume(String s) throws IOException {
        if (s.equals("Introduce-ti prenumele"))
            throw new IOException("Prenume invalid!");
    }
    public static void verificareAdresa(String s) throws IOException {
        if (s.equals("Introduce-ti adresa"))
            throw new IOException("Adresa invalid!");
        String regex = "^[A-Za-z\\s]+\\s*\\d+[A-Za-z]*$";
        if(!s.matches(regex))
            throw new IOException("Adresa invalid!");
    }
    public static void verificareNrtel(String s) throws IOException {
        if (s.length()!=10 || !s.matches("\\d+"))
            throw new IOException("Nrtel invalid!");
    }
    public static void verificareEmail(String s) throws IOException {
        if (s.equals("Introduce-ti Email"))
            throw new IOException("Email invalid!");
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if(!s.matches(regex))
            throw new IOException("Email invalid!");

        try{
            String url="jdbc:mysql://139.144.67.202:3306/lms?user=lms&password=WHlQjrrRDs5t";
            Connection conn= DriverManager.getConnection(url);

           PreparedStatement statement=conn.prepareStatement("SELECT email FROM utilizator WHERE email=?");
           statement.setString(1,s);
           ResultSet rs=statement.executeQuery();
           if (!(!rs.isBeforeFirst() && rs.getRow()==0))
                throw new IOException("Email invalid!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void verificareIban(String s) throws IOException {
        if (s.equals("Introduce-ti cont IBAN") || s.length() < 15 || s.length() > 34) {
            throw new IOException("IBAN invalid!"); // IBAN is too short or too long
        }

        // Remove spaces and make uppercase
        s = s.replaceAll("\\s+", "").toUpperCase();

        // Check the length for the specific country
        String countryCode = s.substring(0, 2);
        Integer expectedLength = 24;//pt RO
        if (expectedLength == null || s.length() != expectedLength) {
            throw new IOException("IBAN invalid!"); // Invalid country code or incorrect length
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
            throw new IOException("IBAN invalid!");
    }
    public static void verificareNrContract(String s) throws IOException {
        if (s.equals("Introduce-ti numar de contract") || !s.matches("\\d+"))
            throw new IOException("Numar de contract invalid!");
    }
    public static void verificareUsername(String s) throws IOException {
        if (s.equals("Introduce-ti Username"))
            throw new IOException("Username invalid!");
        try{
            String url="jdbc:mysql://139.144.67.202:3306/lms?user=lms&password=WHlQjrrRDs5t";
            Connection conn= DriverManager.getConnection(url);


            PreparedStatement statement=conn.prepareStatement("SELECT username FROM utilizator WHERE username=?");
            statement.setString(1,s);
            ResultSet rs=statement.executeQuery();
            if (!(!rs.isBeforeFirst() && rs.getRow()==0))
                throw new IOException("Username invalid!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void verificarePassword(String s) throws IOException {
        //TODO:maybe check for a strong password?
        if (s.equals("Introduce-ti Password"))
            throw new IOException("Password invalid!");
    }

    public PaginaAutentificare(JFrame frame,String job) {
        JTextField textCNP = new JTextField();
        JTextField textNume = new JTextField();
        JTextField textPrenume = new JTextField();
        JTextField textAdresa = new JTextField();
        JTextField textNrTel = new JTextField();
        JTextField textEmail = new JTextField();
        JTextField textContIBAN = new JTextField();
        JTextField textNrContract = new JTextField();
        JTextField textUsername = new JTextField();
        JPasswordField textPassword = new JPasswordField();
        JTextField anStudiu=new JTextField();
        JTextField departament =new JTextField();

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
        //daca e student
        if(job.equals("Student")){

            JPanel p= FunctiiUtile.creareText(anStudiu,"An Studiu:","Introduce-ti anul de studiu");
            anStudiu.setFont(new Font("Arial", Font.PLAIN, 15));
            data.add(p);
            data.add(Box.createVerticalStrut(10));
        }
        else if(job.equals("Profesor")){
            JPanel p= FunctiiUtile.creareText(departament,"Departament: ","Introduce-ti departamentul");
            departament.setFont(new Font("Arial", Font.PLAIN, 15));
            data.add(p);
            data.add(Box.createVerticalStrut(10));
        }
        else{
            data.add(new Checkbox("Supervisor"));
        }
        JButton butonSubmit = FunctiiUtile.CreateButton("Submit", data);
        butonSubmit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //TODO:you can check if email or username are duplicates with the same query,you dont need more
                try{
                    verificareCNP(textCNP.getText());
                    verificareNume(textNume.getText());
                    verificarePrenume(textPrenume.getText());
                    verificareAdresa(textAdresa.getText());
                    verificareNrtel(textNrTel.getText());
                    verificareEmail(textEmail.getText());
                    verificareIban(textContIBAN.getText());
                    verificareNrContract(textNrContract.getText());
                    verificareUsername(textUsername.getText());
                    verificarePassword(textPassword.getText());

                    try{//if all data is correct
                        String url="jdbc:mysql://139.144.67.202:3306/lms?user=lms&password=WHlQjrrRDs5t";
                        Connection conn= DriverManager.getConnection(url);
                        PreparedStatement stmt = conn.prepareStatement(" SELECT max(idAdresa) as maxId FROM adresa");
                        ResultSet ls = stmt.executeQuery();

                        int idAdresa = 1; // Default value if no records are found
                        if (ls.next()) {
                            idAdresa = ls.getInt("maxId") + 1; // Incrementing the max idAdresa
                        }

                        String[] parts = textAdresa.getText().split(" ", 2);

                        // Step 4: Insert into 'adresa' table using prepared statement
                        PreparedStatement insertAdresaStmt = conn.prepareStatement("INSERT INTO adresa(idAdresa, strada, numar) VALUES (?, ?, ?)");
                        insertAdresaStmt.setInt(1, idAdresa); // Set idAdresa
                        insertAdresaStmt.setString(2, parts[0]); // Set strada
                        insertAdresaStmt.setInt(3, Integer.parseInt(parts[1])); // Set numar
                        insertAdresaStmt.executeUpdate();

                        // Step 5: Insert into 'utilizator' table using prepared statement
                        PreparedStatement insertUtilizatorStmt = conn.prepareStatement("INSERT INTO utilizator(Username, password, CNP, nume, prenume, idAdresa, numarTelefon, email, IBAN, nrContract,Aprobare) " +
                                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?,'Unknown')");
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

                        // Step 5: Insert into  table depending on what was chosen in combobox using prepared statement
                        if(job.equals("Student")){
                            conn.prepareStatement("SELECT max(idStudent) as maxStudent FROM Student");
                            ResultSet rs = stmt.executeQuery();

                            int idStudent= 1; // Default value if no records are found
                            if (rs.next()) {
                                idStudent = rs.getInt("maxStudent") + 1; // Incrementing the max idStudent
                            }
                           PreparedStatement insertStudentStmt=conn.prepareStatement("INSERT INTO student(idStudent,Username,anStudiu,NrOreObligatori) VALUES ( ?, ?, ?,35)");
                            insertStudentStmt.setInt(1, idStudent);
                            insertStudentStmt.setString(2, textUsername.getText());
                            insertStudentStmt.setString(3,anStudiu.getText());
                            insertStudentStmt.executeUpdate();
                        }
                        else if(job.equals("Profesor")){
                            conn.prepareStatement("SELECT max(idProfesor) as maxProfesor FROM Profesor");
                            ResultSet rp= stmt.executeQuery();

                            int idProfesor = 1; // Default value if no records are found
                            if (rp.next()) {
                                idProfesor = rp.getInt("maxProfesor") + 1; // Incrementing the max idStudent
                            }
                            PreparedStatement insertProfesorStmt=conn.prepareStatement("INSERT INTO profesor(idProfesor,Username,nrMinOre,NrMaxOre,departament) VALUES ( ?, ?,20,40, ?)");
                            insertProfesorStmt.setInt(1, idProfesor);
                            insertProfesorStmt.setString(2, textUsername.getText());
                            insertProfesorStmt.setString(3,departament.getText());
                            insertProfesorStmt.executeUpdate();
                        }
                    } catch (SQLException en) {
                        System.out.println(en.getMessage());
                    }
                    frame.getContentPane().removeAll();
                    frame.setTitle("PaginaInitiala");
                    frame.getContentPane().add(new PaginaInitiala(frame));
                    frame.revalidate();
                    frame.repaint();
                }catch(IOException exception){
                    System.out.println(exception.getMessage());
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
