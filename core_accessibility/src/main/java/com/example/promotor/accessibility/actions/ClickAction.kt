package com.example.promotor.accessibility.actions

import com.example.promotor.accessibility.GestureService

/**
 * A class that encapsulates the logic for performing a click action.
 * It uses the running GestureService instance to execute the actual click.
 */
class ClickAction {

    /**
     * Requests the GestureService to perform a click at the given coordinates.
     *
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     */
    fun perform(x: Float, y: Float) {
        // Call the static instance of our running service.
        // The '?' ensures it won't crash if the service is not running.
        GestureService.instance?.performClick(x, y)
    }

    /**
     * Performs a click at the center of the screen.
     * This requires access to the service instance to get screen dimensions.
     */
    fun performAtCenter() {
        GestureService.instance?.let { service ->
            val displayMetrics = service.resources.displayMetrics
            val middleX = displayMetrics.widthPixels / 2f
            val middleY = displayMetrics.heightPixels / 2f
            service.performClick(middleX, middleY)
        }
    }
}