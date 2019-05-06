package systeme_fichier;

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
}
