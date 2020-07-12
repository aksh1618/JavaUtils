import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * Similar to Kotlin's {@code when { predicate -> value }}.
 * <br>
 * Usage: <pre>{@code
 *  when(
 *      satisfies(() -> amount < 0, () -> "none"),
 *      satisfies(() -> required > 10 && amount < 10, () -> "less"),
 *      satisfies(() -> required < 10 && amount < 15, () -> "enough"),
 *      satisfies(() -> true, () -> "too much")
 *  );
 * }</pre>
 *
 * @param <T> Return type of {@link AnyMatcher#when(AnyMatcher[])}
 * @see Matcher
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class AnyMatcher<T> {

    private final Supplier<Boolean> predicate;
    private final Supplier<T> result;

    @SafeVarargs
    public static <T> Optional<T> when(AnyMatcher<T>... matchers) {
        return Arrays.stream(matchers)
                .filter(matcher -> matcher.predicate.get())
                .findFirst()
                .map(matcher -> matcher.result.get());
    }

    public static <T> AnyMatcher<T> satisfies(Supplier<Boolean> predicate, Supplier<T> result) {
        return new AnyMatcher<>(predicate, result);
    }

}
