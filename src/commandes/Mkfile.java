package commandes;

import systeme_fichier.Dossier;
import systeme_fichier.Fichier;

import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Mkfile implements Commande{

    private Scanner sc = new Scanner(System.in);
    @Override
    public Optional execute(Dossier currDir, String... args) throws Exception {
        String nomFichier = "";
        for (int i = 0; i < args.length; i++) {
           // System.out.println("\u001B[31m"+"MKFILE ARGS["+i+"]= "+args[i]+"\u001B[0m");
        }
        //TODO: Gérer le cas où l'on passe un chemin en paramètre
        if(args.length!=0) {
            for (int i = 0; i < args.length; i++) {

                if(estChemin(args[i])){
                    String chemin = args[i];
                    Matcher matcher = Pattern.compile("/([A-z]|[0-9])*$").matcher(args[i]);
                    matcher.find();
                    String end = matcher.group();
                    end = end.substring(1);
                    if(end.length()==0)throw new Exception("chemin incorrect");
                    matcher = Pattern.compile("^.*/[^(([A-z]|[0-9]|\\(|\\))*)]").matcher(args[i]);
                    matcher.find();
                    chemin = chemin.substring(0,(chemin.length()-(end.length())));
                    System.out.println("\u001B[31m"+chemin+"\u001B[0m");
                    nomFichier = end;
                    currDir = (Dossier) Commandes.importCmd().get(Commandes.CD).execute(currDir, chemin).get();
                }else{
                    nomFichier = args[i];
                }

                String contenu = demandeContenu();
                Fichier fichier = new Fichier(nomFichier,contenu);
                currDir.ajouterNoeud(fichier);

            }

        }else{

            throw new Exception("nombre d'arguments incorrect");

        }
        return Optional.empty();
    }

    public String demandeContenu(){
        switch (choose("voulez-vous mettre du contenu ?","oui","non")){
            case 1 : System.out.println("rentrez le contenu :");
                     return sc.nextLine();

            case 2 : return "";
        }
        return "";
    }

    public boolean estChemin(String s){
        Matcher matcher = Pattern.compile(".*/.*$").matcher(s);

        return ( matcher.find() ) ;
    }

    private int choose(String...ts){

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
