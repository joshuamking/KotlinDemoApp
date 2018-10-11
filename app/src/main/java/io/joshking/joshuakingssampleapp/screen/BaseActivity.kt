package io.joshking.joshuakingssampleapp.screen

import android.app.ActivityOptions
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.TypedValue
import android.view.View


abstract class BaseActivity : AppCompatActivity() {
    abstract fun getViewForSnackbars(): View?

    fun TODO(msg: String): Boolean {
        val viewOpt = getViewForSnackbars()
        viewOpt?.let { view ->
            Snackbar.make(view, "TODO: $msg", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Close") { }
                    .show()
        }
        return true
    }


    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        val uiModes = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        if (uiModes == Configuration.UI_MODE_NIGHT_UNDEFINED || uiModes == Configuration.UI_MODE_NIGHT_NO) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                var flags = window.decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) flags = flags or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
                window.decorView.systemUiVisibility = flags
            }
        }
    }

    override fun onBackPressed() {
        supportFinishAfterTransition()
    }

    protected fun dpToPx(dp: Int): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), resources.displayMetrics).toInt()
    }

    val newActivityDefaultOptions: Bundle
        get() = ActivityOptions.makeSceneTransitionAnimation(this).toBundle()
}
