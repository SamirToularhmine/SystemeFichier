public class ArbreFichiers {

    private ArbreFichiers pere;
    private ArbreFichiers premierFils;
    private ArbreFichiers frereGauche;
    private ArbreFichiers frereDroit;
    //Correspond au nom du dossier/fichier correspondant au noeud
    private String nom;
    //True si le noeud correspond à un fichier, false sinon
    private boolean fichier;
    //Correspond à la taille en octets du dossier/fichier correspondant au noeud
    private int taille;

    public ArbreFichiers(){

    }
}
