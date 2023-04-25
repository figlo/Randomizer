package sk.figlar.randomizer

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import sk.figlar.randomizer.ui.theme.RandomizerTheme
import kotlin.random.Random

val random = Random(System.currentTimeMillis())

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            RandomizerScreen()
        }
    }
}

@DarkLightPreviews
@Composable
fun RandomizerScreen() {
    RandomizerTheme {
        val options: List<Option> = listOf(
            Option(stringResource(R.string.yes)),
            Option(stringResource(R.string.no)),
        )

        var selectedOption: Option? by rememberSaveable { mutableStateOf(null) }
        var timeInMillis: Long by rememberSaveable { mutableStateOf(System.currentTimeMillis()) }


        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = stringResource(R.string.yesNoHint),
                    style = MaterialTheme.typography.h5,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(start = 20.dp, end = 20.dp),
                )
                Spacer(modifier = Modifier.height(60.dp))
                RandomizerOptions(
                    options = options,
                    selectedOption = selectedOption,
                    timeInMillis = timeInMillis,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(60.dp))
                RandomizeButton(onRandomize = {
                    selectedOption = options.random(random)
                    timeInMillis = System.currentTimeMillis()
                })
            }
        }
    }
}

@Composable
fun RandomizerOptions(options: List<Option>, selectedOption: Option?, timeInMillis: Long, modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        for (option in options) {
            val isOptionSelected = option == selectedOption
            Option(
                option = option,
                isOptionSelected = isOptionSelected,
                timeInMillis = timeInMillis,
            )
        }
    }
}

@Composable
fun Option(option: Option, isOptionSelected: Boolean, timeInMillis: Long, modifier: Modifier = Modifier) {
    val primaryColor = MaterialTheme.colors.primary
    val surfaceColor = MaterialTheme.colors.surface

    val color = remember { Animatable(surfaceColor) }

    LaunchedEffect(key1 = timeInMillis) {
        color.animateTo(surfaceColor, animationSpec = tween(600))
        if (isOptionSelected) {
            color.animateTo(primaryColor, animationSpec = tween(300))
        }
    }

    Surface(
        shape = MaterialTheme.shapes.medium,
        elevation = 3.dp,
        color = color.value,
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

@Composable
private fun RandomizeButton(onRandomize: () -> Unit) {
    Row {
        Button(onClick = onRandomize) {
            Text(
                text = stringResource(R.string.randomize),
                style = MaterialTheme.typography.h4,
            )
        }
    }
}

@Preview(
    name = "Light Mode",
    group = "UI mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL,
    showSystemUi = true,
    showBackground = true,
)
@Preview(
    name = "Dark Mode",
    group = "UI mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL,
    showSystemUi = true,
    showBackground = true,
)
annotation class DarkLightPreviews