package commandes;

import systeme_fichier.Dossier;
import systeme_fichier.IArbreFichier;

import java.util.Arrays;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Grep implements Commande{

    /**
     * Cette commande nous permet de trouver les fichiers dont le contenu match avec le pattern passé en paramètre
     * @param currDir dossier courant
     * @param f le patterne ainsi que les différents fichiers
     * @return les fichiers avec leur contenu avec qui ce dernier match avec le pattern passé en paramètre
     * @throws Exception si le fichier n'existe pas
     */
    @Override
    public Optional execute(Dossier currDir, String... f) throws Exception {
        Pattern p = Pattern.compile("^\".*\" .*$");
        String line = "";
        String finalPattern = "";
        for(String s : f){
            line += s + " ";
        }
        Matcher ma = p.matcher(line);
        if(ma.find()){
            Pattern p2 = Pattern.compile("\".*\"");
            Matcher m2 = p2.matcher(line);
            finalPattern = m2.find() ? m2.group(0) : "";
        }else{
            System.out.println("Pas trouvé !");
        }
        String patternRaw = finalPattern.replaceAll("\"", "");
        Pattern pattern = Pattern.compile(patternRaw);
        String retour = "";
        int posLastQuote = -1;
        for(int i = 0; i < f.length; i++){
            if(f[i].charAt(f[i].length() - 1) == '"'){
                posLastQuote = i + 1;
                break;
            }
        }
        String[] fichiers = Arrays.copyOfRange(f, posLastQuote, f.length);
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

    @Override
    public String help() {
        return "La commande grep permet de voir si le contenu d'un fichier correspond à un certain pattern regex passé\n" +
                "en paramètre.";
    }
}
