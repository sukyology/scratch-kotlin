package generic;

public class JavaBox<T> {

    private final T element;
    public JavaBox(T element) {
        this.element = element;
    }

    public T get() {
        return this.element;
    }

//    public JavaBox<? extends T> self() {
//        return this;
//    }



}
