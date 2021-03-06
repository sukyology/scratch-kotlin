package generic

class BoxKt<out T>(
    private val element: T
) {

    fun get(): T = element
}

class MutableBox<T>(
    var element: T
)