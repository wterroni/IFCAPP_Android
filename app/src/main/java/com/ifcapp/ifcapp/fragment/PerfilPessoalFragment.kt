package com.ifcapp.ifcapp.fragment

import android.app.Activity
import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.widget.ArrayAdapter
import com.ifcapp.ifcapp.Util.Util
import com.ifcapp.ifcapp.controller.CepListener
import com.ifcapp.ifcapp.controller.PerfilController
import com.ifcapp.ifcapp.models.Cep
import java.util.*
import android.view.*
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_perfil_pessoal.*
import java.text.SimpleDateFormat
import android.widget.TextView
import android.widget.Spinner
import com.ifcapp.ifcapp.R


class PerfilPessoalFragment : BaseFragment(), CepListener {

    private var mYear: Int = 0
    var mMonth: Int = 0
    var mDay: Int = 0
    var validationListEditText: MutableList<EditText> = ArrayList()
    var validationListSpinner: MutableList<Spinner> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(com.ifcapp.ifcapp.R.layout.fragment_perfil_pessoal, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    fun init() {
        setEstadoCivilSpinner()
        setDataNascimento()
        setFoneTextChangedListener()
        setCepOnClick()
        setEstadoSpinner()
        setBtnOk()
        setListEditText()
        setListSpinner()
    }

    fun setListEditText() {
        validationListEditText.add(nomeEditText)
        validationListEditText.add(edtCpfCnpj)
        validationListEditText.add(dataNascimentoButton)
        validationListEditText.add(FoneEditText)
        validationListEditText.add(cepEditText)
        validationListEditText.add(enderecoTextView)
        validationListEditText.add(numeroEditText)
        validationListEditText.add(complementoEditText)
        validationListEditText.add(bairroEditText)
        validationListEditText.add(cidadeEditText)

        /*validationList.add(dataMembroButton)
        validationList.add(instituicaoEditText)
        validationList.add(cursoEditText)*/
    }

    fun setListSpinner() {
        validationListSpinner.add(estadoCivilSpinner)
        validationListSpinner.add(estadoSpinner)
    }

    fun setBtnOk() {
        btnOk.setOnClickListener {
            validSpinners()
           /*if (validateEditTexts()) {

           }*/
        }
    }

    fun validSpinners(): Boolean {
        return Util.validateSpinner(validationListSpinner, getString(R.string.msg_campo_obrigatorio))

    }

    fun validateEditTexts(): Boolean {
        return Util.validateEditText(validationListEditText, getString(R.string.msg_campo_obrigatorio))
    }

    fun setCepOnClick() {
        btnCep.setOnClickListener {
            onClickCep()
        }
    }

    fun onClickCep() {
        if (cepEditText.text.isEmpty()) {
            Util.closeKeyboard(activity as Activity, cepEditText)
            Toast.makeText(context, getString(R.string.valid_cep), Toast.LENGTH_LONG).show();
        }
        else if (cepEditText.text.length < 8) {
            Util.closeKeyboard(activity as Activity, cepEditText)
            Toast.makeText(context, getString(R.string.error_cep), Toast.LENGTH_LONG).show();
        }
        else {

            var perfilController = PerfilController(this)
            perfilController.getCEP(cepEditText.text.toString())
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
                DatePickerDialog.OnDateSetListener { _: DatePicker, year: Int,
                                                     monthOfYear: Int, dayOfMonth: Int ->

                    c.set(Calendar.YEAR, year)
                    c.set(Calendar.MONTH, monthOfYear)
                    c.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                    val myFormat = "dd/MM/yyyy"
                    val sdf = SimpleDateFormat(myFormat, Locale.US)
                    dataNascimentoButton.setText(sdf.format(c.time))
                    dataNascimentoButton.error = null
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
        val erro = "true"
        if (cep.erro.equals(erro)) {
            Util.closeKeyboard(activity as Activity, cepEditText)
            Toast.makeText(context, getString(R.string.error_cep), Toast.LENGTH_LONG).show();
        }
        else {
            setDadosCep(cep)
            Util.closeKeyboard(activity as Activity, cepEditText)
            numeroEditText.requestFocus()
            Util.openKeyboard(activity as Activity, numeroEditText)
        }
    }
}
