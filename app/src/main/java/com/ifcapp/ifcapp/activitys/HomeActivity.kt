package com.ifcapp.ifcapp.activitys

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.StaggeredGridLayoutManager
import com.ifcapp.ifcapp.R
import com.ifcapp.ifcapp.adapters.AgendaListAdapter
import com.ifcapp.ifcapp.models.Agenda
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val recyclerView = recyclerViewAgenda
        recyclerView.adapter = AgendaListAdapter(agenda(), this)

        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.layoutManager = layoutManager

        // Set toolbar title/app title
        my_toolbar!!.title = "Formosa de Cristo APP"
    }

    private fun agenda(): List<Agenda> {
        return listOf(
                Agenda("18/08/2018",
                        "Acústico BBF"),
                Agenda("19/09/2018",
                        "Culto da família"),
                Agenda("22/08/2018",
                        "Campanha Respostas!"),
                Agenda("24/08/2018",
                        "Culto de Sexta, aquele que ninguém vai."))
    }
}