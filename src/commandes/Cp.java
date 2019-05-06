package commandes;

import systeme_fichier.ArbreFichiers;
import systeme_fichier.Dossier;
import systeme_fichier.Fichier;
import systeme_fichier.ToolBox;

import javax.tools.Tool;
import java.util.Optional;

public class Cp implements Commande {
    @Override
    public Optional execute(Dossier currDir, String... args) throws Exception {

        String nom ="";
        String chemin = "";
        Dossier dossierCourrant = null;
        //if(args.length != 2 ) throw new Exception("nombre d'arguments incorrect : 2 arguments requis");
        String dest =args[ args.length-1];
        for (int i = 0; i < args.length-1; i++) {
            String src = args[i];
            if(ToolBox.estChemin(src)){
                chemin = ToolBox.getChemin(src);
                nom = ToolBox.getNomChemin(src);
                dossierCourrant = (Dossier) Commandes.importCmd().get(Commandes.CD).execute(currDir, chemin).get();
            }else{
                nom = src;
                dossierCourrant = currDir;
            }
            ArbreFichiers arbreFichiers = (ArbreFichiers) dossierCourrant.getNoeud(nom);
            ArbreFichiers toCopy =  (arbreFichiers.isFichier())?((Fichier)arbreFichiers).getCopy():((Dossier)arbreFichiers).getCopy();

            if(dest.charAt(dest.length()-1)=='/'){
                dest = dest.substring(0,dest.length()-1);
            }
            if(ToolBox.estChemin(dest)){
                chemin = ToolBox.getChemin(dest);
                nom = ToolBox.getNomChemin(dest);
                dossierCourrant = (Dossier) Commandes.importCmd().get(Commandes.CD).execute(currDir, chemin).get();



                if(!dossierCourrant.enfantVersMap().containsKey(nom)){
                    toCopy.setNom(nom);
                }else{
                    dossierCourrant = (Dossier) Commandes.importCmd().get(Commandes.CD).execute(dossierCourrant, nom).get();
                }
            }else{
                if(!dossierCourrant.enfantVersMap().containsKey(dest)) {
                    toCopy.setNom(dest);
                }else {
                    dossierCourrant = (Dossier) Commandes.importCmd().get(Commandes.CD).execute(currDir, dest).get();
                }
            }
            System.out.println(toCopy);
            dossierCourrant.ajouterNoeud(toCopy);
        }

        return Optional.empty();
    }

    @Override
    public String help() {
        return " cp  sert  à  copier  des  fichiers  (et eventuellement des\n" +
                "       répertoires).  On peut aussi bien copier un fichier  donné\n" +
                "       vers  une  destination  précise  que copier un ensemble de\n" +
                "       fichiers dans un répertoire.\n";
    }
}
