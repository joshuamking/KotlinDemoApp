package io.joshking.joshuakingssampleapp.screen

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import io.joshking.joshuakingssampleapp.R
import kotlinx.android.synthetic.main.activity_android_first_app_message_display.*
import kotlinx.android.synthetic.main.content_android_first_app_message_display.*

class AndroidFirstAppMessageDisplayActivity : BaseActivity() {
    override fun getViewForSnackbars(): View? {
        return mainView
    }

    object ARGS {
        val MESSAGE = "message"
        val TIMESTAMP = "timestamp"
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                supportFinishAfterTransition()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_android_first_app_message_display)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        intent.extras?.apply {
            message.text = getString(ARGS.MESSAGE)
            getString(ARGS.TIMESTAMP).also {
                timestamp.text = it
                toolbar.subtitle = it
            }
        }

        message.transitionName = ARGS.MESSAGE
        timestamp.transitionName = ARGS.TIMESTAMP
    }

}
