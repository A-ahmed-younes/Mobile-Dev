package com.example.weatherApp;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    TextView ville;
    TextView tmp;
    TextView tmpMin;
    TextView tmpMax;
    TextView txtPressure;
    TextView txtHumidity;
    TextView txtDate;

    String latitude;
    String longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageView imgview = findViewById(R.id.img);
        imgview.setImageResource(R.drawable.cloudy);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        final Context co = this;

        ville = findViewById(R.id.txtville);
        tmp = findViewById(R.id.temp);
        tmpMin = findViewById(R.id.tempmin);
        tmpMax = findViewById(R.id.tempmax);
        txtHumidity = findViewById(R.id.humid);
        txtDate = findViewById(R.id.date);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                ville.setText(query);
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url = "http://api.openweathermap.org/data/2.5/weather?q="
                        + query + "&appid=e457293228d5e1465f30bcbe1aea456b";


                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.i("Log", "--------------------------------");
                            Log.i("Log", response);

                            JSONObject jsonObject = new JSONObject(response);

                            Date date = new Date(jsonObject.getLong("dt") * 1000);
                            SimpleDateFormat simpleDateFormat =
                                    new SimpleDateFormat("dd-MMM-yyyy' T 'HH:mm");
                            String dateString = simpleDateFormat.format(date);

                            JSONObject main = jsonObject.getJSONObject("main");
                            JSONObject coord = jsonObject.getJSONObject("coord");
                            int Temp = (int) (main.getDouble("temp") - 273.15);
                            int TempMin = (int) (main.getDouble("temp_min") - 273.15);
                            int TempMax = (int) (main.getDouble("temp_max") - 273.15);
                            int Pressure = (int) (main.getDouble("pressure"));
                            int Humidite = (int) (main.getDouble("humidity"));
                            double lon = (coord.getDouble("lon"));
                            double lat = (coord.getDouble("lat"));

                            JSONArray weather = jsonObject.getJSONArray("weather");
                            String weatherApp = weather.getJSONObject(0).getString("main");

                            txtDate.setText(dateString);
                            tmp.setText(String.valueOf(Temp + "°C"));
                            tmpMin.setText(String.valueOf(TempMin) + "°C");
                            tmpMax.setText(String.valueOf(TempMax) + "°C");
                            txtHumidity.setText(String.valueOf(Humidite) + "%");

                            Log.i("Weather", "--------------------------------");
                            Log.i("weatherApp", weatherApp);
                            setImage(weatherApp);
                            Toast.makeText(co, weatherApp, Toast.LENGTH_LONG).show();

                            FloatingActionButton fab = findViewById(R.id.fab);
                            fab.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent myIntent = new Intent(MainActivity.this, MapsActivity.class);
                                    myIntent.putExtra("key", ville.getText());
                                    longitude = String.valueOf(lon);
                                    latitude = String.valueOf(lat);
                                    myIntent.putExtra("longitude", longitude);
                                    myIntent.putExtra("latitude", latitude);
                                    MainActivity.this.startActivity(myIntent);

                                }
                            });


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.i("Log", "********** Unable to connect **********");
                                Toast.makeText(MainActivity.this,
                                        "City not found", Toast.LENGTH_LONG).show();


                            }
                        });

                queue.add(stringRequest);


                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {


                return false;
            }
        });

        return true;
    }

    public void setImage(String s) {
        ImageView imgview = findViewById(R.id.img);
        if (s.equals("Rain")) {
            imgview.setImageResource(R.drawable.rainy);
        } else if (s.equals("Clear")) {
            imgview.setImageResource(R.drawable.sunny);
        } else if (s.equals("Thunderstorm")) {
            imgview.setImageResource(R.drawable.thunderstorm);
        } else if (s.equals("Clouds")) {
            imgview.setImageResource(R.drawable.atmospheric);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
