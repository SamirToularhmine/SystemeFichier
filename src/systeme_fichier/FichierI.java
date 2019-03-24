package systeme_fichier;

public class FichierI extends ArbreFichiers {

    public FichierI(String nom){
        super(nom, "");
    }

    public FichierI(String nom, String contenu){
        super(nom,contenu);
        System.out.println("FICHIER contenu ="+contenu);
    }

    //pour les tests contenu rempli automatiquement
    public FichierI(String nom, int taille){
        super(nom,taille);
    }


    public void newContenu(String contenu) {
        super.setContenu(contenu);
    }

    public String dessiner(int n, ArbreFichiers exRS){
        String s ="";
        s+="\u001B[33m"+super.getNom()+" - [ "+"\u001B[35m"+super.getContenu()+"\u001B[0m"+"\u001B[33m"+" ] - "+"\n\u001B[0m";
        return s;
    }

    public void setContenu(String contenu){
        super.setContenu(contenu);
    }

    @Override
    public boolean supprimerNoeud(){
        return super.supprimerNoeud();
    }

    @Override
    public String dessiner() {
        return super.getNom();
    }

    @Override
    public boolean equals(Object o){
        return this.compareTo((ArbreFichiers) o)==0;
    }

    @Override
    public String toString(){
        return "\u001B[33m"+super.getNom()+"\u001B[0m"+" - [taille :"+super.getTaille()+"]";
    }


}