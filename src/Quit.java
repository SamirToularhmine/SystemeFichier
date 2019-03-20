public class Quit implements Commande {

    @Override
    public void execute(ArbreFichiers noeud) {
        System.exit(0);
    }
}
