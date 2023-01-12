package com.alejandro.rio.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alejandro.rio.R;
import com.alejandro.rio.model.Guild;
import com.alejandro.rio.model.PJ;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {
    private RequestQueue requestQueue;
    private ImageView profilePicture;
    private TextView characterNameTV, characterClassTV, characterSpecTV, characterRoleTV, achievementPointsTV,
            honorableKillsTV, guildNameTV, raceTV;
    BottomNavigationView bottomNavigationView;

//    private PJ pj = new PJ();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setup();
//        sendGetRequests();
//        setProfileData();
        jsonObjectWOWRequest();
    }

    private void setup() {
        setUpNavBar();
        profilePicture = findViewById(R.id.activity_main_profile_picture);
        characterNameTV = findViewById(R.id.activity_main_character_name);
        characterClassTV = findViewById(R.id.activity_main_character_class);
        characterSpecTV = findViewById(R.id.activity_main_character_spec);
        characterRoleTV = findViewById(R.id.activity_main_character_role);
        achievementPointsTV = findViewById(R.id.activity_main_character_achievement_points);
        honorableKillsTV = findViewById(R.id.activity_main_honorable_kills);
        guildNameTV = findViewById(R.id.activity_main_character_guild);
        raceTV = findViewById(R.id.activity_main_character_race);
        requestQueue = Volley.newRequestQueue(this);
    }

    private void jsonObjectWOWRequest() {
        final String URL_OBJECT = "https://raider.io/api/v1/characters/profile?region=eu&realm=sanguino&name=anewyn&fields=guild";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL_OBJECT, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String name = response.getString("name");
                            Log.d("RIO", name);
                            characterNameTV.setText(name);
                            String race = response.getString("race");
                            Log.d("RIO", race);
                            raceTV.setText(race);
                            String pjClass = response.getString("class");
                            Log.d("RIO", pjClass);
                            characterClassTV.setText(pjClass);
                            String spec = response.getString("active_spec_name");
                            Log.d("RIO", spec);
                            characterSpecTV.setText(spec);
                            String role = response.getString("active_spec_role");
                            Log.d("RIO", role);
                            characterRoleTV.setText(role);
                            String achievementPoints = response.getString("achievement_points");
                            Log.d("RIO", achievementPoints);
                            achievementPointsTV.setText(achievementPoints);
                            String honorableKills = response.getString("honorable_kills");
                            Log.d("RIO", honorableKills);
                            honorableKillsTV.setText(honorableKills);
                            String guildName = response.getJSONObject("guild").getString("name");
//                            String guildName = response.getString("gender");
                            Log.d("RIO",guildName);
                            guildNameTV.setText(guildName);
                            imageRequest(response.getString("thumbnail_url"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.networkResponse.statusCode == 400) {
                    Log.i("Volley", "Error 400");
                    try {
                        Log.i("API", new String(error.networkResponse.data, StandardCharsets.UTF_8));
                        Toast.makeText(MainActivity.this, "No se ha podido encontrar el personaje en ese servidor", Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
    private void imageRequest(String imageURL) {
        ImageRequest imageRequest = new ImageRequest(imageURL,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        profilePicture.setImageBitmap(response);

                    }
                }, 150, 150, null, null,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(imageRequest);
    }

    private void setUpNavBar() {
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.get);

        bottomNavigationView.setOnItemReselectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.get:
                    break;
                case R.id.post:
                    startActivity(new Intent(MainActivity.this, PostActivity.class));
                    finish();
                    overridePendingTransition(0, 0);
                    break;
            }
        });
    }
}