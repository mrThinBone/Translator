package core.domain.util

import kotlinx.coroutines.flow.StateFlow

actual class CommonStateFlow<T> actual constructor(flow: StateFlow<T>) :
    StateFlow<T> by flow