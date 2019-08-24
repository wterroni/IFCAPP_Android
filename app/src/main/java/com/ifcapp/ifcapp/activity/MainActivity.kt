package com.ifcapp.ifcapp.activity

import android.content.Intent
import android.os.Bundle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import android.view.MenuItem
import com.ifcapp.ifcapp.R
import kotlinx.android.synthetic.main.activity_main.*
import com.google.android.material.navigation.NavigationView
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.ifcapp.ifcapp.Util.MenuConstantes
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog


class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        callActivity(item)
        return false;
    }

    private fun callActivity (item: MenuItem): Boolean {
        when (item.toString()) {
            MenuConstantes.AGENDA -> startActivity(Intent(this, AgendaActivity::class.java))
            MenuConstantes.PROFILE -> startActivity(Intent(this, MainPerfilActivity::class.java))
            MenuConstantes.SAIR -> logout()
        }
        return true;
    }

    private fun logout() {
        if (logoutMessage()) {
            signOut()
        }
    }

    private fun signOut() {
        FirebaseAuth.getInstance().signOut();
        finish()
        startActivity(Intent(this, LoginActivity::class.java))
    }

    private fun logoutMessage() : Boolean {
        var logout = false
        AlertDialog.Builder(this)
                .setTitle(getString(R.string.msg_logout_sair_titulo))
                .setMessage(getString(R.string.msg_logout_sair_msg))
                .setPositiveButton("Sim") { _, _ ->
                    signOut()
                }
                .setNegativeButton("não", null)
                .show()

        return logout
    }

    override fun onBackPressed() {
        logout()
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
