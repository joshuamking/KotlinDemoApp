package io.joshking.joshuakingssampleapp.screen

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.v7.widget.CardView
import android.util.Pair
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import io.joshking.joshuakingssampleapp.R
import kotlinx.android.synthetic.main.activity_android_first_app.*
import kotlinx.android.synthetic.main.content_android_first_app.*
import java.text.DateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*


class AndroidFirstAppActivity : BaseActivity() {
    private val sixteenDp: Int by lazy { dpToPx(16) }

    override fun getViewForSnackbars(): View? {
        return androidFirstAppRootView
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.android_first_app, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.openWebLink -> {
                val uri = Uri.parse("https://developer.android.com/training/basics/firstapp/")
                startActivity(Intent(Intent.ACTION_VIEW, uri))
                true
            }
            android.R.id.home -> {
                supportFinishAfterTransition()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_android_first_app)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        sendBtn.setOnClickListener {
            sendMessage(messageText.text.toString())
            messageText.text = null
        }
    }

    @SuppressLint("SetTextI18n")
    private fun sendMessage(message: String) {
        message.trim().takeIf { it.isNotEmpty() }?.also { trimmedMessage ->
            val timestamp = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT))
            } else {
                DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, Locale.getDefault()).format(Date())
            }

            val timestampTextView = TextView(this).apply {
                this.text = timestamp
            }

            val messageTextView = TextView(this).apply {
                this.text = trimmedMessage
                textSize = 18F
                setPadding(0, 0, 0, sixteenDp)
            }

            fun openDisplayActivity(vararg options: Bundle) {
                startActivity(Intent(this, AndroidFirstAppMessageDisplayActivity::class.java).apply {
                    putExtra(AndroidFirstAppMessageDisplayActivity.ARGS.MESSAGE, trimmedMessage)
                    putExtra(AndroidFirstAppMessageDisplayActivity.ARGS.TIMESTAMP, timestamp)
                }, options.firstOrNull() ?: newActivityDefaultOptions)
            }

            openDisplayActivity()

            messageHistory.addView(
                    CardView(this).apply {
                        clipToPadding = false
                        clipToOutline = false
                        useCompatPadding = true
                        radius = resources.getDimension(R.dimen.card_corner_radius)
                        setContentPadding(sixteenDp, sixteenDp, sixteenDp, sixteenDp)
                        addView(LinearLayout(context).apply {
                            clipToPadding = false
                            clipToOutline = false
                            orientation = LinearLayout.VERTICAL
                            addView(messageTextView)
                            addView(timestampTextView)
                        })
                        setOnClickListener {
                            openDisplayActivity(ActivityOptions.makeSceneTransitionAnimation(this@AndroidFirstAppActivity,
                                    Pair<View, String>(timestampTextView, AndroidFirstAppMessageDisplayActivity.ARGS.TIMESTAMP),
                                    Pair<View, String>(messageTextView, AndroidFirstAppMessageDisplayActivity.ARGS.MESSAGE)
                            ).toBundle())
                        }
                    }
            )
            // 5000 to account for new CardView height. TODO: dynamically calculate CardView height.
            messageHistoryScrollView.postDelayed({ messageHistoryScrollView.smoothScrollTo(0, messageHistory.measuredHeight + 5000) }, 500)
        }
    }
}
