package commandes;

import java.util.HashMap;
import java.util.Map;

public class Commandes {

    //Peut etre provisoire, c'est une proposition de classe pour stocker les commandes reconnues par le système de fichier
    public static final String LS = "ls";
    public static final String CD = "cd";
    public static final String MKDIR = "mkdir";
    public static final String MKFILE = "mkfile";
    public static final String LESS = "less";
    public static final String PWD = "pwd";
    public static final String RM = "rm";
    public static final String QUIT = "quit";
    public static final String EXIT = "exit";

    public static Map<String, Commande> commandes = new HashMap<>();

    static {
        commandes.put("quit", new Quit());
        commandes.put("cd", new Cd());
        commandes.put("ls",new Ls());
    }

    public static Map<String,Commande> importCmd(){
        return commandes;
    }

    public static void putCmd(String s,Commande cmd){
        commandes.put(s,cmd);
    }

}