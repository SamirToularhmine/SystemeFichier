package commandes;

import systeme_fichier.Dossier;

import java.util.Optional;

public class Exit implements Commande {
    @Override
    public Optional execute(Dossier currDir, String... f) throws Exception {
        System.exit(0);
        return Optional.empty();
    }
}