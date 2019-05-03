package commandes;

import systeme_fichier.Dossier;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Help implements Commande{

    /**
     * Cette commande permet d'afficher une aide pour les commandes disponibles. Si il n'y a pas de commande passée en
     * paramètre alors cette commande afficher toutes les commandes disponibles sinon elle afficher l'aide associée à
     * chaque commande
     * @param currDir dossier courant
     * @param f arguments
     * @return l'aide voulue
     * @throws Exception si le nombre d'arguments est incorrect par exemple si l'on veut passer deux commandes en même
     * temps.
     */
    @Override
    public Optional execute(Dossier currDir, String... f) throws Exception {
        String retour = "";
        Map<String, Commande> commandes = Commandes.importCmd();
        if(f.length > 0){
           if(f.length == 1){
               retour += "Voici l'aide pour la commande " + f[0] + " :\n";
               Commande c = commandes.get(f[0]);
               if(c != null){
                retour += commandes.get(f[0]).help();
               }else{
                   throw new Exception("La commande n'existe pas !");
               }
           }else{
               throw new Exception("Vous ne pouvez afficher l'aide que pour une seule commande à la fois !");
           }
        }else{
            retour += "Voici les commandes que vous pouvez utiliser  :\n";
            for(String s : commandes.keySet()) {
                retour += s + "\n";
            }
            retour += "\n" + "Pour plus d'informations sur une commande, vous pouvez tapez : help nom_commande";
        }


        return Optional.of(retour);
    }

    @Override
    public String help() {
        return "La commande help permet soit d'afficher les différentes commandes disponibles soit d'afficher l'aide\n" +
                "pour une commande particulière dont le nom est passé en paramètre.";
    }
}
