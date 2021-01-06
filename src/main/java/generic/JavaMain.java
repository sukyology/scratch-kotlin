package generic;

public class JavaMain {

    public static void main(String[] args) {

        NonGenericBox amubox = new NonGenericBox(new JavaCat());
//        JavaCat amucat = amubox.get();
        // !!!!!!!!!!!!!!!!!!!!!!!!!!
//        JavaLion amucat = (JavaLion) amubox.get();

        JavaBox<JavaCat> box = new JavaBox<>(new JavaCat());
//        JavaBox<Cat> box2 = box;


        JavaBox<? extends JavaCat> wildBox = box;
        JavaBox<? extends Cat> wildBox2 = wildBox;
        Cat catMaybe = wildBox2.get();
//        JavaCat javaCatMaybe = wildBox2.get();



        MutableJavaBox<JavaLion> mutableBox = new MutableJavaBox<>(new JavaLion());
//        MutableJavaBox<Cat> mutableBox2 = mutableBox;

        MutableJavaBox<? extends Cat> extendingBox = mutableBox;

//        extendingBox.set(new JavaCat());
//        extendingBox.set(new JavaLion());
//        extendingBox.set(new JavaAfricaLion());
        Cat catezy = extendingBox.get();
        Cat lion = new JavaLion();

        MutableJavaBox<? extends JavaLion> extendingBox2 = mutableBox;


        MutableJavaBox<? super JavaLion> mutableLionBox = mutableBox;
//        MutableJavaBox<? super JavaAfricaLion> lionBox = mutableLionBox;
        Object maximumAfricaLion = mutableLionBox.get(); // cat or javalion or


    }
}
