package com.ifcapp.ifcapp.activity

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.ifcapp.ifcapp.R
import com.ifcapp.ifcapp.Util.Util
import kotlinx.android.synthetic.main.activity_login.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null
    private var user: FirebaseUser? = null
    private var novoUsuario = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initialize()
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = mAuth?.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null) {
            startMainActivity()
        }
    }

    private fun initialize() {
        mAuth = FirebaseAuth.getInstance()
        setNotification()
        setNovoUsuario()
        setButton()
        Util.openKeyboard(this, editTextSenha)
    }

    private fun setButton() {
        btnEntrar.setOnClickListener {
            loginAction();
        }
    }

    private fun setNovoUsuario() {
        novoUsuarioTextView.setOnClickListener {
            btnEntrar.text = getString(R.string.criar)
            novoUsuarioTextView.visibility = View.GONE
            novoUsuario = true
        }
    }

    private fun loginAction() {
        if (novoUsuario) {
            criaUsuario()
        } else {
            loginUser()
        }
    }

    private fun loginUser() {
        mAuth?.signInWithEmailAndPassword(emailEditText.text.toString(),
                editTextSenha.text.toString())
                ?.addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, getString(R.string.msg_login_sucesso))
                        val user = mAuth?.getCurrentUser()
                        updateUI(user)
                    } else {
                        Log.w(TAG, getString(R.string.msg_login_falha), task.exception)
                        Toast.makeText(this, getString(R.string.msg_login_falha),
                                Toast.LENGTH_SHORT).show()
                        updateUI(null)
                    }
                }
    }

    private fun criaUsuario() {
        mAuth?.createUserWithEmailAndPassword(emailEditText.text.toString(),
                editTextSenha.text.toString())
                ?.addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, getString(R.string.msg_novo_usuario_sucesso))
                        user = mAuth?.getCurrentUser()
                        updateUI(user)
                    } else {
                        Log.w(TAG, getString(R.string.msg_novo_usuario_falha), task.exception)
                        Toast.makeText(this, getString(R.string.msg_novo_usuario_falha),
                                Toast.LENGTH_SHORT).show()
                        updateUI(null)
                    }
                }
    }

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent);
    }

    private fun setNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = getString(R.string.default_notification_channel_id)
            val channelName = getString(R.string.default_notification_channel_name)
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager?.createNotificationChannel(NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_LOW))
        }

        intent.extras?.let {
            for (key in it.keySet()) {
                val value = intent.extras.get(key)
                Log.d(TAG, "Key: $key Value: $value")
            }
        }
    }

    companion object {

        private const val TAG = "LoginActivity"
    }
}

