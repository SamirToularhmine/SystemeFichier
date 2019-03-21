package systeme_fichier;

public interface ArbreFichier extends Comparable<ArbreFichier> {

    public State getInfos();

    public ArbreFichier getThis();

    public String dessiner(int n,ArbreFichier exRS);

    public boolean supprimerNoeud();

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

    public default String cheminAbsolu(){
        // TODO :Vérifier le cas d'un fichier à la racine
        final String SEPARATEUR="/";
        String s=SEPARATEUR+this.getInfos().getNom();
        ArbreFichier node=this.getInfos().getPere();
        while(node!=null){
            s=SEPARATEUR+node.getInfos().getNom()+s;
            node=node.getInfos().getPere();
        }
        return s;
    }

}
