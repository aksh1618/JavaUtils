import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class MutablePair<T, U> {

    private T former;
    private U latter;

    public static <T, U> MutablePair<T, U> of(T former, U latter) {
        return new MutablePair<>(former, latter);
    }

}
