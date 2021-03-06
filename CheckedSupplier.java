import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

@FunctionalInterface
public interface CheckedSupplier<R> {

    R get() throws Exception;

    static <O> Supplier<O> wrap(CheckedSupplier<O> checkedSupplier) {
        return () -> ExceptionUtils.tryCatch(checkedSupplier, ExceptionUtils::throwWrappedInRuntime).orElse(null);
    }

    static <O> Supplier<Optional<O>> wrapWithHandler(
            CheckedSupplier<O> checkedSupplier,
            Consumer<Exception> exceptionHandler
    ) {
        return () -> ExceptionUtils.tryCatch(checkedSupplier, exceptionHandler);
    }

}
