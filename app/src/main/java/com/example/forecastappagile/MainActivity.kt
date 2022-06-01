package com.example.forecastappagile


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.forecastappagile.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private val baseUrl = "https://api.openweathermap.org/"
    private val appId = "c28b2af665d69602d625164a666659a7"

    private lateinit var binding: ActivityMainBinding //defining the binding class
    private lateinit var viewModel: WeatherViewModel  //defining the viewModel class
    private lateinit var myQuery: String

    private val data = ArrayList<ItemsViewModel>()
    private val adapter = CustomAdapter(data)

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater) //initializing the binding class
        setContentView(binding.root)


        viewModel = ViewModelProvider(this)[WeatherViewModel::class.java]

        binding.recyclerview.layoutManager = LinearLayoutManager(this)

        // Functionality of swiping to delete an item
       val swipeToDeleteCallBack = object : SwipeGesture(){
           override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
               val position = viewHolder.adapterPosition
               data.removeAt(position)
               binding.recyclerview.adapter?.notifyItemRemoved(position)
           }

       }

        // Connection for Swipe
        val touchHelper = ItemTouchHelper(swipeToDeleteCallBack)
        touchHelper.attachToRecyclerView(binding.recyclerview)

        // Passing value to another activity using Intent
        adapter.setOnItemClickListener(object : CustomAdapter.OnItemClickListener{
            override fun onItemClick(position: Int) {

                val intent = Intent(this@MainActivity, WeatherActivity::class.java)
                    .apply {
                        putExtra("CityName", viewModel.cityName)
                        putExtra("Temp", viewModel.temp)
                        putExtra("TempMin", viewModel.tempMin)
                        putExtra("TempMax", viewModel.tempMax)
                        putExtra("Description",viewModel.description)
                    }
                startActivity(intent)
            }
        })


        // Calling API Call function using this button
        binding.button.setOnClickListener {
            myQuery = binding.etUserInput.text.toString()   // Set myQuery to editText value input
            getCurrentData()                                // API Call function
            binding.etUserInput.text.clear()
        }

        binding.recyclerview.adapter = adapter

    }

    // Fetching Api call main functionality
    private fun getCurrentData() {

        // Build Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Connection
        val service = retrofit.create(WeatherService::class.java)
        val call = service.getCurrentWeatherData(myQuery,"metric", appId)

        // Implementation
        call.enqueue(object : Callback<WeatherResponse> {

            // functionality of successful API
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                if (response.isSuccessful) {
                    val weatherResponse = response.body()!! // The response object

                    // Setting value for our variables
                    viewModel.cityName = "${weatherResponse.name}"
                    viewModel.temp = "${weatherResponse.main!!.temp.toInt()}" + "°C"
                    viewModel.description = "${weatherResponse.weather[0].description}"
                    viewModel.tempMin = "${weatherResponse.main?.tempMin?.toInt()}" + "°C"
                    viewModel.tempMax= "${weatherResponse.main?.tempMax?.toInt()}" + "°C"

                    // Adding new item to our recyclerView
                    data.add(ItemsViewModel(viewModel.cityName, viewModel.temp))
                    adapter.notifyDataSetChanged()


                }
            }

            // Functionality on unsuccessful API
            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {

            }
        })
    }
}


