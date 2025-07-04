package com.haomai.promotor.accessibility.actions

import com.haomai.promotor.accessibility.GestureService

/**
 * A class that encapsulates the logic for performing a long press action.
 */
class LongPressAction {

    companion object {
        // A reasonable default duration for a long press.
        private const val DEFAULT_LONG_PRESS_DURATION = 1000L // 1000ms = 1 second
    }

    /**
     * Requests the GestureService to perform a long press.
     *
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     * @param duration The duration of the press in milliseconds.
     */
    fun perform(x: Float, y: Float, duration: Long = DEFAULT_LONG_PRESS_DURATION) {
        GestureService.instance?.performLongPress(x, y, duration)
    }
}