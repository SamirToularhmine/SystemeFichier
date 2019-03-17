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



    public String draw(int n){
        String s ="";
        s+="\u001B[33m"+this.getNom() +" -\n"+"\u001B[0m";
        return s;
    }
}
