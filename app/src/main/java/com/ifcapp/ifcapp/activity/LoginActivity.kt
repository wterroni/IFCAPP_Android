package com.ifcapp.ifcapp.activity

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import com.ifcapp.ifcapp.R
import com.ifcapp.ifcapp.Util.Util
import kotlinx.android.synthetic.main.activity_login.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import io.rmiri.buttonloading.ButtonLoading
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat
import android.support.v4.view.AccessibilityDelegateCompat
import android.support.v4.view.ViewCompat
import android.widget.Button
import android.widget.TextView


class LoginActivity : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null
    private var user: FirebaseUser? = null
    private var novoUsuario = false
    private var resetPassword = false
    var listTextView: MutableList<TextView> = ArrayList()
    var listTextViewButton: MutableList<TextView> = ArrayList()

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
        if (novoUsuario) {
            startLoginActivity()
            Toast.makeText(this, getString(R.string.msg_novo_usuario_sucesso),
                    Toast.LENGTH_SHORT).show()
        }
        if (currentUser != null) {
            startMainActivity()
        }
    }

    private fun startLoginActivity() {
        finish()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    private fun initialize() {
        mAuth = FirebaseAuth.getInstance()
        setNotification()
        setNovoUsuario()
        setForgotPassword()
        setButton()
        Util.openKeyboard(this, editTextSenha)
        editTextSenha.hint == ""
        setAccessibility()
    }

    private fun setAccessibility() {
        listTextView.add(emailTextView)
        listTextView.add(textViewSenha)
        listTextViewButton.add(novoUsuarioTextView)
        listTextViewButton.add(esqueceuSenhaTextView)

        for (item in listTextView) {
            ViewCompat.setAccessibilityDelegate(item, object : AccessibilityDelegateCompat() {
                override fun onInitializeAccessibilityNodeInfo(host: View, info: AccessibilityNodeInfoCompat) {
                    super.onInitializeAccessibilityNodeInfo(host, info)
                    info.isHeading = true
                }
            })
        }

        for (item in listTextViewButton) {
            ViewCompat.setAccessibilityDelegate(item, object : AccessibilityDelegateCompat() {
                override fun onInitializeAccessibilityNodeInfo(host: View, info: AccessibilityNodeInfoCompat) {
                    super.onInitializeAccessibilityNodeInfo(host, info)
                    info.className = Button::class!!.java.name
                }
            })
        }

        ViewCompat.setAccessibilityDelegate(btnEntrar, object : AccessibilityDelegateCompat() {
            override fun onInitializeAccessibilityNodeInfo(host: View, info: AccessibilityNodeInfoCompat) {
                super.onInitializeAccessibilityNodeInfo(host, info)
                info.contentDescription = "login"
                info.className = Button::class!!.java.name
            }
        })
    }

    private fun setForgotPassword() {
        esqueceuSenhaTextView.setOnClickListener {
            onClickForgotPassword()
        }
    }

    private fun onClickForgotPassword() {
        textViewSenha.visibility = View.GONE
        editTextSenha.visibility = View.GONE
        novoUsuarioTextView.visibility = View.GONE
        esqueceuSenhaTextView.visibility = View.GONE
        emailEditText.hint = getString(R.string.hint_forgot_password)
        btnEntrar.text = getString(R.string.enviar)
        resetPassword = true
    }

    private fun sendEmail() {
        if (isConnected()) {
            if (!emailEditText.text.isEmpty() && validarFormatoEmail(emailEditText.text.toString())) {
                FirebaseAuth.getInstance().sendPasswordResetEmail(emailEditText.text.toString())
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Log.d(TAG, getString(R.string.msg_forgot_password))
                                Toast.makeText(this, getString(R.string.msg_forgot_password),
                                        Toast.LENGTH_SHORT).show()
                                startLoginActivity()
                            }
                        }
            } else {
                Toast.makeText(this, getString(R.string.msg_email_branco),
                        Toast.LENGTH_SHORT).show()
                finishLoading()
            }
        }
        else {
            erroConection()
        }
    }

    private fun setButton() {
        btnEntrar.setOnButtonLoadingListener(object : ButtonLoading.OnButtonLoadingListener {
            override fun onClick() {
                if (isConnected()) {
                    loginAction()
                }
                else {
                    erroConection()
                    finishLoading()
                }
            }

            override fun onStart() {
            }

            override fun onFinish() {
            }
        })

    }

    private fun erroConection () {
        Toast.makeText(this, getString(R.string.msg_error_network),
                Toast.LENGTH_SHORT).show()
    }

    fun isConnected () : Boolean {
        val connectivityManager = baseContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }

    internal fun finishLoading() {
        btnEntrar.setProgress(false)
    }

    internal fun finishLoadingWithDelay() {
        Handler().postDelayed({ btnEntrar.setProgress(false); updateUI(user) }, 1000)
    }

    private fun setNovoUsuario() {
        novoUsuarioTextView.setOnClickListener {
            btnEntrar.text = getString(R.string.criar)
            novoUsuarioTextView.visibility = View.GONE
            novoUsuario = true
            limpaCampos()
            editTextSenha.hint = getString(R.string.hint_senha)
        }
    }

    private fun limpaCampos() {
        emailEditText.text.clear()
        editTextSenha.text.clear()
    }

    private fun loginAction() {
        if (novoUsuario ) {
            if (validaCampos() && validaSenha()) {
                criaUsuario()
            }
            else {
                finishLoading()
            }
        } else if (resetPassword) {
            sendEmail()
        }

        else {
            loginUser()
        }
    }

    private fun validaSenha() :Boolean {
        if (editTextSenha.text.length < 6) {
            Toast.makeText(this, getString(R.string.msg_senha_invalido),
                    Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun loginUser() {
        if (validaCampos()) {
            mAuth?.signInWithEmailAndPassword(emailEditText.text.toString(),
                    editTextSenha.text.toString())
                    ?.addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Log.d(TAG, getString(R.string.msg_login_sucesso))
                            user = mAuth?.getCurrentUser()
                            finishLoadingWithDelay()

                        } else {
                            Log.w(TAG, getString(R.string.msg_login_falha), task.exception)
                            Toast.makeText(this, getString(R.string.msg_login_falha),
                                    Toast.LENGTH_SHORT).show()
                            finishLoading()
                        }
                    }
        }
        else {
            btnEntrar.setProgress(false)
        }
    }

    private fun validaCampos() : Boolean {

        if (emailEditText.text.isEmpty()) {
            Toast.makeText(this, getString(R.string.msg_email_branco),
                    Toast.LENGTH_SHORT).show()
            return false
        }
        else if (!validarFormatoEmail(emailEditText.text.toString())) {
            Toast.makeText(this, getString(R.string.msg_email_invalido),
                    Toast.LENGTH_SHORT).show()
            return false
        }
        else if (editTextSenha.text.isEmpty()) {
            Toast.makeText(this, getString(R.string.msg_senha_branco),
                    Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    private fun validarFormatoEmail(email: String): Boolean =
            android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()

    private fun criaUsuario() {
        mAuth?.createUserWithEmailAndPassword(emailEditText.text.toString(),
                editTextSenha.text.toString())
                ?.addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, getString(R.string.msg_novo_usuario_sucesso))
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

