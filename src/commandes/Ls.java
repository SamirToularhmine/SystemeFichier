package commandes;

import systeme_fichier.ArbreFichiers;
import systeme_fichier.IArbreFichier;
import systeme_fichier.Dossier;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class Ls implements Commande {

    @Override
    public Optional<String> execute(Dossier currDir, String... args) throws Exception {
        if (currDir == null) {
            System.out.println("\u001B[31m"+"currDir NULL"+"\u001B[0m");
            return Optional.empty();
        }
        if(args.length>1){
            System.out.println(args[0]);
        }else {
            List<ArbreFichiers> childrenCurr = currDir.enfantsVersListe();
            Iterator<ArbreFichiers> it = childrenCurr.listIterator();
            String toShow = "";
            while (it.hasNext()) {
                //todo gérer l'affichage avec la classe Console
                toShow += it.next() + "\n";
            }

            return Optional.of(toShow);
        }
        return Optional.empty();
    }
}
