package com.example.snake

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun SnakeView(
    viewModel: SnakeViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.start()
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(15.dp))

        Score(currentScore = viewModel.currentScore.collectAsStateWithLifecycle().value)
        Spacer(modifier = Modifier.height(15.dp))

        val board = viewModel.boardArray
        GameBoard(
            board = board
        )

        Keypad(
            viewModel = viewModel
        )
    }
}

@Composable
private fun Score(
    currentScore: Int
) {
    Text(
        text = "Score : $currentScore"
    )
}

@Composable
private fun GameBoard(
    board: List<List<SnakeViewModel.CellType>>
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(SnakeViewModel.ARRAY_SIZE),
        modifier = Modifier.padding(horizontal = 10.dp)
    ) {
        items(board.flatten()) {
            BoardBlock(value = it)
        }
    }
}

@Composable
private fun BoardBlock(
    value: SnakeViewModel.CellType
) {
    val color = when (value) {
        SnakeViewModel.CellType.BLANK -> Color.White
        SnakeViewModel.CellType.BLOCK -> Color.LightGray
        SnakeViewModel.CellType.HEAD -> Color.Red
        SnakeViewModel.CellType.BODY -> Color.Yellow
        SnakeViewModel.CellType.BONUS -> Color.Blue
    }

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .aspectRatio(1f)
    ) {
        drawRect(color)
    }
}

@Composable
private fun Keypad(
    viewModel: SnakeViewModel
) {
    Box(
        modifier = Modifier
            .size(300.dp)
    ) {
        val onClickButton = viewModel::setDirection
        KeypadButton(SnakeViewModel.Directions.TOP, onClickButton)
        KeypadButton(SnakeViewModel.Directions.LEFT, onClickButton)
        KeypadButton(SnakeViewModel.Directions.RIGHT, onClickButton)
        KeypadButton(SnakeViewModel.Directions.BOTTOM, onClickButton)
    }
}

@Composable
private fun BoxScope.KeypadButton(
    direction: SnakeViewModel.Directions,
    onClickButton: (SnakeViewModel.Directions) -> Unit
) {
    val align = when (direction) {
        SnakeViewModel.Directions.TOP -> Alignment.TopCenter
        SnakeViewModel.Directions.LEFT -> Alignment.CenterStart
        SnakeViewModel.Directions.RIGHT -> Alignment.CenterEnd
        SnakeViewModel.Directions.BOTTOM -> Alignment.BottomCenter
    }

    Box(
        modifier = Modifier
            .size(100.dp)
            .align(align)
            .clickable { onClickButton(direction) }
            .background(Color.LightGray)
    )
}