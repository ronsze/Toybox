package kr.sdbk.common.functions

import kotlin.math.abs

object Math {
    fun maxAbs(a: Float, b: Float): Float {
        return if (abs(a) > abs(b)) a else b
    }

    fun minAbs(a: Float, b: Float): Float {
        return if (abs(a) < abs(b)) a else b
    }
}