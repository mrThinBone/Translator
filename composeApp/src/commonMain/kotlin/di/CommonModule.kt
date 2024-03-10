package di

import com.squareup.sqldelight.db.SqlDriver
import core.data.local.DatabaseDriverFactory
import io.ktor.client.HttpClient
import org.koin.dsl.module
import translate.data.history.SqldHistoryDataSource
import core.data.remote.HttpClientFactory
import org.example.translator.TranslateDatabase
import translate.data.remote.KtorTranslateClient
import translate.domain.history.HistoryDataSource
import translate.domain.translate.TranslateClient

val commonModule = module {

    single<HttpClient> { HttpClientFactory().create() }

    single<TranslateClient> { KtorTranslateClient(get()) }

    single<SqlDriver> { DatabaseDriverFactory.create()  }

    single<TranslateDatabase> { TranslateDatabase(get()) }

    single<HistoryDataSource> { SqldHistoryDataSource(get()) }

}