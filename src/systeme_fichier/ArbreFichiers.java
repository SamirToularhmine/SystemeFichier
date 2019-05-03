package systeme_fichier;

import javax.annotation.processing.SupportedSourceVersion;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 *  Cette classe est la représentation d'un noeud dans une arborescencek
 */
public abstract class ArbreFichiers implements IArbreFichier, Comparable<ArbreFichiers>{

    private IArbreFichier pere;
    private IArbreFichier premierFils;
    private IArbreFichier frereGauche;
    private IArbreFichier frereDroit;
    private String nom;
    private boolean fichier;
    private String contenu;
    private int taille;

    protected ArbreFichiers(String nom) {
        this.nom = nom;
        this.pere = null;
        this.premierFils = null;
        this.frereGauche = null;
        this.frereDroit = null;
        this.fichier = false;
        this.contenu = "";
        this.taille = 0;
    }

    //on garde
    protected ArbreFichiers(String nom,int taille) {
        this.nom = nom;
        this.pere = null;
        this.premierFils = null;
        this.frereGauche = null;
        this.frereDroit = null;
        this.fichier = true;
        this.taille = taille;
        this.contenu = fillContenu();
    }
    //on garde
    protected ArbreFichiers(String nom,String contenu) {
        this.nom = nom;
        this.pere = null;
        this.premierFils = null;
        this.frereGauche = null;
        this.frereDroit = null;
        this.fichier = true;
        this.contenu = contenu;
        mettreAJourTaille(contenu.length()); // this.taille est mise à jour ici
    }

    protected ArbreFichiers(String nom,boolean isFichier){
        this.nom = nom;
        this.pere = null;
        this.premierFils = null;
        this.frereGauche = null;
        this.frereDroit = null;
        this.fichier = isFichier;
        this.taille = 0;
        this.contenu = "";
    }

    protected ArbreFichiers() {
        this.pere = null;
        this.premierFils = null;
        this.frereGauche = null;
        this.frereDroit = null;
        this.nom = "";
        this.fichier = false;
        this.contenu = "";
        this.taille = 0;
    }

    @Override
    public IArbreFichier getPere() {
        return this.pere;
    }

    @Override
    public IArbreFichier getPremierFils() {
        return this.premierFils;
    }

    @Override
    public IArbreFichier getFrereGauche() {
        return this.frereGauche;
    }

    @Override
    public IArbreFichier getFrereDroit() {
        return this.frereDroit;
    }

    public String getNom() {
        return this.nom;
    }

    @Override
    public boolean isFichier() {
        return this instanceof Fichier;
    }

    @Override
    public String getContenu() {
        return this.contenu;
    }

    @Override
    public int getTaille() {
        return taille;
    }

    public void setTaille(int taille) {
        this.taille = taille;
    }

    protected void setContenu(String contenu) {
        this.contenu = contenu;
        this.mettreAJourTaille(contenu.length()-taille);
        setTaille(contenu.length());
    }

    public String fillContenu(){
        String s ="";
        for(int i=0; i<taille;i++)s+=i;
        return s;
    }


    /**
     * Cette méthode permet d'effectuer un parcours en largeur permmettant de parcourir tous les frères d'un ArbreFichier
     *
     * @param n1 Noeud sur lequel le parcours commence commence
     * @param r ce parametre qui est une interface determine ce que l'on veut faire du parcours en largeur sur un noeud
     * @return la valeur de retour dépend grandement du paramètre r.
     */
    protected Object parcoursLargeurFrere(IArbreFichier n1, Rule r){
        if (n1 == null) {
            throw new RuntimeException("Argument n1 null");
        }
        List<IArbreFichier> atteint = new ArrayList();
        List<IArbreFichier> f = new ArrayList();
        f.add(n1);
        atteint.add(n1);
        Object exit = null;
        ListIterator<IArbreFichier> it = f.listIterator();
        while((!f.isEmpty())&&it.hasNext()){
            IArbreFichier current = it.next();
            List<IArbreFichier> neighbour = current.getFreres();
            ListIterator<IArbreFichier> itr = neighbour.listIterator();

            if(exit instanceof java.lang.Boolean && (boolean) exit) {
                return exit;
                //ici le type du return n'est pas encore déterminé
            }else if(!(exit instanceof ArbreFichiers)){
                exit = r.doTheRule(current);
            }else{
                //ici le type de return sera un systeme_fichier.ArbreFichiers
                return exit;
            }
            it.remove();
            while(itr.hasNext()){
                IArbreFichier crt = itr.next();
                if (!atteint.contains(crt)){
                    it.add(crt);
                    atteint.add(crt);
                }
            }
            //on revient au début de la liste
            if(!it.hasNext()){
                it = f.listIterator();
            }
        }
        if(exit instanceof java.lang.Boolean){
            return false;
        }
        return exit;
    }

    @Override
    public List<IArbreFichier> getFreres() {
        List<IArbreFichier> sibling = new ArrayList();
        if (this.frereDroit != null) sibling.add(frereDroit);
        if (frereGauche != null) sibling.add(frereGauche);
        return sibling;
    }

    @Override
    public void setPere(IArbreFichier pere) {
        this.pere = pere;
    }

    @Override
    public void setFrereDroit(IArbreFichier frereDroit) {
        this.frereDroit = frereDroit;
    }

    @Override
    public void setFrereGauche(IArbreFichier frereGauche) {
        this.frereGauche = frereGauche;
    }

    protected void removeSiblings(){
        this.frereDroit = null;
        this.frereGauche = null;
    }

    /**
     * Méthode permettant de supprimer le noeud,
     * le noeud en question va modifié ses attributs de façon à ce qu'il ne soit plus en liaison avec ses frères et de son père si il s'agissait du premier fils
     * @return retourne true si le noeud a bien été supprimé
     */
    @Override
    public boolean supprimerNoeud() {

        IArbreFichier crt = null;
        if (this.isRoot()) {
            return false;
        }
        Dossier d = (Dossier) this.pere;
        this.setTaille(-(this.getTaille()));
        //cas du fils unique
        if (this.getFrereGauche() == null && this.getFrereDroit() == null) {
            d.setPremierFils(null);
            d.mettreAJourPremierFils();
            d.mettreAJourTaille(this.getTaille());
            return true;
        } else {
            //cas si ce n'est pas le premierFils de son père
            if (!this.estPremierFils() && this.getFrereGauche() != null && this.getFrereDroit() != null) {

                this.getFrereGauche().setFrereDroit(this.getFrereDroit());
                this.getFrereDroit().setFrereGauche(this.getFrereGauche());

                d.mettreAJourPremierFils();
                d.mettreAJourTaille(this.getTaille());
                return true;
            } else {//si le premier fils n'est plus définis comme celui tout à gauche il faut en prendre un parmis ceux dispo puis mettre à jour selon la regle
                if (this.getFrereGauche() != null) {
                    crt = this.getFrereGauche();
                    //crt.setFrereDroit(this.getFrereGauche());
                    crt.setFrereDroit(null);

                } else {

                    crt = this.getFrereDroit();
                    crt.setFrereGauche(null);
                }
                d.setPremierFils(crt);
                d.mettreAJourPremierFils();

                d.mettreAJourTaille(this.getTaille());
                return true;
            }
        }
    }


    /**
     * Cette méthode est appelé récursivement sur le père (si prèsent) de cette objet en modifiant la taille pour concorder selon les changements
     * @param taille il s'agit de la nouvelle taille
     */
    protected void mettreAJourTaille(int taille){
        this.setTaille(this.taille + taille);
        if (this.pere != null) {
            ((ArbreFichiers)this.pere).mettreAJourTaille(taille);
        }
    }

    protected abstract String dessiner(int n, ArbreFichiers exrs);

    protected void setPremierFils(IArbreFichier pf) {
        this.premierFils = pf;
    }

    @Override
    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public boolean estPremierFils(){
        return  getPere().getPremierFils() == this;
    }

    protected boolean isRoot(){
        return this.pere == null;
    }

    public int compareTo(ArbreFichiers o){
        /*if(o instanceof ArbreFichiers){
            ArbreFichiers s = (ArbreFichiers)o;
            if(s.getNom().matches("^.*\\([1-9]+\\)$")){

            }
        }*/
        return this.getNom().compareToIgnoreCase(o.getNom());
    }
    @Override
    public boolean equals(Object o){
        if(o instanceof ArbreFichiers){
            ArbreFichiers arbreFichiers = (ArbreFichiers) o;
            return arbreFichiers.nom.equals(this.nom);
        }
        return false;
    }

    protected void ajouterNoeudDroite(ArbreFichiers toAdd){
        toAdd.setFrereGauche(this);
        if (this.getFrereDroit() != null) {
            this.getFrereDroit().setFrereGauche(toAdd);
        }
        toAdd.setFrereDroit(this.getFrereDroit());
        this.setFrereDroit(toAdd);
        toAdd.setPere(this.pere);
    }

    @Override
    public String cheminAbsolu(){
        // TODO :Vérifier le cas d'un fichier à la racine
        final String SEPARATEUR="/";
        String s=SEPARATEUR+this.nom;
        IArbreFichier node = this.pere;
        while(node!=null){
            s=SEPARATEUR+node.getNom()+s;
            node=node.getPere();
        }
        return s;
    }
    
    public String cheminRelatif(IArbreFichier debut){
        final String SEPARATEUR="/";
        String s=SEPARATEUR+this.nom;
        IArbreFichier node = this.pere;
        while(node!=debut){
            s=SEPARATEUR+node.getNom()+s;
            node=node.getPere();
        }
        return "."+s;
    }
    
    protected ArbreFichiers getThis(){
        return this;
    }

    @Override
    public String toString(){
        String nomPere = (pere!=null)?pere.getNom():"null";
        String nomFrereGauche = (frereGauche!=null)?frereGauche.getNom():"null";
        int n =nomFrereGauche.length()+(nom.length()/2)+3;
        String nomFrereDroit = (frereDroit!=null)?frereDroit.getNom():"null";
        String nomPremierFils = (premierFils!=null)?premierFils.getNom():"null";
        return ToolBox.nChar(" ",n,true)+"["+"\u001B[34m"+nomPere+"\u001B[0m"+"]"+"\n["+"\u001B[34m"+nomFrereGauche+"\u001B[0m"+"]<--[" + nom +","+taille+"]-->["+"\u001B[34m"+nomFrereDroit+"\u001B[0m"+"]\n"+ToolBox.nChar(" ",n,true)+"["+"\u001B[34m"+nomPremierFils+"\u001B[0m"+"]";
    }
}
