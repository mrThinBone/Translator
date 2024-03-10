package translate.data.local

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import org.example.translator.TranslateDatabase

actual class DatabaseDriverFactory {
    actual companion object {
        actual fun create(): SqlDriver {
            return NativeSqliteDriver(TranslateDatabase.Schema, "translate.db")
        }
    }
}