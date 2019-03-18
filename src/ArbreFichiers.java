import java.util.*;

public abstract class ArbreFichiers implements Comparable<ArbreFichiers>,Cloneable{

    //TODO: Systeme de type

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


    public ArbreFichiers(String nom) {
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
    public ArbreFichiers(String nom,int taille) {
        this.nom = nom;
        this.pere = null;
        this.premierFils = null;
        this.frereGauche = null;
        this.frereDroit = null;
        this.fichier = true;
        this.taille = taille;
        this.contenu = fillContenue();
    }
    //on garde
    public ArbreFichiers(String nom,String contenu) {
        this.nom = nom;
        this.pere = null;
        this.premierFils = null;
        this.frereGauche = null;
        this.frereDroit = null;
        this.fichier = true;
        this.taille = contenu.length();
        this.contenu = contenu;
    }

    public ArbreFichiers(String nom,boolean isFichier){
        this.nom = nom;
        this.pere = null;
        this.premierFils = null;
        this.frereGauche = null;
        this.frereDroit = null;
        this.fichier = false;
        this.taille = 0;
        this.contenu = "";
    }

    public ArbreFichiers() {

        this.pere = null;
        this.premierFils = null;
        this.frereGauche = null;
        this.frereDroit = null;
        this.nom = "";
        this.fichier = false;
        this.contenu = "";
        this.taille = 0;
    }

    public boolean isFichier() {
        return fichier;
    }

    public String nodeInfo(){
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
// TODO :Vérifier le cas d'un fichier à la racine
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




    public ArbreFichiers getExtremLeftSon(){
        if (this.haveNoChild())return null;
        ArbreFichiers a = (ArbreFichiers) parcoursLargeurFrere(this.getPremierFils(),(b)->{
            if(b.getFrereGauche()==null) {
                return b;
            }
            return null;
        });
        return a;
    }
    public ArbreFichiers getExtremRightSon(){
        if (this.haveNoChild())return null;
        ArbreFichiers a = (ArbreFichiers) parcoursLargeurFrere(this.getPremierFils(),(b)->{
            if(b.getFrereDroit()==null) {
                return b;
            }
            return null;
        });
        return a;
    }
    //on mets les enfants dans une liste en gardant l'ordre
    public List<ArbreFichiers> childrenToList(){
        List<ArbreFichiers> l = new ArrayList<>();
        if(this.haveNoChild()){
            return l;
        }
        ArbreFichiers crt = this.getExtremLeftSon();
        while(crt!=null){
            l.add(crt);
            crt = crt.getFrereDroit();
        }
        return l;
    }

public void listToChildren(List<ArbreFichiers> l){
        if(!l.isEmpty()){
            ArbreFichiers first_element = l.get(0);
            first_element.removeSiblings();
            this.setPremierFils(first_element);
            if(l.size()>1){
                ListIterator<ArbreFichiers> it = l.listIterator(1);
                while(it.hasNext()){
                    ArbreFichiers prev = l.get(it.previousIndex());
                    ArbreFichiers crt = it.next();
                    crt.removeSiblings();
                    prev.addOnRigthIgnoringFather(crt);
                }
            }
        }else{
            this.setPremierFils(null);
        }
        this.updateFirstSon();
}
public void removeSiblings(){
        this.setFrereDroit(null);
        this.setFrereGauche(null);
}
    //todo updateLength removeNode
    public boolean removeNode(){

        ArbreFichiers crt = null;
        if(this.isRoot()){
            return false;
        }
        ArbreFichiers dad = this.getPere();
        this.setTaille(-(this.getTaille()));
        //cas du fils unique
        if(this.getFrereGauche()==null&&this.getFrereDroit()==null){
            dad.setPremierFils(null);
            dad.updateFirstSon();
            dad.updateLength(this.getTaille());
            return true;
        }else{
            //cas si ce n'est pas le premierFils de son père
            if(!this.isFirstSon()&&this.getFrereGauche()!=null&&this.getFrereDroit()!=null){

                this.getFrereGauche().setFrereDroit(this.getFrereDroit());
                this.getFrereDroit().setFrereGauche(this.getFrereGauche());

                dad.updateFirstSon();
                dad.updateLength(this.getTaille());
                return true;
            }else{//si le premier fils n'est plus définis comme celui tout à gauche il faut en prendre un parmis ceux dispo puis mettre à jour selon la regle
                if(this.getFrereGauche()!=null){
                    crt = this.getFrereGauche();
                    //crt.setFrereDroit(this.getFrereGauche());
                    crt.setFrereDroit(null);

                }else{

                    crt = this.getFrereDroit();
                    crt.setFrereGauche(null);
                }
                dad.setPremierFils(crt);
                dad.updateFirstSon();

                dad.updateLength(this.getTaille());
                return true;
            }
        }
    }
    //getter

        public ArbreFichiers getPere () {
        return pere;
    }

        public ArbreFichiers getThis () { return this; }

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

        public void setFrereGauche(ArbreFichiers frereGauche) {
        this.frereGauche = frereGauche;
    }

        public void setFrereDroit(ArbreFichiers frereDroit) {
        this.frereDroit = frereDroit;
    }

        public void setNom(String nom) {
        this.nom = nom;
    }

        private void setFichier(boolean fichier) {
        this.fichier = fichier;
    }

        public void setContenu(String contenu) {

            this.contenu = contenu;
            int newTaille = contenu.length();
            this.updateLength(newTaille-this.getTaille());

    }

        private void setTaille(int taille) {


            this.taille = taille;
    }


    public boolean isFirstSon(){
        return  getPere().getPremierFils() == this;
    }

    public List<ArbreFichiers> getSibling(){
        List<ArbreFichiers> sibling = new ArrayList();
        if (frereDroit != null) sibling.add(frereDroit);
        if (frereGauche != null) sibling.add(frereGauche);
        return sibling;
    }

    //verification si un chemin entre les deux noyeaux en ne passant que par les frères/soeurs gauches/droit(e)s parcours en largeur en partant de n1
    public boolean isSibling(ArbreFichiers n1){
        return (boolean) parcoursLargeurFrere(this,n2 -> n1 == n2);
    }

   public Object parcoursLargeurFrere(ArbreFichiers n1,Rule r)throws RuntimeException{
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
               //ici le type de return sera un ArbreFichier
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

   public boolean isRoot(){
        return this.getPere() == null;
   }
    public boolean haveNoChild(){
        return this.getPremierFils() == null;
    }

    public void updateFatherLength(){
        if (this.getPere() != null) {
            this.getPere().updateLength(this.taille);
        }
    }

    public void updateLength(int taille){
        this.setTaille(this.getTaille()+taille);
        if (this.getPere() != null) {
            this.getPere().updateLength(taille);
        }
    }
    public void updateFirstSon(){
        //le cas où this a au moins un enfant
        if(!haveNoChild()) {
            ArbreFichiers son = this.getPremierFils();
            parcoursLargeurFrere(son, n2 -> {

                if (n2.getFrereGauche() == null) {

                    this.setPremierFils(n2);
                    return true;
                }
                return false;
            });
        }
    }

    private boolean areSibling(ArbreFichiers n1, ArbreFichiers n2){
        return n1.isSibling(n2);
    }
    public void addOnleft(ArbreFichiers toAdd){

        this.addOnleftIgnoringFather(toAdd);
        toAdd.getPere().setPremierFils(toAdd);
        toAdd.getPere().updateFirstSon();
    }
    public void addOnleftIgnoringFather(ArbreFichiers toAdd){

        if (this.getFrereGauche() != null) {
            this.getFrereGauche().setFrereDroit(toAdd);
        }
        toAdd.setFrereGauche(this.getFrereGauche());
        this.setFrereGauche(toAdd);
        toAdd.setFrereDroit(this);
        toAdd.setPere(this.getPere());
    }

    public void addOnRigth(ArbreFichiers toAdd){
        this.addOnRigthIgnoringFather(toAdd);
        toAdd.getPere().setPremierFils(toAdd);
        toAdd.getPere().updateFirstSon();
    }
    public void addOnRigthIgnoringFather(ArbreFichiers toAdd){
        toAdd.setFrereGauche(this);
        if (this.getFrereDroit() != null) {
            this.getFrereDroit().setFrereGauche(toAdd);
        }
        toAdd.setFrereDroit(this.getFrereDroit());
        this.setFrereDroit(toAdd);
        toAdd.setPere(this.getPere());

    }
    public String nChar(String c,int n,boolean t){
        String s=(n==0||t)?"":"│";
        for (int i = 0; i < n; i++) {
            s += c;
        }
        return s;
    }

    public String draw(){
        return this.draw(0,this.getExtremRightSon());
    }
    public abstract String draw(int n,ArbreFichiers exRS);
    //todo enlever utilisation de isFichier
    /*public String drawe(int n){
        String s ="";
        if(this.isFichier()){
            s+="\u001B[33m"+this.getNom() +" -\n"+"\u001B[0m";
        }else {
            List<ArbreFichiers> l = this.childrenToList();
            s += "\u001B[36m"+this.getNom() + "/\n"+"\u001B[0m";
            for (ArbreFichiers a : l) {
                boolean t = a.getPere().getFrereDroit() == null;
                String tc =(a.getFrereDroit()!=null)? nChar("   ", n,t) + "├── ": nChar("   ", n,t) + "└── ";

                s += tc+a.draw(n+1);
            }
        }
        return s;
    }
*/
/*
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
*/

    public boolean equals(Object o){
        if (o instanceof ArbreFichiers){
            return ((ArbreFichiers)o).getNom().equals(this.getNom());
        }
        return false;
    }

    @Override
    public int compareTo(ArbreFichiers o) {
        return -o.getNom().compareToIgnoreCase(this.getNom());
    }

// for testDonTouli
    @Override
    public String toString(){
        String nomPere = (pere!=null)?pere.getNom():"null";
        String nomFrereGauche = (frereGauche!=null)?frereGauche.getNom():"null";
        int n =nomFrereGauche.length()+(nom.length()/2)+3;
        String nomFrereDroit = (frereDroit!=null)?frereDroit.getNom():"null";
        String nomPremierFils = (premierFils!=null)?premierFils.getNom():"null";
        return nChar(" ",n,true)+"["+"\u001B[34m"+nomPere+"\u001B[0m"+"]"+"\n["+"\u001B[34m"+nomFrereGauche+"\u001B[0m"+"]<--[" + nom +","+taille+"]-->["+"\u001B[34m"+nomFrereDroit+"\u001B[0m"+"]\n"+nChar(" ",n,true)+"["+"\u001B[34m"+nomPremierFils+"\u001B[0m"+"]";
    }



    public String fillContenue(){
        String s ="";
        for(int i=0; i<taille;i++)s+=i;
        return s;
    }
    @Override
    public Object clone()throws CloneNotSupportedException{
        return super.clone();
    }
    public boolean contains(ArbreFichiers n){
        ArbreFichiers it = n;
        while(it!=null){
            if(it == this) return true;
            it = it.getPere();
        }
        return false;
    }

}
