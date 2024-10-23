package com.example.dflens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.layout.positionOnScreen
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun DfLensView(
    viewModel: DfLensViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    var isInfoDialogVisible by remember { mutableStateOf(false) }
    if (isInfoDialogVisible) InfoDialog { isInfoDialogVisible = false }
    Column {
        Toolbar(
            currentLifeCount = 0,
            onClickInfo = { isInfoDialogVisible = true }
        )

        GameBoard(viewModel)
    }
}

@Composable
private fun Toolbar(
    currentLifeCount: Int,
    onClickInfo: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 10.dp)
    ) {
        LifeLayer(
            fullLifeCount = 5,
            currentLifeCount = currentLifeCount,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.weight(1f))
        
        InfoButton(
            onClickInfo = onClickInfo
        )
    }
}

@Composable
private fun LifeLayer(
    fullLifeCount: Int,
    currentLifeCount: Int,
    modifier: Modifier
) {
    LazyVerticalGrid(
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        columns = GridCells.Fixed(5),
        modifier = modifier
    ) {
        items((0..fullLifeCount).toList()) {
            val icon = if (it < currentLifeCount) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder
            Image(
                imageVector = icon,
                contentDescription = "",
                colorFilter = ColorFilter.tint(Color.Red),
                modifier = Modifier
                    .size(12.dp)
            )
        }
    }
}

@Composable
private fun InfoButton(
    onClickInfo: () -> Unit
) {
    Image(
        imageVector = Icons.Filled.Info,
        contentDescription = "",
        modifier = Modifier
            .size(24.dp)
            .clickable { onClickInfo() }
    )
}

@Composable
private fun GameBoard(
    viewModel: DfLensViewModel
) {
    var offsetPosition by remember {
        mutableStateOf(Offset(0f, 0f))
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput("dragging") {
                detectDragGestures { change, p ->
                    offsetPosition += p
                }
            }
            .drawWithContent {
                drawContent()
                drawRect(
                    Brush.radialGradient(
                        listOf(Color.Transparent, Color.Black),
                        center = offsetPosition,
                        radius = 100.dp.toPx()
                    )
                )
            }
            .onGloballyPositioned {
                viewModel.start(it.size)
                offsetPosition = Offset(it.size.width / 2f, it.size.height / 2f)
            }
    ) {
        val currentPosition by viewModel.currentPosition.collectAsStateWithLifecycle()
        Canvas(modifier = Modifier) {
            currentPosition?.run {
                drawRect(
                    color = Color.Blue,
                    topLeft = Offset(x.toFloat(), y.toFloat()),
                    size = Size(15f, 15f)
                )
            }
        }
    }
}

@Composable
private fun InfoDialog(
    onDismissRequest: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp)
                .padding(horizontal = 15.dp)
                .background(Color.White, RoundedCornerShape(20.dp))
        ) {
            Text(text = "")
        }
    }
}