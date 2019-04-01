package commandes;

import systeme_fichier.ArbreFichiers;
import systeme_fichier.Dossier;
import systeme_fichier.IArbreFichier;

import java.util.*;

public class Find implements Commande {
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
        }
        return Optional.of(s);
    }
}
