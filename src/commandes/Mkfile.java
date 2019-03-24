package commandes;

import systeme_fichier.Dossier;
import systeme_fichier.Fichier;

import java.util.Optional;

public class Mkfile implements Commande{
    @Override
    public Optional execute(Dossier currDir, String... args) throws Exception {

        if(args.length<4 && args.length>0) {
            String s = args[2];
            Fichier fichier = new Fichier(args[1],s);
            currDir.ajouterNoeud(fichier);

        }else{

            throw new Exception("nombre d'arguments incorrect");

        }
        return Optional.empty();
    }
}
