import lombok.NonNull;
import lombok.Value;


@Value(staticConstructor = "of")
public class Pair<T, U> {
    @NonNull T former;
    @NonNull U latter;
}

// Delomboked version
//
// import java.util.Objects;
//
// public final class Pair<T, U> {
//     private final T former;
//     private final U latter;
//
//     private Pair(T former, U latter) {
//         Objects.requireNonNull(former);
//         Objects.requireNonNull(latter);
//         this.former = former;
//         this.latter = latter;
//     }
//
//     public static <T, U> Pair<T, U> of(T former, U latter) {
//         Objects.requireNonNull(former);
//         Objects.requireNonNull(latter);
//         return new Pair<T, U>(former, latter);
//     }
//
//     public T getFormer() {
//         return this.former;
//     }
//
//     public U getLatter() {
//         return this.latter;
//     }
//
//     public boolean equals(final Object o) {
//         if (o == this) return true;
//         if (!(o instanceof Pair)) return false;
//         final Pair<?, ?> other = (Pair<?, ?>) o;
//         final Object this$former = this.getFormer();
//         final Object other$former = other.getFormer();
//         if (this$former == null ? other$former != null : !this$former.equals(other$former)) return false;
//         final Object this$latter = this.getLatter();
//         final Object other$latter = other.getLatter();
//         if (this$latter == null ? other$latter != null : !this$latter.equals(other$latter)) return false;
//         return true;
//     }
//
//     public int hashCode() {
//         final int PRIME = 59;
//         int result = 1;
//         final Object $former = this.getFormer();
//         result = result * PRIME + ($former == null ? 43 : $former.hashCode());
//         final Object $latter = this.getLatter();
//         result = result * PRIME + ($latter == null ? 43 : $latter.hashCode());
//         return result;
//     }
//
//     public String toString() {
//         return "Pair(former=" + this.getFormer() + ", latter=" + this.getLatter() + ")";
//     }
//
// }
