import java.util.*;
import java.util.regex.*;
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
        Folder root = new Folder("root");
        Folder a = new Folder("a");
        Fichier b = new Fichier("b",1);
        Fichier c = new Fichier("c",1);
        Folder d = new Folder("d");
        Folder e = new Folder("e");
        Fichier f = new Fichier("f",100);
        Fichier a1 = new Fichier("a1",10);

        System.out.println("\u001B[33m"+a1.getPere()+"\u001B[0m");

        /*
        a.addOnRigth(b);
        b.addOnRigth(c);
        b.addOnleft(d);
        d.addOnRigth(e);
        a.addOnleft(f);
        */
        // f a d e b c
        System.out.println("\u001B[36m"+"premier fils root = "+root.getPremierFils()+"\u001B[0m");
        System.out.println("\u001B[32m"+a.getFrereDroit()+"\u001B[0m");
        List<ArbreFichiers> l2 = root.childrenToList();

        Collections.sort(l2);
        Collections.reverse(l2);
        System.out.println("\u001B[35m"+l2+"\u001B[0m");
        System.out.println("\u001B[33m"+"list2children"+"\u001B[0m");
        root.listToChildren(l2);
        System.out.println("\u001B[33m"+"fin"+"\u001B[0m");
        System.out.println("\u001B[36m"+"premier fils root = "+root.getPremierFils()+"\u001B[0m");
        System.out.println(GREEN+root.childrenToList()+RESET);
        //System.out.println("\u001B[33m"+"children root = "+root.childrenToList()+"\u001B[0m");
        try {
            root.addNode(a1);
            root.addNode(a);
            root.addNode(b);
            root.addNode(c);
            a.addNode(d);
            d.addNode(e);
            e.addNode(f);
            for (int i = 1; i < 3; i++) {

                e.addNode(new Fichier("f"));
            }

        }catch(Exception e1){
            System.out.println(e1.getMessage());
        }
        //System.out.println("\u001B[33m"+"before rm children root = "+root.childrenToList()+"\u001B[0m");
        ArbreFichiers victim = c;
        System.out.println(root.draw());
        System.out.println(a1);
        System.out.println(victim);
        System.out.println("\u001B[31m"+root.nChar("-",15,false)+"\u001B[0m");
        victim.removeNode();
        b.removeNode();
        e.removeNode();
        System.out.println("\u001B[31m"+root.nChar("-",15,false)+"\u001B[0m");
        System.out.println(b);
        System.out.println(root.draw());
        //b.removeNode();
        //a.removeNode();
       //System.out.println(root.draw());









    }


}
