public class Quit implements Commande {

    @Override
    public void execute(ArbreFichier noeud) {
        System.exit(0);
    }
}
