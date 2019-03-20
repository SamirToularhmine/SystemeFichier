import java.io.ObjectOutputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Dossier implements ArbreFichier{

    private State state;

    public Dossier(String nom){
        this.state = new State(nom,false);
    }

    public void ajouterNoeud(ArbreFichier n2) {

        List<ArbreFichier> l = this.enfantsVersListe();
        n2.getInfos().setPere(this);
        int n = 1;

        while (l.contains(n2)) {
            String nom = n2.getInfos().getNom();
            Matcher matcher = Pattern.compile(".*\\(\\d+\\)$").matcher(nom);
            if (matcher.find()) {
                matcher = Pattern.compile("\\(\\d+\\)$").matcher(nom);
                matcher.find();
                String end = matcher.group();
                matcher = Pattern.compile("\\d+").matcher(end);
                matcher.find();
                end = matcher.group();
                int nv = Integer.valueOf(end);
                matcher = Pattern.compile("[^\\(\\d+\\)$)]").matcher(nom);
                matcher.find();
                String begin = matcher.group();
                nom = begin + "(" + (nv + 1) + ")";
            } else {
                nom += "(" + (n++) + ")";
            }
            n2.getInfos().setNom(nom);
        }
        l.add(n2);
        l.sort(Comparator.comparing(ArbreFichier::getThis));
        this.listeVersEnfant(l);
        this.state.mettreAJourTaille(n2.getInfos().getTaille());
    }

   public String dessiner(int n,ArbreFichier exRS){
        String s ="";
            List<ArbreFichier> l = this.enfantsVersListe();
            s += "\u001B[36m"+this.state.getNom() + "/\n"+"\u001B[0m";
            for (ArbreFichier a : l) {
                boolean t = false;
                if(exRS instanceof Dossier){
                    Dossier exRSD = (Dossier)exRS;
                    t = exRSD.contient(this);
                }
                String tc =(a.getInfos().getFrereDroit()!=null)? nChar("   ", n,t) + "├── ": nChar("   ", n,t) + "└── ";

                s += tc + a.dessiner(n+1,exRS);
            }

        return s;
    }

    private boolean contient(ArbreFichier n){
        ArbreFichier it = n;
        while(it!=null){
            if(it == this) return true;
            it = it.getInfos().getPere();
        }
        return false;
    }

    private String nChar(String c,int n,boolean t){
        String s=(n==0||t)?"":"│";
        for (int i = 0; i < n; i++) {
            s += c;
        }
        return s;
    }

    private List<ArbreFichier> enfantsVersListe(){
        List<ArbreFichier> l = new ArrayList<>();
        if(this.estVide()){
            return l;
        }
        ArbreFichier crt = this.getEnfantExtremeGauche();
        while(crt!=null){
            l.add(crt);
            crt = crt.getInfos().getFrereDroit();
        }
        return l;
    }

    private void listeVersEnfant(List<ArbreFichier> l){
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
                    prev.ajouterNoeudDroite(crt);
                }
            }
        }else{
            this.state.setPremierFils(null);
        }
        this.mettreAJourPremierFils();
    }

    public void mettreAJourPremierFils(){
        //le cas où this a au moins un enfant
        if(!estVide()) {
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

    private ArbreFichier getEnfantExtremeGauche(){
        if (this.estVide())return null;
        ArbreFichier a = (ArbreFichier) this.state.parcoursLargeurFrere(this.state.getPremierFils(),(b)->{
            if(b.getInfos().getFrereGauche()==null) {
                return b;
            }
            return null;
        });
        return a;
    }
    private ArbreFichier getEnfantExtremeDroite(){
        if (this.estVide())return null;
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

    private boolean estVide(){
        return this.state.getPremierFils() == null;
    }

    @Override
    public State getInfos() {
        return this.state;
    }

    @Override
    public String dessiner() {
        return this.dessiner(0,this.getEnfantExtremeDroite());
    }

    @Override
    public boolean equals(Object o){
        return this.compareTo((ArbreFichier)o) == 0;
    }

    @Override
    public int compareTo(ArbreFichier af) {
        return af.getInfos().compareTo(this.getInfos());
    }
}
