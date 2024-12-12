import java.util.ArrayList;

public class Profesor extends Utilizator{
    private int nrMinOre;
    private int nrMaxOre;
    private String departament;
    private ArrayList<String> cursuriPredate;

    public Profesor(String username, String password, String CNP, String nume, String prenume, String numarTelefon, String email, String IBAN, int nrContract,String adresa, int nrMinOre, int nrMaxOre, String departament) {
        super(username,password,CNP,nume,prenume,numarTelefon,email,IBAN,nrContract,adresa);
        this.nrMinOre=nrMinOre;
        this.nrMaxOre=nrMaxOre;
        this.departament=departament;
    }

    public int getNrMinOre() {
        return nrMinOre;
    }

    public void setNrMinOre(int nrMinOre) {
        this.nrMinOre = nrMinOre;
    }

    public int getNrMaxOre() {
        return nrMaxOre;
    }

    public void setNrMaxOre(int nrMaxOre) {
        this.nrMaxOre = nrMaxOre;
    }

    public String getDepartament() {
        return departament;
    }

    public void setDepartament(String departament) {
        this.departament = departament;
    }

    //profesorul se inscrie la cursuri

}
