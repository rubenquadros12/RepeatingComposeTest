package com.ruben.repeatingtest

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.ProvideWindowInsets
import com.ruben.repeatingtest.ui.theme.RepeatingTestTheme
import kotlinx.coroutines.flow.distinctUntilChanged

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            RepeatingTestTheme {
                ProvideWindowInsets {
                    // A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colors.background
                    ) {
                        MyScreen()
                    }
                }
            }
        }
    }
}

@Composable
fun MyScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Greeting(modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
fun Greeting(modifier: Modifier = Modifier) {
    val windowInsets = LocalWindowInsets.current

    val keyboard by remember {
        mutableStateOf(windowInsets.ime)
    }

    var shouldShow by remember {
        mutableStateOf(true)
    }

    LaunchedEffect(Unit) {
        snapshotFlow { keyboard.isVisible }.distinctUntilChanged().collect {
            Log.d("Ruben", "isVisible: $it")
            shouldShow = it.not()
        }
    }

    Column(modifier = modifier) {
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(8.dp),
            text = "Hello man!"
        )

        if (shouldShow) {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(8.dp),
                text = "Hey there"
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RepeatingTestTheme {
        Greeting()
    }
}