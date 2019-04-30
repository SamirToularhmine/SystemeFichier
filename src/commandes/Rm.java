package commandes;

import systeme_fichier.Dossier;
import systeme_fichier.IArbreFichier;
import systeme_fichier.ToolBox;

import java.util.Optional;

public class Rm implements Commande {
    @Override
    public Optional execute(Dossier currDir, String... args) throws Exception {
        Dossier dossierCourrant = currDir;
        if(args.length>0){
            for (int i = 0; i < args.length; i++) {
                String nom = args[i];
                if(ToolBox.estChemin(args[i])){
                    String chemin = ToolBox.getChemin(args[i]);
                    nom = ToolBox.getNomChemin(args[i]);
                    dossierCourrant = (Dossier) Commandes.importCmd().get(Commandes.CD).execute(currDir, chemin).get();
                }else{
                    dossierCourrant = currDir;
                }
                IArbreFichier d=dossierCourrant.getNoeud(nom);
                d.supprimerNoeud();
            }
        }
        return Optional.of(currDir);
    }

    @Override
    public String help() {
        return "La commande rm permet de supprimer un dossier ou un fichier dont le chemin est passé en paramètre.";
    }
}
