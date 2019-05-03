package commandes;

import systeme_fichier.Dossier;
import systeme_fichier.Fichier;
import systeme_fichier.ToolBox;

import javax.tools.Tool;
import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Mkfile implements Commande{

    private Scanner sc = new Scanner(System.in);

    /**
     * Cette commande permet de créer un fichier
     * @param currDir dossier courant
     * @param args nom + éventuellement le chemin dans lequel créer le fichier
     * @return rien
     * @throws Exception si le nombre d'arguments est incorrect
     */
    @Override
    public Optional execute(Dossier currDir, String... args) throws Exception {
        Dossier dossierCourrant = currDir;


        if(args.length!=0) {
            for (int i = 0; i < args.length; i++) {
                String nom = args[i];
                if(ToolBox.estChemin(args[i])){
                    String chemin = ToolBox.getChemin(args[i]);
                    nom = ToolBox.getNomChemin(args[i]);
                    dossierCourrant = (Dossier) Commandes.importCmd().get(Commandes.CD).execute(currDir, chemin).get();
                }else{
                    dossierCourrant = currDir;
                }

                String contenu = demandeContenu();
                Fichier fichier = new Fichier(nom,contenu);
                dossierCourrant.ajouterNoeud(fichier);

            }

        }else{

            throw new Exception("Nombre d'arguments incorrect");

        }
        return Optional.empty();
    }

    public String demandeContenu(){
        System.out.print("Contenu du fichier ? ");
        return sc.nextLine();

    }

    @Override
    public String help() {
        return "La commande mkfile permet de créer un fichier dans un chemin passé en paramètre se terminant par \n" +
                "le nom du fichier à créer.";
    }

}
