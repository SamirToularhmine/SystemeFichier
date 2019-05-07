import systeme_fichier.Console;
import systeme_fichier.SystemeFichier;

public class Main {

    public static void main(String[] args) {
        Console c = new Console();
        SystemeFichier sf;

        if (c.demanderChargerFichier()){
            String nomFichier = c.demanderNomFichier();
            if(nomFichier.equals("")){
                sf = new SystemeFichier();
            }else{
                sf = new SystemeFichier(nomFichier);
            }
        }else{
            sf = new SystemeFichier();
        }

        sf.in();
    }
}
