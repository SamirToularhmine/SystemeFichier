package commandes;

import systeme_fichier.Dossier;

import java.util.Optional;

public class Tree implements Commande {
    @Override
    public Optional execute(Dossier currDir, String... f) throws Exception {
        System.out.println(currDir.dessiner());
        return Optional.empty();
    }
}
