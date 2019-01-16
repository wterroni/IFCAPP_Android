package com.ifcapp.ifcapp.fragment

import android.app.Activity
import android.app.DatePickerDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.widget.ArrayAdapter
import com.ifcapp.ifcapp.R
import com.ifcapp.ifcapp.Util.Util
import com.ifcapp.ifcapp.controller.CepListener
import com.ifcapp.ifcapp.controller.PerfilController
import com.ifcapp.ifcapp.models.Cep
import kotlinx.android.synthetic.main.activity_perfil.*
import java.util.*
import android.view.*

class PerfilPessoalFragment : BaseFragment(), CepListener {

    private var mYear: Int = 0
    var mMonth: Int = 0
    var mDay: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_perfil_pessoal, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    fun init() {
        setEstadoCivilSpinner()
        setDataNascimento()
        setFoneTextChangedListener()
        setOnClickTeste()
        setEstadoSpinner()
    }

    fun setOnClickTeste() {
        btnCep.setOnClickListener {
            var teste = PerfilController(this)
            teste.getCEP(cepEditText.text.toString())
        }
    }

    fun setFoneTextChangedListener() {
        FoneEditText.addTextChangedListener(Mask.insert("(##)#####-####", FoneEditText));
    }

    fun setEstadoCivilSpinner() {
        val adapter = ArrayAdapter.createFromResource(activity as Activity,
                R.array.estado_civil_array, R.layout.spinner_item)
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        estadoCivilSpinner.adapter = adapter
    }

    fun setEstadoSpinner() {
        val adapter = ArrayAdapter.createFromResource(activity as Activity,
                R.array.estado_array, R.layout.spinner_item)
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        estadoSpinner.adapter = adapter
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

        val datePickerDialog = DatePickerDialog(activity as Activity,
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear,
                                                     dayOfMonth ->
                    dataNascimentoButton
                            .setText(dayOfMonth.toString() +
                                    "/" + (monthOfYear + 1) + "/" + year)
                },
                mYear, mMonth, mDay)
        datePickerDialog.show()
    }

    fun setDadosCep(cep: Cep) {
        enderecoTextView.setText(cep.logradouro)
        cidadeEditText.setText(cep.localidade)
        bairroEditText.setText(cep.bairro)
        estadoSpinner.setSelection(Util.setPosicaoEstado(cep.uf))
    }

    override fun onCepAvailable(cep: Cep) {
        setDadosCep(cep)
        Util.closeKeyboard(activity as Activity, cepEditText)
        numeroEditText.requestFocus()
        Util.openKeyboard(activity as Activity, numeroEditText)
    }
}
