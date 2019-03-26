package commandes;

import systeme_fichier.Dossier;

import java.util.Optional;

public class Pwd implements Commande{

    private static final String COMMANDE = "PWD";

    @Override
    public Optional execute(Dossier currDir, String... args) throws Exception {
        if(args.length == 1){
            throw new Exception(COMMANDE + " : Trop d'argument !");
        }
        String retour = currDir.cheminAbsolu();

        return Optional.of(retour);
    }
}
