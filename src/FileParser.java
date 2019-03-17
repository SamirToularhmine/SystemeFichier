import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class FileParser {
    //TODO: Hashmap pour les mots réservés
    // Generaliser le type decque
    // Ajouter help général et help commande

    private File file;
    private ArbreFichiers racine;
    private List motsReserves;

    public FileParser(String cheminFichier, String[] motsReserves){
        this.racine = null;
        this.file = new File(cheminFichier);
        this.motsReserves = List.of(motsReserves);
    }

    public ArbreFichiers parserFichier(){

        try{
            BufferedReader br = new BufferedReader(new FileReader(this.file));
            String line = br.readLine();
            int numLigne = 1;
            ArbreFichiers currentFile = null;
            Deque<ArbreFichiers> arborescence = new ArrayDeque<>();
            boolean finOk = false;
            while(line != null){
                if(numLigne == 1){
                    if(line.equals("racine")){
                        this.racine = new ArbreFichiers("racine", 0, false);
                        arborescence.add(racine);
                    }else{
                        throw new FileParseException("Votre fichier doit commencer par le mot racine !", numLigne);
                    }
                }else{
                    if(line.charAt(0) != '%'){
                        if(line.matches("^[*]+ ([a-zA-Z0-9]*|([a-zA-Z0-9]*_[a-zA-Z0-9]*)*) (d|f)( %.*)?$")){
                            String[] lineSplitted = line.split(" ");
                            String arbo = lineSplitted[0];
                            String nom = lineSplitted[1];
                            String type = lineSplitted[2];
                            //System.out.println("On crée le " + type + " nommé " + nom + " à l'emplacement " + arbo);
                            if(type.equals("f")){
                                ArbreFichiers fichier = new ArbreFichiers(nom, 0, true);
                                currentFile = fichier;
                                arborescence.getLast().addNode(fichier);
                            }else{
                                if(type.equals("d")){
                                    ArbreFichiers dossier = new ArbreFichiers(nom, 0, false);
                                    arborescence.getLast().addNode(dossier);
                                    arborescence.add(dossier);
                                }
                            }
                            if(arbo.length() > arborescence.size()){
                                throw new FileParseException("Vous devez d'abord définir un dossier avant de pouvoir descendre dans l'arborescence !", numLigne);
                            }else{
                                if(arbo.length() < arborescence.size() - 1){
                                    throw new FileParseException("Vous devez terminer la déclaration d'un dossier par le mot fin suivit du nombre d'étoiles correspondants", numLigne);
                                }
                            }

                        }else{
                            if(line.matches("fin")){
                                if(finOk){
                                    throw new FileParseException("Vous avez déjà terminé le fichier avec le mot fin !", numLigne);
                                }
                                finOk = true;
                            }else{
                                if(line.matches("^[*]+ fin( %.*)?$")){
                                    arborescence.removeLast();
                                }else{
                                    if(line.matches(".*") && currentFile != null && !line.contains("*")){
                                        //on ajoute la ligne au contenu du fichier
                                        //System.out.println("On ajoute " + line + " au fichier " + currentFile);
                                        currentFile.setContenu(line);
                                        currentFile = null;
                                    }else{
                                        //ajouter internationalisation
                                        throw new FileParseException("Vous devez définir un fichier avant de pouvoir préciser son contenu ou la ligne est mal formattée", numLigne);
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
                throw new FileParseException("Le fichier ne se termine pas par le mot 'fin'", numLigne);
            }
            if(arborescence.size() > 1){
                throw new FileParseException("Vous devez fermer le dossier défini afin de finir le fichier", numLigne);
            }
        }catch(IOException ioe){
            ioe.printStackTrace();
        }

        return racine;

    }
}
