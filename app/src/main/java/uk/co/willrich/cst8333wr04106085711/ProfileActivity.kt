package uk.co.willrich.cst8333wr04106085711

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout

class ProfileActivity : AppCompatActivity() {

    private var username: String? = null

    private lateinit var userNameTextView: TextView
    private lateinit var firstNameTextView: TextView
    private lateinit var lastNameTextView: TextView
    private lateinit var genderTextView: TextView
    private lateinit var heightTextView: TextView
    private lateinit var weightTextView: TextView
    private lateinit var emailTextView: TextView
    private lateinit var clearButton: Button
    private lateinit var submitButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_page)

        // Retrieve the username from the intent
        username = intent.getStringExtra("username")

        userNameTextView = findViewById(R.id.userNameProfileInput)
        firstNameTextView = findViewById(R.id.firstNameProfileInput)
        lastNameTextView = findViewById(R.id.lastNameProfileInput)
        genderTextView = findViewById(R.id.genderProfileInput)
        heightTextView = findViewById(R.id.heightProfileInput)
        weightTextView = findViewById(R.id.weightProfileInput)
        emailTextView = findViewById(R.id.emailProfileInput)


        // Update user details on profile page
        updateUserDetails()

        val toolbar = findViewById<ConstraintLayout>(R.id.toolbar_layout)
        val tbhButton = toolbar.findViewById<ImageButton>(R.id.homeToolbarButton)
        val tbpButton = toolbar.findViewById<ImageButton>(R.id.profileToolbarButton)
        val tblButton = toolbar.findViewById<ImageButton>(R.id.logToolbarButton)
        val tboButton = toolbar.findViewById<ImageButton>(R.id.overviewToolbarButton)
        val tbeButton = toolbar.findViewById<ImageButton>(R.id.exitToolbarButton)

        tbhButton.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        tbpButton.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        tblButton.setOnClickListener {
            val intent = Intent(this, LogActivity::class.java)
            startActivity(intent)
        }
        tboButton.setOnClickListener {
            val intent = Intent(this, OverviewActivity::class.java)
            startActivity(intent)
        }

        tbeButton.setOnClickListener {
            showExitConfirmationDialog()
        }
    }

    private fun updateUserDetails() {
        val dbHelper = DatabaseHelper(this)
        val user = dbHelper.getUser(username ?: "")

        Log.d("UserProfile", "User: $user") // Add this line to log the user object

        if (user != null) {
            userNameTextView.text = "Username: ${user.username}"
            firstNameTextView.text = "First Name: ${user.firstname}"
            lastNameTextView.text = "Last Name: ${user.lastname}"
            genderTextView.text = "Gender: ${user.gender}"
            heightTextView.text = "Height: ${user.height}"
            weightTextView.text = "Weight: ${user.weight}"
            emailTextView.text = "Email: ${user.email}"
        } else {
            Log.d("UserProfile", "User is null")
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