package translate.di

import org.koin.dsl.module
import translate.domain.translate.TranslateUseCase

val translateModule = module {

    factory { TranslateUseCase(get(), get()) }

}