package core.domain.util

import kotlinx.coroutines.flow.MutableStateFlow

actual class CommonMutableStateFlow<T> actual constructor(flow: MutableStateFlow<T>) :
    MutableStateFlow<T> by flow