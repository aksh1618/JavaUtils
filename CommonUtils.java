import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class CommonUtils {

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

    public static <T> CheckedFunction<T, T> applyChecked(Consumer<T> tConsumer) {
        return t -> apply(() -> t, tConsumer);
    }

    public static <T, R> R with(Supplier<T> tSupplier, Function<T, R> transformation) {
        T t = tSupplier.get();
        return transformation.apply(t);
    }

    public static void tryCatch(CheckedRunnable tryCode, Consumer<Exception> exceptionHandler) {
        try {
            tryCode.run();
        } catch (Exception e) {
            exceptionHandler.accept(e);
        }
    }

    public static <R> Optional<R> tryCatch(CheckedSupplier<R> trySupplier, Consumer<Exception> exceptionHandler) {
        try {
            return Optional.ofNullable(trySupplier.get());
        } catch (Exception e) {
            exceptionHandler.accept(e);
            return Optional.empty();
        }
    }

    public static Consumer<Exception> throwWrappedInRuntime() {
        return e -> {
            throw new RuntimeException(e);
        };
    }

    public static Consumer<? super RuntimeException> throwConsumer() {
        return e -> {
            throw e;
        };
    }

    @SafeVarargs
    public static <T> List<T> combineLists(List<? extends T>... lists) {
        return Arrays.stream(lists).flatMap(Collection::stream).collect(Collectors.toList());
    }

}
