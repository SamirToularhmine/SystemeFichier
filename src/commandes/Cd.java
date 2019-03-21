package commandes;

import systeme_fichier.Dossier;

import java.util.Optional;

public class Cd implements Commande {

    @Override
    public Optional<Dossier> execute(Dossier currDir, String...args) throws Exception {
        if(args.length > 1){
            String[] chemin = args[1].split("/");
            for(String i : chemin){
                if(i.equals("..")){
                    if(currDir.getPere() == null){
                        throw new Exception("Vous ne pouvez pas remonter plus haut !");
                    }
                    currDir = currDir.getPere();
                }else{
                    currDir = currDir.getNoeud(i);
                }
            }
        }

        System.out.println(currDir.dessiner());
        return Optional.of(currDir);
    }
}
