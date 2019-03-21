package commandes;

import systeme_fichier.Dossier;

import java.util.Optional;

public interface Commande<T> {

    public Optional<T> execute(Dossier currDir, String...f) throws Exception;
}
