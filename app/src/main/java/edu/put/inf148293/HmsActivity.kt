package edu.put.inf148293

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import edu.put.inf148293.R
import edu.put.inf148293.databinding.ActivityMainBinding

class HmsActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.hms_layout)
        val wsteczBtn: Button = findViewById(R.id.backBtnCzas)
        val H1= findViewById<EditText>(R.id.czasInputH1)
        val H2= findViewById<EditText>(R.id.czasInputH2)
        val M1= findViewById<EditText>(R.id.czasInputM1)
        val M2= findViewById<EditText>(R.id.czasInputM2)
        val S1= findViewById<EditText>(R.id.czasInputS1)
        val S2= findViewById<EditText>(R.id.czasInputS2)


        wsteczBtn.setOnClickListener{
            var poprzedniaAktywnosc: Intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(poprzedniaAktywnosc)
        }
        val plusBtn: Button = findViewById(R.id.plusSignCzas)
        plusBtn.setOnClickListener {
            if (M1.text.toString().toInt()<61 && M2.text.toString().toInt()<61 && S1.text.toString().toInt()<61 && S2.text.toString().toInt()<61){
                var hour1 = H1.text.toString().toInt()
                var hour2 = H2.text.toString().toInt()
                var sumHour = (hour1 + hour2)
                var min1 = M1.text.toString().toInt()
                var min2 = M2.text.toString().toInt()
                var sumMin = (min1 + min2)
                var sec1 = S1.text.toString().toInt()
                var sec2 = S2.text.toString().toInt()
                var sumSec = (sec1 + sec2)
                if(sumSec>=60){
                    sumSec-=60
                    sumMin+=1
                }
                if(sumMin >= 60) {
                    sumHour += 1
                    sumMin -= 60
                }
                H1.setText(sumHour.toString())
                H2.setText("0")
                M1.setText(sumMin.toString())
                M2.setText("0")
                S1.setText(sumSec.toString())
                S2.setText("0")
            } else {
                Toast.makeText(this, "Podaj poprawne dane!", Toast.LENGTH_LONG).show()
            }
        }

        val minusBtn: Button = findViewById(R.id.minusSignCzas)
        minusBtn.setOnClickListener {
            if (M1.text.toString().toInt()<61 && M2.text.toString().toInt()<61 && S1.text.toString().toInt()<61 && S2.text.toString().toInt()<61){
                var hour1 = H1.text.toString().toInt()
                var hour2 = H2.text.toString().toInt()
                var sumHour = (hour1 - hour2)
                var min1 = M1.text.toString().toInt()
                var min2 = M2.text.toString().toInt()
                var sumMin = (min1 - min2)
                var sec1 = S1.text.toString().toInt()
                var sec2 = S2.text.toString().toInt()
                var sumSec = (sec1 - sec2)
                if(sumMin<0 && sumHour>0){
                    sumHour-=1
                    sumMin+=60
                }
                if(sumSec<0 && sumMin>0){
                    sumMin-=1
                    sumSec+=60
                }
                if(sumSec<0 && sumHour>0 && sumMin <=0){
                    sumHour-=1
                    sumMin+=59
                    sumSec+=60
                }
                H1.setText(sumHour.toString())
                H2.setText("0")
                M1.setText(sumMin.toString())
                M2.setText("0")
                S1.setText(sumSec.toString())
                S2.setText("0")
            } else {
                Toast.makeText(this, "Podaj poprawne dane!", Toast.LENGTH_LONG).show()
            }
        }

        val ACBtn: Button = findViewById(R.id.ACButtonCzas)

        ACBtn.setOnClickListener {
            H1.setText("0")
            H2.setText("0")
            M1.setText("0")
            M2.setText("0")
            S1.setText("0")
            S2.setText("0")
        }
    }
    private lateinit var binding: ActivityMainBinding
}