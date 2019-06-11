package com.wordpress.pravinkanna.weatherapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

public class MainActivity extends AppCompatActivity {

    EditText etGetCity;
    TextView tvShowWeather, tvShowCity;
    Button btnGetWeather;
    String city;
    RequestQueue requestQueue;


    String BASE_URL = "https://api.openweathermap.org/data/2.5/weather?q=";
    String API_KEY = "&appid=6f47f1a76e423cf152ec02631451a7a0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etGetCity = findViewById(R.id.editText_get_city);
        tvShowWeather = findViewById(R.id.textView_show_info);
        btnGetWeather = findViewById(R.id.button_get_weather);
        tvShowCity = findViewById(R.id.textView_show_city);

//        requestQueue = Volley.newRequestQueue(this);

        requestQueue = Singleton.getInstance(this).getRequestQueue();

        btnGetWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                city = etGetCity.getText().toString();
                if(city.isEmpty() == true){
                    etGetCity.setError("Please enter a city");
                } else {
                    String finalUrl = BASE_URL + city + API_KEY;
                    sendApiRequest(finalUrl);
                }
            }
        });


    }

    public void sendApiRequest(String url){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String cityName = response.getString("name");
                    tvShowCity.setText(cityName);

                    String weather = response.getString("weather");
                    JSONArray weatherArray = new JSONArray(weather);
                    JSONObject weatherArrayObject = weatherArray.getJSONObject(0);
                    String weatherMain =  weatherArrayObject.getString("main");
                    tvShowWeather.setText(weatherMain);

                    String main = response.getString("main");
                    JSONObject mainObject = new JSONObject(main);
                    String temp = mainObject.getString("temp");
                    int tempInCelsius = (int) Math.round(Float.parseFloat(temp)-273.15);
                    tvShowWeather.append("\n Temprature: " + tempInCelsius + "°C ");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "No Internet Connection",Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonObjectRequest);

    }
}
