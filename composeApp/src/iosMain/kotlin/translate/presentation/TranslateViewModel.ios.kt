package translate.presentation

actual class TranslateViewModel actual constructor(
    useCase: TranslateUseCase,
    historyDataSource: HistoryDataSource,
) {

    private val viewModel by lazy {
        TranslateViewModelHandler(useCase, historyDataSource, null)
    }

    val state: CommonStateFlow<TranslateState> = viewModel.state

    actual fun onEvent(event: TranslateEvent) {
        viewModel.onEvent(event)
    }

}

@Composable
actual fun provideTranslateViewModel(useCase: TranslateUseCase, historyDataSource: HistoryDataSource,): TranslateViewModel {
    return TranslateViewModel(useCase, historyDataSource)
}