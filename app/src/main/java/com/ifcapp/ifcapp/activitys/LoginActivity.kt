package com.ifcapp.ifcapp.activitys

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.ifcapp.ifcapp.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initialize()
    }

    private fun initialize() {
        var buttonEntrar = findViewById(R.id.btnEntrar) as Button
        buttonEntrar.setOnClickListener {
            checkLoginIsTrue();
        }
    }

    private fun checkLoginIsTrue() {
        var cpf = edtCpfCnpj.text.toString().replace(".","").replace("-", "");
        if (cpf != "39144510845") {
            Toast.makeText(this, "CPF ou Senha inválidos.", Toast.LENGTH_LONG).show();
        }
        else {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent);
        }
    }
}
