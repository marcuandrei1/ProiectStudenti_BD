public class Student extends Utilizator{
    private int anStudiu;
    private int nrOreObligatorii;
    public Student(String username, String password, String CNP, String nume, String prenume, String numarTelefon, String email, String IBAN, int nrContract,String adresa, int anStudiu, int nrOreObligatorii) {
        super(username,password,CNP,nume,prenume,numarTelefon,email,IBAN,nrContract,adresa);
        this.anStudiu = anStudiu;
        this.nrOreObligatorii = nrOreObligatorii;
    }

    //studentul se poate inscrie la curs daca mai sunt locuri
    //studentul poate vedea activitatiile din ziua curenta
    //studentul poate vedea toate activitatille la care este inscris
    //renunta la cursuri?
    //sa isi vada notele
    //+chestii despre grupu de stidiu chat+stuff
}
