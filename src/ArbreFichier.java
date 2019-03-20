public interface ArbreFichier extends Comparable<ArbreFichier> {

    public State getInfos();

    public ArbreFichier getThis();

    public String dessiner(int n,ArbreFichier exRS);

    public String dessiner();

    public default void ajouterNoeudDroite(ArbreFichier toAdd){
        toAdd.getInfos().setFrereGauche(this);
        if (this.getInfos().getFrereDroit() != null) {
            this.getInfos().getFrereDroit().getInfos().setFrereGauche(toAdd);
        }
        toAdd.getInfos().setFrereDroit(this.getInfos().getFrereDroit());
        this.getInfos().setFrereDroit(toAdd);
        toAdd.getInfos().setPere(this.getInfos().getPere());
    }

}
