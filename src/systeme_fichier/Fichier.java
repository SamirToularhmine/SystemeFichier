package systeme_fichier;

public class Fichier implements ArbreFichier{

    private State state;

    public Fichier(String nom){
        this.state = new State(nom, "");
    }

    public Fichier(String nom, String contenu){
        this.state = new State(nom,contenu);
    }

    //pour les tests contenu rempli automatiquement
    public Fichier(String nom, int taille){
        this.state = new State(nom,taille);
    }


    public void newContenu(String contenu) {
        this.state.setContenu(contenu);
    }


    @Override
    public String dessiner(int n,ArbreFichier exRS){
        String s ="";
        s+="\u001B[33m"+this.state.getNom()+" - [ "+"\u001B[35m"+this.getInfos().getContenu()+"\u001B[0m"+"\u001B[33m"+" ] - "+"\n\u001B[0m";
        return s;
    }

    public void setContenu(String contenu){
        this.state.setContenu(contenu);
    }

    @Override
    public ArbreFichier getThis() {
        return this;
    }

    @Override
    public boolean supprimerNoeud(){
        return this.getInfos().supprimerNoeud();
    }

    @Override
    public State getInfos() {
        return this.state;
    }

    @Override
    public String dessiner() {
        return this.state.getNom();
    }

    @Override
    public boolean equals(Object o){
        return this.compareTo((ArbreFichier)o)==0;
    }

    @Override
    public int compareTo(ArbreFichier af) {

        return af.getInfos().compareTo(this.getInfos());

    }
}
