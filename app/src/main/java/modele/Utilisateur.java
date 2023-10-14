package modele;

import java.util.List;
import java.util.Objects;

public class Utilisateur {
    private String nomUtilisateur;
    private String courriel;
    private String motDePasse;
    private List<String> listeEvenement;

    public Utilisateur() {
    }

    public Utilisateur(String nomUtilisateur, String courriel, String motDePasse, List<String> listeEvenement) {
        this.nomUtilisateur = nomUtilisateur;
        this.courriel = courriel;
        this.motDePasse = motDePasse;
        this.listeEvenement = listeEvenement;
    }

    public String getNomUtilisateur() {
        return nomUtilisateur;
    }

    public void setNomUtilisateur(String nomUtilisateur) {
        this.nomUtilisateur = nomUtilisateur;
    }

    public String getCourriel() {
        return courriel;
    }

    public void setCourriel(String courriel) {
        this.courriel = courriel;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public List<String> getListeEvenement() {
        return listeEvenement;
    }

    public void setListeEvenement(List<String> listeEvenement) {
        this.listeEvenement = listeEvenement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Utilisateur that = (Utilisateur) o;
        return Objects.equals(nomUtilisateur, that.nomUtilisateur) && Objects.equals(courriel, that.courriel) && Objects.equals(motDePasse, that.motDePasse) && Objects.equals(listeEvenement, that.listeEvenement);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nomUtilisateur, courriel, motDePasse, listeEvenement);
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "nomUtilisateur='" + nomUtilisateur + '\'' +
                ", courriel='" + courriel + '\'' +
                ", motDePasse='" + motDePasse + '\'' +
                ", listeEvenement=" + listeEvenement +
                '}';
    }
}
