package commandes;

import systeme_fichier.Dossier;

import java.util.Optional;

public class Cd implements Commande {

    /**
     * Cette commande nous permet de nous déplacer dans l'arborescence
     * @param currDir dossier courant
     * @param args chemin
     * @return le dossier dans lequel l'utilisateur s'est déplacé
     * @throws Exception si le dossier n'existe pas
     */
    @Override
    public Optional<Dossier> execute(Dossier currDir, String...args) throws Exception {
        if(args.length == 1){
            String[] chemin = args[0].split("/");
            for(String i : chemin){
                if(i.equals(".")){
                    continue;
                }
                if(i.equals("..")){
                    if(currDir.getPere() == null){
                        throw new Exception("Vous ne pouvez pas remonter plus haut !");
                    }
                    currDir = (Dossier)currDir.getPere();
                }else{
                    if(!currDir.getNoeud(i).isFichier()){
                        currDir = (Dossier)currDir.getNoeud(i);
                    }else{
                        throw new DossierAttenduException("Pas de dossier portant ce nom : "+i+", il s'agit d'un fichier");
                    }
                }
            }
        }
        
        return Optional.of(currDir);
    }

    @Override
    public String help() {
        return "La commande cd permet de se déplacer dans l'arborescence en passant le chemin en paramètre. \n" +
                "Ce chemin doit être composé de noms de dossiers séparés par des /";
    }
}
