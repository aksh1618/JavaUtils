import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class ScopeFunctionUtils {

    /**
     * @param tSupplier Supplier supplying t
     * @param tConsumer Consumer accepting t
     * @param <T>       Type of t
     * @return t after applying consumer to t supplied by supplier
     */
    public static <T> T apply(Supplier<T> tSupplier, Consumer<T> tConsumer) {
        T t = tSupplier.get();
        tConsumer.accept(t);
        return t;
    }

    /**
     * @param tConsumer Consumer accepting t
     * @param <T>       Type of t
     * @return Function accepting t, applying consumer and returning t
     */
    public static <T> Function<T, T> apply(Consumer<T> tConsumer) {
        return t -> apply(() -> t, tConsumer);
    }

    /**
     * @param tConsumer Consumer accepting t
     * @param <T>       Type of t
     * @return Checked function accepting t, applying consumer and returning t
     */
    public static <T> CheckedFunction<T, T> applyUnsafe(Consumer<T> tConsumer) {
        return t -> apply(() -> t, tConsumer);
    }

    /**
     * @param tSupplier   Supplier supplying t
     * @param transformer Function transforming t to r
     * @param <T>         Type of t
     * @param <R>         Type of r
     * @return r after applying transformer to t supplied by supplier
     */
    public static <T, R> R with(Supplier<T> tSupplier, Function<T, R> transformer) {
        T t = tSupplier.get();
        return transformer.apply(t);
    }

}
