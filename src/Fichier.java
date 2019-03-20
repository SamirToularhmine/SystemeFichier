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
    public String draw(int n,ArbreFichier exRS){
        String s ="";
        s+="\u001B[33m"+this.state.getNom() +" -\n"+"\u001B[0m";
        return s;
    }

    public void addOnRigthIgnoringFather(ArbreFichier toAdd){
        toAdd.getInfos().setFrereGauche(this);
        if (this.getInfos().getFrereDroit() != null) {
            this.getInfos().getFrereDroit().getInfos().setFrereGauche(toAdd);
        }
        toAdd.getInfos().setFrereDroit(this.getInfos().getFrereDroit());
        this.getInfos().setFrereDroit(toAdd);
        toAdd.getInfos().setPere(this.getInfos().getPere());

    }

    @Override
    public ArbreFichier getThis() {
        return this;
    }

    @Override
    public State getInfos() {
        return this.state;
    }

    @Override
    public String draw() {
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
