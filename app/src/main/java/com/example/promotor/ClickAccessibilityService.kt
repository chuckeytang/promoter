package com.example.promotor

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.graphics.Path
import android.os.Build
import android.util.DisplayMetrics
import android.view.accessibility.AccessibilityEvent

class ClickAccessibilityService : AccessibilityService() {

    companion object {
        // A static reference to the service instance for easy access
        var instance: ClickAccessibilityService? = null
    }

    override fun onServiceConnected() {
        super.onServiceConnected()
        instance = this
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        // We don't need to react to events for this specific task,
        // but this method is required.
    }

    override fun onInterrupt() {
        instance = null
    }

    override fun onDestroy() {
        super.onDestroy()
        instance = null
    }

    /**
     * Performs a click at the center of the screen.
     */
    fun performCenterClick() {
        val displayMetrics: DisplayMetrics = resources.displayMetrics
        val middleX = displayMetrics.widthPixels / 5f * 4f
        val middleY = displayMetrics.heightPixels / 20f * 19f

        // Create a path for the click
        val path = Path().apply {
            moveTo(middleX, middleY)
        }

        // Create a gesture description
        val gestureBuilder = GestureDescription.Builder()
        // A short duration makes it a tap
        gestureBuilder.addStroke(GestureDescription.StrokeDescription(path, 0, 10))

        // Dispatch the gesture
        dispatchGesture(gestureBuilder.build(), null, null)
    }
}