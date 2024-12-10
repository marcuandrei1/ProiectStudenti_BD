public class Administrator extends Utilizator{
    private boolean ifSuper;
    public Administrator(String username, String password, String CNP, String nume, String prenume, String numarTelefon, String email, String IBAN, int nrContract,String adresa) {
        super(username,password,CNP,nume,prenume,numarTelefon,email,IBAN,nrContract,adresa);
        System.out.println(numarTelefon);
        this.ifSuper=false;
    }
}
