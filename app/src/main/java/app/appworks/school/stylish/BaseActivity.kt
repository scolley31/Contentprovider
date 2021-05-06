package app.appworks.school.stylish

import android.graphics.Color
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.view.DisplayCutout
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import app.appworks.school.stylish.util.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

/**
 * Created by Wayne Chen in Jul. 2019.
 */
open class BaseActivity : AppCompatActivity(), CoroutineScope {

    val job = Job()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupStatusBar()
    }

    /**
     * Get cutout height from [DisplayCutout]
     * @notice if device has cutout, the status bar height will be same as cutout height.
     */
    suspend fun getCutoutHeight(): Int {
         return withContext(Dispatchers.IO) {
             when {
                 Build.VERSION.SDK_INT >= Build.VERSION_CODES.P -> {

                     window?.let {
                         val displayCutout: DisplayCutout? = it.decorView.rootWindowInsets.displayCutout
                         Logger.d("displayCutout?.safeInsetTop=${displayCutout?.safeInsetTop}")
                         Logger.d("displayCutout?.safeInsetBottom=${displayCutout?.safeInsetBottom}")
                         Logger.d("displayCutout?.safeInsetLeft=${displayCutout?.safeInsetLeft}")
                         Logger.d("displayCutout?.safeInsetRight=${displayCutout?.safeInsetRight}")

                         val rects: List<Rect>? = displayCutout?.boundingRects
                         Logger.d("rects?.size=${rects?.size}")
                         Logger.d("rects=$rects")

                         displayCutout?.safeInsetTop ?: 0
                     } ?: 0
                 }
                 else -> 0
             }
         }
    }

    /**
     * Set up status bar to transparent
     * @notice this method have to be used before setContentView.
     */
    private fun setupStatusBar() {
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

        val window = window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = Color.TRANSPARENT // calculateStatusColor(Color.WHITE, (int) alphaValue)
    }
}
