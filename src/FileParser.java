import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;

public class FileParser {

    private File file;

    public FileParser(String cheminFichier){
        this.file = new File(cheminFichier);
    }

    public SystemeFichier parserFichier(){
        SystemeFichier sf = new SystemeFichier();

        try{
            BufferedReader br = new BufferedReader(new FileReader(this.file));
            String line = br.readLine();
            int numLigne = 1;
            Deque<String> pile = new ArrayDeque<>();
            ArbreFichiers currentFile = null;
            ArbreFichiers racine = null;
            Deque<ArbreFichiers> arborescence = new ArrayDeque<>();
            while(line != null){
                if(numLigne == 1){
                    if(line.equals("racine")){
                        racine = new ArbreFichiers();
                        arborescence.add(racine);
                        pile.add("fin");
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
                            System.out.println("On crée le " + type + " nommé " + nom + " à l'emplacement " + arbo);
                            if(type.equals("f")){
                                ArbreFichiers fichier = new ArbreFichiers(nom);
                                racine.addNode(fichier);
                                currentFile = fichier;
                            }else{
                                if(type.equals("d")){
                                    ArbreFichiers dossier = new ArbreFichiers(nom);
                                    pile.add("fin");
                                    arborescence.getLast().addNode(dossier);
                                    arborescence.add(dossier);
                                }
                            }
                            if(arbo.length() > 1 && arborescence.getLast().getNom().equals("racine")){
                                throw new FileParseException("Vous devez d'abord définir un dossier avant de pouvoir descendre dans l'arborescence !", numLigne);
                            }
                        }else{
                            if(line.matches("fin")){
                                if(line.equals("fin")){
                                    pile.pop();
                                    arborescence.pop();
                                }
                            }else{
                                if(line.matches(".*") && !currentFile.equals("")){
                                    //on ajoute la ligne au contenu du fichier
                                    System.out.println("On ajoute " + line + " au fichier " + currentFile);
                                    currentFile.setContenu(line);
                                    currentFile = null;
                                }else{
                                    throw new FileParseException("Vous devez définir un fichier avant de pouvoir préciser son contenu", numLigne);
                                }
                            }
                        }
                    }
                }
                numLigne++;
                line = br.readLine();
            }
            if(!pile.isEmpty()){
                throw new FileParseException("Le fichier ne se termine pas par le mot 'fin'", numLigne);
            }
        }catch(IOException ioe){
            ioe.printStackTrace();
        }

        return sf;
    }
}
