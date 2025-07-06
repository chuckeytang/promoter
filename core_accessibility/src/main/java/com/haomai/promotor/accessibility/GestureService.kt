package com.haomai.promotor.accessibility

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.graphics.Bitmap
import android.graphics.Path
import android.os.Build
import android.view.Display
import android.view.accessibility.AccessibilityEvent
import androidx.annotation.RequiresApi
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class GestureService : AccessibilityService() {

    companion object {
        // A static reference to the service instance for easy access
        var instance: GestureService? = null
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
     * Performs a click gesture at the specified coordinates.
     * This is the core function that executes the gesture.
     *
     * @param x The x-coordinate for the click.
     * @param y The y-coordinate for the click.
     * @param duration The duration of the click in milliseconds.
     */
    fun performClick(x: Float, y: Float, duration: Long = 10L) {
        val path = Path().apply {
            moveTo(x, y)
        }
        val gestureBuilder = GestureDescription.Builder()
        gestureBuilder.addStroke(GestureDescription.StrokeDescription(path, 0, duration))

        dispatchGesture(gestureBuilder.build(), null, null)
    }

    /**
     * Performs a long press gesture at the specified coordinates.
     *
     * @param x The x-coordinate for the long press.
     * @param y The y-coordinate for the long press.
     * @param duration The duration of the press in milliseconds.
     */
    fun performLongPress(x: Float, y: Float, duration: Long) {
        val path = Path().apply {
            moveTo(x, y)
        }
        val gestureBuilder = GestureDescription.Builder()
        // The only difference from a click is the longer duration.
        gestureBuilder.addStroke(GestureDescription.StrokeDescription(path, 0, duration))

        dispatchGesture(gestureBuilder.build(), null, null)
    }

    /**
     * Performs a swipe gesture from a start point to an end point.
     *
     * @param startX The starting x-coordinate.
     * @param startY The starting y-coordinate.
     * @param endX The ending x-coordinate.
     * @param endY The ending y-coordinate.
     * @param duration The time the swipe will take in milliseconds.
     */
    fun performSwipe(startX: Float, startY: Float, endX: Float, endY: Float, duration: Long) {
        val path = Path().apply {
            moveTo(startX, startY) // Move to the start point
            lineTo(endX, endY)   // Draw a line to the end point
        }
        val gestureBuilder = GestureDescription.Builder()
        gestureBuilder.addStroke(GestureDescription.StrokeDescription(path, 0, duration))

        dispatchGesture(gestureBuilder.build(), null, null)
    }

    /**
     * Captures the current screen content.
     * This method is asynchronous and should be called from a coroutine.
     * It requires Android R (API 30) or higher.
     *
     * @return A Bitmap of the screen capture, or null if it fails or is unsupported.
     */
    @RequiresApi(Build.VERSION_CODES.R)
    suspend fun captureScreen(): Bitmap? {
        // suspendCancellableCoroutine is a modern way to wrap callback-based APIs into suspend functions.
        return suspendCancellableCoroutine { continuation ->
            takeScreenshot(
                Display.DEFAULT_DISPLAY,
                mainExecutor, // An executor to run the callback on the main thread
                object : TakeScreenshotCallback {
                    override fun onSuccess(screenshot: ScreenshotResult) {
                        // On success, resume the coroutine with the bitmap.
                        // We need to copy to a software bitmap to make it usable/mutable.
                        val bitmap = Bitmap.wrapHardwareBuffer(screenshot.hardwareBuffer, screenshot.colorSpace)
                            ?.copy(Bitmap.Config.ARGB_8888, false)
                        continuation.resume(bitmap)
                    }

                    override fun onFailure(errorCode: Int) {
                        // On failure, print an error and resume with null.
                        System.err.println("Screen capture failed with error code: $errorCode")
                        continuation.resume(null)
                    }
                }
            )

            // If the coroutine is cancelled, there's nothing extra to do.
            continuation.invokeOnCancellation { throwable ->
                System.err.println("Screen capture was cancelled: ${throwable?.message}")
            }
        }
    }
}