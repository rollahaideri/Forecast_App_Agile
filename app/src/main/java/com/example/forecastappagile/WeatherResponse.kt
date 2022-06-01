package com.example.forecastappagile

import com.google.gson.annotations.SerializedName

class WeatherResponse {

    @SerializedName("weather")
    var weather = ArrayList<Weather>()
    @SerializedName("main")
    var main: Main? = null
    @SerializedName("name")
    var name: String? = null

}

class Weather {

    @SerializedName("description")
    var description: String? = null

}

class Main {

    @SerializedName("temp")
    var temp: Float = 0.toFloat()
    @SerializedName("temp_min")
    var tempMin: Float = 0.toFloat()
    @SerializedName("temp_max")
    var tempMax: Float = 0.toFloat()
}





