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
import androidx.compose.runtime.saveable.rememberSaveable
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

        val listOfOptions: List<Option> = listOf(
            Option("Yes"),
            Option("No"),
        )

        setContent {
            RandomizerTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Options(options = listOfOptions)
                }

                val clicks = rememberSaveable {
                    mutableStateOf(0)
                }
                val lambda = { clicks.value += 1}
                ClickCounter(clicks = clicks.value, lambda)
            }
        }
    }
}

@Composable
fun ClickCounter(clicks: Int, onClick: () -> Unit) {
    Button(onClick = onClick) {
        Text("I've been clicked $clicks times.")
    }
}

@Composable
fun Options(options: List<Option>) {
    Column(
        verticalArrangement = Arrangement.spacedBy(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(80.dp))
        for (option in options) {
            Option(option = option)
        }
    }
}

@Preview(
    name = "Light Mode",
)
@Preview(
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
)
@Composable
fun PreviewOptions() {
    RandomizerTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Options(options = listOf(Option("Yes"), Option("No")))
        }
    }
}

@Composable
fun Option(option: Option) {
    var isHighlighted by remember { mutableStateOf(false) }
    val surfaceColor by animateColorAsState(
        if (isHighlighted) MaterialTheme.colors.primary else MaterialTheme.colors.surface
    )
    Surface(
        shape = MaterialTheme.shapes.medium,
        elevation = 3.dp,
        color = surfaceColor,
        modifier = Modifier.clickable { isHighlighted = !isHighlighted },
    ) {
        Text(
            text = option.text,
            color = MaterialTheme.colors.secondaryVariant,
            modifier = Modifier.padding(all = 8.dp),
            style = MaterialTheme.typography.h4,
        )
    }
}