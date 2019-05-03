package commandes;

import systeme_fichier.Dossier;

import java.util.Optional;

public class Pwd implements Commande{

    private static final String COMMANDE = "PWD";

    /**
     * Cette commande permet d'afficher le chemin actuel
     * @param currDir dossier courant
     * @param args aucun
     * @return une chaine de charactère contenant ce chemin
     * @throws Exception si la commande à trop d'arguments
     */
    @Override
    public Optional execute(Dossier currDir, String... args) throws Exception {
        if(args.length == 1){
            throw new Exception(COMMANDE + " : Trop d'argument !");
        }
        String retour = currDir.cheminAbsolu();

        return Optional.of(retour);
    }

    @Override
    public String help() {
        return "La commande pwd permet d'afficher le chemin absolu du dossier actuel.";
    }
}
