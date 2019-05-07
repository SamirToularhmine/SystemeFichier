package systeme_fichier;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ToolBox {

    private static final String PATTERN_CHAR_AUTORISEES = "[^\\\\|:|*|?|<|>|\\|]*" ;

    static String nChar(String c, int n, boolean t){
        String s=(n==0||t)?"":"│";
        for (int i = 0; i < n; i++) {
            s += c;
        }
        return s;
    }

    public static void characterAutorises(String s) throws Exception{

        Pattern p = Pattern.compile("[^\\\\|:|*|?|<|>|\\|]*");
        Matcher m  = p.matcher(s);
        if(!m.matches()){
            Pattern pattern = Pattern.compile("[^"+PATTERN_CHAR_AUTORISEES+"]");
            Matcher matcher = pattern.matcher(s);
            String notAllowed = "";
            while (matcher.find()) {
               notAllowed += ("\u001B[36m"+matcher.group()+" "+"\u001B[0m") ;
            }
            throw new Exception("\u001B[31m"+"characters : "+notAllowed+"\u001B[31m"+"non autorisés"+"\u001B[0m");
        }
    }

    public static boolean estChemin(String s){
        Matcher matcher = Pattern.compile("(.*/.*)|(\\.{1,2})$").matcher(s);

        return (matcher.find() ) ;
    }

    public static String getNomChemin(String s) throws Exception{
        String nom = s;

        if (ToolBox.estChemin(s)){
            String chemin = s;
            Matcher matcher = Pattern.compile("/[^\\\\|:|*|?|<|>|\\||/]*$").matcher(s);
            matcher.find();
            String end = matcher.group();
            end = end.substring(1);
            if (end.length() == 0) throw new NomCheminException("chemin : \" "+s+ " \" incorrect");
            nom = end;
        }

        return nom;
    }

    public  static String getChemin(String s)throws Exception{
        String chemin = s;
        String end = getNomChemin(s);
        //Matcher matcher = Pattern.compile("^[^(([A-z]|[0-9]|\\(|\\))*)]*/[^(([A-z]|[0-9]|\\(|\\))*)]").matcher(s);
        //matcher.find();
        chemin = chemin.substring(0, (chemin.length() - (end.length())));
        return chemin;
    }

    public static int  choose(String...ts){

        Scanner sc = new Scanner(System.in);
        StringBuilder s = new StringBuilder();
        s.append(ts[0]);
        if(!(ts.length==1)) {

            for (int i = 1; i < ts.length; i++) {

                String s1 = (!ts[i].equals(""))?"\n"+i+" - "+ts[i]:"";
                s.append(s1);
            }

            while (true) {

                System.out.println(s);

                if (sc.hasNextInt()) {
                    int choice = sc.nextInt();
                    if (choice >= 1 && choice < ts.length) {
                        return choice;
                    }
                }
                System.out.println("Saisie erronée : Votre saisie doit être comprise entre 1 et " + (ts.length - 1));
                sc.nextLine();
            }

        }else{
            System.out.println(s);
            while(true) {
                if(sc.hasNextInt()) {
                    return sc.nextInt();
                }
                if(sc.hasNextLine()){
                    String quit = sc.next();
                    if(quit.equals("q")){
                        return -1;
                    }
                }
                sc.nextLine();
                System.out.println("Veuillez n'entrer que des chiffres svp !");
            }
        }

    }
}
