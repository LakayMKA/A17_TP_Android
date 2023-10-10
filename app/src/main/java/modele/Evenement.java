package modele;

public class Evenement {

    private String nom;
    private String adresse;
    private String date;

    private String description;
    private String type;
    private int nombreParticipant;

    public Evenement() {
    }

    public Evenement(String nom, String date, int nombreParticipant) {
        this.nom = nom;
        this.date = date;
        this.nombreParticipant = nombreParticipant;
    }

    public Evenement(String nom,String adresse, String date, String description, String type, int nombreParticipant) {

        this.nom = nom;
        this.adresse = adresse;
        this.date = date;
        this.type = type;
        this.nombreParticipant = nombreParticipant;
        this.description = description;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getNombreParticipant() {
        return nombreParticipant;
    }

    public void setNombreParticipant(int nombreParticipant) {
        this.nombreParticipant = nombreParticipant;
    }

    @Override
    public String toString() {
        return "Evenement{" +
                ", nom='" + nom + '\'' +
                ", date='" + date + '\'' +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", nombreParticipant=" + nombreParticipant +
                '}';
    }
}