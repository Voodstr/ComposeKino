package ru.voodster.composekino

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.voodster.composekino.ui.theme.ComposeKinoTheme
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeKinoTheme {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Greeting("Player")
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(10.dp)
                    )
                    PieScroll()
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(10.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
}

@Composable
fun PieScroll() {
    val animationAngle = remember { mutableStateOf(0F) }

    val angle: Float by animateFloatAsState(
        targetValue = animationAngle.value,
        animationSpec = tween(
            durationMillis = 2000, // duration
            easing = LinearOutSlowInEasing
        )
    )
    Box(
        modifier = Modifier
            .size(300.dp)
            .rotate(angle)
            .draggable(
                state = rememberDraggableState {
                    animationAngle.value += it
                }, orientation = Orientation.Horizontal
            )
    ) {
        val pieces = listOf("1", "2", "3", "4", "5", "6")
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(color = Color.Red)
            endsOfPieCuts(size.height, pieces.size).forEach {
                println(it.toString())
                drawLine(
                    Color.Black,
                    start = size.center,
                    end = it,
                    strokeWidth = 10f,
                    cap = StrokeCap.Round
                )
            }
        }
    }

}

fun endsOfPieCuts(sizeOfPie: Float, pieces: Int): Array<Offset> {
    val radOfPie = sizeOfPie / 2
    return Array(pieces) {
        Offset(
            x = (radOfPie * cos(2 * PI * it / pieces)).toFloat().times(0.9f).plus(radOfPie),
            y = (radOfPie * sin(2 * PI * it / pieces)).toFloat().times(0.9f).plus(radOfPie)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PieScrollPreview() {
    ComposeKinoTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Greeting("Player")
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
            )
            PieScroll()
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
            )
        }
    }
}

@Composable
fun MainContent() {
    val animationAngle = remember { mutableStateOf(0F) }

    val angle: Float by animateFloatAsState(
        targetValue = animationAngle.value,
        animationSpec = tween(
            durationMillis = 2000, // duration
            easing = LinearOutSlowInEasing
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF0F8FF))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                animationAngle.value += 30F
            },
            colors = ButtonDefaults.buttonColors(
                Color(0xFF4B0082), Color(0xCCFFFFFF)
            )
        ) {
            Text(
                text = "Rotate Icon",
                modifier = Modifier.padding(12.dp)
            )
        }

        Icon(
            Icons.Default.AccountBox,
            contentDescription = "Localized description",
            Modifier
                .size(300.dp)
                .rotate(angle),
            tint = Color(0xFF5218FA)
        )
    }
}


@Preview
@Composable
fun RotatePreview() {
    MainContent()
}