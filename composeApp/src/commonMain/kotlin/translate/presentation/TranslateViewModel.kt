package translate.presentation

import androidx.compose.runtime.Composable
import core.domain.util.CommonStateFlow
import translate.domain.history.HistoryDataSource
import translate.domain.translate.TranslateUseCase

expect class TranslateViewModel(
    useCase: TranslateUseCase,
    historyDataSource: HistoryDataSource,
) {
    val state: CommonStateFlow<TranslateState>

    fun onEvent(event: TranslateEvent)

}

@Composable
expect fun provideTranslateViewModel(useCase: TranslateUseCase, historyDataSource: HistoryDataSource,): TranslateViewModel