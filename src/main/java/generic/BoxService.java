package generic;

public class BoxService {

    private JLion catish;

    public BoxService(JLion catish) {
        this.catish = catish;
    }

    public Box<? extends Cat> getBox() {
        return new JBox<>(this.catish);
    }
}
