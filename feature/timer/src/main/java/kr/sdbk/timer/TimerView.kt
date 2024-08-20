package kr.sdbk.timer

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kr.sdbk.common.ui.composables.BaseText
import kr.sdbk.common.ui.theme.Orange
import kr.sdbk.common.ui.theme.RightGrey

@Composable
fun TimerView(
    popupBackStack: () -> Unit,
    viewModel: TimerViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val progressedTime by viewModel.progressedTime.collectAsStateWithLifecycle()

        Clock(totalTime = viewModel.timer.totalTime, progressedTime = progressedTime, viewModel = viewModel)
        TimeInput()
    }
}

@Composable
private fun Clock(
    totalTime: Long,
    progressedTime: Long,
    viewModel: TimerViewModel
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.clickable { viewModel.timer.startTimer(600000) }
    ) {
        ProgressCircle(totalTime = totalTime, progressedTime = progressedTime)
        TimeText(totalTime = totalTime, progressedTime = progressedTime)
    }
}

@SuppressLint("DefaultLocale")
@Composable
private fun TimeText(
    totalTime: Long,
    progressedTime: Long
) {
    val lessTime = (totalTime - progressedTime)
    val timeSec = lessTime / 1000
    val hour = timeSec / 60
    val sec = timeSec % 60
    val locale = Locale.current
    BaseText(
        text = "${String.format("%02d", hour, locale)}:${String.format("%02d", sec, locale)}",
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
        Log.e("qweqwe", "${sweepAngle}")
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
private fun TimeInput() {

}

@Composable
@Preview
private fun Preview() {
    TimerView(popupBackStack = {})
}