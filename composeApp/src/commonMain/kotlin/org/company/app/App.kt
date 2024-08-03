package org.company.app

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import waterchecker.composeapp.generated.resources.*
import org.company.app.theme.AppTheme
import org.company.app.theme.LocalThemeIsDark
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.random.Random

@Composable
internal fun App() = AppTheme {
    WaterTrackerApp()
}


@Composable
fun WaterTrackerApp() {
    var waterCount by remember { mutableIntStateOf(0) }
    var dayCount by remember {
        mutableIntStateOf(0)
    }
    LoadColorButton(url = "fhaskfhadsf") {
        println("clicked fhaskfhadsf")
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Трекер воды",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(48.dp))

        Text(
            text = "$waterCount мл",
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
            onClick = {
                waterCount += 250
            }
        ) {
            Text(
                text = "+250 мл",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }


        Button(
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
            onClick = {
                if (waterCount >= 1500) {
                    dayCount++
                } else {
                    dayCount = 0
                }

                waterCount = 0
            }
        ) {
            Text(
                text = "Завершить день",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }

        if (dayCount > 0) {
            Text(
                text = "$dayCount дней подряд",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun LoadColorButton(
    url: String,
    onClick: () -> Unit
) {
    var buttonColor by remember { mutableStateOf(Color.Gray) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = url) {
        buttonColor = fetchColorFromNetwork(url)
    }

    Button(
        modifier = Modifier
            .size(100.dp)
            .background(buttonColor),
        onClick = {
            onClick.invoke()
            coroutineScope.launch {
                while (true) {
                    buttonColor = fetchColorFromNetwork(url)
                }
            }
        }
    ){}
}

suspend fun fetchColorFromNetwork(url: String): Color {
    delay(2000)
    val red = Random.nextInt(256)
    val green = Random.nextInt(256)
    val blue = Random.nextInt(256)
    return Color(red, green, blue)
}