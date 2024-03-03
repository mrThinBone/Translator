package translate.domain.translate

import core.domain.language.Language
import core.domain.util.Resource
import translate.domain.history.HistoryDataSource
import translate.domain.history.HistoryItem

class TranslateUseCase(
    private val client: TranslateClient,
    private val dataSource: HistoryDataSource,
) {
    suspend fun execute(
        fromText: String,
        fromLanguage: Language,
        toLanguage: Language,
    ): Resource<String> {
        return try {
            val translatedText = client.translate(fromLanguage, fromText, toLanguage)
            dataSource.insert(
                HistoryItem(
                    id = null,
                    fromLanguageCode = fromLanguage.langCode,
                    toLanguageCode = toLanguage.langCode,
                    fromText = fromText,
                    toText = translatedText
                )
            )
            Resource.Success(translatedText)
        } catch (e: TranslateException) {
            e.printStackTrace()
            Resource.Error(e)
        }
    }
}