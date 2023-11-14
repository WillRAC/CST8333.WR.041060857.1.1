package uk.co.willrich.cst8333wr04106085711

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import java.util.Calendar

class LogActivity : AppCompatActivity() {

    private var username: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.log_page)

        username = intent.getStringExtra("username")

        val weightLog = findViewById<EditText>(R.id.WeightLogInput)
        val minLog = findViewById<EditText>(R.id.MinLogInput)
        val calLog = findViewById<EditText>(R.id.CalorieLogInput)
        val submitButton = findViewById<Button>(R.id.logSubmitButton)

        submitButton.setOnClickListener {

            val weightValue = weightLog.text.toString().toDoubleOrNull() ?: 0.0
            val minuteValue = minLog.text.toString().toIntOrNull() ?: 0
            val calorieValue = calLog.text.toString().toIntOrNull() ?: 0

            val currentDate = Calendar.getInstance().time

            Log.d("LogActivity", "Username: $username, Weight: $weightValue, Minutes: $minuteValue, Calories: $calorieValue, Date: $currentDate")


            val dbHelper = DatabaseHelper(this)
            val userId = dbHelper.getUser(username ?: "")?.id ?: 0
            val userData = UserData(0, userId, currentDate, calorieValue, minuteValue)
            dbHelper.addUserData(userData)

            // Log the data before inserting
            Log.d("LogActivity", "Adding new data: $userData")

            // Insert the data
            val rowId = dbHelper.addUserData(userData)

            // Log the result of the insertion
            Log.d("LogActivity", "Inserted row ID: $rowId")


            weightLog.text.clear()
            minLog.text.clear()
            calLog.text.clear()
        }






        val toolbar = findViewById<ConstraintLayout>(R.id.toolbar_layout)
        val tbhButton = toolbar.findViewById<ImageButton>(R.id.homeToolbarButton)
        val tbpButton = toolbar.findViewById<ImageButton>(R.id.profileToolbarButton)
        val tblButton = toolbar.findViewById<ImageButton>(R.id.logToolbarButton)
        val tboButton = toolbar.findViewById<ImageButton>(R.id.overviewToolbarButton)
        val tbeButton = toolbar.findViewById<ImageButton>(R.id.exitToolbarButton)

        tbhButton.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.putExtra("username", username)
            startActivity(intent)
        }

        tbpButton.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            intent.putExtra("username", username)
            startActivity(intent)
        }

        tblButton.setOnClickListener {
            val intent = Intent(this, LogActivity::class.java)
            intent.putExtra("username", username)
            startActivity(intent)
        }

        tboButton.setOnClickListener {
            val intent = Intent(this, OverviewActivity::class.java)
            intent.putExtra("username", username)
            startActivity(intent)
        }

        tbeButton.setOnClickListener {
            showExitConfirmationDialog()
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
