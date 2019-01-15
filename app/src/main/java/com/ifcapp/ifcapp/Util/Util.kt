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
    }
}