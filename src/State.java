import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class State{

    private ArbreFichier pere;
    private ArbreFichier premierFils;
    private ArbreFichier frereGauche;
    private ArbreFichier frereDroit;
    private String nom;
    private boolean fichier;
    private String contenu;
    private int taille;

    public State(String nom) {
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
    public State(String nom,int taille) {
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
    public State(String nom,String contenu) {
        this.nom = nom;
        this.pere = null;
        this.premierFils = null;
        this.frereGauche = null;
        this.frereDroit = null;
        this.fichier = true;
        this.taille = contenu.length();
        this.contenu = contenu;
    }

    public State(String nom,boolean isFichier){
        this.nom = nom;
        this.pere = null;
        this.premierFils = null;
        this.frereGauche = null;
        this.frereDroit = null;
        this.fichier = false;
        this.taille = 0;
        this.contenu = "";
    }

    public State() {
        this.pere = null;
        this.premierFils = null;
        this.frereGauche = null;
        this.frereDroit = null;
        this.nom = "";
        this.fichier = false;
        this.contenu = "";
        this.taille = 0;
    }

    public ArbreFichier getPere() {
        return pere;
    }

    public ArbreFichier getPremierFils() {
        return premierFils;
    }

    public ArbreFichier getFrereGauche() {
        return frereGauche;
    }

    public ArbreFichier getFrereDroit() {
        return frereDroit;
    }

    public String getNom() {
        return nom;
    }

    public boolean isFichier() {
        return fichier;
    }

    public String getContenu() {
        return contenu;
    }

    public int getTaille() {
        return taille;
    }

    public void setTaille(int taille) {
        this.taille = taille;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public String fillContenu(){
        String s ="";
        for(int i=0; i<taille;i++)s+=i;
        return s;
    }

    public Object parcoursLargeurFrere(ArbreFichier n1,Rule r)throws RuntimeException{
        if (n1 == null) {
            throw new RuntimeException("Argument n1 null");
        }
        List<ArbreFichier> atteint = new ArrayList();
        List<ArbreFichier> f = new ArrayList();
        f.add(n1);
        atteint.add(n1);
        Object exit = null;
        ListIterator<ArbreFichier> it = f.listIterator();
        while((!f.isEmpty())&&it.hasNext()){
            ArbreFichier current = it.next();
            List<ArbreFichier> neighbour = current.getInfos().getSibling();
            ListIterator<ArbreFichier> itr = neighbour.listIterator();

            if(exit instanceof java.lang.Boolean && (boolean) exit) {
                return exit;
                //ici le type du return n'est pas encore déterminé
            }else if(!(exit instanceof ArbreFichier)){
                exit = r.doTheRule(current);
            }else{
                //ici le type de return sera un ArbreFichier
                return exit;
            }
            it.remove();
            while(itr.hasNext()){
                ArbreFichier crt = itr.next();
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

    public List<ArbreFichier> getSibling() {
        List<ArbreFichier> sibling = new ArrayList();
        if (this.frereDroit != null) sibling.add(frereDroit);
        if (frereGauche != null) sibling.add(frereGauche);
        return sibling;
    }

    public void setPere(ArbreFichier pere) {
        this.pere = pere;
    }

    public void setPremierFils(ArbreFichier premierFils) {
        this.premierFils = premierFils;
    }

    public void setFrereDroit(ArbreFichier frereDroit) {
        this.frereDroit = frereDroit;
    }

    public void setFrereGauche(ArbreFichier frereGauche) {
        this.frereGauche = frereGauche;
    }

    public void removeSiblings(){
        this.frereDroit = null;
        this.frereGauche = null;
    }

    public boolean removeNode() {

        ArbreFichier crt = null;
        if (this.isRoot()) {
            return false;
        }
        ArbreFichier dad = this.getPere();
        Dossier d = (Dossier) dad;
        this.setTaille(-(this.getTaille()));
        //cas du fils unique
        if (this.getFrereGauche() == null && this.getFrereDroit() == null) {
            d.getInfos().setPremierFils(null);
            d.updateFirstSon();
            d.getInfos().updateLength(this.getTaille());
            return true;
        } else {
            //cas si ce n'est pas le premierFils de son père
            if (!this.isFirstSon() && this.getFrereGauche() != null && this.getFrereDroit() != null) {

                this.getFrereGauche().getInfos().setFrereDroit(this.getFrereDroit());
                this.getFrereDroit().getInfos().setFrereGauche(this.getFrereGauche());

                d.updateFirstSon();
                d.getInfos().updateLength(this.getTaille());
                return true;
            } else {//si le premier fils n'est plus définis comme celui tout à gauche il faut en prendre un parmis ceux dispo puis mettre à jour selon la regle
                if (this.getFrereGauche() != null) {
                    crt = this.getFrereGauche();
                    //crt.setFrereDroit(this.getFrereGauche());
                    crt.getInfos().setFrereDroit(null);

                } else {

                    crt = this.getFrereDroit();
                    crt.getInfos().setFrereGauche(null);
                }
                d.getInfos().setPremierFils(crt);
                d.updateFirstSon();

                d.getInfos().updateLength(this.getTaille());
                return true;
            }
        }
    }

    public void updateLength(int taille){
        this.setTaille(this.getTaille()+taille);
        if (this.getPere() != null) {
            this.getPere().getInfos().updateLength(taille);
        }
    }


    public void setNom(String nom) {
        this.nom = nom;
    }

    public State getThis() {
        return this;
    }

    public boolean isFirstSon(){
        return  getPere().getInfos().getPremierFils().getInfos() == this;
    }

    public boolean isRoot(){
        return this.getPere() == null;
    }

    public int compareTo(Object o){
        return -getNom().compareToIgnoreCase(((State)o).getNom());
    }

}
