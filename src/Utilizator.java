public class Utilizator {
    private String username;
    private String password;
    private String CNP;
    private String nume;
    private String prenume;
    private String numarTelefon;
    private String email;
    private String IBAN;
    private int nrContract;

    public Utilizator(String username, String password, String CNP, String nume, String prenume, String numarTelefon, String email, String IBAN, int nrContract) {
        this.username = username;
        this.password = password;
        this.CNP = CNP;
        this.nume = nume;
        this.prenume = prenume;
        this.numarTelefon = numarTelefon;
        this.email = email;
        this.IBAN = IBAN;
        this.nrContract = nrContract;
    }
}
