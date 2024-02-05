package com.siam.easy_ip;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    TextView ip, region, city, country, location, org, timezone, postal;
    ProgressBar progress_circular;
    LinearLayout layout;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ip = findViewById(R.id.ip);
        region = findViewById(R.id.region);
        city = findViewById(R.id.city);
        country = findViewById(R.id.country);
        location = findViewById(R.id.location);
        org = findViewById(R.id.org);
        timezone = findViewById(R.id.timeZone);
        postal = findViewById(R.id.postal);
        progress_circular = findViewById(R.id.progress_circular);
        layout = findViewById(R.id.Layout);

        IP();
    }
    private void IP(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://ipinfo.io/ip";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dataCollect(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "That didn't work!", Toast.LENGTH_SHORT).show();
                progress_circular.setVisibility(View.GONE);
                Log.d("TAGTAGTAGTAG", "onErrorResponse: "+error.toString());

            }
        });
        queue.add(stringRequest);
    }

    private void dataCollect(String response) {
        String url = "https://ipinfo.io/"+response+"?token=c79e99d0e5c9f5";
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        progress_circular.setVisibility(View.GONE);
                        layout.setVisibility(View.VISIBLE);

                        try {
                            String ip_address = response.getString("ip");
                            String city_l = response.getString("city");
                            String region_l = response.getString("region");
                            String country_l = response.getString("country");
                            String loc_l = response.getString("loc");
                            String org_l = response.getString("org");
                            String postal_l = response.getString("postal");
                            String timezone_l = response.getString("timezone");

                            ip.append(ip_address);
                            city.append(city_l);
                            region.append(region_l);
                            country.append(country_l);
                            location.append(loc_l);
                            org.append(org_l);
                            postal.append(postal_l);
                            timezone.append(timezone_l);

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Toast.makeText(MainActivity.this, "That didn't work dataCollect!", Toast.LENGTH_SHORT).show();
                        progress_circular.setVisibility(View.GONE);

                    }
                });
        queue.add(jsonObjectRequest);
    }
}