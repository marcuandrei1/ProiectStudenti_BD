import javax.swing.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

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
                    chatArea.addChatBox(new ModelMessage(name, date, message), ChatBox.BoxType.RIGHT);
                    chatArea.clearTextAndGrabFocus();
                }
            }
        });
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

