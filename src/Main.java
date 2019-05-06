import systeme_fichier.Console;
import systeme_fichier.SystemeFichier;

public class Main {

    public static void main(String[] args) {
        Console c = new Console();
        SystemeFichier sf;

        if (c.demanderChargerFichier()){
            sf = new SystemeFichier("arbo");
        }else{
            sf = new SystemeFichier();
        }

        sf.in();
    }
}
