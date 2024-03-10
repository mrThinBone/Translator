import android.app.Application
import core.data.local.DatabaseDriverFactory

class TranslateApp : Application() {

    override fun onCreate() {
        super.onCreate()
        DatabaseDriverFactory.context = this
    }

}