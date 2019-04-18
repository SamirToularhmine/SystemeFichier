package commandes;

import systeme_fichier.Dossier;
import systeme_fichier.Fichier;
import systeme_fichier.ToolBox;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Mkdir implements Commande{
    @Override
    public Optional execute(Dossier currDir, String... args) throws Exception {
        Dossier dossierCourrant = currDir;
        //TODO: Gérer le cas où l'on passe un fichier en paramètre
        if( args.length>0) {
            for (int i = 0; i < args.length; i++) {
                String nom = args[i];
                if(ToolBox.estChemin(args[i])){
                    String chemin = ToolBox.getChemin(args[i]);
                    nom = ToolBox.getNomChemin(args[i]);
                    dossierCourrant = (Dossier) Commandes.importCmd().get(Commandes.CD).execute(currDir, chemin).get();
                }else{
                    dossierCourrant = currDir;
                }
                System.out.println("\u001B[31m"+nom+"\u001B[0m");
                Dossier dossier = new Dossier(nom);
                dossierCourrant.ajouterNoeud(dossier);

            }


        }else{

            throw new Exception("nombre d'arguments incorrect");

        }
        return Optional.empty();
    }
}
