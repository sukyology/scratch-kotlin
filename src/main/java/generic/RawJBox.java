package generic;

public class RawJBox {

    private final Object element;
    public RawJBox(Object element) {
        this.element = element;
    }

    public Object get() {
        return this.element;
    }

}
