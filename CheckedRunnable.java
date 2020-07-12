import java.util.function.Consumer;

@FunctionalInterface
public interface CheckedRunnable {

    void run() throws Exception;

    static Runnable wrap(CheckedRunnable checkedRunnable) {
        return () -> ExceptionUtils.tryCatch(checkedRunnable, ExceptionUtils::throwWrappedInRuntime);
    }

    static Runnable wrapWithHandler(CheckedRunnable checkedRunnable, Consumer<Exception> exceptionHandler) {
        return () -> ExceptionUtils.tryCatch(checkedRunnable, exceptionHandler);
    }

}
