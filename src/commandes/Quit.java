package commandes;

import systeme_fichier.Dossier;

import java.util.Optional;

public class Quit implements Commande {

    @Override
    public Optional execute(Dossier currDir, String... f) {
        System.exit(0);
        return Optional.empty();
    }
}
