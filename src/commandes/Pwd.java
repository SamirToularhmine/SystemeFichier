package commandes;

import systeme_fichier.Dossier;

import java.util.Optional;

public class Pwd implements Commande{

    @Override
    public Optional execute(Dossier currDir, String... f) throws Exception {
        if(f.length > 1){
            throw new Exception(f[0] + " : Trop d'argument !");
        }
        String retour = currDir.cheminAbsolu();

        return Optional.of(retour);
    }
}
