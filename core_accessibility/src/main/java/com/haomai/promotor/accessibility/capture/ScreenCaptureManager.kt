package com.haomai.promotor.accessibility.capture

import android.graphics.Bitmap
import android.os.Build
import com.haomai.promotor.accessibility.GestureService

/**
 * A manager class for handling screen capture operations via the Accessibility Service.
 */
class ScreenCaptureManager {

    /**
     * Captures the current screen.
     * This is a suspend function and must be called from a coroutine scope.
     *
     * Note: This functionality requires Android 11 (API 30) or higher.
     *
     * @return A Bitmap of the screen capture, or null if the capture fails,
     * the service is not running, or the Android version is too low.
     */
    suspend fun capture(): Bitmap? {
        // Check for the required Android version.
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            System.err.println("Screen capture requires Android 11 (API 30) or higher.")
            return null
        }

        // Call the capture method on the running GestureService instance.
        return GestureService.instance?.captureScreen()
    }
}