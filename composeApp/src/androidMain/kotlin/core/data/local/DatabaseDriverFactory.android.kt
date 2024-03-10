package core.data.local

import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import org.example.translator.TranslateDatabase

actual class DatabaseDriverFactory {

    actual companion object {

        lateinit var context: Context

        actual fun create(): SqlDriver {
            return AndroidSqliteDriver(TranslateDatabase.Schema, context, "translate.db")
        }
    }


}