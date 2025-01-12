import java.awt.*;

public class GraphNode {
    private int color;
    private int index;
    private int ID;
    private String type;
    private String disciplina;

    GraphNode(int color,int ID, int index,String type, String disciplina) {
        this.color = color;
        this.ID = ID;
        this.index = index;
        this.type = type;
        this.disciplina = disciplina;
    }
    public int getIndex() {
        return index;
    }
    public void setColor(int color) {
        this.color = color;
    }
    public int getColor() {
        return color;
    }
    public String getType() {
        return type;
    }
    public int getID(){
        return ID;
    }
    public String toString(){
        return "Nodul "+index+" Disciplina "+ID +" Tipul "+type+ " are culoarea "+ color;
        //return  Integer.toString(ID)+" "+type;
    }
}
