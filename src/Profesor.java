public class Profesor extends Utilizator{
    private int nrMinOre;
    private int nrMaxOre;
    private String departament;
    public Profesor(String username, String password, String CNP, String nume, String prenume, String numarTelefon, String email, String IBAN, int nrContract,String adresa, int nrMinOre, int nrMaxOre, String departament) {
        super(username,password,CNP,nume,prenume,numarTelefon,email,IBAN,nrContract,adresa);
        this.nrMinOre=nrMinOre;
        this.nrMaxOre=nrMaxOre;
        this.departament=departament;
    }
}
