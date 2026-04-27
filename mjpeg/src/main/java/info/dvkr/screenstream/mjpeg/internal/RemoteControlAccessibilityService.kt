package info.dvkr.screenstream.mjpeg.internal

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.graphics.Path
import android.os.Build
import android.view.accessibility.AccessibilityEvent
import com.elvishew.xlog.XLog

public class RemoteControlAccessibilityService : AccessibilityService() {

    public companion object {
        private const val TAP_DURATION_MS: Long = 80L
        private const val SWIPE_DURATION_MS: Long = 300L

        @Volatile
        private var instance: RemoteControlAccessibilityService? = null

        internal fun tap(x: Int, y: Int): Boolean {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                XLog.w("Remote control tap requires Android 7.0 or newer")
                return false
            }

            return instance?.dispatchTap(x, y) ?: false
        }

        internal fun swipe(fromX: Int, fromY: Int, toX: Int, toY: Int): Boolean {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                XLog.w("Remote control swipe requires Android 7.0 or newer")
                return false
            }

            return instance?.dispatchSwipe(fromX, fromY, toX, toY) ?: false
        }

        internal fun isEnabled(): Boolean = instance != null
    }

    override fun onServiceConnected() {
        super.onServiceConnected()
        instance = this
        XLog.i("Remote control accessibility service connected")
    }

    override fun onDestroy() {
        if (instance === this) instance = null
        XLog.i("Remote control accessibility service destroyed")
        super.onDestroy()
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?): Unit = Unit

    override fun onInterrupt() {
        XLog.i("Remote control accessibility service interrupted")
    }

    private fun dispatchTap(x: Int, y: Int): Boolean {
        val path = Path().apply { moveTo(x.toFloat(), y.toFloat()) }
        val gesture = GestureDescription.Builder()
            .addStroke(GestureDescription.StrokeDescription(path, 0L, TAP_DURATION_MS))
            .build()

        return dispatchGesture(gesture, null, null)
    }

    private fun dispatchSwipe(fromX: Int, fromY: Int, toX: Int, toY: Int): Boolean {
        val path = Path().apply {
            moveTo(fromX.toFloat(), fromY.toFloat())
            lineTo(toX.toFloat(), toY.toFloat())
        }
        val gesture = GestureDescription.Builder()
            .addStroke(GestureDescription.StrokeDescription(path, 0L, SWIPE_DURATION_MS))
            .build()

        return dispatchGesture(gesture, null, null)
    }
}
