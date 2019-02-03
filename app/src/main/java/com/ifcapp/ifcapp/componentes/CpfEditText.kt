package com.ifcapp.ifcapp.componentes

import android.content.Context
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.text.method.NumberKeyListener
import android.util.AttributeSet
import android.widget.EditText

class CpfEditText : EditText {

    private var isUpdating: Boolean = false
    private val positioning = intArrayOf(0, 1, 2, 3, 5, 6, 7, 9, 10, 11, 13, 14)

    val cleanText: String
        get() {
            val text = this@CpfEditText.text.toString()

            text.replace("[^0-9]*".toRegex(), "")
            return text

        }

    private val keylistenerNumber = KeylistenerNumber()

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        initialize()

    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initialize()

    }

    constructor(context: Context) : super(context) {

    }

    private fun initialize() {

        val maxNumberLength = 11
        this.keyListener = keylistenerNumber

        //this.setText(" - ")
        //this.setSelection(1)

        this.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                val current = s.toString()

                /*
                 * Ok, here is the trick... calling setText below will recurse
                 * to this function, so we set a flag that we are actually
                 * updating the text, so we don't need to reprocess it...
                 */
                if (isUpdating) {
                    isUpdating = false
                    return

                }

                /* Strip any non numeric digit from the String... */
                var number = current.replace("[^0-9]*".toRegex(), "")
                if (number.length > 11)
                    number = number.substring(0, 11)
                val length = number.length

                /* Pad the number to 10 characters... */
                val paddedNumber = padNumber(number, maxNumberLength)

                /* Split phone number into parts... */
                val part1 = paddedNumber.substring(0, 3)
                val part2 = paddedNumber.substring(3, 6)
                val part3 = paddedNumber.substring(6, 9)
                val part4 = paddedNumber.substring(9, 11)

                /* build the masked phone number... */
                val cpf = "$part1.$part2.$part3-$part4"

                /*
                 * Set the update flag, so the recurring call to
                 * afterTextChanged won't do nothing...
                 */
                isUpdating = true
                this@CpfEditText.setText(cpf)

                this@CpfEditText.setSelection(positioning[length])

            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int,
                                           after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int,
                                       count: Int) {

            }
        })
    }

    protected fun padNumber(number: String, maxLength: Int): String {
        var padded = number
        for (i in 0 until maxLength - number.length)
            padded += " "
        return padded

    }

    private inner class KeylistenerNumber : NumberKeyListener() {

        override fun getInputType(): Int {
            return InputType.TYPE_CLASS_NUMBER or InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS

        }

        override fun getAcceptedChars(): CharArray {
            return charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9')

        }
    }

}