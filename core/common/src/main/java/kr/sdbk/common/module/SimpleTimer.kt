package kr.sdbk.common.module

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Timer

class SimpleTimer(
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Default)
) {
    private var listener: SimpleTimerListener? = null

    var totalTime: Long = 0
    private set

    private var progressedTime: Long = 0

    private var timerJob: Job? = null

    var isStarted: Boolean = false
        private set
    var isPaused: Boolean = false
        private set

    fun startTimer(time: Long) {
        totalTime = time
        listener?.onStart()
        Timer()
        timerJob = scope.launch {
            isStarted = true
            while (totalTime >= progressedTime) {
                listener?.onTimeProgressed(progressedTime)
                progressedTime += 10
                delay(10)
            }
            listener?.onDone()
            timerJob = null
            stop()
        }
    }

    fun pause() {
        isPaused = true
        timerJob?.cancel()
    }

    fun resume() {
        isPaused = false
        startTimer(totalTime)
    }

    fun stop() {
        isPaused = false
        isStarted = false
        totalTime = 0
        progressedTime = 0
        timerJob?.cancel()
        timerJob = null
    }

    fun setTimerListener(listener: SimpleTimerListener) {
        this.listener = listener
    }

    fun clearTimerListener() {
        listener = null
    }

    interface SimpleTimerListener {
        fun onStart()
        fun onDone()
        fun onTimeProgressed(progressedTime: Long)
    }
}