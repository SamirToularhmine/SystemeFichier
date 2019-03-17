import java.util.Map;
import java.util.Scanner;

public class SystemeFichier {

    private ArbreFichiers arborescence;
    private Map<String, Commande> commandes;
    private FileParser fp;
    private static final String[] MOTS_RESERVES = {
        "*"
    };

    public SystemeFichier(String cheminFichier){
        this.fp = new FileParser(cheminFichier, MOTS_RESERVES);
        this.arborescence = fp.parserFichier();
        System.out.println(this.arborescence.draw());
        this.in();
    }

    public SystemeFichier(){
        this.arborescence = new Folder("/");
        this.commandes = null;
        this.fp = null;
    }

    public void in(){
        Scanner sc = new Scanner(System.in);
        boolean stop = false;
        Console c = new Console();
        do {
            c.afficherMenu();
            if(sc.hasNext()){
                String line = sc.nextLine();
                if(line.equals("exit")){
                    stop = true;
                }
            }
        }while(!stop);

    }


}
