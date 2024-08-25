package kr.sdbk.timer

import android.widget.NumberPicker
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kr.sdbk.common.ui.composables.BaseText
import kr.sdbk.common.ui.theme.Orange
import kr.sdbk.common.ui.theme.RightGrey
import kotlin.math.ceil

@Composable
fun TimerView(
    viewModel: TimerViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.weight(0.5f))
            val progressedTime by viewModel.progressedTime.collectAsStateWithLifecycle()
            val min = remember { mutableStateOf(0) }
            val sec = remember { mutableStateOf(0) }

            Clock(
                totalTime = viewModel.timer.totalTime,
                progressedTime = progressedTime,
                onClickTimer = {
                    if (viewModel.timer.isStarted) {
                        if (viewModel.timer.isPaused) viewModel.timer.resume()
                        else viewModel.timer.pause()
                    } else {
                        viewModel.timer.startTimer(((min.value * 60) + sec.value) * 1000L )
                    }
                }
            )

            val isPlaying by viewModel.isPlaying.collectAsStateWithLifecycle()
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                if (!isPlaying) {
                    TimePicker(
                        min = min,
                        sec = sec
                    )
                }
            }
        }
    }
}

@Composable
private fun Clock(
    totalTime: Long,
    progressedTime: Long,
    onClickTimer: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.clickable { onClickTimer() }
    ) {
        ProgressCircle(totalTime = totalTime, progressedTime = progressedTime)
        TimeText(totalTime = totalTime, progressedTime = progressedTime)
    }
}

@Composable
private fun TimeText(
    totalTime: Long,
    progressedTime: Long
) {
    val lessTime = (totalTime - progressedTime)
    val timeSec = if (lessTime <= 0) 0 else ceil(lessTime / 1000f).toInt()
    val min = timeSec / 60
    val sec = timeSec % 60
    BaseText(
        text = "${String.format("%02d", min)}:${String.format("%02d", sec)}",
        fontSize = 75.sp,
        fontWeight = FontWeight.Bold
    )
}

@Composable
private fun ProgressCircle(
    totalTime: Long,
    progressedTime: Long
) {
    Canvas(
        modifier = Modifier
            .size(350.dp)
            .padding(25.dp)
    ) {
        val size = Size(300.dp.toPx(), 300.dp.toPx())
        val strokeStyle = Stroke(
            width = 25.dp.toPx(),
            cap = StrokeCap.Round
        )

        drawArc(
            color = RightGrey,
            startAngle = 0f,
            sweepAngle = 360f,
            useCenter = false,
            size = size,
            style = strokeStyle
        )

        val sweepAngle = if (progressedTime == 0L) 0f else 360f * (progressedTime / totalTime.toFloat())
        drawArc(
            color = Orange,
            startAngle = -90f,
            sweepAngle = -360f + sweepAngle,
            useCenter = false,
            size = size,
            style = strokeStyle
        )
    }
}

@Composable
private fun TimePicker(
    min: MutableState<Int>,
    sec: MutableState<Int>
) {
    AndroidView(factory = {
        NumberPicker(it).apply {
            value = min.value
            minValue = 0
            maxValue = 59
            displayedValues = (minValue .. maxValue).map { String.format("%02d", it) }.toTypedArray()
            setOnValueChangedListener { numberPicker, i, i2 -> min.value = i2 }
        }
    }, update = {
        it.value = min.value
    })
    Spacer(modifier = Modifier.width(25.dp))

    AndroidView(factory = {
        NumberPicker(it).apply {
            value = min.value
            minValue = 0
            maxValue = 59
            displayedValues = (minValue .. maxValue).map { String.format("%02d", it) }.toTypedArray()
            setOnValueChangedListener { numberPicker, ov, nv ->
                sec.value = nv
                if (ov == maxValue && nv == minValue) min.value++
                else if (ov == minValue && nv == maxValue) min.value--
            }
        }
    }, update = {
        it.value = sec.value
    })
}

@Composable
@Preview
private fun Preview() {
    TimerView()
}