import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
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
 *      otherwise(() -> "¯\_(ツ)_/¯")
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
    private final Supplier<U> resultSupplier;

    /**
     * @see Matcher#when(Object, Matcher[])
     */
    @SafeVarargs
    public static <T, U> Function<T, Optional<U>> whenValue(Matcher<T, U>... matchers) {
        return value -> when(value, matchers);
    }

    /**
     * @see Matcher
     */
    @SafeVarargs
    public static <T, U> Optional<U> when(T value, Matcher<T, U>... matchers) {
        return Arrays.stream(matchers)
                .filter(matcher -> matcher.predicate.test(value))
                .findFirst()
                .map(matcher -> matcher.resultSupplier.get());
    }

    public static <T, U> Matcher<T, U> matches(T value, Supplier<U> resultSupplier) {
        return new Matcher<>(value::equals, resultSupplier);
    }

    public static <T, U> Matcher<T, U> matchesAny(List<T> values, Supplier<U> resultSupplier) {
        return new Matcher<>(t -> values.stream().anyMatch(v -> v.equals(t)), resultSupplier);
    }

    public static <T, U, V> Matcher<T, U> instanceOf(Class<V> clazz, Supplier<U> resultSupplier) {
        return new Matcher<>(clazz::isInstance, resultSupplier);
    }

    public static <T, U> Matcher<T, U> satisfies(Predicate<T> predicate, Supplier<U> resultSupplier) {
        return new Matcher<>(predicate, resultSupplier);
    }

    public static <T, U> Matcher<T, U> otherwise(Supplier<U> resultSupplier) {
        return new Matcher<>(__ -> true, resultSupplier);
    }

}
