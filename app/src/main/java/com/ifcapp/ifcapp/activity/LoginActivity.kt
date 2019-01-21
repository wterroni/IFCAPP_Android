package com.ifcapp.ifcapp.activity

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.ifcapp.ifcapp.R
import com.ifcapp.ifcapp.Util.Util
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initialize()
    }


    private fun initialize() {
        setNotification()
        var buttonEntrar = findViewById(R.id.btnEntrar) as Button
        buttonEntrar.setOnClickListener {
            checkLoginIsTrue();
        }
        Util.openKeyboard(this, editTextSenha)
    }

    private fun checkLoginIsTrue() {
        var cpf = edtCpfCnpj.text.toString().replace(".", "").replace("-", "");
        if (cpf == "") {
            Toast.makeText(this, "CPF ou Senha inválidos.", Toast.LENGTH_LONG).show();
        } else {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent);
        }
    }

    private fun setNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            val channelId = getString(R.string.default_notification_channel_id)
            val channelName = getString(R.string.default_notification_channel_name)
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager?.createNotificationChannel(NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_LOW))
        }

        // If a notification message is tapped, any data accompanying the notification
        // message is available in the intent extras. In this sample the launcher
        // intent is fired when the notification is tapped, so any accompanying data would
        // be handled here. If you want a different intent fired, set the click_action
        // field of the notification message to the desired intent. The launcher intent
        // is used when no click_action is specified.
        //
        // Handle possible data accompanying notification message.
        // [START handle_data_extras]
        intent.extras?.let {
            for (key in it.keySet()) {
                val value = intent.extras.get(key)
                Log.d(TAG, "Key: $key Value: $value")
            }
        }

    }

    companion object {

        private const val TAG = "MainActivity"
    }
}

