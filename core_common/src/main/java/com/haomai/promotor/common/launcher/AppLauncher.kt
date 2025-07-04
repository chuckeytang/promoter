package com.haomai.promotor.common.launcher

import android.content.Context
import android.content.Intent
import android.widget.Toast

/**
 * A singleton utility object for launching other applications.
 */
object AppLauncher {

    /**
     * Launches an application using its package name.
     *
     * @param context The context to use for launching the intent.
     * @param packageName The package name of the app to launch.
     * @return `true` if the app was launched successfully, `false` otherwise.
     */
    fun launchByPackageName(context: Context, packageName: String): Boolean {
        val launchIntent = context.packageManager.getLaunchIntentForPackage(packageName)
        if (launchIntent == null) {
            // App not found, show a toast or log an error
            Toast.makeText(context, "应用未安装: $packageName", Toast.LENGTH_SHORT).show()
            return false
        }

        // Add this flag if you are calling from a non-activity context (like a service)
        launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        context.startActivity(launchIntent)
        return true
    }

    /**
     * Checks if an application is installed.
     *
     * @param context The context to use.
     * @param packageName The package name to check.
     * @return `true` if the app is installed, `false` otherwise.
     */
    fun isAppInstalled(context: Context, packageName: String): Boolean {
        return try {
            context.packageManager.getPackageInfo(packageName, 0)
            true
        } catch (e: Exception) {
            false
        }
    }
}