package com.example.retrofit03102022

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Display
import android.view.View
import com.example.retrofit03102022.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getCurrentPost()

        binding.layoutGenerateFact.setOnClickListener{
            getCurrentPost()
        }
    }

    private fun getCurrentPost() {
        binding.tvFact.visibility = View.GONE
        binding.tvLength.visibility = View.GONE
        binding.pbLoading.visibility = View.VISIBLE

        val api: ApiRequests = Retrofit.Builder()
            .baseUrl("https://catfact.ninja/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiRequests::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            val response = api.getFact().awaitResponse()
            if(response.isSuccessful){
                val data = response.body()!!
                withContext(Dispatchers.Main){
                    binding.tvFact.visibility = View.VISIBLE
                    binding.tvLength.visibility = View.VISIBLE
                    binding.pbLoading.visibility = View.GONE

                    binding.tvFact.text = data.fact
                    binding.tvLength.text = data.length.toString()
                }
            }

        }
    }
}