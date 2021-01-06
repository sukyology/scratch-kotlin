package generic;

public class MutableJavaBox<T> {

    private T element;
    public MutableJavaBox(T element) {
        this.element = element;
    }

    public T get() {
        return this.element;
    }

    public void set(T element) {
        this.element = element;
    }

}
