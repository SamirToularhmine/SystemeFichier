package systeme_fichier;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public abstract class ArbreFichiers implements IArbreFichier, Comparable<ArbreFichiers>{

    private ArbreFichiers pere;
    private ArbreFichiers premierFils;
    private ArbreFichiers frereGauche;
    private ArbreFichiers frereDroit;
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
        this.taille = contenu.length();
        this.contenu = contenu;
        mettreAJourTaille(this.taille);
        System.out.println("contenu = "+contenu+" Nom = "+ nom);
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

    protected ArbreFichiers getPere() {
        return pere;
    }

    protected ArbreFichiers getPremierFils() {
        return premierFils;
    }

    protected ArbreFichiers getFrereGauche() {
        return frereGauche;
    }

    protected ArbreFichiers getFrereDroit() {
        return frereDroit;
    }

    public String getNom() {
        return nom;
    }

    protected boolean isFichier() {
        return fichier;
    }

    protected String getContenu() {
        return contenu;
    }

    protected int getTaille() {
        return taille;
    }

    protected void setTaille(int taille) {
        this.taille = taille;
    }

    protected void setContenu(String contenu) {
        this.contenu = contenu;
        this.mettreAJourTaille(taille);
    }

    public String fillContenu(){
        String s ="";
        for(int i=0; i<taille;i++)s+=i;
        return s;
    }

    protected Object parcoursLargeurFrere(ArbreFichiers n1, Rule r)throws RuntimeException{
        if (n1 == null) {
            throw new RuntimeException("Argument n1 null");
        }
        List<ArbreFichiers> atteint = new ArrayList();
        List<ArbreFichiers> f = new ArrayList();
        f.add(n1);
        atteint.add(n1);
        Object exit = null;
        ListIterator<ArbreFichiers> it = f.listIterator();
        while((!f.isEmpty())&&it.hasNext()){
            ArbreFichiers current = it.next();
            List<ArbreFichiers> neighbour = current.getSibling();
            ListIterator<ArbreFichiers> itr = neighbour.listIterator();

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
                ArbreFichiers crt = itr.next();
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

    private List<ArbreFichiers> getSibling() {
        List<ArbreFichiers> sibling = new ArrayList();
        if (this.frereDroit != null) sibling.add(frereDroit);
        if (frereGauche != null) sibling.add(frereGauche);
        return sibling;
    }

    protected void setPere(ArbreFichiers pere) {
        this.pere = pere;
    }

    protected void setPremierFils(ArbreFichiers premierFils) {
        this.premierFils = premierFils;
    }

    protected void setFrereDroit(ArbreFichiers frereDroit) {
        this.frereDroit = frereDroit;
    }

    protected void setFrereGauche(ArbreFichiers frereGauche) {
        this.frereGauche = frereGauche;
    }

    protected void removeSiblings(){
        this.frereDroit = null;
        this.frereGauche = null;
    }

    @Override
    public boolean supprimerNoeud() {

        ArbreFichiers crt = null;
        if (this.isRoot()) {
            return false;
        }
        ArbreFichiers dad = this.pere;
        Dossier d = (Dossier) dad;
        this.setTaille(-(this.getTaille()));
        //cas du fils unique
        if (this.getFrereGauche() == null && this.getFrereDroit() == null) {
            d.setPremierFils(null);
            d.mettreAJourPremierFils();
            d.mettreAJourTaille(this.getTaille());
            return true;
        } else {
            //cas si ce n'est pas le premierFils de son père
            if (!this.isFirstSon() && this.getFrereGauche() != null && this.getFrereDroit() != null) {

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

    protected void mettreAJourTaille(int taille){
        this.setTaille(this.getTaille()+taille);
        if (this.pere != null) {
            this.pere.mettreAJourTaille(taille);
        }
    }

    protected abstract String dessiner(int n, ArbreFichiers exrs);

    protected void setNom(String nom) {
        this.nom = nom;
    }

    protected boolean isFirstSon(){
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
        return -this.getNom().compareToIgnoreCase(o.getNom());
    }

    public void ajouterNoeudDroite(ArbreFichiers toAdd){
        toAdd.setFrereGauche(this);
        if (this.getFrereDroit() != null) {
            this.getFrereDroit().setFrereGauche(toAdd);
        }
        toAdd.setFrereDroit(this.getFrereDroit());
        this.setFrereDroit(toAdd);
        toAdd.setPere(this.pere);
    }

    public String cheminAbsolu(){
        // TODO :Vérifier le cas d'un fichier à la racine
        final String SEPARATEUR="/";
        String s=SEPARATEUR+this.nom;
        ArbreFichiers node = this.pere;
        while(node!=null){
            s=SEPARATEUR+node.getNom()+s;
            node=node.getPere();
        }
        return s;
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