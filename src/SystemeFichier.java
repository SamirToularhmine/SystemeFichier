import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SystemeFichier {

    private Dossier arborescence;
    private Map<String, Commande> commandes;
    private FileParser fp;
    private static final String[] MOTS_RESERVES = {
            "*",
            "fin",
            "racine"
    };

    public SystemeFichier(String cheminFichier){
        this.fp = new FileParser(cheminFichier, MOTS_RESERVES);
        this.arborescence = fp.parserFichier();
        System.out.println(this.arborescence.draw());
        this.commandes = new HashMap<>();
        this.commandes.put("quit", new Quit());
        this.commandes.put("cd", new Cd());
        this.in();
    }

    public SystemeFichier(){
        this.arborescence = new Dossier("/");
        this.commandes = null;
        this.fp = null;
    }

    public void in(){
        Scanner sc = new Scanner(System.in);
        boolean stop = false;
        Dossier currDir = this.arborescence;
        Console c = new Console();
        do {
            c.afficherMenu();
            if(sc.hasNext()){
                String line = sc.nextLine();
                for(String s : this.commandes.keySet()){
                    if(s.equals(line)){
                        this.commandes.get(s).execute(currDir);
                        break;
                    }
                }
            }
        }while(!stop);

    }


}
