package generic;

public class SuperContainer {
    private String element;

    public SuperContainer() {

    }

    public void setElement(String element) {
        this.element = element;
    }
}

class ChildContainer extends SuperContainer {
    private Object element;

    public void setElement(Object element) {
        this.element = element.toString();
    }
}
