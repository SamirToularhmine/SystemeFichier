package commandes;

import systeme_fichier.Dossier;

import java.util.Optional;

public class Exit implements Commande {

    /**
     * Permet de quitter le programme
     * @param currDir dossier courant
     * @param f arguements de la commande
     * @return rien car le programme s'arrête.
     */
    @Override
    public Optional execute(Dossier currDir, String... f) throws Exception {
        System.exit(0);
        return Optional.empty();
    }

    @Override
    public String help() {
        return "La commande exit permet de quitter le programme.";
    }
}
