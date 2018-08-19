package com.ifcapp.ifcapp.adapters

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.view.View
import android.view.ViewGroup
import com.ifcapp.ifcapp.models.Agenda
import android.content.Context
import android.view.LayoutInflater
import com.ifcapp.ifcapp.R
import kotlinx.android.synthetic.main.agenda_item.view.*

class AgendaListAdapter(private val agenda: List<Agenda>,
                        private val context: Context) : Adapter<AgendaListAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = agenda[position]
        holder?.let {
            it.bindView(note)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.agenda_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return agenda.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindView(note: Agenda) {
            val title = itemView.agenda_item_title
            val description = itemView.agenda_item_description

            title.text = note.title
            description.text = note.description
        }


    }



}


