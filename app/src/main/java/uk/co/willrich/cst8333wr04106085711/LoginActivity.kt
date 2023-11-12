package uk.co.willrich.cst8333wr04106085711

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
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

        rButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        eButton.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        cButton.setOnClickListener {
            userNameText.text.clear()
            pwdText.text.clear()
        }




    }
}