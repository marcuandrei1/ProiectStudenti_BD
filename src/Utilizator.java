// TODO: de facut clasa abstracta si de facut clasele: student, administrator, profesor
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
    private String adresa;

    public Utilizator(String username, String password, String CNP, String nume, String prenume, String numarTelefon, String email, String IBAN, int nrContract,String adresa) {
        this.username = username;
        this.password = password;
        this.CNP = CNP;
        this.nume = nume;
        this.prenume = prenume;
        this.numarTelefon = numarTelefon;
        this.email = email;
        this.IBAN = IBAN;
        this.nrContract = nrContract;
        this.adresa = adresa;
    }

    public String getUsername() {
        return username;
    }

    public String getAdresa() {
        return adresa;
    }

    public String getPassword() {
        return password;
    }

    public String getCNP() {
        return CNP;
    }

    public String getNume() {
        return nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public String getNumarTelefon() {
        return numarTelefon;
    }

    public String getEmail() {
        return email;
    }

    public String getIBAN() {
        return IBAN;
    }

    public int getNrContract() {
        return nrContract;
    }
}
