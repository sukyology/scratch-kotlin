package generic;

public class JBox<T> implements Box<T> {

    protected T element;
    public JBox(T element) {
        this.element = element;
    }

    public T get() {
        return this.element;
    }

//    public JavaBox<? extends T> self() {
//        return this;
//    }



}
