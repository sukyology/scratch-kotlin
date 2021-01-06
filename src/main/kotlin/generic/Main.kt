package generic

fun main() {
    val box : Box<JavaCat> = Box<JavaCat>(JavaCat())
    val box2 : Box<Cat> = box

    val mutable = MutableBox(JavaCat())
    val mutable2 : MutableBox<out Cat> = mutable
//    mutable2.element = JavaLion()

}