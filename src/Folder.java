import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Folder extends ArbreFichiers{

    public Folder(String nom){
        super(nom,false);
    }

    public void addNode(ArbreFichiers n2){

        List<ArbreFichiers> l = this.childrenToList();
        n2.setPere(this);
        int n =1;

        while(l.contains(n2)){
            String nom = n2.getNom();
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
            n2.setNom(nom);
        }
        l.add(n2);
        l.sort(Comparator.comparing(ArbreFichiers::getThis));
        this.listToChildren(l);
        this.updateLength(n2.getTaille());
    }



    public String draw(int n,ArbreFichiers exRS){
        String s ="";
            List<ArbreFichiers> l = this.childrenToList();
            s += "\u001B[36m"+this.getNom() + "/\n"+"\u001B[0m";
            for (ArbreFichiers a : l) {
                boolean t = exRS.contains(a);
                String tc =(a.getFrereDroit()!=null)? nChar("   ", n,t) + "├── ": nChar("   ", n,t) + "└── ";

                s += tc+a.draw(n+1,exRS);
            }

        return s;
    }




}
