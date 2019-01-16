package com.ifcapp.ifcapp.adapter

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.view.View
import android.view.ViewGroup
import com.ifcapp.ifcapp.models.Agenda
import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.widget.TextView
import com.ifcapp.ifcapp.R
import kotlinx.android.synthetic.main.agenda_item.view.*

class AgendaListAdapter(private val listener: OnAgendaClick,
                        private val agenda: List<Agenda>,
                        private val context: Context) : Adapter<AgendaListAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return agenda.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = agenda[position]
        holder.bindView(note)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.agenda_item, parent, false)
        return ViewHolder(view, listener)
    }

    inner class ViewHolder(itemView: View, listener: OnAgendaClick) : RecyclerView.ViewHolder(itemView) {

        val dataCulto: TextView
        val nomeCulto: TextView
        var item: Agenda? = null

        init {
            dataCulto = itemView.agenda_item_title
            nomeCulto = itemView.agenda_item_title

            itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary))

            itemView.setOnClickListener{
                listener.onAgendaClick(item)
            }
        }

        fun bindView(item: Agenda) {
            this.item = item
            val title = itemView.agenda_item_title
            val description = itemView.agenda_item_description

            title.text = item.title
            description.text = item.description
        }
    }

    interface OnAgendaClick {
        fun onAgendaClick(item: Agenda?)
    }



}


