package com.ifcapp.ifcapp.fragment

import android.app.Activity
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.ifcapp.ifcapp.R

import kotlinx.android.synthetic.main.fragment_perfil_igreja.*
import java.text.SimpleDateFormat
import java.util.*


class PerfilIgrejaFragment : BaseFragment() {

    private var mYear: Int = 0
    var mMonth: Int = 0
    var mDay: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_perfil_igreja, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    fun init() {
        setDataMembro()
        setBatizadoSpinner()
        setEscolaridadeSpinner()
    }

    fun setDataMembro() {
        dataMembroButton.setOnClickListener {
            onClickData()
        }
    }

    fun onClickData() {
        val c = Calendar.getInstance()
        mYear = c.get(Calendar.YEAR)
        mMonth = c.get(Calendar.MONTH)
        mDay = c.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(activity as Activity,
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear,
                                                     dayOfMonth ->

                    c.set(Calendar.YEAR, year)
                    c.set(Calendar.MONTH, monthOfYear)
                    c.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                    val myFormat = "dd/MM/yyyy"
                    val sdf = SimpleDateFormat(myFormat, Locale.US)
                    dataMembroButton.setText(sdf.format(c.time))
                },
                mYear, mMonth, mDay)
        datePickerDialog.show()
    }

    fun setBatizadoSpinner() {
        val adapter = ArrayAdapter.createFromResource(activity as Activity,
                R.array.sim_nao, R.layout.spinner_item)
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        batizadoSpinner.adapter = adapter
    }

    fun setEscolaridadeSpinner() {
        val adapter = ArrayAdapter.createFromResource(activity as Activity,
                R.array.escolaridade_array, R.layout.spinner_item)
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        escolaridadeSpinner.adapter = adapter
    }
}
