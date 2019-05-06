package commandes;

import systeme_fichier.ArbreFichiers;
import systeme_fichier.Dossier;
import systeme_fichier.ToolBox;

import java.security.KeyPair;
import java.util.HashMap;
import java.util.Optional;
import java.util.Scanner;

public class Mv implements Commande{

        private Scanner sc = new Scanner(System.in);

    @Override
    public Optional execute(Dossier currDir, String... args) throws Exception {

        if(args.length != 2 ) throw new Exception("nombre d'arguments incorrect : 2 arguments requis");
        ArbreFichiers[] tabNoeud = new ArbreFichiers[2];
        ArbreFichiers[] tabDossierCur = new ArbreFichiers[2];
        HashMap<ArbreFichiers,ArbreFichiers> mapCurrAF = new HashMap<>();

        for (int i = 0; i < 3; i++) {
            Dossier dossierCourrant = null;
            String nom = args[i];
            if(ToolBox.estChemin(args[i])){

                String chemin = ToolBox.getChemin(args[i]);
                nom = ToolBox.getNomChemin(args[i]);
                dossierCourrant = (Dossier) Commandes.importCmd().get(Commandes.CD).execute(currDir, chemin).get();
            }else{
                dossierCourrant = currDir;
            }
            HashMap<String, ArbreFichiers> map = dossierCourrant.enfantVersMap();
            if(map.containsKey(nom)){
                mapCurrAF.put(map.get(nom),dossierCourrant);
            }else{
                throw new Exception("argument invalide : "+args[i]);
            }
        }



        Commandes.importCmd().get(Commandes.RM).execute(currDir,args);
        return Optional.empty();
    }

    @Override
    public String help() {
        return null;
    }
}
