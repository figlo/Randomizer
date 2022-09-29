package com.example.randomizer

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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

        var selectedOption: Option? by rememberSaveable { mutableStateOf(null) }

        Surface(
            color = MaterialTheme.colors.surface,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.height(80.dp))
                RandomizerOptions(options = options, selectedOption = selectedOption)

                Spacer(modifier = Modifier.height(80.dp))
                RandomizeButton(onRandomize = { selectedOption = options.random() })
            }
        }
    }
}

@Composable
private fun RandomizeButton(onRandomize: () -> Unit) {
    Row {
        Button(onClick = onRandomize) {
            Text(
                text = "Randomize!",
                style = MaterialTheme.typography.h4,
            )
        }
    }
}

@Composable
fun RandomizerOptions(options: List<Option>, selectedOption: Option?, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Top,
    ) {
        for (option in options) {
            Spacer(modifier = Modifier.width(48.dp))
            val isOptionSelected = option == selectedOption
            Option(option = option, isOptionSelected = isOptionSelected)
        }
        Spacer(modifier = Modifier.width(48.dp))
    }
}

@Composable
fun Option(option: Option, isOptionSelected: Boolean, modifier: Modifier = Modifier) {
    val surfaceColor by animateColorAsState(
        if (isOptionSelected) MaterialTheme.colors.primary else MaterialTheme.colors.surface
    )
    Surface(
        shape = MaterialTheme.shapes.medium,
        elevation = 3.dp,
        color = surfaceColor,
        modifier = modifier,
    ) {
        Text(
            text = option.text,
            color = MaterialTheme.colors.secondaryVariant,
            modifier = Modifier.padding(all = 8.dp),
            style = MaterialTheme.typography.h4,
        )
    }
}