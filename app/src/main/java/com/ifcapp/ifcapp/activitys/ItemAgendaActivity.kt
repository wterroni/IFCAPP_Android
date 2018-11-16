package com.ifcapp.ifcapp.activitys

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Switch
import com.ifcapp.ifcapp.R
import com.ifcapp.ifcapp.models.Agenda
import kotlinx.android.synthetic.main.activity_agenda.*
import kotlinx.android.synthetic.main.activity_item_agenda.*

class ItemAgendaActivity() : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_agenda)
        init()
    }

    fun init () {

        val selectedAgenda = intent.extras.get("selectedAgenda") as Agenda

        TituloTextView.text = selectedAgenda.title
        DescricaoTextView.text = selectedAgenda.description
        setBanner(selectedAgenda)
    }
    fun setBanner(selectedAgenda : Agenda) {
        when (selectedAgenda.banner) {
            "BBF" -> imgBanner.setImageResource(R.drawable.culto_bbf)
            "KIDS" -> imgBanner.setImageResource(R.drawable.culto_criancas)
            "EMAG" -> imgBanner.setImageResource(R.drawable.culto_emagrecimento)
            "ESP" -> imgBanner.setImageResource(R.drawable.culto_imersao)
            "JOV" -> imgBanner.setImageResource(R.drawable.culto_jovens)
            "KEM" -> imgBanner.setImageResource(R.drawable.culto_niver)
            "WORK" -> imgBanner.setImageResource(R.drawable.culto_workshop_vocal)
        }
    }
}
