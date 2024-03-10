package core.data.local

import com.squareup.sqldelight.db.SqlDriver

expect class DatabaseDriverFactory {

    companion object {
        fun create(): SqlDriver
    }

}