package ma.enset.IPLocation;

import android.app.SearchManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    TextView city;

    TextView txtIP;
    TextView txtCity;
    TextView txtRegion;
    TextView txtCountry;
    TextView txtLoc;
    TextView txtPostal;
    TextView txtTimezone;

    TextView txtHello;

    private SharedPreferences sharedPreferences;
    private static final String PREF_USERNAME = "username";

    private static final String PREFS_NAME = "MyPrefs";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        final Context co = this;

        city = findViewById(R.id.txtCity);
        txtIP = findViewById(R.id.ip);
        txtRegion = findViewById(R.id.region);
        txtCountry = findViewById(R.id.country);
        txtLoc = findViewById(R.id.loc);
        txtPostal = findViewById(R.id.postal);
        txtTimezone = findViewById(R.id.timezone);
        txtCity = findViewById(R.id.city);
        txtHello = findViewById(R.id.txtHello);
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String username = sharedPreferences.getString(PREF_USERNAME, null);
        if (username != null) {
            txtHello.setText("Welcome " + username);
        } else {
            txtHello.setText("Please provide your username");
        }


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                city.setText(query);
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url = "https://ipinfo.io/" + query + "/geo";


                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.i("MyLog", "----------------------------------------------");
                            Log.i("MyLog", response);

                            JSONObject jsonObject = new JSONObject(response);


                            String loc = jsonObject.getString("loc");
                            double lat = Double.parseDouble(loc.split(",")[0]);
                            double lon = Double.parseDouble(loc.split(",")[1]);

                            System.out.println("-----------------------------------");
                            Log.i("MyLog", String.valueOf(lat));
                            Log.i("MyLog", String.valueOf(lon));


                            // Set marker on the map
                            LatLng position = new LatLng(lat, lon);
                            mMap.addMarker(new MarkerOptions().position(position).title("Marker in Sydney"));
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 12));

                            String ip = jsonObject.getString("ip");
                            String city = jsonObject.getString("city");
                            String region = jsonObject.getString("region");
                            String country = jsonObject.getString("country");
                            String postal = jsonObject.getString("postal");
                            String timezone = jsonObject.getString("timezone");


                            txtIP.setText(String.valueOf(ip));
                            txtCity.setText(String.valueOf(city));
                            txtRegion.setText(String.valueOf(region));
                            txtCountry.setText(String.valueOf(country));
                            txtLoc.setText(String.valueOf(loc));
                            txtPostal.setText(String.valueOf(postal));
                            txtTimezone.setText(String.valueOf(timezone));


                            Log.i("Location", "----------------------------------------------");


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("MyLog", "-----------------Unable to connect-----------------");
                                Log.d("MyLog", error.getMessage());
                                Log.d("MyLog", "---------------------------------");
                                Toast.makeText(MainActivity.this,
                                        "City not fond ", Toast.LENGTH_LONG).show();
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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);

    }


}