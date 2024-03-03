package core.domain.util

fun interface DisposableHandle : kotlinx.coroutines.DisposableHandle

/**
 *
 * equivalent conversion of "fun interface"
 *
fun DisposableHandle(block: () -> Unit): kotlinx.coroutines.DisposableHandle {
    return object : kotlinx.coroutines.DisposableHandle {
        override fun dispose() {
            block()
        }
    }
}
*/