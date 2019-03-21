package systeme_fichier;

import commandes.Cd;
import commandes.Commande;
import commandes.Quit;
import fileparser.FileParser;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
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
        System.out.println(this.arborescence.dessiner());
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
                String[] lineSplitted = line.split(" ");
                for(String s : this.commandes.keySet()){
                    if(s.equals(lineSplitted[0])){
                        Object o = null;
                        try {
                            o = this.commandes.get(s).execute(currDir, lineSplitted).get();
                        }catch(Exception e){
                            System.out.println(e.getLocalizedMessage());
                        }
                        currDir = o instanceof Dossier ? (Dossier)o:currDir;
                        break;
                    }
                }
            }
        }while(!stop);

    }


}
