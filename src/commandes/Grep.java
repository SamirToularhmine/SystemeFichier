package commandes;

import systeme_fichier.ArbreFichiers;
import systeme_fichier.Dossier;
import systeme_fichier.IArbreFichier;

import java.util.Arrays;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Grep implements Commande{
    @Override
    public Optional execute(Dossier currDir, String... f) throws Exception {
        String[] fichiers = Arrays.copyOfRange(f, 2, f.length);
        Pattern pattern = Pattern.compile(f[1]);
        String retour = "";
        for(String s : fichiers){
            if(currDir.getNoeud(s).isFichier()){
                IArbreFichier noeud = currDir.getNoeud(s);
                String contenu = noeud.getContenu();
                Matcher m = pattern.matcher(contenu);
                if(m.find()){
                    retour += noeud.getNom() + " : " + m.group(0) + "\n";
                }
            }
        }
        retour = !retour.isEmpty() ? retour.substring(0, retour.length() - 1) : "";

        return Optional.of(retour);
    }
}
