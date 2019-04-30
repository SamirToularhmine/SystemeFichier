package fileparser;

import systeme_fichier.Dossier;
import systeme_fichier.Fichier;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class FileParser{
    //TODO: Hashmap pour les mots réservés
    //TODO: Internationalisation des messages
    //TODO: Ajouter help général et help commande

    private static final String BEGIN_RACINE = "Votre fichier doit commencer par le mot racine !";
    private static final String PB_ARBO = "Vous devez d'abord définir un dossier avant de pouvoir descendre dans l'arborescence !";
    private static final String FIN_DECLA = "Vous devez terminer la déclaration d'un dossier par le mot fin suivit du nombre d'étoiles correspondants";
    private static final String DEJA_FINI = "Vous avez déjà terminé le fichier avec le mot fin !";
    private static final String FORMAT_LIGNE = "Vous devez définir un fichier avant de pouvoir préciser son contenu ou la ligne est mal formattée";
    private static final String END_FIN = "Le fichier ne se termine pas par le mot 'fin'";
    private static final String FERMETURE_DOSSIER = "Vous devez fermer le dossier défini afin de finir le fichier";
    private static final String NB_ETOILES = "Vous devez terminer la définition de ce dossier avec le bon nombre d'étoiles suivies de fin";

    private File file;
    private Dossier racine;
    private List<String> motsReserves;

    public FileParser(String cheminFichier, String[] motsReserves){
        this.racine = null;
        this.file = new File(cheminFichier);
        this.motsReserves = List.of(motsReserves);
    }

    public Dossier parserFichier(){
        try{
            BufferedReader br = new BufferedReader(new FileReader(this.file));
            String line = br.readLine();
            int numLigne = 1;
            Fichier currentFile = null;
            Deque<Dossier> arborescence = new ArrayDeque<>();
            boolean finOk = false;
            while(line != null){
                line = line.stripLeading();
                if(numLigne == 1){
                    if(!line.equals("racine")){
                        throw new FileParseException(BEGIN_RACINE, numLigne);
                    }
                    this.racine = new Dossier("racine");
                    arborescence.add(racine);
                }else{
                    if(line.charAt(0) != '%'){
                        if(line.matches("^[*]+ ([a-zA-Z0-9]*|([a-zA-Z0-9]*_[a-zA-Z0-9]*)*) (d|f)( %.*)?$")){
                            currentFile = null;
                            String[] lineSplitted = line.split(" ");
                            String arbo = lineSplitted[0];
                            String nom = lineSplitted[1];
                            String type = lineSplitted[2];
                            int tailleArborescence = arborescence.size();
                            int tailleArbo = arbo.length();
                            //System.out.println("On crée le " + type + " nommé " + nom + " à l'emplacement " + arbo);
                            if(tailleArbo > tailleArborescence){
                                throw new FileParseException(PB_ARBO, numLigne);
                            }
                            if(tailleArbo < tailleArborescence){
                                throw new FileParseException(FIN_DECLA, numLigne);
                            }
                            if(type.equals("f")){
                                Fichier fichier = new Fichier(nom);
                                currentFile = fichier;
                                arborescence.getLast().ajouterNoeud(fichier);
                            }else{
                                if(type.equals("d")){
                                    Dossier dossier = new Dossier(nom);
                                    arborescence.getLast().ajouterNoeud(dossier);
                                    arborescence.add(dossier);
                                }
                            }
                        }else{
                            if(line.equals("fin")){
                                if(finOk){
                                    throw new FileParseException(DEJA_FINI, numLigne);
                                }
                                finOk = true;
                            }else{
                                if(line.matches("^[*]+ fin( %.*)?$")){
                                    String arbo = line.split(" ")[0];
                                    if(arbo.length() == arborescence.size() - 1){
                                        arborescence.removeLast();
                                    }else{
                                        throw new FileParseException(NB_ETOILES, numLigne);
                                    }
                                }else{
                                    boolean contenuOk = line.matches(".*") && currentFile != null;
                                    if(contenuOk){
                                        for(String mr : this.motsReserves){
                                            if(line.contains(mr)){
                                                contenuOk = false;
                                                break;
                                            }
                                        }
                                    }
                                    if(contenuOk){
                                        String contenu  = currentFile.getContenu();
                                        contenu = contenu + line + "___";
                                        currentFile.setContenu(contenu);
                                        //currentFile = null;
                                    }else{
                                        throw new FileParseException(FORMAT_LIGNE, numLigne);
                                    }
                                }
                            }
                        }
                    }
                }
                numLigne++;
                line = br.readLine();
            }
            if(!finOk){
                throw new FileParseException(END_FIN, numLigne);
            }
            if(arborescence.size() > 1){
                throw new FileParseException(FERMETURE_DOSSIER, numLigne);
            }
        }catch(IOException ioe){
            ioe.printStackTrace();
        }

        return racine;

    }
}
