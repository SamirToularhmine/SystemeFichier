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
    @Override
    public Optional execute(Dossier currDir, String... args) throws Exception {
        Dossier dossierCourrant = currDir;

        //TODO: Gérer le cas où l'on passe un chemin en paramètre
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

            throw new Exception("nombre d'arguments incorrect");

        }
        return Optional.empty();
    }

    public String demandeContenu(){
        switch (ToolBox.choose("voulez-vous mettre du contenu ?","oui","non")){
            case 1 : System.out.println("rentrez le contenu :");
                     return sc.nextLine();

            case 2 : return "";
        }
        return "";
    }

}
