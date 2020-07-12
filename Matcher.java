import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Similar to Kotlin's {@code when(param) { matcher -> value }}.
 * <br>
 * Usage: <pre>{@code
 *  when(value,
 *      matches(myVal, () -> "Matched myVal!"),
 *      instanceOf(MyClass.class, () -> "Instance of MyClass!"),
 *      satisfies(val -> val > 10 && val < 15, () -> "Between 10 & 15!"),
 *  );
 * }</pre>
 *
 * @param <T> Type of value to match
 * @param <U> Return type of {@link Matcher#when}
 * @see AnyMatcher
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Matcher<T, U> {

    private final Predicate<T> predicate;
    private final Supplier<U> result;

    @SafeVarargs
    public static <T, U> Optional<U> when(T value, Matcher<T, U>... matchers) {
        return Arrays.stream(matchers)
                .filter(matcher -> matcher.predicate.test(value))
                .findFirst()
                .map(matcher -> matcher.result.get());
    }

    public static <T, U> Matcher<T, U> matches(T value, Supplier<U> result) {
        return new Matcher<>(value::equals, result);
    }

    public static <T, U, V> Matcher<T, U> instanceOf(Class<V> clazz, Supplier<U> result) {
        return new Matcher<>(clazz::isInstance, result);
    }

    public static <T, U> Matcher<T, U> satisfies(Predicate<T> predicate, Supplier<U> result) {
        return new Matcher<>(predicate, result);
    }

}
