import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SingleElementImmutableList<T> extends ArrayList<T> {

    private final T element;

    private SingleElementImmutableList(T element) {
        super(1);
        super.add(element);
        this.element = element;
    }

    public static <R> SingleElementImmutableList<R> of(R element) {
        return new SingleElementImmutableList<>(element);
    }

    public T getElement() {
        return element;
    }

    public List<T> toModifiableList() {
        ArrayList<T> list = new ArrayList<>(1);
        list.add(element);
        return list;
    }

    @Override
    public boolean add(T t) {
        throw new UnsupportedOperationException("Can't add to a " + SingleElementImmutableList.class.getName());
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException("Can't remove from a " + SingleElementImmutableList.class.getName());
    }

    @Override
    public boolean addAll(Collection<? extends T> collection) {
        throw new UnsupportedOperationException("Can't add to a " + SingleElementImmutableList.class.getName());
    }

    @Override
    public boolean addAll(int i, Collection<? extends T> collection) {
        throw new UnsupportedOperationException("Can't add to a " + SingleElementImmutableList.class.getName());
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        throw new UnsupportedOperationException("Can't remove from a " + SingleElementImmutableList.class.getName());
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        throw new UnsupportedOperationException("Can't remove from a " + SingleElementImmutableList.class.getName());
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("Can't clear a " + SingleElementImmutableList.class.getName());
    }

    @Override
    public T set(int i, T t) {
        throw new UnsupportedOperationException("Can't set for a " + SingleElementImmutableList.class.getName());
    }

    @Override
    public void add(int i, T t) {
        throw new UnsupportedOperationException("Can't add to a " + SingleElementImmutableList.class.getName());
    }

    @Override
    public T remove(int i) {
        throw new UnsupportedOperationException("Can't remove from a " + SingleElementImmutableList.class.getName());
    }

    @Override
    public List<T> subList(int i, int i1) {
        throw new UnsupportedOperationException("Can't sublist a " + SingleElementImmutableList.class.getName());
    }

}
