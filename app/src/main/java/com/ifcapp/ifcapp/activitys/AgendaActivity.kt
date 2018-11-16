package com.ifcapp.ifcapp.activitys

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.StaggeredGridLayoutManager
import com.ifcapp.ifcapp.R
import com.ifcapp.ifcapp.adapters.AgendaListAdapter
import com.ifcapp.ifcapp.models.Agenda
import kotlinx.android.synthetic.main.activity_agenda.*

class AgendaActivity : BaseActivity(), AgendaListAdapter.OnAgendaClick {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agenda)

        val recyclerView = recyclerViewAgenda
        recyclerView.adapter = AgendaListAdapter(this, agenda(), this)

        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.layoutManager = layoutManager
    }

    private fun agenda(): List<Agenda> {
        return listOf(
                Agenda("18/08/2018",
                        "Acústico BBF", "BBF"),
                Agenda("20/10/2018",
                        "Culto das crianças", "KIDS"),
                Agenda("05/10/2018",
                        "Palestra Emagrecimento e Alimentação", "EMAG"),
                Agenda("20, 21 e 22 de setembro de 2018",
                        "Seminario Imersão", "ESP"),
                Agenda("29/09/2018",
                        "Culto dos jovens", "JOV"),
                Agenda("07/04/2018",
                        "Aniversário de 15 anos - Coral Kemuel", "KEM"),
                Agenda("15/09/2018",
                        "Workshop Vocal", "WORK"))
    }

    override fun onAgendaClick(item: Agenda?) {
        var selectedAgenda: Agenda
        selectedAgenda = item!!

        val intent = Intent(this, ItemAgendaActivity::class.java)
        intent.putExtra("selectedAgenda", selectedAgenda)
        startActivity(intent);
    }
}