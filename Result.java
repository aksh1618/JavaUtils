import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@ToString @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Result<T> {

    private final T data;
    private final Exception error;

    public static <T> Result<T> succeed(@NonNull T data) {
        return new Result<>(data, null);
    }

    public static <T> Result<T> fail(@NonNull Exception e) {
        return new Result<>(null, e);
    }

    public static <T> Result<T> generate(Supplier<T> generator) {
        try {
            return Result.succeed(generator.get());
        } catch (Exception e) {
            return Result.fail(e);
        }
    }

    public T get() throws Exception {
        if (null == data) {
            throw error;
        } else {
            return data;
        }
    }

    public Optional<T> get(Consumer<Exception> exceptionHandler) throws Exception {
        if (null == data) {
            exceptionHandler.accept(error);
            return Optional.empty();
        } else {
            return Optional.of(data);
        }
    }

    public void consume(Consumer<T> dataConsumer, Consumer<Exception> exceptionHandler) throws Exception {
        if (null == data) {
            exceptionHandler.accept(error);
        } else {
            dataConsumer.accept(data);
        }
    }

    public <R> Result<R> map(Function<T, R> dataTransformer) {
        if (null != data) {
            return Result.generate(() -> dataTransformer.apply(data));
        } else {
            return Result.fail(error);
        }
    }

    public <R> Optional<R> mapToOptional(Function<T, R> dataTransformer, Consumer<Exception> exceptionHandler) {
        if (null == data) {
            exceptionHandler.accept(error);
            return Optional.empty();
        } else {
            return Optional.ofNullable(dataTransformer.apply(data));
        }
    }

    public boolean succeeded() {
        return null != data;
    }

    public boolean failed() {
        return !succeeded();
    }

}
