
import systeme_fichier.Dossier;
import systeme_fichier.Fichier;
import systeme_fichier.SystemeFichier;
import systeme_fichier.ToolBox;


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
        Fichier b = new Fichier("b", 1);
        Fichier c = new Fichier("c", 1);
        Dossier d = new Dossier("d");
        Dossier e = new Dossier("e");
        Fichier f = new Fichier("f", 100);
        Fichier a1 = new Fichier("a1", 10);



            root.ajouterNoeud(a1);
            root.ajouterNoeud(a);
            root.ajouterNoeud(b);
            root.ajouterNoeud(c);
            a.ajouterNoeud(d);
            d.ajouterNoeud(e);
            //e.ajouterNoeud(f);
            for (int i = 1; i < 2; i++) {

                e.ajouterNoeud(new Fichier("azerzrazazra","1"));

            }

            System.out.println(root.dessiner());
            System.out.println("\u001B[34m"+a1+"\n"+root+"\u001B[0m");
            System.out.println("------------------------------------");
        a1.setContenu("11");
        System.out.println("\u001B[34m"+a1+"\n"+root+"\u001B[0m");
        System.out.println(root.dessiner());

           // SystemeFichier systemeFichier = new SystemeFichier("arbo2");
            //systemeFichier.in();


    }
}
