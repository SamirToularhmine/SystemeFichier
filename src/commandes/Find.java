package commandes;

import systeme_fichier.ArbreFichiers;
import systeme_fichier.Dossier;
import systeme_fichier.IArbreFichier;

import java.util.*;

public class Find implements Commande {

    /**
     * Cette commande permet de détailler entièrement une arborescence, avec par exemple un dossier passé en paramètre
     * @param currDir dossier courant
     * @param f dossier à afficher
     * @return l'arborescence complète du dossier
     * @throws Exception si le dossier n'existe pas ou si l'on passe un fichier
     */
    @Override
    public Optional execute(Dossier currDir, String... f) throws Exception {
        String s="";
        IArbreFichier h;
        if(f.length==1) {
            String nom=f[0];
            Dossier niveau=currDir;
            Deque<IArbreFichier> g= new ArrayDeque<>();
            Set<IArbreFichier> nonatteints=new HashSet<>();
            g.add(niveau);
            while(!g.isEmpty()) {
                IArbreFichier u=g.pop();
                if(!nonatteints.contains(u)){
                    nonatteints.add(u);
                    if ((u.getNom().equals(nom))) {
                        s += u.cheminRelatif(currDir) + "\n";
                    }
                    if(u instanceof Dossier) {
                        List<ArbreFichiers> currChildren = ((Dossier)u).enfantsVersListe();
                        Iterator<ArbreFichiers> it = currChildren.iterator();
                        while (it.hasNext()) {
                            ArbreFichiers enfant = it.next();
                            g.add(enfant);
                        }
                    }
                }
            }
        }else{
            throw new Exception("Nombre d'arguments invalide!");
        }
        return Optional.of(s);
    }

    @Override
    public String help() {
        return "La commande find permet d'afficher l'arborescence d'un dossier passé en paramètre.";
    }
}
