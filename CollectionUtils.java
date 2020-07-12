import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class CollectionUtils {

    // ---------------------- Padding lists ----------------------

    /**
     * Pads given lists with default value upto the size of the largest list (in-place), initializing a list if null
     *
     * @param tLists       List of lists to pad with default
     * @param defaultValue Value to use for padding
     * @param <T>          Type of list elements and default value
     * @return Input list of lists with padding applied
     */
    public static <T> List<List<T>> padListsWithDefault(List<List<T>> tLists, T defaultValue) {
        int paddedSize = tLists.stream().filter(Objects::nonNull).mapToInt(List::size).max().orElse(0);
        tLists.forEach(ts -> {
            if (null == ts) ts = new ArrayList<>();
            if (ts.size() < paddedSize) ts.addAll(Collections.nCopies(paddedSize - ts.size(), defaultValue));
        });
        return tLists;
    }

    /**
     * Pads given lists with default value upto the size of the largest list (in-place), initializing a list if null
     *
     * @param tLists                List of lists to pad with default
     * @param defaultValueGenerator Generator that takes list and gives default value to use for padding
     * @param <T>                   Type of list elements and default value
     * @return Input list of lists with padding applied
     */
    public static <T> List<List<T>> padListsWithGeneratedDefault(
            List<List<T>> tLists,
            Function<List<T>, T> defaultValueGenerator
    ) {
        int paddedSize = tLists.stream().filter(Objects::nonNull).mapToInt(List::size).max().orElse(0);
        for (int i = 0; i < tLists.size(); i++) {
            List<T> ts = tLists.get(i);// This must be done before mutating the lists
            T defaultValue = defaultValueGenerator.apply(ts);
            if (null == ts) ts = new ArrayList<>();
            if (ts instanceof SingleElementImmutableList) ts = ((SingleElementImmutableList<T>) ts).toModifiableList();
            if (ts.size() < paddedSize) ts.addAll(Collections.nCopies(paddedSize - ts.size(), defaultValue));
            tLists.set(i, ts);
        }
        return tLists;
    }

    // ---------------------- Zipping lists ----------------------

    /**
     * Creates a stream of same indexed elements zipped together in a list, upto size of smallest provided list
     * <p>
     * Given lists [a1, a2, a3, a4] & [b1, b2, b3]
     * Return [a1, b1], [a2, b2], [a3, b3]
     * where ai & bi are of same type
     *
     * @param tList List of lists to process
     * @param <T>   Type of elements in lists
     * @return Stream of elements zipped together in a list
     */
    @SafeVarargs
    public static <T> Stream<List<T>> homogenousZippedStreamOf(List<T>... tList) {
        return IntStream
                .range(0, Arrays.stream(tList).mapToInt(List::size).min().orElse(0))
                .mapToObj(i -> Arrays.stream(tList).map(ts -> ts.get(i)).collect(Collectors.toList()));
    }

    /**
     * Creates a stream of same indexed elements zipped together in a list, upto size of smallest provided list
     * <p>
     * Given lists [a1, a2, a3, a4] & [b1, b2, b3]
     * <p>
     * Returns [a1, b1], [a2, b2], [a3, b3]
     * <p>
     * where ai & bi are of same type
     *
     * @param tList List of lists to process
     * @param <T>   Type of elements in lists
     * @return List of elements zipped together in a list
     */
    @SafeVarargs
    public static <T> List<List<T>> homogenousZippedListOf(List<T>... tList) {
        return homogenousZippedStreamOf(tList).collect(Collectors.toList());
    }

    /**
     * Creates a stream of same indexed elements zipped together in a list, upto size of smallest provided list
     * <p>
     * Given lists [a1, a2, a3, a4] & [b1, b2, b3]
     * <p>
     * Returns [a1, b1], [a2, b2], [a3, b3]
     * <p>
     * where ai & bi are of same type
     *
     * @param tListStreamSupplier Stream supplier for lists to process
     * @param <T>                 Type of elements in lists
     * @return Stream of elements zipped together in a list
     */
    public static <T> Stream<List<T>> homogenousZippedStreamOf(Supplier<Stream<List<T>>> tListStreamSupplier) {
        return IntStream
                .range(0, tListStreamSupplier.get().mapToInt(List::size).min().orElse(0))
                .mapToObj(i -> tListStreamSupplier.get().map(ts -> ts.get(i)).collect(Collectors.toList()));
    }

    /**
     * Creates a list of same indexed elements zipped together in a list, upto size of smallest provided list
     * <p>
     * Given lists [a1, a2, a3, a4] & [b1, b2, b3]
     * <p>
     * Returns [a1, b1], [a2, b2], [a3, b3]
     * <p>
     * where ai & bi are of same type
     *
     * @param tListStreamSupplier Stream supplier for lists to process
     * @param <T>                 Type of elements in lists
     * @return List of elements zipped together in a list
     */
    public static <T> List<List<T>> homogenousZippedListOf(Supplier<Stream<List<T>>> tListStreamSupplier) {
        return homogenousZippedStreamOf(tListStreamSupplier).collect(Collectors.toList());
    }

    /**
     * Joins lists maintaining order
     *
     * @param lists Lists to join
     * @param <T>   Type of elements in lists
     * @return list joining provided lists (preserving order)
     */
    @SafeVarargs
    public static <T> List<T> joinLists(List<? extends T>... lists) {
        return Arrays.stream(lists).flatMap(Collection::stream).collect(Collectors.toList());
    }

}
