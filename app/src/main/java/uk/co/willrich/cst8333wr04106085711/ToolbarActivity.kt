package uk.co.willrich.cst8333wr04106085711

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.toolbar_layout)

        val htButton = findViewById<ImageButton>(R.id.homeToolbarButton)
        val ptButton = findViewById<ImageButton>(R.id.profileToolbarButton)
        val ltButton = findViewById<ImageButton>(R.id.logToolbarButton)
        val otButton = findViewById<ImageButton>(R.id.overviewToolbarButton)
        val etButton = findViewById<ImageButton>(R.id.exitToolbarButton)


        htButton.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        ptButton.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        ltButton.setOnClickListener {
            val intent = Intent(this, LogActivity::class.java)
            startActivity(intent)
        }

        otButton.setOnClickListener {
            val intent = Intent(this, OverviewActivity::class.java)
            startActivity(intent)
        }

    }
}