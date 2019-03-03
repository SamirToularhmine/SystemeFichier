import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

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

        List<Integer> l =  new ArrayList();
        l.add(1);
        l.add(2);
        ListIterator<Integer> it = l.listIterator();
        int i =5;
        while((!l.isEmpty())){
            if(i<7){
                it.add(i);
            }

            i++;
            System.out.println("iterator value = "+it.next());
            it.remove();
            System.out.println("i = "+i);
            if(!it.hasNext()){
                it = l.listIterator();
            }
            System.out.println(l);
        }

        ArbreFichiers root = new ArbreFichiers(null,null,null,null,"root");
        ArbreFichiers a = new ArbreFichiers(root,null,null,null,"a");
        ArbreFichiers b = new ArbreFichiers("b");
        ArbreFichiers c = new ArbreFichiers("c");
        ArbreFichiers d = new ArbreFichiers("d");
        ArbreFichiers e = new ArbreFichiers("e");
        ArbreFichiers f = new ArbreFichiers("f");
        a.addOnRigth(b);
        b.addOnRigth(c);
        b.addOnleft(d);
        d.addOnRigth(e);
        a.addOnleft(f);
        // f a d e b c
        System.out.println("\u001B[32m"+a.getFrereDroit()+"\u001B[0m");
        System.out.println(GREEN+root.childrenToList()+RESET);









    }


}
