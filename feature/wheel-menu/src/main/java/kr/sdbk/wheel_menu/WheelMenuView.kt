package kr.sdbk.wheel_menu

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.center
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toOffset
import kotlin.math.abs
import kotlin.math.atan2

@Composable
fun WheelMenuView(
    viewModel: WheelMenuViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    var rotate by remember { mutableStateOf(0f) }
    var center by remember { mutableStateOf(Offset(0f, 0f)) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .onGloballyPositioned {
                center = it.size.center.toOffset()
            }
            .pointerInput(Unit) {
                detectDragGestures(
                    onDrag = { change, _ ->
                        change.consume()
                        val angle = getAngleAmount(center, change.previousPosition, change.position)
                        rotate += angle
                    }
                )
            }
    ) {
        Box(modifier = Modifier
            .size(300.dp)
            .align(Alignment.Center)
            .graphicsLayer {
                rotationZ = rotate
            }
        ) {
            Box(modifier = Modifier
                .fillMaxSize()
                .background(Color.Blue)
            )
        }
    }
}

fun getAngleAmount(center: Offset, previousPosition: Offset, currentPosition: Offset): Float {
    val previousVector = previousPosition - center
    val currentVector = currentPosition - center

    val previousAngle = Math.toDegrees(atan2(previousVector.y, previousVector.x).toDouble())
    val currentAngle = Math.toDegrees(atan2(currentVector.y, currentVector.x).toDouble())

    val diff = currentAngle - previousAngle
    return if (abs(diff) > 180) 0f else diff.toFloat() * 0.8f
}

//private fun getRotateAmount(center: Offset, initialPointer: Offset, currentTouch: Offset): Float {
//    val initialVector = initialPointer - center
//    val currentVector = currentTouch - center
//
//    val initialAngle = atan2(initialVector.y, initialVector.x)
//    val currentAngle = atan2(currentVector.y, currentVector.x)
//
//    val degrees =  Math.toDegrees((currentAngle - initialAngle).toDouble()).toFloat()
//    Log.e("qweqwe4", "$degrees")
//    return degrees
//}