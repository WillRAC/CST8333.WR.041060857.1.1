package uk.co.willrich.cst8333wr04106085711

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_page)

        val rButton = findViewById<Button>(R.id.loginRegisterButton)
        val cButton = findViewById<Button>(R.id.loginClearButton)
        val eButton = findViewById<Button>(R.id.loginEnterButton)
        val userNameText = findViewById<EditText>(R.id.userNameInput)
        val pwdText = findViewById<EditText>(R.id.pwdInput)

        cButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }


    }
}