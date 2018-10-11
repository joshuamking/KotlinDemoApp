package io.joshking.joshuakingssampleapp.screen

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import io.joshking.joshuakingssampleapp.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.nav_header_main.*


class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun getViewForSnackbars(): View? {
        return mainFab
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        mainFab.setOnClickListener { TODO("Show 'contact me' page.") }

        val actionBarDrawerToggle = ActionBarDrawerToggle(this, navDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        navDrawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        navDrawerView.setNavigationItemSelectedListener(this)
        navDrawerView.post {
            val navImageBitmap = BitmapFactory.decodeResource(resources, R.drawable.selfie)
            val navImageDrawable = RoundedBitmapDrawableFactory.create(resources, navImageBitmap)
            navImageDrawable.cornerRadius = 1000F
            navImageView.setImageDrawable(navImageDrawable)
        }

        openDemoBtn.setOnClickListener {
            startActivity(Intent(this, AndroidFirstAppActivity::class.java))
        }
    }

    override fun onBackPressed() {
        if (navDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            navDrawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> {

            }
            else -> TODO("Handle action: ${item.title}")
        }

        navDrawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}
