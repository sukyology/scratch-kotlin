package generic;

public class MutableJBox<T> extends JBox<T> {

    public MutableJBox(T element) {
        super(element);
    }

    public T get() {
        return this.element;
    }

    public void set(T element) {
        this.element = element;
    }

}
