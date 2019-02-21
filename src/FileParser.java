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
            String currentFile = "";
            String currentDir = "racine";
            while(line != null){
            if(numLigne == 1){
                if(line.equals("racine")){
                    ArbreFichiers racine = new ArbreFichiers();
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
                            currentFile = nom;
                        }else{
                            if(type.equals("d")){
                                currentDir = nom;
                            }
                        }

                        if(!currentDir.equals("")){
                            pile.add("fin");
                        }else{
                            pile.add("fin");
                        }
                        if(arbo.length() > 1 && currentDir.equals("racine")){
                            throw new FileParseException("Vous devez d'abord définir un dossier avant de pouvoir descendre dans l'arborescence !", numLigne);
                        }
                    }else{
                        if(line.matches("fin")){
                            if(line.equals("fin")){
                                pile.pop();
                            }
                        }else{
                            if(line.matches(".*") && !currentFile.equals("")){
                                //on ajoute la ligne au contenu du fichier
                                System.out.println("On ajoute " + line + " au fichier " + currentFile);
                                currentFile = "";
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
