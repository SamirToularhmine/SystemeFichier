package systeme_fichier;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Console {

    public Console(){

    }

    public void afficherMenu(IArbreFichier af){
        String s = "\u001B[36m" + af.cheminAbsolu() + "\u001B[0m";
        String in = "\u001B[35m" + " ❯" + "\u001B[0m";
        System.out.print(s);
        System.out.print(in + " ");
    }

    public boolean demanderChargerFichier(){
        Scanner sc = new Scanner(System.in);
        String s = "Souhaitez-vous charger un fichier ?" +
                "\n1 - Oui" +
                "\n2 - Non";
        System.out.println(s);
        int in = 0;
        do{
            if(sc.hasNextInt()){
                in = sc.nextInt();
                switch(in){
                    case 1: return true;
                    case 2: return false;
                    default: in = 0;break;
                }
            }
            sc.nextLine();
        }while(in == 0);

        return false;
    }

    public String demanderNomFichier(){
        Scanner sc = new Scanner(System.in);
        boolean exists = false;
        String nom= "";
        System.out.println("Veuillez entrer le nom du fichier (rien pour un arborescence vide):");
        do {
            nom = sc.nextLine();
            if(nom.equals("")){
                exists = true;
            }else{
                File f = new File(nom);
                if(f.exists()){
                    exists = true;
                }else{
                    System.out.println("Le fichier est introuvable, veuillez entrer un autre nom de fichier !");
                }
            }

        }while(!exists);

        return nom;
    }
}
