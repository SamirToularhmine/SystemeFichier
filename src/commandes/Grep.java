package commandes;

import systeme_fichier.Dossier;
import systeme_fichier.IArbreFichier;

import java.util.Arrays;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Grep implements Commande{
    @Override
    public Optional execute(Dossier currDir, String... f) throws Exception {
        String[] fichiers = Arrays.copyOfRange(f, 1, f.length);
        String patternRaw = f[0].replaceAll("\"", "");
        Pattern pattern = Pattern.compile(patternRaw);
        String retour = "";
        for(String s : fichiers){
            String contenu = new Less().execute(currDir, s).get().toString();
            Matcher m = pattern.matcher(contenu);
            String[] chemin = s.split("/");
            String nomFichier = chemin[chemin.length - 1];
            if(m.find()){
                retour += nomFichier + " : " + m.group(0) + "\n";
            }
        }
        retour = !retour.isEmpty() ? retour.substring(0, retour.length() - 1) : "";

        return Optional.of(retour);
    }
}
