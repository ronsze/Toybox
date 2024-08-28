package com.example.snake

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
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
    board: List<List<Int>>
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
    value: Int
) {
    val color = when (value) {
        SnakeViewModel.BLANK -> Color.White
        SnakeViewModel.BLOCK -> Color.LightGray
        SnakeViewModel.HEAD -> Color.Red
        SnakeViewModel.BODY -> Color.Yellow
        SnakeViewModel.BONUS -> Color.Blue
        else -> Color.DarkGray
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
        Box(
            modifier = Modifier
                .size(100.dp)
                .align(Alignment.TopCenter)
                .clickable { viewModel.currentDirection = SnakeViewModel.TOP }
                .background(Color.LightGray)
        )

        Box(
            modifier = Modifier
                .size(100.dp)
                .align(Alignment.CenterStart)
                .clickable { viewModel.currentDirection = SnakeViewModel.LEFT }
                .background(Color.LightGray)
        )

        Box(
            modifier = Modifier
                .size(100.dp)
                .align(Alignment.CenterEnd)
                .clickable { viewModel.currentDirection = SnakeViewModel.RIGHT }
                .background(Color.LightGray)
        )

        Box(
            modifier = Modifier
                .size(100.dp)
                .align(Alignment.BottomCenter)
                .clickable { viewModel.currentDirection = SnakeViewModel.BOTTOM }
                .background(Color.LightGray)
        )
    }
}