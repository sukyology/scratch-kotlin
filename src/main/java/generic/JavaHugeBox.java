package generic;

import java.util.LinkedList;
import java.util.List;

public class JavaHugeBox<T> {

    private List<T> list;

    public JavaHugeBox() {
        list = new LinkedList<>();
    }

    public void add(T cat) {
        list.add(cat);
    }

    public List<T> getList() {
        return list;
    }
}
