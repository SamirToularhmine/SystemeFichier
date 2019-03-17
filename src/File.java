import java.util.List;

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

    public String draw(){
        return this.draw(0);
    }

    public String draw(int n){
        String s ="";
            s+="\u001B[33m"+this.getNom() +" -\n"+"\u001B[0m";
        return s;
    }

}
