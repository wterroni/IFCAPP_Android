package com.ifcapp.ifcapp.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class AbasAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private val fragments: MutableList<Fragment> = ArrayList()
    private val titulos: MutableList<String> = ArrayList()

    fun adicionar(fragment: Fragment, tituloAba: String) {
        this.fragments.add(fragment)
        this.titulos.add(tituloAba);
    }

    override fun getItem(position: Int): Fragment {
        return this.fragments.get(position)
    }

    override fun getCount(): Int {
        return this.fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return this.titulos.get(position)
    }
}