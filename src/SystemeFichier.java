import java.util.Map;

public class SystemeFichier {

    private ArbreFichiers arborescence;
    private Map<String, Commande> commandes;
    private FileParser fp;

    public SystemeFichier(String cheminFichier){
        //this.fp = new FileParser(cheminFichier);
        this.arborescence = fp.parserFichier();
    }

    public SystemeFichier(){
        this.arborescence = new Folder("/");
        this.commandes = null;
        this.fp = null;
    }


}
