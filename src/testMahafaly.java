import java.util.*;

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
        ArbreFichiers root = new ArbreFichiers(null,null,null,null,"root");
        ArbreFichiers a = new ArbreFichiers("a",0,false);
        ArbreFichiers b = new ArbreFichiers("b",1);
        ArbreFichiers c = new ArbreFichiers("c",1);
        ArbreFichiers d = new ArbreFichiers("d",0,false);
        ArbreFichiers e = new ArbreFichiers("e",0,false);
        ArbreFichiers f = new ArbreFichiers("f",100);
        ArbreFichiers a1 = new ArbreFichiers("a1",10);
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
        System.out.println("\u001B[33m"+"children root = "+root.childrenToList()+"\u001B[0m");
        try {
            root.addNode(a1);
            root.addNode(a);
            root.addNode(b);
            root.addNode(c);
            a.addNode(d);
            d.addNode(e);
            e.addNode(f);
        }catch(Exception e1){

        }
        System.out.println("\u001B[33m"+"before rm children root = "+root.childrenToList()+"\u001B[0m");

        //a1.removeNode();
        //b.removeNode();
        //a.removeNode();

        System.out.println("\u001B[32m"+"after rm"+root.childrenToList()+"\u001B[0m");
        System.out.println(root);
        System.out.println(root.nChar("*",3));
        System.out.println(a.getFrereDroit());
        System.out.println(root.draw());







    }


}
