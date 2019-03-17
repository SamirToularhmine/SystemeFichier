public class File extends ArbreFichiers{

    public File(String nom){
        super(nom,"");
    }

    public File(String nom,String contenu){
        super(nom,contenu);
    }

    //pour les tests contenu rempli automatiquement
    public File(String nom,int taille){
        super(nom,taille);
    }

}
