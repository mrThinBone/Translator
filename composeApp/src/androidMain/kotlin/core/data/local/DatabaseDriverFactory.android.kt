package core.data.local

import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import org.example.translator.TranslateApp
import org.example.translator.TranslateDatabase

actual class DatabaseDriverFactory {

    actual companion object {

        actual fun create(): SqlDriver {
            return AndroidSqliteDriver(TranslateDatabase.Schema, TranslateApp.self, "translate.db")
        }
    }


}