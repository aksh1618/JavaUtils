import java.util.Optional;
import java.util.function.Consumer;

public class ExceptionUtils {

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

    public static void throwWrappedInRuntime(Exception e) {
        throw new RuntimeException(e);
    }

    public static <R extends RuntimeException> void rethrow(R r) {
        throw r;
    }

}
