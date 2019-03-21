package fileparser;

public class FileParseException extends RuntimeException {

    public FileParseException(String message, int numLigne){
        super("\nLigne n°" + numLigne + "\nMessage : " + message);
    }
}
