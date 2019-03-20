import java.util.Optional;

public class Cd implements Commande {

    @Override
    public Optional<String> execute(Object...args) {
        return Optional.of("Le cd quoi");
    }
}
