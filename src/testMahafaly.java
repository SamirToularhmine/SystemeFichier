import commandes.Commandes;
import systeme_fichier.Dossier;
import systeme_fichier.FichierI;

public class testMahafaly {
    public static final String RESET = "\u001B[0m";
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";

    public static void main(String[] args) {
        Dossier root = new Dossier("root");
        Dossier a = new Dossier("a");
        FichierI b = new FichierI("b", 1);
        FichierI c = new FichierI("c", 1);
        Dossier d = new Dossier("d");
        Dossier e = new Dossier("e");
        FichierI f = new FichierI("f", 100);
        FichierI a1 = new FichierI("a1", 10);



            root.ajouterNoeud(a1);
            root.ajouterNoeud(a);
            root.ajouterNoeud(b);
            root.ajouterNoeud(c);
            a.ajouterNoeud(d);
            d.ajouterNoeud(e);
            //e.ajouterNoeud(f);
            for (int i = 1; i < 30; i++) {

                e.ajouterNoeud(new FichierI("azerzrazazra"));

            }

            System.out.println(root.dessiner());


    }
}
