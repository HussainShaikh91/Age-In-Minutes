package com.exaple.ageinminutes

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private var  SelectedDateText : TextView? = null
    private var  ageInMinutes : TextView? = null
    private var  ageInhours : TextView? = null
    private var  ageInSeconds : TextView? = null
    private var  ageInDaysText : TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        SelectedDateText = findViewById(R.id.selectedDateText)
        ageInMinutes = findViewById(R.id.ageInMinutes)
        ageInhours = findViewById(R.id.ageInHours)
        ageInSeconds = findViewById(R.id.ageInSeconds)
        ageInDaysText = findViewById(R.id.ageInDays)
        val btnSelectDate = findViewById<TextView>(R.id.selectDate)
        btnSelectDate.setOnClickListener {
            SelectDatePicker()
        }
    }

    private fun SelectDatePicker() {
        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val dayOfMonth = myCalendar.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(this,DatePickerDialog.OnDateSetListener{_, SelectedYear, SelectedMonth, SelectedDayOfMonth ->
            //Toast.makeText(this,"selected year $SelectedYear",Toast.LENGTH_SHORT).show()
            //for set selected date in textView
            val selectedDate = "$SelectedDayOfMonth/${SelectedMonth+1}/$SelectedYear"
            SelectedDateText?.text = selectedDate
            //date format
            val sdf = SimpleDateFormat("dd/MM/yyyy",Locale.ENGLISH)
            val theDate = sdf.parse(selectedDate)

            //for avoid crush
            theDate?.let {
                val selectedDateInMinutes = theDate.time/60000
                val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))

                currentDate?.let {
                    val currentDateInMinutes = currentDate.time/60000
                    val diffrenceInMinutes = currentDateInMinutes - selectedDateInMinutes
                    ageInMinutes?.text = diffrenceInMinutes.toString() //age in minutes

                    //this is for converting age in hours
                    val ageInHrs = diffrenceInMinutes / 60
                    ageInhours?.text = ageInHrs.toString()
                    //this is for converting age in seconds
                    val ageInSnds = diffrenceInMinutes * 60
                    ageInSeconds?.text = ageInSnds.toString()
                    //this is for converting age in days
                    val ageInDays = diffrenceInMinutes / 1440
                    ageInDaysText?.text = ageInDays.toString()

                }

            }
        },
        year,month,dayOfMonth)
        //only select pass date
        dpd.datePicker.maxDate = System.currentTimeMillis() - 86400000
        //show datePicker dialog
        dpd.show()

    }
}