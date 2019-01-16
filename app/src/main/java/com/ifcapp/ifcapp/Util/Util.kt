package com.ifcapp.ifcapp.Util

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText


class Util {

    companion object {

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