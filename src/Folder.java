import java.util.Comparator;
import java.util.List;

public class Folder extends ArbreFichiers{

    public Folder(String nom){
        super(nom,false);
    }

    public void addNode(ArbreFichiers n2){
        if(this.isFichier()){
            throw new RuntimeException("c'est un fichier");
        }
        List<ArbreFichiers> l = this.childrenToList();
        n2.setPere(this);
        if(l.contains(n2)){
            throw new RuntimeException("fichier du même nom déjà présent");//fichierDéjàexistantExcetpion -> catch = ajout du fichier avec (n+1) ajouté à son nom
        }
        l.add(n2);
        l.sort(Comparator.comparing(ArbreFichiers::getNom));
        this.listToChildren(l);
        this.updateLength(n2.getTaille());
    }

    public String draw(){
        return this.draw(0);
    }

    public String draw(int n){
        String s ="";
            List<ArbreFichiers> l = this.childrenToList();
            s += "\u001B[36m"+this.getNom() + "/\n"+"\u001B[0m";
            for (ArbreFichiers a : l) {
                boolean t = a.getPere().getFrereDroit() == null;
                String tc =(a.getFrereDroit()!=null)? nChar("   ", n,t) + "├── ": nChar("   ", n,t) + "└── ";

                s += tc+a.draw(n+1);
            }

        return s;
    }

}
