package systeme_fichier;

/**
 * Cette classe est un Arbrefichier qui peut avoir du contenu
 */
public class Fichier extends ArbreFichiers {

    public Fichier(String nom){
        super(nom, "");
    }

    public Fichier(String nom, String contenu){
        super(nom,contenu);
    }

    protected Fichier(Fichier f){
        super(f);
    }

    //pour les tests contenu rempli automatiquement
    public Fichier(String nom, int taille){
        super(nom,taille);
    }


    public void newContenu(String contenu) {
        super.setContenu(contenu);
    }

    public String dessiner(int n, ArbreFichiers exRS){
        String s ="";
        s+="\u001B[33m"+super.getNom()+" -"+" [t:"+this.getTaille()+"]"+" [ "+"\u001B[35m"+super.getContenu()+"\u001B[0m"+"\u001B[33m"+" ] - "+"\n\u001B[0m";
        return s;
    }

    public void setContenu(String contenu){
        super.setContenu(contenu);
    }

    @Override
    public boolean supprimerNoeud(){
        return super.supprimerNoeud();
    }

    /**
     * methode necessaire au dessin d'un arbrefichier
     * @return
     */
    @Override
    public String dessiner() {
        return super.getNom();
    }

    public ArbreFichiers getCopy(){
        return new Fichier(this);
    }

    @Override
    public String toString(){
        return "\u001B[33m"+super.getNom()+"\u001B[0m"+" - [taille :"+super.getTaille()+"]";
    }


}
