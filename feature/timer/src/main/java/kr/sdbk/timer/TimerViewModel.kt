package kr.sdbk.timer

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kr.sdbk.common.base.BaseViewModel
import kr.sdbk.common.module.SimpleTimer

class TimerViewModel: BaseViewModel() {
    private val _progressedTime: MutableStateFlow<Long> = MutableStateFlow(0)
    val progressedTime get() = _progressedTime

    private val _isPlaying: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isPlaying get() = _isPlaying

    private val timerListener = object: SimpleTimer.SimpleTimerListener {
        override fun onStart() { _isPlaying.set(true) }
        override fun onDone() { _isPlaying.set(false) }
        override fun onTimeProgressed(progressedTime: Long) {
            _progressedTime.set(progressedTime)
        }
    }

    val timer = SimpleTimer(viewModelScope).apply {
        setTimerListener(timerListener)
    }
}