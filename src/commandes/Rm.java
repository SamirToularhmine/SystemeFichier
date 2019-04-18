package commandes;

import systeme_fichier.Dossier;
import systeme_fichier.IArbreFichier;

import java.util.Optional;

public class Rm implements Commande {
    @Override
    public Optional execute(Dossier currDir, String... f) throws Exception {
        if(f.length==1){
            IArbreFichier d=currDir.getNoeud(f[0]);
            d.supprimerNoeud();
        }
        return Optional.of(currDir);
    }
}
