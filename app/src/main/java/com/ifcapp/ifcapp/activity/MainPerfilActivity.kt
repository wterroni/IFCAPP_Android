package com.ifcapp.ifcapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ifcapp.ifcapp.R
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import android.view.View
import com.ifcapp.ifcapp.adapter.AbasAdapter
import com.ifcapp.ifcapp.fragment.PerfilIgrejaFragment
import com.ifcapp.ifcapp.fragment.PerfilPessoalFragment


class MainPerfilActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_perfil)

        val adapter = AbasAdapter(supportFragmentManager)
        adapter.adicionar(PerfilPessoalFragment(), "Dados Pessoais")
        adapter.adicionar(PerfilIgrejaFragment(), "Dados Membro")

        val viewPager = findViewById<View>(R.id.abas_view_pager) as ViewPager
        viewPager.adapter = adapter

        val tabLayout = findViewById<View>(R.id.abas) as TabLayout
        tabLayout.setupWithViewPager(viewPager)
    }
}
