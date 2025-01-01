package GroupChat.model;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ModelMessage {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ModelMessage( String name, String date, String message) {
        this.name = name;
        this.date = date;
        this.message = message;
    }
    private static java.sql.Timestamp getCurrentTimeStamp() {
        java.util.Date today = new java.util.Date();
        return new java.sql.Timestamp(today.getTime());
    }

    public void InserareMesaj(String TableName,int idGrupStudiu,String username) throws SQLException {
        String url="jdbc:mysql://139.144.67.202:3306/lms?user=lms&password=WHlQjrrRDs5t";
        Connection conn= DriverManager.getConnection(url);
        PreparedStatement stmt=conn.prepareStatement(" SELECT idStudent from student where Username=?;");
        stmt.setString(1,username);
        ResultSet rs=stmt.executeQuery();
        while(rs.next()){
            int IDS=rs.getInt("idStudent");
            String query=String.format("INSERT INTO `%s`(idGrupStudiu,idStudent,mesaj,dataMesaj) values(?,?,?,?);",TableName.replace("`","``"));
            stmt=conn.prepareStatement(query);
            stmt.setInt(1,idGrupStudiu);
            stmt.setInt(2,IDS);
            stmt.setString(3,message);
            try {
                SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy, HH:mm");
                java.util.Date parsedDate =df.parse(date);

                stmt.setTimestamp(4,new Timestamp(parsedDate.getTime()));
                stmt.executeUpdate();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    public void CautareMesaje(String TableName,int idGrupStudiu) throws SQLException {
        String url="jdbc:mysql://139.144.67.202:3306/lms?user=lms&password=WHlQjrrRDs5t";
        Connection conn= DriverManager.getConnection(url);
        PreparedStatement stmt=conn.prepareStatement("SELECT idStudent,mesaj,dataMesaj from ? where idGrupStudiu=?");
        stmt.setString(1,TableName);
        stmt.setInt(2,idGrupStudiu);
        ResultSet rs=stmt.executeQuery();
        while(rs.next()){
            message=rs.getString("mesaj");
        }
    }
    private String name;
    private String date;
    private String message;
}
