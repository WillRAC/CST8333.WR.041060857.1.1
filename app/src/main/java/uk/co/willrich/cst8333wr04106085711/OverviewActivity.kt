package uk.co.willrich.cst8333wr04106085711

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import java.util.Calendar

class OverviewActivity : AppCompatActivity() {

    private var username: String? = null

    private lateinit var calDayTotalInput: TextView
    private lateinit var minDayTotalInput: TextView
    private lateinit var calWeekTotalInput: TextView
    private lateinit var minWeekTotalInput: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.overview_page)

        calDayTotalInput = findViewById(R.id.calDayTotalInput)
        minDayTotalInput = findViewById(R.id.minDayTotalInput)
        calWeekTotalInput = findViewById(R.id.calWeekTotalInput)
        minWeekTotalInput = findViewById(R.id.minWeekTotalInput)

        username = intent.getStringExtra("username")


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

        loadDataFromDatabase()

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

    private fun loadDataFromDatabase() {
        val dbHelper = DatabaseHelper(this)
        val user = dbHelper.getUser(username ?: "")

        Log.d("UserProfile", "User: $user")

        // Get current date in milliseconds
        val currentDate = Calendar.getInstance().timeInMillis

        // Day totals
        val allDayUserData = dbHelper.getAllUserData(user?.id ?: 0)
        val dayUserData = allDayUserData.find { isSameDay(it.date.time, currentDate) }

        Log.d("OverviewActivity", "Day UserData: $dayUserData")

        if (dayUserData != null) {
            calDayTotalInput.text = dayUserData.calorie.toString()
            minDayTotalInput.text = dayUserData.minutes.toString()
        } else {
            calDayTotalInput.text = "0"
            minDayTotalInput.text = "0"
        }


        // Week totals
        val oneWeekAgo = Calendar.getInstance()
        oneWeekAgo.add(Calendar.DAY_OF_MONTH, -7)

        val currentTime = Calendar.getInstance().timeInMillis
        val allWeekUserData = dbHelper.getAllUserData(user?.id ?: 0)

// Include the time up to the current moment
        val weekUserData = allWeekUserData.find { it.date.time >= oneWeekAgo.timeInMillis && it.date.time <= currentTime }

        Log.d("OverviewActivity", "Week UserData: $weekUserData")

        if (weekUserData != null) {
            calWeekTotalInput.text = weekUserData.calorie.toString()
            minWeekTotalInput.text = weekUserData.minutes.toString()
        } else {
            calWeekTotalInput.text = "0"
            minWeekTotalInput.text = "0"
        }
    }

    private fun isSameDay(date1: Long, date2: Long): Boolean {
        val cal1 = Calendar.getInstance()
        val cal2 = Calendar.getInstance()
        cal1.timeInMillis = date1
        cal2.timeInMillis = date2
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
    }

    override fun onResume() {
        super.onResume()
        loadDataFromDatabase()
    }


}
