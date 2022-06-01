package com.example.forecastappagile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.example.forecastappagile.databinding.ActivityWeatherBinding

class WeatherActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWeatherBinding //defining the binding class

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeatherBinding.inflate(layoutInflater) //initializing the binding class
        setContentView(binding.root)

        // Getting the values from main activity by intent
        binding.idCityName.text = intent.getStringExtra("CityName")
        binding.idDescribe.text = intent.getStringExtra("Description")
        binding.idTemp.text = intent.getStringExtra("Temp")
        binding.idTempMin.text = intent.getStringExtra("TempMin")
        binding.idTempMax.text = intent.getStringExtra("TempMax")

        // Intent/navigate to main activity
        binding.idSearchBtn.setOnClickListener(){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }

    }
}