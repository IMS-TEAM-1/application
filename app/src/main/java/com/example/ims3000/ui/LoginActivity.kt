package com.example.ims3000.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import com.example.ims3000.R

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        setContentView(R.layout.activity_login)

        findViewById<Button>(R.id.login_button).setOnClickListener{
            val email = findViewById<EditText>(R.id.email_inputText)
            val password = findViewById<EditText>(R.id.password_inputText)
            val errorsExists = validate(email, password)
         }
    }


private fun validate(email: EditText, password: EditText):Boolean{
    var errorsExists = false
    if (!email.editableText.toString().contains("@")) {
        email.error = "This Is Not an Email"
        errorsExists = true
    }
    if (password.editableText.toString().length<8) {
        password.error = "Password needs to be shorter than 9 characters"
        errorsExists = true
    }
    return errorsExists
}
}