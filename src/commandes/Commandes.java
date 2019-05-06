package commandes;

import java.util.HashMap;
import java.util.Map;

public class Commandes {

    /**
     * Ces constantes réprésentes les différentes commandes disponibles dans notre système de fichier
     */
    private static final String LS = "ls";
    public static final String CD = "cd";
    private static final String MKDIR = "mkdir";
    private static final String MKFILE = "mkfile";
    private static final String LESS = "less";
    private static final String PWD = "pwd";
    public static final String RM = "rm";
    private static final String QUIT = "quit";
    private static final String EXIT = "exit";
    private static final String FIND = "find";
    private static final String GREP = "grep";
    private static final String TREE = "tree";
    private static final String HELP = "help";

    private static final Map<String, Commande> commandes = new HashMap<>();

    static {
        commandes.put(QUIT, new Quit());
        commandes.put(EXIT, new Exit());
        commandes.put(CD, new Cd());
        commandes.put(LS, new Ls());
        commandes.put(GREP, new Grep());
        commandes.put(PWD, new Pwd());
        commandes.put(MKFILE, new Mkfile());
        commandes.put(MKDIR, new Mkdir());
        commandes.put(LESS, new Less());
        commandes.put(FIND, new Find());
        commandes.put(TREE,new Tree());
        commandes.put(RM, new Rm());
        commandes.put(HELP, new Help());
    }

    public static Map<String,Commande> importCmd(){
        return commandes;
    }

    public static void putCmd(String s,Commande cmd){
        commandes.putIfAbsent(s,cmd);
    }

}
