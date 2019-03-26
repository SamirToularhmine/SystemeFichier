package commandes;

import systeme_fichier.Dossier;
import systeme_fichier.IArbreFichier;

import java.util.Arrays;
import java.util.Optional;

public class Less implements Commande{

    @Override
    public Optional execute(Dossier currDir, String... f) throws Exception {
        String retour = "";
        if(f.length == 1){
            String chemin = f[0];
            String[] arbo = chemin.split("/");
            IArbreFichier fichier = null;
            if(arbo.length > 1){
                String arbo1 = String.join("/", Arrays.copyOfRange(arbo, 0, arbo.length - 1));
                IArbreFichier goToFichier = new Cd().execute(currDir, arbo1).get();
                if(!goToFichier.isFichier()){
                    Dossier dossier = (Dossier)goToFichier;
                    fichier = dossier.getNoeud(arbo[arbo.length - 1]);
                    if(fichier != null && fichier.isFichier()){
                        retour = fichier.getContenu();
                    }
                }
            }else{
                fichier = currDir.getNoeud(chemin);
            }
            if(fichier.isFichier()){
                retour += fichier.getContenu();
            }else{
                throw new Exception("Il faut passer un fichier en argument !");
            }
        }else{
            throw new Exception(f[0] + " : Pas assez d'arguments !");
        }
        return Optional.of(retour);
    }
}
