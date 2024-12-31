import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import GroupChat.Background;
import GroupChat.Components.ChatArea;
import GroupChat.Components.ChatBox;
import GroupChat.model.ModelMessage;
import GroupChat.ChatEvent;

public class ChatWindow extends JPanel {
    private Student student;
    private int idGrupStudiu;
    private Background background1;
    private ChatArea chatArea;
    public ChatWindow(Student student,int idGrupStudiu, JFrame frame) {
        this.student = student;
        this.idGrupStudiu = idGrupStudiu;

        initComponents();//initializare intefata chat

        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy, HH:mm");
        chatArea.addChatEvent(new ChatEvent() {
            @Override
            public void mousePressedSendButton(ActionEvent evt) {
                if(!Objects.equals(chatArea.getText(), "")){
                    String name = student.getUsername();
                    String date = df.format(new Date());
                    String message = chatArea.getText().trim();
                    ModelMessage modelM=new ModelMessage(name, date, message);
                    chatArea.addChatBox(modelM, ChatBox.BoxType.RIGHT);
                    chatArea.clearTextAndGrabFocus();
                    try{
                       modelM.InserareMesaj("mesaj",idGrupStudiu,student.getUsername());
                       modelM.InserareMesaj("mesajasteptare",idGrupStudiu,student.getUsername());
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        });
        ScheduledExecutorService scheduleExe= Executors.newScheduledThreadPool(1);
        scheduleExe.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                try {
                    String url = "jdbc:mysql://139.144.67.202:3306/lms?user=lms&password=WHlQjrrRDs5t";
                    Connection conn = DriverManager.getConnection(url);
                    PreparedStatement stmt = conn.prepareStatement("SELECT idStudent,mesaj,dataMesaj from mesajasteptare where idGrupStudiu=?");
                    stmt.setInt(1, idGrupStudiu);
                    ResultSet rs = stmt.executeQuery();
                    while (rs.next()) {
                        PreparedStatement stmt2=conn.prepareStatement("SELECT Username from student where idStudent=?");
                        stmt2.setInt(1, rs.getInt("idStudent"));
                        ResultSet rs2=stmt2.executeQuery();
                        while (rs2.next()){
                            String name=rs2.getString("Username");
                            System.out.println("sunt aici");
                            if(!name.equals(student.getUsername())){
                                System.out.println("nu sunt utilizatorul bun");
                                String message=rs.getString("mesaj");
                                String date = rs.getString("dataMesaj");
                                ModelMessage modelM=new ModelMessage(name, date, message);
                                chatArea.addChatBox(modelM, ChatBox.BoxType.LEFT);
                            }
                        }
                    }
                    stmt=conn.prepareStatement("DELETE From mesajasteptare");
                    stmt.executeUpdate();
                }catch (SQLException e){
                    System.out.println(e.getMessage());
                }
            }
        },0,2, TimeUnit.SECONDS);

        //verificare o data la 2 sec daca este mesaj nou si daca da in functie daca e useru conectat,care o scris mesaju sau nu se pune right sau left
    }




    private void initComponents() {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        background1 = new Background();
        chatArea = new ChatArea();
        javax.swing.GroupLayout background1Layout = new javax.swing.GroupLayout(background1);
        background1.setLayout(background1Layout);
        background1Layout.setHorizontalGroup(
                background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(background1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(chatArea, javax.swing.GroupLayout.DEFAULT_SIZE, 438, Short.MAX_VALUE)
                                .addContainerGap())
        );
        background1Layout.setVerticalGroup(
                background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(background1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(chatArea, javax.swing.GroupLayout.DEFAULT_SIZE, 707, Short.MAX_VALUE)
                                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(background1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(background1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }

}

