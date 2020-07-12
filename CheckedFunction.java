import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

@FunctionalInterface
public interface CheckedFunction<T, R> {

    R apply(T t) throws Exception;

    static <I, O> Function<I, O> wrap(CheckedFunction<I, O> function) {
        return i -> ExceptionUtils.tryCatch(() -> function.apply(i), ExceptionUtils::throwWrappedInRuntime).orElse(null);
    }

    static <I, O> Function<I, Optional<O>> wrapWithHandler(
            CheckedFunction<I, O> function,
            Consumer<Exception> exceptionHandler
    ) {
        return i -> ExceptionUtils.tryCatch(() -> function.apply(i), exceptionHandler);
    }

    static <I, O> Function<I, Optional<O>> wrapWithHandler(
            CheckedFunction<I, O> function,
            BiConsumer<I, Exception> exceptionHandler
    ) {
        return i -> ExceptionUtils.tryCatch(() -> function.apply(i), e -> exceptionHandler.accept(i, e));
    }

}
