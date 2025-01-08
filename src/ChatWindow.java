import javax.swing.*;
import javax.xml.stream.Location;
import java.awt.*;
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
import net.miginfocom.swing.MigLayout;

public class ChatWindow extends JPanel {
    private Student student;
    private int idGrupStudiu;
    private Background background1;
    private ChatArea chatArea;
    private JFrame frame;
    private String studentAdmin;
    private PaginaHomeStudent mainPanel;

    public ChatWindow(Student student,int idGrupStudiu, JFrame frame,PaginaHomeStudent mainPanel) {
        this.student = student;
        this.idGrupStudiu = idGrupStudiu;
        this.frame = frame;
        this.studentAdmin=FindAdmin();
        this.mainPanel=mainPanel;
        initComponents();//initializare intefata chat
        initMessagesChat();
        InitMenuBar();
        frame.setVisible(true);
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
                    PreparedStatement stmt = conn.prepareStatement("SELECT idMesaj,idStudent,mesaj,dataMesaj from mesajasteptare where idGrupStudiu=?");
                    stmt.setInt(1, idGrupStudiu);
                    ResultSet rs = stmt.executeQuery();
                    while (rs.next()) {
                        PreparedStatement stmt2=conn.prepareStatement("SELECT Username from student where idStudent=?");
                        stmt2.setInt(1, rs.getInt("idStudent"));
                        ResultSet rs2=stmt2.executeQuery();
                        while (rs2.next()){
                            String name=rs2.getString("Username");
                            if(!name.equals(student.getUsername())){
                                String message=rs.getString("mesaj");
                                Timestamp date = rs.getTimestamp("dataMesaj");
                                String dateString=df.format(date);
                                ModelMessage modelM=new ModelMessage(name, dateString, message);
                                chatArea.addChatBox(modelM, ChatBox.BoxType.LEFT);
                                PreparedStatement stmt3=conn.prepareStatement("DELETE From mesajasteptare where idMesaj=?");
                                stmt3.setInt(1, rs.getInt("idMesaj"));
                                stmt3.executeUpdate();
                            }
                        }
                    }
                    conn.close();
                }catch (SQLException e){
                    System.out.println(e.getMessage());
                }
            }
        },0,2, TimeUnit.SECONDS);

        //verificare o data la 2 sec daca este mesaj nou si daca da in functie daca e useru conectat,care o scris mesaju sau nu se pune right sau left
    }
    public void InitMenuBar(){
        JMenuBar mb=new JMenuBar();
        mb.setLayout(new MigLayout("inset 0","[grow]"));
        JMenu settings=new JMenu("");
        ImageIcon ic=new ImageIcon("Resources/MenuDots.png");
        Image image=ic.getImage();
        Image newimg=image.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        ic=new ImageIcon(newimg);
        settings.setIcon(ic);
        mb.add(settings,"align right");

        Dimension buttonSize = new Dimension(160, 30);

        JButton vizualizareMembri=new JButton("Membri");
        vizualizareMembri.setMinimumSize(buttonSize);
        vizualizareMembri.setPreferredSize(buttonSize);
        vizualizareMembri.setMaximumSize(buttonSize);
        vizualizareMembri.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Component c=(Component)e.getSource();
                Window window=SwingUtilities.windowForComponent(c);
                JDialog membri=new JDialog();
                membri.setModal(true);
                membri.setLayout(new MigLayout("wrap,inset 5"));
                try{
                    String url="jdbc:mysql://139.144.67.202:3306/lms?user=lms&password=WHlQjrrRDs5t";
                    Connection conn= DriverManager.getConnection(url);

                    PreparedStatement stmt=conn.prepareStatement("SELECT distinct student.Username from student join mesaj using(idStudent)  where idGrupStudiu=? and mesaj='';");
                    stmt.setInt(1, idGrupStudiu);
                    ResultSet rs=stmt.executeQuery();
                    while(rs.next()){
                        JPanel namePanel=new JPanel();
                        JLabel name=new JLabel(rs.getString("Username"));
                        namePanel.add(name);
                        if(name.getText().equals(studentAdmin)){
                            JLabel admin=new JLabel("(Admin)");
                            admin.setForeground(Color.GRAY);
                            admin.setFont(new Font("Arial", Font.PLAIN, 13));
                            namePanel.add(admin);
                        }
                        membri.add(namePanel);
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }

                membri.setMinimumSize(membri.getSize());
                membri.setLocation(new Point(window.getLocation().x+frame.getWidth()-15, window.getLocation().y));
                membri.pack();
                membri.setVisible(true);
            }
        });
        settings.add(vizualizareMembri);

        JButton leaveGrup=new JButton("Iesire Grup");
        leaveGrup.setMinimumSize(buttonSize);
        leaveGrup.setPreferredSize(buttonSize);
        leaveGrup.setMaximumSize(buttonSize);
        leaveGrup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    String url="jdbc:mysql://139.144.67.202:3306/lms?user=lms&password=WHlQjrrRDs5t";
                    Connection conn= DriverManager.getConnection(url);

                    PreparedStatement stmt=conn.prepareStatement("DELETE from mesaj where idStudent=(SELECT idStudent from student where Username=?) and mesaj='' and idGrupStudiu=?;");
                    stmt.setString(1, student.getUsername());
                    stmt.setInt(2, idGrupStudiu);
                    stmt.executeUpdate();
                    JOptionPane.showMessageDialog(null,
                            "Ai iesit din grup cu succes");
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
                RefreshInfo();//inchide chatul si sterge metoda de accesare a grupului
            }
        });
        settings.add(leaveGrup);
        if(student.getUsername().equals(studentAdmin)){
            JButton deleteGrup=new JButton("Delete grup");
            deleteGrup.setMinimumSize(buttonSize);
            deleteGrup.setPreferredSize(buttonSize);
            deleteGrup.setMaximumSize(buttonSize);
            deleteGrup.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try{
                        String url="jdbc:mysql://139.144.67.202:3306/lms?user=lms&password=WHlQjrrRDs5t";
                        Connection conn= DriverManager.getConnection(url);

                        PreparedStatement stmt=conn.prepareStatement("DELETE from grupstudiu where idGrupStudiu=?;");
                        stmt.setInt(1, idGrupStudiu);
                        stmt.executeUpdate();
                        JOptionPane.showMessageDialog(null,
                                "Grupul de studiu a fost sters cu succes");
                    } catch (SQLException ex) {
                        System.out.println(ex.getMessage());
                    }
                    RefreshInfo();//inchide chatul si sterge metoda de accesare a grupului
                }
            });
            settings.add(deleteGrup);
        }

        settings.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        frame.setJMenuBar(mb);
    }

    private void RefreshInfo(){
        frame.dispose();
        JPanel panel= (JPanel) mainPanel.getComponent(2);
        if(panel!=null){
            mainPanel.remove(panel);
        }
        mainPanel.add(mainPanel.InterfataVizualizareGrupuri(),BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }
    private String FindAdmin(){
        try{
            String url="jdbc:mysql://139.144.67.202:3306/lms?user=lms&password=WHlQjrrRDs5t";
            Connection conn= DriverManager.getConnection(url);

            PreparedStatement stmt=conn.prepareStatement("SELECT Username from student join grupstudiu using (idStudent) where idGrupStudiu=?;");
            stmt.setInt(1, idGrupStudiu);
            stmt.setInt(1, idGrupStudiu);
            ResultSet rs=stmt.executeQuery();
            while(rs.next()){
                return rs.getString("Username");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return "";
    }
    private void initComponents() {
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
    public void initMessagesChat(){
        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy, HH:mm");
        try{
            String url="jdbc:mysql://139.144.67.202:3306/lms?user=lms&password=WHlQjrrRDs5t";
            Connection conn= DriverManager.getConnection(url);

            PreparedStatement stmt=conn.prepareStatement("SELECT idStudent,mesaj,dataMesaj from mesaj where idGrupStudiu=? ORDER BY dataMesaj ASC ;");
            stmt.setInt(1, idGrupStudiu);
            ResultSet rs=stmt.executeQuery();
            while(rs.next()){
                PreparedStatement stmt2=conn.prepareStatement("SELECT Username from student where idStudent=?");
                stmt2.setInt(1, rs.getInt("idStudent"));
                ResultSet rs2=stmt2.executeQuery();
                while (rs2.next()) {
                    String name = rs2.getString("Username");
                    String message=rs.getString("mesaj");
                    Timestamp date = rs.getTimestamp("dataMesaj");
                    String dateString=df.format(date);
                    ModelMessage modelM=new ModelMessage(name,dateString,message);
                    if(!Objects.equals(message, "")) {
                        if (Objects.equals(name, student.getUsername())) {
                            chatArea.addChatBox(modelM, ChatBox.BoxType.RIGHT);
                        } else {
                            chatArea.addChatBox(modelM, ChatBox.BoxType.LEFT);
                        }
                    }
                }
            }
        } catch (SQLException er) {
            System.out.println(er.getMessage());
        }
        JScrollPane scrollPane =chatArea.getScroll();
        scrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
            public void adjustmentValueChanged(AdjustmentEvent e) {
                Adjustable adjustable = e.getAdjustable();
                adjustable.setValue(adjustable.getMaximum());
               scrollPane.getVerticalScrollBar().removeAdjustmentListener(this);
            }
        });
    }
}

