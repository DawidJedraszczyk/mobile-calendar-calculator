package edu.put.inf148293

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import edu.put.inf148293.databinding.ActivityMainBinding

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val DatyBtn: Button = findViewById(R.id.DatyBtn)
        DatyBtn.setOnClickListener{
            var datyAktywnosc: Intent = Intent(applicationContext, DatyActivity::class.java)
            startActivity(datyAktywnosc)
        }

        val CzasBtn: Button = findViewById(R.id.CzasBtn)
        CzasBtn.setOnClickListener{
            var czasAktywnosc: Intent = Intent(applicationContext, HmsActivity::class.java)
            startActivity(czasAktywnosc)
        }
    }

    private lateinit var binding: ActivityMainBinding
}