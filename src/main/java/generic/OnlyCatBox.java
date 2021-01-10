package generic;

public class OnlyCatBox <T extends Cat
        > {

    private final Cat element;
    public OnlyCatBox(T element) {
        this.element = element;
    }

    public Cat get() {
        return this.element;
    }

    public OnlyCatBox<? extends T> self() {
        return this;
    }
}