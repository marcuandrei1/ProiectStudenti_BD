import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.*;


public class PaginaAutentificare extends JPanel {




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
        JButton butonSubmit = FunctiiUtile.CreateButton("Submit", data);
        butonSubmit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //TODO:you can check if email or username are duplicates with the same query,you dont need more
                try{
                    ValidareUtilizator.verificareCNP(textCNP.getText(),textCNP);
                    ValidareUtilizator.verificareNume(textNume.getText(),textNume);
                    ValidareUtilizator.verificarePrenume(textPrenume.getText(),textPrenume);
                    ValidareUtilizator.verificareAdresa(textAdresa.getText(),textAdresa);
                    ValidareUtilizator.verificareNrtel(textNrTel.getText(),textNrTel);
                    ValidareUtilizator.verificareEmail(textEmail.getText(),textEmail);
                    ValidareUtilizator.verificareIban(textContIBAN.getText(),textContIBAN);
                    ValidareUtilizator.verificareNrContract(textNrContract.getText(),textNrContract);
                    ValidareUtilizator.verificareUsername(textUsername.getText(),textUsername);
                    ValidareUtilizator.verificarePassword(textPassword.getText(),textPassword);

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
                            stmt=conn.prepareStatement("SELECT max(idStudent) as maxStudent FROM student");
                            ResultSet rs = stmt.executeQuery();

                            int idStudent= 1; // Default value if no records are found
                            if (rs.next()) {
                                idStudent = rs.getInt("maxStudent") + 1; // Incrementing the max idStudent
                            }
                           PreparedStatement insertStudentStmt=conn.prepareStatement("INSERT INTO student(idStudent,Username,anStudiu,NrOreObligatorii) VALUES ( ?, ?, ?,35)");
                            insertStudentStmt.setInt(1, idStudent);
                            insertStudentStmt.setString(2, textUsername.getText());
                            insertStudentStmt.setString(3,anStudiu.getText());
                            insertStudentStmt.executeUpdate();
                        }
                        else if(job.equals("Profesor")){
                            stmt=conn.prepareStatement("SELECT max(idProfesor) as maxProfesor FROM profesor");
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
