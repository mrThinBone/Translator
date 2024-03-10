package translate.presentation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.compose.viewModel
import core.domain.util.CommonStateFlow
import translate.domain.history.HistoryDataSource
import translate.domain.translate.TranslateUseCase

actual class TranslateViewModel actual constructor(
    useCase: TranslateUseCase,
    historyDataSource: HistoryDataSource,
) : ViewModel() {

    private val viewModel by lazy {
        TranslateViewModelHandler(useCase, historyDataSource, viewModelScope)
    }

    actual val state: CommonStateFlow<TranslateState> = viewModel.state

    actual fun onEvent(event: TranslateEvent) {
        viewModel.onEvent(event)
    }

}

//https://developer.android.com/topic/libraries/architecture/viewmodel/viewmodel-factories
class TranslateViewModelFactory(
    private val useCase: TranslateUseCase,
    private val historyDataSource: HistoryDataSource,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        modelClass: Class<T>,
        extras: CreationExtras
    ): T {

        return TranslateViewModel(
            useCase, historyDataSource
        ) as T
    }
}


@Composable
actual fun provideTranslateViewModel(useCase: TranslateUseCase, historyDataSource: HistoryDataSource,): TranslateViewModel {
    Log.d("vinhtv", "provideTranslateViewModel: ")
    return viewModel<TranslateViewModel>(
        factory = TranslateViewModelFactory(useCase, historyDataSource)
    )
}
