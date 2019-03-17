public class Fichier extends ArbreFichiers{

    public Fichier(String nom){
        super(nom,"");
    }

    public Fichier(String nom, String contenu){
        super(nom,contenu);
    }

    //pour les tests contenu rempli automatiquement
    public Fichier(String nom, int taille){
        super(nom,taille);
    }

}
