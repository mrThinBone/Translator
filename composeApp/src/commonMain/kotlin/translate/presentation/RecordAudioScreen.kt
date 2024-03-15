package translate.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow

class RecordAudioScreen(private val onRecorded: (String) -> Unit) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        Column {
            Text("record audio screen", Modifier.align(Alignment.CenterHorizontally))
            Button(onClick = {
                navigator.pop()
                onRecorded("go back")
            }) {
                Text("go back")
            }
        }
    }
}