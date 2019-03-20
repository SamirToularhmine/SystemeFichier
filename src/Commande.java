import java.util.Optional;

public interface Commande<T> {

    public Optional<T> execute(Object...f);
}
