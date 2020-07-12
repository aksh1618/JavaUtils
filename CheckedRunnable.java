import java.util.function.Consumer;

@FunctionalInterface
public interface CheckedRunnable {

    void run() throws Exception;

    static Runnable wrap(CheckedRunnable checkedRunnable) {
        return () -> CommonUtils.tryCatch(checkedRunnable, CommonUtils.throwWrappedInRuntime());
    }

    static Runnable wrapWithHandler(CheckedRunnable checkedRunnable, Consumer<Exception> exceptionHandler) {
        return () -> CommonUtils.tryCatch(checkedRunnable, exceptionHandler);
    }

}
