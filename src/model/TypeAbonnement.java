package model;

public class TypeAbonnement {

    private int idType;
    private String libelle;
    private int dureeMois;
    private double prix;

    public TypeAbonnement() {}

    public TypeAbonnement(int idType, String libelle, int dureeMois, double prix) {
        this.idType = idType;
        this.libelle = libelle;
        this.dureeMois = dureeMois;
        this.prix = prix;
    }

    public int getIdType() {
        return idType;
    }

    public void setIdType(int idType) {
        this.idType = idType;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public int getDureeMois() {
        return dureeMois;
    }

    public void setDureeMois(int dureeMois) {
        this.dureeMois = dureeMois;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }
    @Override
    public String toString() {
        return libelle;
    }

}
