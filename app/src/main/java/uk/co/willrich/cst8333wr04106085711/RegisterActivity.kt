package uk.co.willrich.cst8333wr04106085711

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_page)

        val sButton = findViewById<Button>(R.id.submitButton)
        val cButton = findViewById<Button>(R.id.clearButton)
        val userNameText = findViewById<EditText>(R.id.userNameInput)
        val firstNameText = findViewById<EditText>(R.id.firstNameInput)
        val lastNameText = findViewById<EditText>(R.id.lastNameInput)
        val maleCheck = findViewById<CheckBox>(R.id.malecheckBox)
        val femaleCheck = findViewById<CheckBox>(R.id.femalecheckBox)
        val weightText = findViewById<EditText>(R.id.weightInput)
        val emailText = findViewById<EditText>(R.id.emailInput)
        val pwdText = findViewById<EditText>(R.id.passwordInput)

        // Toolbar buttons

        val hButton = findViewById<ImageButton>(R.id.homeToolbarButton)
        val pButton = findViewById<ImageButton>(R.id.profileToolbarButton)
        val lButton = findViewById<ImageButton>(R.id.logToolbarButton)
        val oButton = findViewById<ImageButton>(R.id.overviewToolbarButton)
        val eButton = findViewById<ImageButton>(R.id.exitToolbarButton)

        hButton.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        pButton.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        lButton.setOnClickListener {
            val intent = Intent(this, LogActivity::class.java)
            startActivity(intent)
        }

        oButton.setOnClickListener {
            val intent = Intent(this, OverviewActivity::class.java)
            startActivity(intent)
        }

        eButton.setOnClickListener {
            showExitConfirmationDialog()
        }

        sButton.setOnClickListener {
            val newUser = UserMain(
                id = 0,  // You may leave this as 0 since it will be auto-generated in the database
                username = userNameText.text.toString(),
                firstname = firstNameText.text.toString(),
                lastname = lastNameText.text.toString(),
                gender = if (maleCheck.isChecked) "Male" else if (femaleCheck.isChecked) "Female" else "",
                weight = weightText.text.toString().toDoubleOrNull() ?: 0.0,
                height = 0.0,  // Set default height or get it from the UI
                email = emailText.text.toString(),
                password = pwdText.text.toString()
            )

            val dbHelper = DatabaseHelper(this)
            val userId = dbHelper.addUser(newUser)

            if (userId != -1L) {
                // User added successfully, you can navigate to another screen or show a success message
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            } else {
                AlertDialog.Builder(this)
                    .setTitle("Register Failed")
                    .setMessage("Error with Registration")
                    .setPositiveButton("OK", null)
                    .show()
            }
        }

    }

    private fun showExitConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Exit Confirmation")
            .setMessage("Are you sure you want to exit?")
            .setPositiveButton("Yes") { dialog, which ->
                finishAffinity()
            }
            .setNegativeButton("No", null)

        val dialog = builder.create()
        dialog.show()
    }




}
