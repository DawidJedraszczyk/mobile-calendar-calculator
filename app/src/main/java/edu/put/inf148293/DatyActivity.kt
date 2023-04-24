package edu.put.inf148293

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import edu.put.inf148293.databinding.ActivityMainBinding
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.Calendar

class DatyActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.daty_layout)

        val datePicker1: DatePicker = findViewById(R.id.pierwszaData)
        val datePicker2: DatePicker = findViewById(R.id.drugaData)

        val wsteczBtn: Button = findViewById(R.id.backBtn)

        wsteczBtn.setOnClickListener{
            var poprzedniaAktywnosc: Intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(poprzedniaAktywnosc)
        }
        val addBtn: Button = findViewById(R.id.add)


        var iloscDni = findViewById<EditText>(R.id.ileDni)
        var year1 = datePicker1.year
        var month1 = datePicker1.month
        var day1 = datePicker1.dayOfMonth
        var year2 = datePicker2.year
        var month2 = datePicker2.month
        var day2 = datePicker2.dayOfMonth

        var dniRobocze = findViewById<TextView>(R.id.dniRobocze)
        fun isHoliday(date: LocalDate): Boolean {
            val holidays = listOf(
                LocalDate.of(2005, 4, 8),
                LocalDate.of(2018, 11, 12),
                LocalDate.of(date.year, 1, 1),  // Nowy Rok
                LocalDate.of(date.year, 1, 6),  // Trzech Króli
                LocalDate.of(date.year, 5, 1),  // Święto Pracy
                LocalDate.of(date.year, 5, 3),  // Święto Konstytucji 3 Maja
                LocalDate.of(date.year, 8, 15), // Wniebowzięcie Najświętszej Maryi Panny
                LocalDate.of(date.year, 11, 1), // Wszystkich Świętych
                LocalDate.of(date.year, 11, 11),// Narodowe Święto Niepodległości
                LocalDate.of(date.year, 12, 25),// Boże Narodzenie (pierwszy dzień)
                LocalDate.of(date.year, 12, 26) // Boże Narodzenie (drugi dzień)
            )
            return holidays.contains(date)
        }

        fun calculateEasterDate(year: Int): MutableList<LocalDate>{
            val a = year % 19
            val b = year / 100
            val c = year % 100
            val d = b / 4
            val e = b % 4
            val f = (b + 8) / 25
            val g = (b - f + 1) / 3
            val h = (19 * a + b - d - g + 15) % 30
            val i = c / 4
            val k = c % 4
            val l = (32 + 2 * e + 2 * i - h - k) % 7
            val m = (a + 11 * h + 22 * l) / 451
            val p = (h + l - 7 * m + 114) % 31
            val day = p + 1
            val month = (h + l - 7 * m + 114) / 31
            var result = mutableListOf<LocalDate>()
            result.add(LocalDate.of(year, month, day))
            result.add(LocalDate.of(year, month, day+1))
            return result
        }


        fun countWorkingDays(startDate: LocalDate, endDate: LocalDate): Int{
            val result = mutableListOf<Pair<Int, Int>>()
            val years = mutableListOf<Int>()
            var date = startDate
            if(date.year == endDate.year){
                years.add(date.year)
            }
            while (date.year < endDate.year) {
                years.add(date.year)
                date = date.plusYears(1)
            }
            for (year in years) {
                val easter = calculateEasterDate(year)
                var count = 0
                var date = startDate
                while (date <= endDate) {
                    if (date.dayOfWeek != DayOfWeek.SATURDAY &&
                        date.dayOfWeek != DayOfWeek.SUNDAY &&
                        !isHoliday(date) && date !in easter
                    ) {
                        count++
                    }
                    date = date.plusDays(1)
                }
                result.add(year to count)
            }
            var workingDays = 0
            for (year in result){
                workingDays += year.second
            }
            return workingDays
        }


        addBtn.setOnClickListener{
            val calendar = Calendar.getInstance()
            calendar.set(year1, month1, day1)
            calendar.add(Calendar.DAY_OF_YEAR, iloscDni.text.toString().toInt())
            datePicker2.updateDate(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
        }

        datePicker1.setOnDateChangedListener() { _, year, month, dayOfMonth ->
            val startDate = LocalDate.of(year, month + 1, dayOfMonth)
            val endDate = LocalDate.of(datePicker2.year, datePicker2.month + 1, datePicker2.dayOfMonth)
            val daysBetween = ChronoUnit.DAYS.between(startDate, endDate)
            val workingDays = countWorkingDays(startDate, endDate)
            dniRobocze.setText(workingDays.toString())
            iloscDni.setText(daysBetween.toString())
        }
        datePicker2.setOnDateChangedListener() { _, year, month, dayOfMonth ->
            val endDate = LocalDate.of(year, month + 1, dayOfMonth)
            val startDate = LocalDate.of(datePicker1.year, datePicker1.month + 1, datePicker1.dayOfMonth)
            val daysBetween = ChronoUnit.DAYS.between(startDate, endDate)
            val workingDays = countWorkingDays(startDate, endDate)
            dniRobocze.setText(workingDays.toString())
            iloscDni.setText(daysBetween.toString())
        }
    }
    private lateinit var binding: ActivityMainBinding
}