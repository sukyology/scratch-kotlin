package generic

fun main() {
    val box : BoxKt<JCat> = BoxKt<JCat>(JCat())
    val box2 : BoxKt<Cat> = box

    val mutable = MutableBox(JCat())
    val mutable2 : MutableBox<out Cat> = mutable
//    mutable2.element = JavaLion()

}