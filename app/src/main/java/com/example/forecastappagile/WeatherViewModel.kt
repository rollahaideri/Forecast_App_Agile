package com.example.forecastappagile


import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherViewModel: ViewModel() {

    var cityName= ""
    var temp = ""
    var description = ""
    var tempMin = ""
    var tempMax = ""
}