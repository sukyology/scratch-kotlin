package generic;

public class NonGenericBox {

    private final Object element;
    public NonGenericBox(Object element) {
        this.element = element;
    }

    public Object get() {
        return this.element;
    }

}
