package com.example.snake

import android.graphics.Point
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kr.sdbk.common.base.BaseViewModel

class SnakeViewModel: BaseViewModel() {
    companion object {
        const val ARRAY_SIZE = 100
    }

    private val _currentScore: MutableStateFlow<Int> = MutableStateFlow(0)
    val currentScore get() = _currentScore

    private var _boardArray: SnapshotStateList<SnapshotStateList<CellType>> = mutableStateListOf()
    val boardArray get() = _boardArray

    private val snakeArray: MutableList<Point> = mutableListOf()
    private var currentDirection: Directions = Directions.TOP

    private var isPlaying = true
    private var moveTimeMs = 100L

    fun start() {
        val initList = List(ARRAY_SIZE) { i ->
            List(ARRAY_SIZE) { j ->
                if (i == 0 || j == 0 || i == ARRAY_SIZE - 1 || j == ARRAY_SIZE - 1) {
                    CellType.BLOCK
                } else CellType.BLANK
            }.toMutableStateList()
        }
        _boardArray.addAll(initList)

        snakeArray.add(Point(50, 50)) //head
        snakeArray.add(Point(50, 51)) //tail
        updateSnake()

        viewModelScope.launch {
            launch(Dispatchers.Default) {
                while (isPlaying) {
                    var newItemPoint = Point(0, 0)
                    while (boardArray[newItemPoint.y][newItemPoint.x] != CellType.BLANK) {
                        newItemPoint = getNewItemPoint()
                    }
                    boardArray[newItemPoint.y][newItemPoint.x] = CellType.BONUS
                    delay(3000)
                }
            }

            launch {
                while (isPlaying) {
                    withContext(Dispatchers.Default) {
                        delay(moveTimeMs)

                        moveNext()
                    }
                }
            }
        }
    }

    private fun moveNext() {
        val start = System.currentTimeMillis()
        val currentHead = snakeArray.first()
        val nextHead: Point = when (currentDirection) {
            Directions.TOP -> currentHead.copy().apply { y-- }
            Directions.LEFT -> currentHead.copy().apply { x-- }
            Directions.RIGHT -> currentHead.copy().apply { x++ }
            Directions.BOTTOM -> currentHead.copy().apply { y++ }
        }
        val nextValue = boardArray[nextHead.y][nextHead.x]
        if (nextValue == CellType.BLOCK || nextValue == CellType.BODY) { //game over
            isPlaying = false
        } else {
            snakeArray.add(0, nextHead)
            val tail = snakeArray.last()
            if (nextValue == CellType.BLANK) snakeArray.remove(tail)
            updateSnake()

            if (nextValue == CellType.BLANK) boardArray[tail.y][tail.x] = CellType.BLANK
        }
    }

    private fun updateSnake() {
        snakeArray.forEachIndexed { i, v ->
            boardArray[v.y][v.x] = if (i == 0) CellType.HEAD else CellType.BODY
        }
    }

    private fun getNewItemPoint() = Point(
        (1 until ARRAY_SIZE).random(),
        (1 until ARRAY_SIZE).random()
    )

    fun setDirection(direction: Directions) {
        if (currentDirection.ordinal % 2 != direction.ordinal % 2) {
            currentDirection = direction
        }
    }

    private fun Point.copy() = Point(x, y)

    enum class CellType {
        BLANK, HEAD, BODY, BLOCK, BONUS
    }

    enum class Directions {
        TOP, LEFT, BOTTOM, RIGHT
    }
}