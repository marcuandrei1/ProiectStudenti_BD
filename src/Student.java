import java.sql.*;

public class Student extends Utilizator{
    private int anStudiu;
    private int nrOreObligatorii;
    public Student(String username, String password, String CNP, String nume, String prenume, String numarTelefon, String email, String IBAN, int nrContract,String adresa, int anStudiu, int nrOreObligatorii) {
        super(username,password,CNP,nume,prenume,numarTelefon,email,IBAN,nrContract,adresa);
        this.anStudiu = anStudiu;
        this.nrOreObligatorii = nrOreObligatorii;
    }

    public void InscriereStudent(String discplina)throws SQLException{
            String url="jdbc:mysql://139.144.67.202:3306/lms?user=lms&password=WHlQjrrRDs5t";
            Connection conn= DriverManager.getConnection(url);
            CallableStatement stmt=conn.prepareCall("{call InscriereStudent(?,?)}");
            stmt.setString(1, super.getUsername());
            stmt.setString(2, discplina);
            stmt.execute();
            System.out.println(super.getUsername()+" "+discplina);
    }
    public ResultSet VizualizareNote() throws SQLException{
        String url="jdbc:mysql://139.144.67.202:3306/lms?user=lms&password=WHlQjrrRDs5t";
        Connection conn= DriverManager.getConnection(url);
        PreparedStatement stmt=conn.prepareStatement("SELECT Nume,notaSeminar,notaCurs,notaLaborator,notaFinala from nota join lms.disciplina using(idDisciplina) join lms.student using(idStudent) where Username=?");
        stmt.setString(1, super.getUsername());
        return stmt.executeQuery();
    }
    public void ConectareGrup() throws SQLException{

    }
    //trebuie sa vedem cum facem sa aiba acelasi prof la discipline diferite,nu se poate aceeasi primary key,se alege alt prof?
    //studentul poate vedea activitatiile din ziua curenta
    //studentul poate vedea toate activitatille la care este inscris
    //renunta la cursuri?
    //+chestii despre grupu de stidiu chat+stuff
}
