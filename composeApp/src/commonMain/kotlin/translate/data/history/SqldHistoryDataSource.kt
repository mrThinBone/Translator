package translate.data.history

import core.domain.util.CommonFlow
import core.domain.util.toCommonFlow
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.datetime.Clock
import org.example.translator.TranslateDatabase
import translate.domain.history.HistoryDataSource
import translate.domain.history.HistoryItem

class SqldHistoryDataSource(
    db: TranslateDatabase
) : HistoryDataSource {

    private val queries = db.translateQueries

    override fun get(): CommonFlow<List<HistoryItem>> {
        return callbackFlow<List<HistoryItem>> {
            val query = queries.getHistory()
            val listener = object : com.squareup.sqldelight.Query.Listener{
                override fun queryResultsChanged() {
                    val list = query.executeAsList()
                    trySend(list.map { it.toHistoryItem() })
                }
            }

            val list = query.executeAsList()
            trySend(list.map { it.toHistoryItem() })

            query.addListener(listener)

            awaitClose { query.removeListener(listener) }
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