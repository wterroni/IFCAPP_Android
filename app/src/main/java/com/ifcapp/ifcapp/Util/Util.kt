package com.ifcapp.ifcapp.Util

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView


class Util {

    companion object {

        fun validateEditText(validationList: List<EditText>, msgError: String): Boolean {
            for (et in validationList) {
                if (isInvalidEditText(et)) {
                    et.error = msgError
                    et.requestFocus()
                    return false
                }
            }
            return true
        }

        private fun isInvalidEditText(et: EditText) : Boolean {
            return et.text.isEmpty() || et.length() == 0 || et.equals("");
        }

        fun validateSpinner(validationList: List<Spinner>, msgError: String): Boolean {
            for (et in validationList) {
                if (isInvalidSpinner(et, msgError)) {
                    return false
                }
            }
            return true
        }

        private fun isInvalidSpinner(spinner: Spinner, error: String): Boolean {
            if (spinner.selectedItemPosition == 0) {
                setSpinnerError(spinner, "")
                return false
            }
            return true
        }

        private fun setSpinnerError(spinner: Spinner, error: String) {
            val selectedView = spinner.selectedView
            if (selectedView != null && selectedView is TextView) {
                selectedView.error = "error"
                selectedView.setTextColor(Color.RED)
                selectedView.text = error
                //spinner.performClick()
            }
        }

        fun closeKeyboard(activity: Activity, editText: EditText) {
            val view = activity.currentFocus
            if (view != null) {
                val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(editText.getWindowToken(), 0)
            }
        }

        fun openKeyboard(activity: Activity, editText: EditText) {
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
        }

        fun setPosicaoEstado(estado: String) : Int {
            val posicaoEstado = when (estado) {
                "AC" -> 1
                "AL" -> 2
                "AM" -> 3
                "AP" -> 4
                "BA" -> 5
                "CE" -> 6
                "DF" -> 7
                "ES" -> 8
                "GO" -> 9
                "MA" -> 10
                "MG" -> 11
                "MS" -> 12
                "MT" -> 13
                "PA" -> 14
                "PB" -> 15
                "PE" -> 16
                "PI" -> 17
                "PR" -> 18
                "RJ" -> 19
                "RN" -> 20
                "RO" -> 21
                "RR" -> 22
                "RS" -> 23
                "SC" -> 24
                "SE" -> 25
                "SP" -> 26
                "TO" -> 27
                else -> 0
            }
            return posicaoEstado
        }
    }


}