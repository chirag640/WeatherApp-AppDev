package com.chirag.weatherapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.chirag.weatherapp.databinding.ActivityMainBinding;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        fetchWeatherData("Vadodara");
        searchCity();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void searchCity() {
        SearchView searchView = binding.searchView;

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if (!s.isEmpty()) {
                    fetchWeatherData(s);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return true;
            }
        });

    }

    private void fetchWeatherData(String cityName) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        Call<WeatherApp> call = apiInterface.getWeather(cityName, "4761f9e0e91ab33c14835a73bd34f599", "metric");
        call.enqueue(new Callback<WeatherApp>() {
            @Override
            public void onResponse(Call<WeatherApp> call, Response<WeatherApp> response) {
                if (response.isSuccessful() && response.body() != null) {
                    WeatherApp responseBody = response.body();
                    String temperature = String.valueOf(responseBody.getMain().getTemp());
                    String humidity = String.valueOf(responseBody.getMain().getHumidity());
                    String windSpeed = String.valueOf(responseBody.getWind().getSpeed());
                    String condition = responseBody.getWeather().get(0).getMain();

                    binding.cityName.setText(cityName);
                    binding.date.setText(getCurrentDate());
                    binding.day.setText(getCurrentDay());
                    binding.weather.setText(condition);
                    binding.condition.setText(condition);
                    binding.humidity.setText(humidity + " %");
                    binding.windSpeed.setText(windSpeed + " m/s");
                    binding.temp.setText(temperature + " °C");

                    changeImagesAccordingToWeatherCondition(condition);
                } else {
                    Log.d("TAG", "onResponse: Response not successful");
                }
            }

            @Override
            public void onFailure(Call<WeatherApp> call, Throwable throwable) {
                Log.d("TAG", "onFailure: " + throwable.getMessage());
            }
        });
    }

    private void changeImagesAccordingToWeatherCondition(String condition) {
        switch (condition) {
            case "Clouds":
            case "Partly Clouds":
            case "Overcast":
            case "Mist":
            case "Foggy":
                binding.lottieAnimationView3.setAnimation(R.raw.cloud);
                binding.getRoot().setBackgroundResource(R.drawable.colud_background);
                break;
            case "Clear":
            case "Clear Sky":
            case "Sunny":
                binding.lottieAnimationView3.setAnimation(R.raw.sun);
                binding.getRoot().setBackgroundResource(R.drawable.sunny_background);
                break;
            case "Rain":
            case "Light Rain":
            case "Drizzle":
            case "Moderate Rain":
            case "Showers":
                binding.lottieAnimationView3.setAnimation(R.raw.rain);
                binding.getRoot().setBackgroundResource(R.drawable.rain_background);
                break;
            case "Blizzards":
            case "Light Snow":
            case "Moderate Snow":
            case "Heavy Snow":
                binding.lottieAnimationView3.setAnimation(R.raw.snow);
                binding.getRoot().setBackgroundResource(R.drawable.snow_background);
                break;
            default:
                binding.lottieAnimationView3.setAnimation(R.raw.sun);
                binding.getRoot().setBackgroundResource(R.drawable.sunny_background);
        }
        Log.d("TAG", "changeImagesAccordingToWeatherCondition: " + condition);
        binding.lottieAnimationView3.playAnimation();
    }

    private String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return sdf.format(new Date());
    }

    private String getCurrentDay() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE", Locale.getDefault());
        return sdf.format(new Date());
    }

}
