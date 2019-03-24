package commandes;

import systeme_fichier.Dossier;
import systeme_fichier.Fichier;

import java.util.Optional;

public class Mkdir implements Commande{
    @Override
    public Optional execute(Dossier currDir, String... args) throws Exception {

        if( args.length>0) {
            for (int i = 1; i < args.length; i++) {

                Dossier dossier = new Dossier(args[i]);
                currDir.ajouterNoeud(dossier);

            }


        }else{

            throw new Exception("nombre d'arguments incorrect");

        }
        return Optional.empty();
    }
}
