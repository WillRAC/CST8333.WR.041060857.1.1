package uk.co.willrich.cst8333wr04106085711

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_page)

        val username = intent.getStringExtra("username")

        val lButton = findViewById<ImageButton>(R.id.logHomeButton)
        val oButton = findViewById<ImageButton>(R.id.overviewHomeButton)
        val pButton = findViewById<ImageButton>(R.id.profileHomeButton)
        val eButton = findViewById<ImageButton>(R.id.exitHomeButton)

        lButton.setOnClickListener {
            val intent = Intent(this, LogActivity::class.java)
            startActivity(intent)
        }

        oButton.setOnClickListener {
            val intent = Intent(this, OverviewActivity::class.java)
            startActivity(intent)
        }

        pButton.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            intent.putExtra("username", username)
            startActivity(intent)
        }

        eButton.setOnClickListener {
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
