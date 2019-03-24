package commandes;

import systeme_fichier.Dossier;
import systeme_fichier.Fichier;

import java.util.Optional;

public class Mkfile implements Commande{
    @Override
    public Optional execute(Dossier currDir, String... args) throws Exception {

        if(args.length<3 && args.length>0) {
            Fichier fichier = new Fichier(args[1]);
            currDir.ajouterNoeud(fichier);

        }else{

            throw new Exception("nombre d'arguments incorrect");

        }
        return Optional.empty();
    }
}
