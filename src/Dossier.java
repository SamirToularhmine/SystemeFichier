import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Dossier implements ArbreFichier, Comparable<ArbreFichier>{

    private State state;

    public Dossier(String nom){
        this.state = new State(nom,false);
    }

    public void addNode(ArbreFichier n2){

        List<ArbreFichier> l = this.childrenToList();
        n2.getInfos().setPere(this);
        int n =1;

        while(l.contains(n2)){
            String nom = n2.getInfos().getNom();
            Matcher matcher = Pattern.compile(".*\\(\\d+\\)$").matcher(nom);
            if(matcher.find()){
                matcher = Pattern.compile("\\(\\d+\\)$").matcher(nom);
                matcher.find();
                String end = matcher.group();
                matcher =  Pattern.compile("\\d+").matcher(end);
                matcher.find();
                end = matcher.group();
                int nv = Integer.valueOf(end);
                matcher = Pattern.compile("[^\\(\\d+\\)$)]").matcher(nom);
                matcher.find();
                String begin = matcher.group();
                nom = begin +"("+ (nv+1) +")";
            }else {
                nom +="(" + (n++) + ")";
            }
            n2.getInfos().setNom(nom);
        }
        l.add(n2);
        //l.sort(Comparator.comparing(ArbreFichier::getThis));
        this.listToChildren(l);
        this.state.updateLength(n2.getInfos().getTaille());
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


   public String draw(int n,ArbreFichier exRS){
        String s ="";
            List<ArbreFichier> l = this.childrenToList();
            s += "\u001B[36m"+this.state.getNom() + "/\n"+"\u001B[0m";
            for (ArbreFichier a : l) {
                boolean t = false;
                if(exRS instanceof Dossier){
                    Dossier exRSD = (Dossier)exRS;
                    t = exRSD.contains(this);
                }
                String tc =(a.getInfos().getFrereDroit()!=null)? nChar("   ", n,t) + "├── ": nChar("   ", n,t) + "└── ";

                s += tc+a.draw(n+1,exRS);
            }

        return s;
    }

    public boolean contains(ArbreFichier n){
        ArbreFichier it = n;
        while(it!=null){
            if(it == this) return true;
            it = it.getInfos().getPere();
        }
        return false;
    }

   /* public String draw(){
        return this.draw(0,this.getExtremRightSon());
    }*/

    public String nChar(String c,int n,boolean t){
        String s=(n==0||t)?"":"│";
        for (int i = 0; i < n; i++) {
            s += c;
        }
        return s;
    }

    public List<ArbreFichier> childrenToList(){
        List<ArbreFichier> l = new ArrayList<>();
        if(this.haveNoChild()){
            return l;
        }
        ArbreFichier crt = this.getExtremLeftSon();
        while(crt!=null){
            l.add(crt);
            crt = crt.getInfos().getFrereDroit();
        }
        return l;
    }

    public void listToChildren(List<ArbreFichier> l){
        if(!l.isEmpty()){
            ArbreFichier first_element = l.get(0);
            first_element.getInfos().removeSiblings();
            this.state.setPremierFils(first_element);
            if(l.size()>1){
                ListIterator<ArbreFichier> it = l.listIterator(1);
                while(it.hasNext()){
                    ArbreFichier prev = l.get(it.previousIndex());
                    ArbreFichier crt = it.next();
                    crt.getInfos().removeSiblings();
                    prev.addOnRigthIgnoringFather(crt);
                }
            }
        }else{
            this.state.setPremierFils(null);
        }
        this.updateFirstSon();
    }

    public void updateFirstSon(){
        //le cas où this a au moins un enfant
        if(!haveNoChild()) {
            ArbreFichier son = this.state.getPremierFils();
            this.state.parcoursLargeurFrere(son, n2 -> {

                if (n2.getInfos().getFrereGauche() == null) {

                    this.state.setPremierFils(n2);
                    return true;
                }
                return false;
            });
        }
    }

    public ArbreFichier getExtremLeftSon(){
        if (this.haveNoChild())return null;
        ArbreFichier a = (ArbreFichier) this.state.parcoursLargeurFrere(this.state.getPremierFils(),(b)->{
            if(b.getInfos().getFrereGauche()==null) {
                return b;
            }
            return null;
        });
        return a;
    }
    public ArbreFichier getExtremRightSon(){
        if (this.haveNoChild())return null;
        ArbreFichier a = (ArbreFichier) this.state.parcoursLargeurFrere(this.state.getPremierFils(),(b)->{
            if(b.getInfos().getFrereDroit()==null) {
                return b;
            }
            return null;
        });
        return a;
    }

    public ArbreFichier getThis(){
        return this;
    }

    public boolean haveNoChild(){
        return this.state.getPremierFils() == null;
    }

    @Override
    public State getInfos() {
        return this.state;
    }

    @Override
    public String draw() {
        return this.draw(0,this.getExtremRightSon());
    }

    @Override
    public int compareTo(ArbreFichier af) {
        return -af.getInfos().getNom().compareToIgnoreCase(this.getInfos().getNom());
    }
}
