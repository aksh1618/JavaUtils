import java.util.function.BiConsumer;
import java.util.function.Consumer;

@FunctionalInterface
public interface CheckedConsumer<T> {

    void accept(T t) throws Exception;

    static <I> Consumer<I> wrap(CheckedConsumer<I> checkedConsumer) {
        return i -> CommonUtils.tryCatch(() -> checkedConsumer.accept(i), CommonUtils.throwWrappedInRuntime());
    }

    static <I, __> Consumer<I> wrap(CheckedFunction<I, __> checkedFunction) {
        return i -> CommonUtils.tryCatch(() -> checkedFunction.apply(i), CommonUtils.throwWrappedInRuntime());
    }

    static <I> Consumer<I> wrapWithHandler(CheckedConsumer<I> checkedConsumer, Consumer<Exception> exceptionHandler) {
        return i -> CommonUtils.tryCatch(() -> checkedConsumer.accept(i), exceptionHandler);
    }

    static <I> Consumer<I> wrapWithHandler(
            CheckedConsumer<I> checkedConsumer,
            BiConsumer<I, Exception> exceptionHandler
    ) {
        return i -> CommonUtils.tryCatch(() -> checkedConsumer.accept(i), e -> exceptionHandler.accept(i, e));
    }

}
