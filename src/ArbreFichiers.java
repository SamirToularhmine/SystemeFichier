import java.util.*;

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
//attention à méthode pas du tout prête à l'utilisation, les premiers fils des futures père ne sont pas mis à jour (par exemple)
    public ArbreFichiers(ArbreFichiers pere, ArbreFichiers premierFils, ArbreFichiers frereGauche, ArbreFichiers frereDroit, String nom, boolean fichier, String contenu, int taille) {
        this.pere = pere;
        this.premierFils = premierFils;
        this.frereGauche = frereGauche;
        this.frereDroit = frereDroit;
        this.nom = nom;
        this.fichier = fichier;
        this.contenu = contenu;
        this.taille = taille;
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

        public String getNom () {
        return nom;
    }

        public String getContenu () {
        return contenu;
    }

        public int getTaille () {
        return taille;
    }


    //setter
        public void setPere(ArbreFichiers pere) {
        this.pere = pere;
    }

        public void setPremierFils(ArbreFichiers premierFils) {
        this.premierFils = premierFils;
    }

        private void setFrereGauche(ArbreFichiers frereGauche) {
        this.frereGauche = frereGauche;
    }

        private void setFrereDroit(ArbreFichiers frereDroit) {
        this.frereDroit = frereDroit;
    }

        private void setNom(String nom) {
        this.nom = nom;
    }

        private void setFichier(boolean fichier) {
        this.fichier = fichier;
    }

        private void setContenu(String contenu) {
        this.contenu = contenu;
    }

        private void setTaille(int taille) {
        this.taille = taille;
    }


    public boolean isFirstSon(){
        return  getPere().getPremierFils() == this;
    }

    public List<ArbreFichiers> getSibling(){
        List sibling = new ArrayList();
        if (frereDroit != null) sibling.add(frereDroit);
        if (frereGauche != null) sibling.add(frereGauche);
        return sibling;
    }

    //verification si un chemin entre les deux noyeaux en ne passant que par les frères/soeurs gauches/droit(e)s parcours en largeur en partant de n1
    public boolean isSibling(ArbreFichiers n1){
        return parcoursLargeurFrere(this,n2 -> n1 == n2);
    }

   public boolean parcoursLargeurFrere(ArbreFichiers n1,Rule r){
       List<ArbreFichiers> atteint = new ArrayList();
       List<ArbreFichiers> f = new ArrayList();
       f.add(n1);
       atteint.add(n1);
       ListIterator<ArbreFichiers> it = f.listIterator();
       while((!f.isEmpty())&&it.hasNext()){
           ArbreFichiers current = it.next();
           List<ArbreFichiers> neighbour = current.getSibling();
           ListIterator<ArbreFichiers> itr = neighbour.listIterator();
           boolean exit = r.doTheRule(current);
           if(exit)return true;
           it.remove();
           while(itr.hasNext()){
               ArbreFichiers crt = itr.next();
               if (!atteint.contains(crt)){
                   it.add(crt);
                   atteint.add(crt);
               }
           }
           if(!it.hasNext()){
               it = f.listIterator();
           }
       }
       return false;
   }


    public void updateFirstSon(){
        ArbreFichiers son = this.getPremierFils();
        System.out.println("entrer parcours en largeur");
        parcoursLargeurFrere(son, n2 -> {
            if(n2.getFrereGauche()==null){

                System.out.println("this is ="+this);this.setPremierFils(n2);
                return true;
            }
            return false;
        });
        System.out.println("fin parcours en largeur");
    }

    private boolean areSibling(ArbreFichiers n1, ArbreFichiers n2){
        return n1.isSibling(n2);
    }
    public void addOnleft(ArbreFichiers toAdd){
            toAdd.setFrereGauche(this.getFrereGauche());
            this.setFrereGauche(toAdd);
            toAdd.setFrereDroit(this);
            toAdd.setPere(this.getPere());
            System.out.println("toAdd = "+toAdd);
            toAdd.getPere().updateFirstSon();
    }
    public void addOnRigth(ArbreFichiers toAdd){
        toAdd.setFrereGauche(this);
        toAdd.setFrereDroit(this.getFrereDroit());
        this.setFrereDroit(toAdd);
        toAdd.setPere(this.getPere());
        toAdd.getPere().updateFirstSon();
    }


    @Override
    public String toString() {
        String nomPere = (pere!=null)?pere.getNom():"null";
        String nomPremierFils = (premierFils!=null)?premierFils.getNom():"null";
        String nomFrereGauche = (frereGauche!=null)?frereGauche.getNom():"null";
        String nomFrereDroit = (frereDroit!=null)?frereDroit.getNom():"null";
        return "ArbreFichiers{" +
                "pere=" + nomPere +
                ", premierFils=" + nomPremierFils +
                ", frereGauche=" + nomFrereGauche +
                ", frereDroit=" + nomFrereDroit +
                ", nom='" + nom + '\'' +
                ", fichier=" + fichier +
                ", contenu='" + contenu + '\'' +
                ", taille=" + taille +
                '}';
    }
}
