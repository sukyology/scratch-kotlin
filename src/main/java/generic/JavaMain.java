package generic;

import java.util.ArrayList;
import java.util.List;

public class JavaMain {

    public static void main(String[] args) {




        // 1. generic이 없을 때 문제
        RawJBox rawBox = new RawJBox(new JCat());
        JCat amucat = rawBox.get(); // compile error!! 실제 인스턴스의 클래스와는 상관없이 에러가 난다.
        // !!!!!!!!!!!!!!!!!!!!!!!!!!
        JLion possibleLion = (JLion) rawBox.get(); // no compile error!! 실제 인스턴스의 클래스가 아닌데도...



        JBox<JCat> box = new JBox<>(new JCat());
//        JavaBox<Cat> box2 = box;



        JBox<? extends JCat> wildBox = box;
        JBox<? extends Cat> wildBox2 = wildBox;
        Cat catMaybe = wildBox2.get();
//        JCat javaCatMaybe = wildBox2.get();



        MutableJBox<JLion> mutableBox = new MutableJBox<>(new JLion());
//        MutableJavaBox<Cat> mutableBox2 = mutableBox;

        MutableJBox<? extends Cat> extendingBox = mutableBox;
        MutableJBox<?> wildwildBox = mutableBox;
        Object o = wildwildBox.get();

//        extendingBox.set(new JavaCat());
//        extendingBox.set(new JavaLion());
//        extendingBox.set(new JavaAfricaLion());
        Cat catezy = extendingBox.get();
        Cat lion = new JLion();

        MutableJBox<? extends JLion> extendingBox2 = mutableBox;


        MutableJBox<? super JLion> mutableLionBox = mutableBox;
//        MutableJavaBox<? super JavaAfricaLion> lionBox = mutableLionBox;
        Object maximumAfricaLion = mutableLionBox.get(); // cat or javalion or

        List<? super Box<? extends Cat>> boxes = new ArrayList<>();
        JBox<JLion> lionJBox = new JBox<>(new JLion());
        boxes.add(lionJBox);

        //생성 코드 짜는 사람은 JLion이 뭔지 알고 있다.
        BoxService lionBoxService = new BoxService(new JLion());

        //해당 박스를 쓰는 사람은...
        Box<? extends Cat> notKnowLionCaller = lionBoxService.getBox();
    }
}
