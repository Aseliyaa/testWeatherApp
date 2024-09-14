package com.example.testweatherapp.activity

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testweatherapp.R
import com.example.testweatherapp.adapter.CityAdapter
import com.example.testweatherapp.databinding.ActivityCityListBinding
import com.example.testweatherapp.databinding.CityViewholderBinding
import com.example.testweatherapp.model.CityResponseApi
import com.example.testweatherapp.repository.CityRepository
import com.example.testweatherapp.viewmodel.CityViewModel
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class CityListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCityListBinding
    private val cityAdapter by lazy { CityAdapter() }
    private val cityViewModel: CityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.apply {
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            statusBarColor = Color.TRANSPARENT
        }

        binding.apply {
            cityEdit.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun afterTextChanged(p0: Editable?) {
                    progressBar2.visibility = View.VISIBLE
                    cityViewModel.loadCity(p0.toString(), 10).enqueue(object :
                        retrofit2.Callback<CityResponseApi> {
                        override fun onResponse(
                            call: Call<CityResponseApi>,
                            response: Response<CityResponseApi>
                        ) {
                            if (response.isSuccessful) {
                                val data = response.body()
                                data?.let {
                                    progressBar2.visibility = View.GONE
                                    cityAdapter.differ.submitList(it)
                                    cityView.apply {
                                        layoutManager = LinearLayoutManager(
                                            this@CityListActivity,
                                            LinearLayoutManager.VERTICAL,
                                            false
                                        )
                                        adapter = cityAdapter
                                    }
                                }
                            }
                        }

                        override fun onFailure(call: Call<CityResponseApi>, t: Throwable) {

                        }

                    })
                }

            })
        }
    }
}