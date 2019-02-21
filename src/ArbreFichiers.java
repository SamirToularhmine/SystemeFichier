import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ArbreFichiers {

    private ArbreFichiers pere;
    private ArbreFichiers premierFils;
    private ArbreFichiers frereGauche;
    private ArbreFichiers frereDroit;
    //Correspond au nom du dossier/fichier correspondant au noeud
    private String nom;
    //True si le noeud correspond à un fichier, false sinon
    private boolean fichier;
    //contenu du fichier ou null si dossier
    private String contenu;
    //Correspond à la taille en octets du dossier/fichier correspondant au noeud
    private int taille;

    public ArbreFichiers(){
    }

    public boolean isFichier() {
        return fichier;
    }

    public String nodeInfos(){
        final String FICHIER="f";
        final String DOSSIER="d";
        String s="";
        ArbreFichiers node=this.premierFils;
        while(node!=null){
            if(node.isFichier()){
                s+=FICHIER;
            }else{
                s+=DOSSIER;
            }
            s+=" "+node.nom+" "+node.taille+"\n";
            node=node.frereDroit;
        }
        return s;
    }

    public String rootToNode(){
        final String SEPARATEUR="/";
        String s=SEPARATEUR+this.nom;
        ArbreFichiers node=this.pere;
        while(node!=null){
            s=SEPARATEUR+node.nom+s;
            node=node.pere;
        }
        return s;
    }
    protected boolean addNode(ArbreFichiers n1,ArbreFichiers n2){
        if (n1.getPremierFils() == null) {
            n1.setPremierFils(n2);
        }else{

        }

        return false;
    }

    protected boolean supprimerNoeud(){

        return false;
    }
    //getter

        public ArbreFichiers getPere () {
        return pere;
    }

        public ArbreFichiers getPremierFils () {
        return premierFils;
    }

        public ArbreFichiers getFrereGauche () {
        return frereGauche;
    }

        public ArbreFichiers getFrereDroit () {
        return frereDroit;
    }

        private String getNom () {
        return nom;
    }

        private String getContenu () {
        return contenu;
    }

        private int getTaille () {
        return taille;
    }


    //setter
        public void setPere(ArbreFichiers pere) {
        this.pere = pere;
    }

        public void setPremierFils(ArbreFichiers premierFils) {
        this.premierFils = premierFils;
    }

        public void setFrereGauche(ArbreFichiers frereGauche) {
        this.frereGauche = frereGauche;
    }

        public void setFrereDroit(ArbreFichiers frereDroit) {
        this.frereDroit = frereDroit;
    }

        public void setNom(String nom) {
        this.nom = nom;
    }

        public void setFichier(boolean fichier) {
        this.fichier = fichier;
    }

        public void setContenu(String contenu) {
        this.contenu = contenu;
    }

        public void setTaille(int taille) {
        this.taille = taille;
    }


    public boolean isFirstSon(ArbreFichiers n1){
        return getPremierFils() == n1;
    }
    public boolean isFirstSon(ArbreFichiers n1, ArbreFichiers n2){
        return n1.isFirstSon(n2);
    }
    public List getSibling(){
        List sibling = new ArrayList();
        if (frereDroit != null) sibling.add(frereDroit);
        if (frereGauche != null) sibling.add(frereGauche);
        return sibling;
    }

    //verification si un chemin entre les deux noyeaux en ne passant que par les frères/soeurs gauches/droit(e)s parcours en largeur en partant de n1
    public boolean areSibling(ArbreFichiers n1,ArbreFichiers n2){
        List<ArbreFichiers> atteint = new ArrayList();
        List<ArbreFichiers> f = new ArrayList();
        f.add(n1);
        atteint.add(n1);
        while(f.listIterator().hasNext()){

        }

        return false;
    }
    public boolean addNodeBetween(ArbreFichiers n1, ArbreFichiers n2){

        return false;
    }
}
