import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.*;

public class ValidareUtilizator {
    public static void verificareCNP(String s, JTextField text) throws IOException {
        if (s.length()!=13 || !s.matches("\\d+")){
            Color lightRed = new Color(255, 102, 102);
            text.setBackground(lightRed);
            throw new IOException("CNP invalid!");
        }
        text.setBackground(Color.WHITE);

    }
    public static void verificareNume(String s, JTextField text) throws IOException {
        if (s.equals("Introduce-ti numele")) {
            Color lightRed = new Color(255, 102, 102);
            text.setBackground(lightRed);
            throw new IOException("Nume invalid!");
        }
        text.setBackground(Color.WHITE);
    }
    public static void verificarePrenume(String s, JTextField text) throws IOException {
        if (s.equals("Introduce-ti prenumele")){
            Color lightRed = new Color(255, 102, 102);
            text.setBackground(lightRed);
            throw new IOException("Prenume invalid!");
        }
        text.setBackground(Color.WHITE);
    }
    public static void verificareAdresa(String s, JTextField text) throws IOException {
        if (s.equals("Introduce-ti adresa")){
            Color lightRed = new Color(255, 102, 102);
            text.setBackground(lightRed);
            throw new IOException("Adresa invalid!");
        }
        String regex = "^[A-Za-z\\s]+\\s*\\d+[A-Za-z]*$";
        if(!s.matches(regex)) {
            Color lightRed = new Color(255, 102, 102);
            text.setBackground(lightRed);
            throw new IOException("Adresa invalid!");
        }
        text.setBackground(Color.WHITE);
    }
    public static void verificareNrtel(String s, JTextField text) throws IOException {
        if (s.length()!=10 || !s.matches("\\d+")){
            Color lightRed = new Color(255, 102, 102);
            text.setBackground(lightRed);
            throw new IOException("Nrtel invalid!");
        }
        text.setBackground(Color.WHITE);
    }
    public static void verificareEmail(String s, JTextField text) throws IOException {
        if (s.equals("Introduce-ti Email")){
            Color lightRed = new Color(255, 102, 102);
            text.setBackground(lightRed);
            throw new IOException("Email invalid!");
        }
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if(!s.matches(regex)){
            Color lightRed = new Color(255, 102, 102);
            text.setBackground(lightRed);
            throw new IOException("Email invalid!");
        }
        /*try{
            String url="jdbc:mysql://139.144.67.202:3306/lms?user=lms&password=WHlQjrrRDs5t";
            Connection conn= DriverManager.getConnection(url);

            PreparedStatement statement=conn.prepareStatement("SELECT email FROM utilizator WHERE email=?");
            statement.setString(1,s);
            ResultSet rs=statement.executeQuery();
            if (!(!rs.isBeforeFirst() && rs.getRow()==0)){
                Color lightRed = new Color(255, 102, 102);
                text.setBackground(lightRed);
                throw new IOException("Email invalid!");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }*/
        text.setBackground(Color.WHITE);
    }
    public static void verificareIban(String s, JTextField text) throws IOException {
        if (s.equals("Introduce-ti cont IBAN") || s.length() < 15 || s.length() > 34) {
            Color lightRed = new Color(255, 102, 102);
            text.setBackground(lightRed);
            throw new IOException("IBAN invalid!"); // IBAN is too short or too long
        }

        // Remove spaces and make uppercase
        s = s.replaceAll("\\s+", "").toUpperCase();

        // Check the length for the specific country
        String countryCode = s.substring(0, 2);
        Integer expectedLength = 24;//pt RO
        if (expectedLength == null || s.length() != expectedLength) {
            Color lightRed = new Color(255, 102, 102);
            text.setBackground(lightRed);
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
        if (ibanNumber.mod(BigInteger.valueOf(97)).intValue() != 1){
            Color lightRed = new Color(255, 102, 102);
            text.setBackground(lightRed);
            throw new IOException("IBAN invalid!");
        }
        text.setBackground(Color.WHITE);
    }
    public static void verificareNrContract(String s, JTextField text) throws IOException {
        if (s.equals("Introduce-ti numar de contract") || !s.matches("\\d+")){
            Color lightRed = new Color(255, 102, 102);
            text.setBackground(lightRed);
            throw new IOException("Numar de contract invalid!");
        }
        text.setBackground(Color.WHITE);
    }
    public static void verificareUsername(String s, JTextField text) throws IOException {
        if (s.equals("Introduce-ti Username")){
            Color lightRed = new Color(255, 102, 102);
            text.setBackground(lightRed);
            throw new IOException("Username invalid!");
        }
        /*try{
            String url="jdbc:mysql://139.144.67.202:3306/lms?user=lms&password=WHlQjrrRDs5t";
            Connection conn= DriverManager.getConnection(url);


            PreparedStatement statement=conn.prepareStatement("SELECT username FROM utilizator WHERE username=?");
            statement.setString(1,s);
            ResultSet rs=statement.executeQuery();
            if (!(!rs.isBeforeFirst() && rs.getRow()==0)) {
                Color lightRed = new Color(255, 102, 102);
                text.setBackground(lightRed);
                throw new IOException("Username invalid!");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }*/
        text.setBackground(Color.WHITE);
    }
    public static void verificarePassword(String s, JTextField text) throws IOException {
        //TODO:maybe check for a strong password?
        if (s.equals("Introduce-ti Password")){
            Color lightRed = new Color(255, 102, 102);
            text.setBackground(lightRed);
            throw new IOException("Password invalid!");
        }
        text.setBackground(Color.WHITE);
    }
}
