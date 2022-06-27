package ru.voodster.composekino

import android.graphics.Paint
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun ReelCake(cakePieces:List<String>,modifier: Modifier) {
    val animationAngle = remember { mutableStateOf(0F) }
    val angle: Float by animateFloatAsState(
        targetValue = animationAngle.value,
        animationSpec = tween(
            durationMillis = 2000, // duration
            easing = LinearOutSlowInEasing
        )
    )
    Box(
        modifier = modifier
            .defaultMinSize(200.dp)
            .padding(30.dp)
            .rotate(angle)
            .transformable(rememberTransformableState { _, _, rotationChange ->
                animationAngle.value += rotationChange * 5f
            })

    ) {
        val pieTextPaint = Paint()
        pieTextPaint.color = MaterialTheme.colors.onPrimary.hashCode()
        pieTextPaint.textAlign = Paint.Align.LEFT
        pieTextPaint.textSize = 40f
        val cakeColor = MaterialTheme.colors.primary
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(color = cakeColor)
            endsOfCakeCuts(sizeOfPie = size.height, cuts = cakePieces.size)
                .forEach {
                    println(it.toString())
                    drawLine(
                        Color.Black,
                        start = size.center,
                        end = it,
                        strokeWidth = 6f,
                        cap = StrokeCap.Round
                    )
                }
            pointsBetweenCuts(size.height, cuts = cakePieces.size).forEachIndexed{ ind,it ->
                drawContext.canvas.nativeCanvas
                    .drawText(cakePieces[ind],it.x.minus(pieTextPaint.textSize/4),it.y.plus(pieTextPaint.textSize/4), pieTextPaint)
            }
        }
    }
}



fun endsOfCakeCuts(sizeOfPie: Float, cuts: Int): Array<Offset> {
    val radOfPie = sizeOfPie / 2
    return Array(cuts) {
        Offset(
            x = (radOfPie * cos(2 * PI * it / cuts)).toFloat().times(1.05f).plus(radOfPie),
            y = (radOfPie * sin(2 * PI * it / cuts)).toFloat().times(1.05f).plus(radOfPie)
        )
    }
}

fun pointsBetweenCuts(sizeOfPie: Float, cuts: Int): List<Offset>{
    return endsOfCakeCuts(sizeOfPie, cuts*2).filterIndexed{index, _ ->  (index%2)==1 }
        .map { it.copy((it.x/2).plus(sizeOfPie/4),(it.y/2).plus(sizeOfPie/4)) }
}