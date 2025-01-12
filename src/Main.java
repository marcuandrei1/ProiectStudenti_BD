
import net.miginfocom.swing.MigLayout;

import java.awt.*;
import java.sql.*;
import java.util.*;
import java.util.List;
import javax.swing.*;



public class Main {
    private static void initGraph(Graph<GraphNode> graph){
        int index=0;
        try{
            String url="jdbc:mysql://139.144.67.202:3306/lms?user=lms&password=WHlQjrrRDs5t";
            Connection conn= DriverManager.getConnection(url);

            PreparedStatement stmt=conn.prepareStatement("SELECT idCurs as ID,idProfesor,Nume,'Curs'as type from curs join disciplina using(idDisciplina)UNION  ALL\n" +
                    "SELECT idSeminar,idProfesor,Nume,'Seminar'as type from seminar join disciplina using(idDisciplina) UNION ALL\n" +
                    "SELECT idLaborator,idProfesor,Nume,'Laborator'as type from laborator join disciplina using(idDisciplina);");
            ResultSet rs=stmt.executeQuery();
            List<GraphNode> nodes=new ArrayList<>();
            List<Integer> idProfesor=new ArrayList<>();
            while(rs.next()){
                nodes.add(new GraphNode(0,rs.getInt(1),index,rs.getString(4),rs.getString(3)));
                idProfesor.add(rs.getInt(2));
                index++;
            }
            for (int i = 0; i < idProfesor.size(); i++) {
                for (int j = i + 1; j < idProfesor.size(); j++) {
                    // If both nodes have the same idProfesor, add an edge
                    if (idProfesor.get(i).equals(idProfesor.get(j))) {
                        graph.addEdge(nodes.get(i), nodes.get(j)); // Add edge between nodes
                    }
                }
            }

            stmt=conn.prepareStatement("SELECT GROUP_CONCAT(nota.idStudent ORDER BY nota.idStudent ASC SEPARATOR ',') AS student_ids FROM curs JOIN disciplina USING(idDisciplina) JOIN nota ON nota.idDisciplina = curs.idDisciplina AND nota.idProfesor = curs.idProfesor GROUP BY curs.idCurs UNION ALL\n" +
                    "SELECT GROUP_CONCAT(nota.idStudent ORDER BY nota.idStudent ASC SEPARATOR ',') AS student_ids FROM seminar JOIN disciplina USING(idDisciplina) JOIN nota ON nota.idDisciplina = seminar.idDisciplina AND nota.idProfesor = seminar.idProfesor GROUP BY seminar.idSeminar UNION ALL\n" +
                    "SELECT GROUP_CONCAT(nota.idStudent ORDER BY nota.idStudent ASC SEPARATOR ',') AS student_ids FROM laborator JOIN disciplina USING(idDisciplina) JOIN nota ON nota.idDisciplina = laborator.idDisciplina AND nota.idProfesor = laborator.idProfesor GROUP BY laborator.idLaborator;");
            rs=stmt.executeQuery();
            List<String> idStudents=new ArrayList<>();
            while(rs.next()){
                idStudents.add(rs.getString(1));
            }
            for (int i = 0; i < idStudents.size(); i++) {
                for (int j = i + 1; j < idStudents.size(); j++) {
                    String str1 = idStudents.get(i);
                    String str2 = idStudents.get(j);

                    // Convert both strings to sets of elements
                    Set<String> set1 = new HashSet<>();
                    Set<String> set2 = new HashSet<>();

                    // Split the strings and add the elements to the sets
                    for (String item : str1.split(",")) {
                        set1.add(item.trim()); // Add to set 1
                    }

                    for (String item : str2.split(",")) {
                        set2.add(item.trim()); // Add to set 2
                    }

                    // Retain only the common elements between set1 and set2
                    set1.retainAll(set2);

                    // Check if the intersection is not empty
                    if (!set1.isEmpty()) {
                        graph.addEdge(nodes.get(i), nodes.get(j)); // Add edge between nodes
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    private static void scheduleOrar(){
        Graph<GraphNode> graph = new Graph<>();
        initGraph(graph);

        List<GraphNode> sortedList=graph.sortVertex();
        int currentColor=0;

        for(int i=0;i<sortedList.size();i++){
            if(sortedList.get(i).getColor()==0){
                sortedList.get(i).setColor(currentColor+1);
                for(int j=1;j<sortedList.size();j++){
                    if(!graph.hasEdge(sortedList.get(i), sortedList.get(j))){
                        sortedList.get(j).setColor(currentColor+1);
                    }
                }
                currentColor++;
            }
        }

        graph.printNodeColors();
        try{
            String url = "jdbc:mysql://139.144.67.202:3306/lms?user=lms&password=WHlQjrrRDs5t";
            Connection conn = DriverManager.getConnection(url);
            PreparedStatement stmt;
            String []zileleSaptamanii={"Luni","Marti","Miercuri","Joi","Vineri","Sâmbătă","Duminica"};
            int ziuaCurenta=0;
            String []activityHours={"08:00:00","10:00:00","12:00:00","14:00:00","16:00:00","18:00:00"};
            int oraCurenta=0;
            for(int color=0;color<=currentColor;color++){
                for(GraphNode node :graph.getMap()) {
                    if(node.getColor()==color){
                        switch (node.getType()) {
                            case "Curs":
                                stmt = conn.prepareStatement("UPDATE curs set ziua=?,ora=? where idCurs=?;");
                                stmt.setString(1, zileleSaptamanii[ziuaCurenta%5]);
                                stmt.setTime(2, Time.valueOf(activityHours[oraCurenta%6]));
                                stmt.setInt(3,node.getID());
                                stmt.executeUpdate();
                                break;
                            case "Seminar":
                                stmt = conn.prepareStatement("UPDATE seminar set ziua=?,ora=? where idSeminar=?;");
                                stmt.setString(1, zileleSaptamanii[ziuaCurenta%5]);
                                stmt.setTime(2, Time.valueOf(activityHours[oraCurenta%6]));
                                stmt.setInt(3,node.getID());
                                stmt.executeUpdate();
                                break;
                            case "Laborator":
                                stmt = conn.prepareStatement("UPDATE laborator set ziua=?,ora=? where idLaborator=?;");
                                stmt.setString(1, zileleSaptamanii[ziuaCurenta%5]);
                                stmt.setTime(2, Time.valueOf(activityHours[oraCurenta%6]));
                                stmt.setInt(3,node.getID());
                                stmt.executeUpdate();
                                break;
                            default:
                                break;
                        }
                    }
                }
                ziuaCurenta++;
                if(ziuaCurenta%5==4){
                    oraCurenta++;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public static void main(String[] args) {
        //scheduleOrar(); Daca e necesar la inceput de an?
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
        ///  Frame-ul nostru
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



        frame.setMinimumSize(new Dimension(800, 600));
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
       JPanel panel = new PaginaInitiala(frame);

        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }
}