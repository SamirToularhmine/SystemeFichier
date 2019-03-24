package systeme_fichier;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Dossier extends ArbreFichiers {

    public Dossier(String nom){
        super(nom,false);
    }

    public void ajouterNoeud(ArbreFichiers n2) {

        List<ArbreFichiers> l = this.enfantsVersListe();
        n2.setPere(this);
        int n = 1;

        while (l.contains(n2)) { //todo : c'est chaud ici enfait ça marche pas j'men occupe asap
            String nom = n2.getNom();
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
            n2.setNom(nom);
        }
        l.add(n2);
        l.sort(Comparator.comparing(ArbreFichiers::getThis));
        this.listeVersEnfant(l);
        this.mettreAJourTaille(n2.getTaille());
    }

    public Dossier getNoeud(String nom) throws  Exception{
        ArbreFichiers noeud = this.getPremierFils();
        String nomNoeud = noeud.getNom();
        while(!nomNoeud.equals(nom)){
            if(noeud.getFrereDroit() == null){
                break;
            }
            noeud = noeud.getFrereDroit();
        }
        if(nomNoeud.equals(nom)){
            if(noeud instanceof Dossier){
                return (Dossier) noeud;
            }
        }
        throw new Exception("Pas de dossier avec ce nom !");
    }



    protected boolean contient(ArbreFichiers n){
        ArbreFichiers it = n;
        while(it!=null){
            if(it == this) return true;
            it = it.getPere();
        }
        return false;
    }

    protected  String nChar(String c,int n,boolean t){
        String s=(n==0||t)?"":"│";
        for (int i = 0; i < n; i++) {
            s += c;
        }
        return s;
    }

    public String dessiner(int n, ArbreFichiers exRS){
        String s ="";
        List<ArbreFichiers> l = this.enfantsVersListe();
        s += "\u001B[36m"+this.getNom() + "/\n"+"\u001B[0m";
        for (ArbreFichiers a : l) {
            boolean t = false;
            if(exRS instanceof Dossier){
                Dossier exRSD = (Dossier)exRS;
                t = exRSD.contient(this);
            }
            String tc =(a.getFrereDroit()!=null)? nChar("   ", n,t) + "├── ": nChar("   ", n,t) + "└── ";

            s += tc + a.dessiner(n+1,exRS);
        }

        return s;
    }

    public List<ArbreFichiers> enfantsVersListe(){
        List<ArbreFichiers> l = new ArrayList<>();
        if(this.estVide()){
            return l;
        }
        ArbreFichiers crt = this.getEnfantExtremeGauche();
        while(crt!=null){
            l.add(crt);
            crt = crt.getFrereDroit();
        }
        return l;
    }

    private void listeVersEnfant(List<ArbreFichiers> l){
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
                    prev.ajouterNoeudDroite(crt);
                }
            }
        }else{
            this.setPremierFils(null);
        }
        this.mettreAJourPremierFils();
    }

    protected void mettreAJourPremierFils(){
        //le cas où this a au moins un enfant
        if(!estVide()) {
            ArbreFichiers son = this.getPremierFils();
            this.parcoursLargeurFrere(son, n2 -> {

                if (n2.getFrereGauche() == null) {

                    this.setPremierFils(n2);
                    return true;
                }
                return false;
            });
        }
    }

    private ArbreFichiers getEnfantExtremeGauche(){
        if (this.estVide())return null;
        ArbreFichiers a = (ArbreFichiers) this.parcoursLargeurFrere(this.getPremierFils(),(b)->{
            if(b.getFrereGauche()==null) {
                return b;
            }
            return null;
        });
        return a;
    }
    private ArbreFichiers getEnfantExtremeDroite(){
        if (this.estVide())return null;
        ArbreFichiers a = (ArbreFichiers) this.parcoursLargeurFrere(this.getPremierFils(),(b)->{
            if(b.getFrereDroit()==null) {
                return b;
            }
            return null;
        });
        return a;
    }

    public Dossier getPere(){
        return (Dossier)super.getPere();
    }

    public ArbreFichiers getThis(){
        return this;
    }

    @Override
    public boolean supprimerNoeud(){
        return this.supprimerNoeud();
    }

    private boolean estVide(){
        return this.getPremierFils() == null;
    }

    @Override
    public String dessiner() {
        return this.dessiner(0,this.getEnfantExtremeDroite());
    }

    @Override
    public boolean equals(Object o){
        return this.compareTo((ArbreFichiers) o) == 0;
    }

    @Override
    public String toString(){
        return "\u001B[36m"+super.getNom()+"\u001B[0m"+"/ - [taille :"+super.getTaille()+"]";
    }
}