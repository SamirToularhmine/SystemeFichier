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
                String begin = nom.substring(0,(nom.length()-end.length()));
                matcher = Pattern.compile("\\d+").matcher(end);
                matcher.find();
                end = matcher.group();
                int nv = Integer.valueOf(end);
                matcher = Pattern.compile("[^\\(\\d+\\)$)]*").matcher(nom);
                matcher.find();

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

    public IArbreFichier getNoeud(String nom) throws Exception{
        IArbreFichier noeud = this.getPremierFils();
        String nomNoeud = noeud.getNom();
        while(!nomNoeud.equals(nom)){
            if(noeud.getFrereDroit() == null){
                break;
            }
            noeud = noeud.getFrereDroit();
            nomNoeud = noeud.getNom();
        }
        if(noeud.getNom().equals(nom)){
            return noeud;
        }
        throw new Exception("Pas de dossier/fichier avec ce nom !");
    }

    protected boolean contient(IArbreFichier n){
        IArbreFichier it = n;
        while(it!=null){
            if(it == this) return true;
            it = it.getPere();
        }
        return false;
    }

    public String dessiner(int n, ArbreFichiers exRS){
        String s ="";
        List<ArbreFichiers> l = this.enfantsVersListe();
        s += "\u001B[36m"+this.getNom()+" [t:"+this.getTaille()+"]"+ "/\n"+"\u001B[0m";
        for (ArbreFichiers a : l) {
            boolean t = false;
            if(exRS instanceof Dossier){
                Dossier exRSD = (Dossier)exRS;
                t = exRSD.contient(this);
            }
            String tc =(a.getFrereDroit()!=null)? ToolBox.nChar("   ", n,t) + "├── ": ToolBox.nChar("   ", n,t) + "└── ";

            s += tc + a.dessiner(n+1,exRS);
        }

        return s;
    }

    public List<ArbreFichiers> enfantsVersListe(){
        List<ArbreFichiers> l = new ArrayList<>();
        if(this.estVide()){
            return l;
        }
        ArbreFichiers crt = (ArbreFichiers) this.getEnfantExtremeGauche();
        while(crt!=null){
            l.add(crt);
            crt = (ArbreFichiers) crt.getFrereDroit();
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
            IArbreFichier son = this.getPremierFils();
            this.parcoursLargeurFrere(son, n2 -> {

                if (n2.getFrereGauche() == null) {

                    this.setPremierFils(n2);
                    return true;
                }
                return false;
            });
        }
    }

    private IArbreFichier getEnfantExtremeGauche(){
        if (this.estVide())return null;
        IArbreFichier a = (IArbreFichier) this.parcoursLargeurFrere(this.getPremierFils(),(b)->{
            if(b.getFrereGauche()==null) {
                return b;
            }
            return null;
        });
        return a;
    }
    private IArbreFichier getEnfantExtremeDroite(){
        if (this.estVide())return null;
        IArbreFichier a = (IArbreFichier) this.parcoursLargeurFrere(this.getPremierFils(),(b)->{
            if(b.getFrereDroit()==null) {
                return b;
            }
            return null;
        });
        return a;
    }

    public boolean estVide(){
        return this.getPremierFils() == null;
    }

    /*@Override
    public boolean supprimerNoeud(){
        return this.supprimerNoeud();
    }*/

    @Override
    public String dessiner() {
        return this.dessiner(0,(ArbreFichiers)this.getEnfantExtremeDroite());
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
