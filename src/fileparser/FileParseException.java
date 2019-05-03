package fileparser;

public class FileParseException extends RuntimeException {

    /**
     * Cette exception nous permet de regrouper les différentes erreurs du FileParser.
     * @param message le message affiché
     * @param numLigne le numéro de la ligne correspondant au message d'erreur
     */
    public FileParseException(String message, int numLigne){
        super("\nLigne n°" + numLigne + "\nMessage : " + message);
    }
}
