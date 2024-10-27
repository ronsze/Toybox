package kr.sdbk.wheel_menu

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import kr.sdbk.common.functions.dpToPx
import kr.sdbk.common.functions.pxToDp
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

@Composable
fun WheelMenuView(
    viewModel: WheelMenuViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val items by remember {
        mutableStateOf(
            listOf(Color.Blue, Color.Red, Color.Yellow, Color.Green, Color.White, Color.Black)
        )
    }

    var rotate by remember { mutableStateOf(0f) }
    var center by remember { mutableStateOf(Offset(0f, 0f)) }
    var initialOffset by remember { mutableStateOf(Offset(0f, 0f)) }
    var selectedIndex by remember { mutableStateOf(0) }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .background(Color.LightGray)
                .onGloballyPositioned {
                    center = it.size.center.toOffset()
                    initialOffset = Offset(center.x, center.y * 0.3f)
                }
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDrag = { change, _ ->
                            change.consume()
                            val angle = getAngleAmount(center, change.previousPosition, change.position)
                            rotate += angle
                        },
                        onDragEnd = {
                            val value: Float = rotate / 60
                            val closest: Float = when {
                                rotate < 0 -> -value
                                rotate > 0 -> abs(value - items.size)
                                else -> 0f
                            }
                            selectedIndex = closest.roundToInt()
                            rotate = -(selectedIndex * 60f)
                        }
                    )
                }
                .graphicsLayer {
                    rotationZ = rotate
                }
        ) {
            items.forEachIndexed { i, v ->
                MenuItem(
                    initialOffset = initialOffset,
                    center = center,
                    index = i,
                    item = v,
                    totalCount = items.size,
                    modifier = Modifier
                )
            }
        }
    }
}

@Composable
private fun MenuItem(
    initialOffset: Offset,
    center: Offset,
    index: Int,
    item: Color,
    totalCount: Int,
    modifier: Modifier
) {
    val size = 75.dp
    val rotatedOffset = rotatePoint(
        initialOffset,
        center,
        360.0 / totalCount * index
    )
    val offset = Offset(
        rotatedOffset.x - dpToPx(dp = (size / 2)),
        rotatedOffset.y - dpToPx(dp = (size / 2))
    )

    Box(
        modifier = modifier
            .size(size)
            .offset(
                pxToDp(px = offset.x),
                pxToDp(px = offset.y)
            )
            .background(item, CircleShape)
    )
}

private fun rotatePoint(input: Offset, center: Offset, angleDegrees: Double): Offset {
    val angleRadians = Math.toRadians(angleDegrees)

    val bx = center.x + (input.x - center.x) * cos(angleRadians) - (input.y - center.y) * sin(angleRadians)
    val by = center.y + (input.x - center.x) * sin(angleRadians) + (input.y - center.y) * cos(angleRadians)

    return Offset(bx.toFloat(), by.toFloat())
}

private fun getAngleAmount(center: Offset, previousPosition: Offset, currentPosition: Offset): Float {
    val previousVector = previousPosition - center
    val currentVector = currentPosition - center

    val previousAngle = Math.toDegrees(atan2(previousVector.y, previousVector.x).toDouble())
    val currentAngle = Math.toDegrees(atan2(currentVector.y, currentVector.x).toDouble())

    val diff = currentAngle - previousAngle
    return if (abs(diff) > 180) 0f else diff.toFloat()
}