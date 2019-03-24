package systeme_fichier;

public class Console {

    public Console(){

    }

    public void afficherMenu(IArbreFichier af){
        String s = "\u001B[36m" + af.cheminAbsolu() + "\u001B[0m";
        String in = "\u001B[35m" + "❯" + "\u001B[0m";
        System.out.println(s);
        System.out.print(in + " ");
    }
}
