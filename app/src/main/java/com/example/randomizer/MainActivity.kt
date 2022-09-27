package com.example.randomizer

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.randomizer.ui.theme.RandomizerTheme

data class Option(
    val text: String
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            RandomizerScreen()

//                var clicks by rememberSaveable {
//                    mutableStateOf(0)
//                }
//                val lambda = { clicks += 1}
//                ClickCounter(clicks = clicks, lambda)
        }
    }
}


@Preview(
    name = "Light Mode",
    showBackground = true,
)
@Preview(
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
)
@Composable
fun RandomizerScreen() {
    RandomizerTheme {
        val options: List<Option> = listOf(
            Option("Yes"),
            Option("No"),
        )

        Surface(
            color = MaterialTheme.colors.surface,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.height(80.dp))
                RandomizerOptions(options = options)

                Spacer(modifier = Modifier.height(80.dp))
                Row {
                    Button(onClick = {
                        options.random()
                    }) {
                        Text(
                            text = "Randomize!",
                            style = MaterialTheme.typography.h4,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun RandomizerOptions(options: List<Option>, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Top,
    ) {
        for (option in options) {
            Spacer(modifier = Modifier.width(48.dp))
            Option(option = option)
        }
        Spacer(modifier = Modifier.width(48.dp))
    }
}

@Composable
fun Option(option: Option, modifier: Modifier = Modifier) {
    var isHighlighted by remember { mutableStateOf(false) }
    val surfaceColor by animateColorAsState(
        if (isHighlighted) MaterialTheme.colors.primary else MaterialTheme.colors.surface
    )
    Surface(
        shape = MaterialTheme.shapes.medium,
        elevation = 3.dp,
        color = surfaceColor,
        modifier = modifier.clickable { isHighlighted = !isHighlighted },
    ) {
        Text(
            text = option.text,
            color = MaterialTheme.colors.secondaryVariant,
            modifier = Modifier.padding(all = 8.dp),
            style = MaterialTheme.typography.h4,
        )
    }
}

@Composable
fun ClickCounter(clicks: Int, onClick: () -> Unit) {
    Button(onClick = onClick) {
        Text("I've been clicked $clicks times.")
    }
}