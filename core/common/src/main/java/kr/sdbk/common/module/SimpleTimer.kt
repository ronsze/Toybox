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

    private var isStarted: Boolean = false

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
        }
    }

    fun pause() {
        isStarted = false
        timerJob?.cancel()
    }

    fun resume() {
        timerJob?.start()
    }

    fun stop() {
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