package com.example.dflens

import android.graphics.Point
import android.util.Log
import androidx.compose.ui.unit.IntSize
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kr.sdbk.common.base.BaseViewModel
import kotlin.random.Random

class DfLensViewModel: BaseViewModel() {
    private val _currentPosition: MutableStateFlow<Point?> = MutableStateFlow(null)
    val currentPosition get() = _currentPosition.asStateFlow()

    private var boardSize: IntSize = IntSize(0, 0)
    private var speed: Int = 2

    fun start(boardSize: IntSize) {
        if (currentPosition.value != null) return
        this.boardSize = boardSize
        _currentPosition.set(Point(boardSize.width / 2, boardSize.height / 2))

        viewModelScope.launch {
            while (true) {
                move()
                delay(5)
                if (Random.nextInt(1000) % 500 == 0) changeDirection()
            }
        }
    }

    fun move() {
        val newPosition = currentPosition.value?.run {
            Point(x + speed, y + speed)
        }
        _currentPosition.set(newPosition)
    }

    fun changeDirection() {
        speed = -speed
    }
}