package translate.data.history

import core.domain.util.CommonFlow
import core.domain.util.toCommonFlow
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.Clock
import org.example.translator.TranslateDatabase
import translate.domain.history.HistoryDataSource
import translate.domain.history.HistoryItem

class SqldHistoryDataSource(
    db: TranslateDatabase
) : HistoryDataSource {

    private val queries = db.translateQueries

    override fun get(): CommonFlow<List<HistoryItem>> {
        return flow {
            val list = queries.getHistory().executeAsList()
            emit(list.map { it.toHistoryItem() })
        }.toCommonFlow()
    }

    override suspend fun insert(item: HistoryItem) {
        queries.insertHistoryEntity(
            id = item.id,
            fromLanguageCode = item.fromLanguageCode,
            fromText = item.fromText,
            toLanguageCode = item.toLanguageCode,
            toText = item.toText,
            timestamp = Clock.System.now().toEpochMilliseconds()
        )
    }

}