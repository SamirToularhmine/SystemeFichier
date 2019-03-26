package commandes;

import systeme_fichier.Dossier;

import java.util.Optional;

public class Cd implements Commande {

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
                        throw new Exception("Pas de dossier portant ce nom !");
                    }
                }
            }
        }
        
        return Optional.of(currDir);
    }
}
