package com.ifcapp.ifcapp.activity

import android.os.Bundle
import com.ifcapp.ifcapp.R
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_perfil.*
import android.app.DatePickerDialog
import com.ifcapp.ifcapp.controller.PerfilController
import java.util.*


class PerfilActivity : BaseActivity() {

    private var mYear: Int = 0
    var mMonth: Int = 0
    var mDay: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)
        init()
    }

    fun init() {
        setEstadoCivilSpinner()
        setDataNascimento()
        setFoneTextChangedListener()
        setOnClickTeste()
    }

    fun setOnClickTeste() {
        btnTeste.setOnClickListener {
            var teste = PerfilController()
            teste.getCEP(this)
        }
    }

    fun setFoneTextChangedListener() {
        FoneEditText.addTextChangedListener(Mask.insert("(##)#####-####", FoneEditText));
    }

    fun setEstadoCivilSpinner() {
        val adapter = ArrayAdapter.createFromResource(this,
                R.array.estado_civil_array, R.layout.spinner_item)
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        estadoCivilSpinner.adapter = adapter
    }

    fun setDataNascimento() {
        dataNascimentoButton.setOnClickListener {
            onClickData()
        }
    }

    fun onClickData() {
        val c = Calendar.getInstance()
        mYear = c.get(Calendar.YEAR)
        mMonth = c.get(Calendar.MONTH)
        mDay = c.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this,
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear,
                                                     dayOfMonth ->
                    dataNascimentoButton
                            .setText(dayOfMonth.toString() +
                                    "/" + (monthOfYear + 1) + "/" + year)
                },
                mYear, mMonth, mDay)
        datePickerDialog.show()
    }
}
