package generic;

import java.util.LinkedList;
import java.util.List;

public class JHugeBox<T> {

    private List<T> list;

    public JHugeBox() {
        list = new LinkedList<>();
    }

    public void add(T cat) {
        list.add(cat);
    }

    public List<T> getList() {
        return list;
    }
}
