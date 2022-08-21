package com.example.randomizer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
                    PrintOptions(options = listOfOptions)
                }
            }
        }
    }
}

@Composable
fun PrintOptions(options: List<Option>) {
    Column(
        verticalArrangement = Arrangement.spacedBy(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(80.dp))
        for (option in options) {
            PrintOption(option = option)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPrintOptions() {
    RandomizerTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            PrintOptions(options = listOf(Option("Yes"), Option("No")))
        }
    }
}

@Composable
fun PrintOption(option: Option) {
    Surface(shape = MaterialTheme.shapes.medium, elevation = 3.dp) {
        Text(
            text = option.text,
            color = MaterialTheme.colors.secondaryVariant,
            modifier = Modifier.padding(all = 8.dp),
            style = MaterialTheme.typography.h4,
        )
    }
}