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
import kotlin.random.Random

class SnakeViewModel: BaseViewModel() {
    companion object {
        const val BLANK = 0
        const val HEAD = 1
        const val BODY = 2
        const val BLOCK = 3
        const val BONUS = 4
        const val ARRAY_SIZE = 15

        const val TOP = 10
        const val LEFT = 11
        const val RIGHT = 12
        const val BOTTOM = 13

        const val MOVE_TIME_MS = 1000L
    }

    private val _currentScore: MutableStateFlow<Int> = MutableStateFlow(0)
    val currentScore get() = _currentScore

    private var _boardArray: SnapshotStateList<SnapshotStateList<Int>> = Array(ARRAY_SIZE) {
        Array(ARRAY_SIZE) { BLANK }.toList().toMutableStateList()
    }.apply {
        forEachIndexed { i, row ->
            row.forEachIndexed { j, _ ->
                if (i == 0 || j == 0 || i == ARRAY_SIZE - 1 || j == ARRAY_SIZE - 1) this[i][j] = BLOCK
            }
        }
    }.toList().toMutableStateList()
    val boardArray get() = _boardArray

    private val snakeArray: MutableList<Point> = mutableListOf()
    var currentDirection: Int = TOP

    private var isPlaying = true

    fun start() {
        snakeArray.add(Point(7, 7)) //head
        snakeArray.add(Point(7, 8)) //tail
        updateSnake()

        viewModelScope.launch {
            launch(Dispatchers.Default) {
                while (isPlaying) {
                    var newItemPoint = Point(0, 0)
                    while (boardArray[newItemPoint.y][newItemPoint.x] != BLANK) {
                        newItemPoint = getNewItemPoint()
                    }
                    boardArray[newItemPoint.y][newItemPoint.x] = BONUS
                    delay(3000)
                }
            }

            launch {
                while (isPlaying) {
                    withContext(Dispatchers.Default) {
                        delay(MOVE_TIME_MS)

                        val currentHead = snakeArray.first()
                        val nextHead: Point = when (currentDirection) {
                            TOP -> currentHead.copy().apply { y-- }
                            LEFT -> currentHead.copy().apply { x-- }
                            RIGHT -> currentHead.copy().apply { x++ }
                            BOTTOM -> currentHead.copy().apply { y++ }
                            else -> throw Exception()
                        }
                        val nextValue = boardArray[nextHead.y][nextHead.x]
                        if (nextValue == BLOCK || nextValue == BODY) { //game over
                            isPlaying = false
                        } else {
                            snakeArray.add(0, nextHead)
                            val tail = snakeArray.last()
                            if (nextValue == BLANK) snakeArray.remove(tail)
                            updateSnake()

                            if (nextValue == BLANK) boardArray[tail.y][tail.x] = BLANK
                        }
                    }
                }
            }
        }
    }

    private fun updateSnake() {
        snakeArray.forEachIndexed { i, v ->
            boardArray[v.y][v.x] = if (i == 0) HEAD else BODY
        }
    }

    private fun getNewItemPoint() = Point(
        (1..14).random(),
        (1..14).random()
    )

    private fun Point.copy() = Point(x, y)
}