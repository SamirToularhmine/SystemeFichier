import java.util.Optional;

public class Quit implements Commande {

    @Override
    public Optional<String> execute(Object...args) {
        System.exit(0);
        return Optional.empty();
    }
}
