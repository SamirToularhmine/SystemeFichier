public class ArbreFichiers {

    private ArbreFichiers pere;
    private ArbreFichiers premierFils;
    private ArbreFichiers frereGauche;
    private ArbreFichiers frereDroit;
    //Correspond au nom du dossier/fichier correspondant au noeud
    private String nom;
    //Correspond au contenu du fichier
    private String contenu;
    //True si le noeud correspond à un fichier, false sinon
    private boolean fichier;
    //Correspond à la taille en octets du dossier/fichier correspondant au noeud
    private int taille;

    public ArbreFichiers(){
        this.pere = null;
        this.premierFils = null;
        this.frereGauche = null;
        this.frereDroit = null;
        this.nom = "";
        this.contenu = "";
        this.fichier = false;
        this.taille = 0;
    }

    public ArbreFichiers(ArbreFichiers pere, ArbreFichiers premierFils, ArbreFichiers frereGauche, ArbreFichiers frereDroit, String nom, String contenu, boolean fichier, int taille){
        this.pere = pere;
        this.premierFils = premierFils;
        this.frereGauche = frereGauche;
        this.frereDroit = frereDroit;
        this.nom = nom;
        this.contenu = contenu;
        this.fichier = fichier;
        this.taille = taille;
    }
}
