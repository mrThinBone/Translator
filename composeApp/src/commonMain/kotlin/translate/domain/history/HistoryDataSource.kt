package translate.domain.history

import core.domain.util.CommonFlow

interface HistoryDataSource {

    fun get(): CommonFlow<List<HistoryItem>>
    suspend fun insert(item: HistoryItem)

}