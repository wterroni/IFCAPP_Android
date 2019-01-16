package com.ifcapp.ifcapp.activity

import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import com.ifcapp.ifcapp.R
import kotlinx.android.synthetic.main.activity_main.*
import android.support.design.widget.NavigationView
import android.view.View
import com.ifcapp.ifcapp.Util.MenuConstantes


class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        callActivity(item)
        return false;
    }

    private fun callActivity (item: MenuItem): Boolean {
        when (item.toString()) {
            MenuConstantes.AGENDA -> startActivity(Intent(this, AgendaActivity::class.java))
            MenuConstantes.PROFILE -> startActivity(Intent(this, MainPerfilActivity::class.java))
        }
        return true;
    }

    private lateinit var mDrawerLayout: DrawerLayout
    private lateinit var mToggle : ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mDrawerLayout = drawer
        mToggle = ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close)
        mDrawerLayout.addDrawerListener(mToggle)
        mToggle.syncState()
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val navigationView = findViewById<View>(R.id.nav_view) as NavigationView
        navigationView.setNavigationItemSelectedListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (mToggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
