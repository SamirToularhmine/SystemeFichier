package systeme_fichier;

import commandes.Cd;
import commandes.Commande;
import commandes.Commandes;
import commandes.Quit;
import fileparser.FileParser;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        this.commandes = Commandes.importCmd();
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
            c.afficherMenu(currDir);
            if(sc.hasNext()) {


                String line = sc.nextLine();
                try{
                ToolBox.characterAutorises(line);

                String[] lineSplitted = line.split(" ");
                //TODO: Utiliser la stream api
                if (!this.commandes.containsKey(lineSplitted[0]))
                    throw new Exception("la commande : " + lineSplitted[0] + " n'existe pas");

                    Commande commande = this.commandes.get(lineSplitted[0]);
                    Object o = null;
                    try {
                        String[] args = Arrays.copyOfRange(lineSplitted, 1, lineSplitted.length);

                        Optional opt = commande.execute(currDir, args);
                        if (opt.isPresent()) {
                            o = opt.get();
                        }
                    } catch (Exception e) {
                        System.out.println(e.getLocalizedMessage());
                    }
                    currDir = o instanceof Dossier ? (Dossier) o : currDir;
                    if (o instanceof String && !((String) o).isEmpty()) {
                        System.out.println(o);
                    }
            }catch (Exception e){
            System.out.println("\u001B[31m"+e.getMessage()+"\u001B[0m");
        }
            }

        }while(!stop);

    }


}
