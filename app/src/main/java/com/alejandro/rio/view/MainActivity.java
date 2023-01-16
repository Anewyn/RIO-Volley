package com.alejandro.rio.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alejandro.rio.R;
import com.alejandro.rio.model.Guild;
import com.alejandro.rio.model.PJ;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
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
    private ImageView profilePicture, achievementIcon, honorableKillsIcon, raceIcon, guildIcon;
    private TextView characterNameTV, characterClassTV, characterSpecTV, characterRoleTV, achievementPointsTV,
            honorableKillsTV, guildNameTV, raceTV, classFieldTV, specFieldTV, roleFieldTV;
    BottomNavigationView bottomNavigationView;
    private ProgressBar loadingPB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setup();
        hideUI();
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
        loadingPB = findViewById(R.id.main_activity_progress_bar);
        classFieldTV = findViewById(R.id.activity_main_class_field);
        specFieldTV = findViewById(R.id.activity_main_spec_field);
        roleFieldTV = findViewById(R.id.activity_main_role_field);
        achievementIcon = findViewById(R.id.activity_main_character_achievement_points_icon);
        honorableKillsIcon = findViewById(R.id.activity_main_honorable_kills_icon);
        raceIcon = findViewById(R.id.activity_main_character_race_icon);
        guildIcon = findViewById(R.id.activity_main_character_guild_icon);
        requestQueue = Volley.newRequestQueue(this);
    }

    private void hideUI() {
        profilePicture.setVisibility(View.GONE);
        characterNameTV.setVisibility(View.GONE);
        characterClassTV.setVisibility(View.GONE);
        characterSpecTV.setVisibility(View.GONE);
        characterRoleTV.setVisibility(View.GONE);
        achievementPointsTV.setVisibility(View.GONE);
        honorableKillsTV.setVisibility(View.GONE);
        guildNameTV.setVisibility(View.GONE);
        raceTV.setVisibility(View.GONE);
        classFieldTV.setVisibility(View.GONE);
        roleFieldTV.setVisibility(View.GONE);
        specFieldTV.setVisibility(View.GONE);
        achievementIcon.setVisibility(View.GONE);
        honorableKillsIcon.setVisibility(View.GONE);
        raceIcon.setVisibility(View.GONE);
        guildIcon.setVisibility(View.GONE);
    }

    private void showUI() {
        profilePicture.setVisibility(View.VISIBLE);
        characterNameTV.setVisibility(View.VISIBLE);
        characterClassTV.setVisibility(View.VISIBLE);
        characterSpecTV.setVisibility(View.VISIBLE);
        characterRoleTV.setVisibility(View.VISIBLE);
        achievementPointsTV.setVisibility(View.VISIBLE);
        honorableKillsTV.setVisibility(View.VISIBLE);
        guildNameTV.setVisibility(View.VISIBLE);
        raceTV.setVisibility(View.VISIBLE);
        classFieldTV.setVisibility(View.VISIBLE);
        roleFieldTV.setVisibility(View.VISIBLE);
        specFieldTV.setVisibility(View.VISIBLE);
        achievementIcon.setVisibility(View.VISIBLE);
        honorableKillsIcon.setVisibility(View.VISIBLE);
        raceIcon.setVisibility(View.VISIBLE);
        guildIcon.setVisibility(View.VISIBLE);
    }

    private void jsonObjectWOWRequest() {
        final String URL_OBJECT = "https://raider.io/api/v1/characters/profile?region=eu&realm=sanguino&name=anewyn&fields=guild";
        loadingPB.setVisibility(View.VISIBLE);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL_OBJECT, null,
                response -> {
                    try {
                        if (response != null) {
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
                            Log.d("RIO", guildName);
                            guildNameTV.setText(guildName);
                            imageRequest(response.getString("thumbnail_url"));
                            loadingPB.setVisibility(View.GONE);
                            showUI();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
            if (error.networkResponse.statusCode == 400) {
                Log.i("Volley", "Error 400");
                try {
                    Log.i("API", new String(error.networkResponse.data, StandardCharsets.UTF_8));
                    Toast.makeText(MainActivity.this, "No se ha podido encontrar el personaje en ese servidor", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (error instanceof ServerError) {
                Log.i("Volley", "Error del servidor");
                Toast.makeText(MainActivity.this, "Error del servidor", Toast.LENGTH_LONG).show();

            }
            if (error instanceof NoConnectionError) {
                Log.i("Volley", "Error, no hay conexión a internet");
                Toast.makeText(MainActivity.this, "Sin conexión a internet", Toast.LENGTH_LONG).show();
            }
            if (error instanceof NetworkError) { //Salta cuando no hay conexión a internet, cuando hay problemas de DNS o cuando falla el socket
                Log.i("Volley", "Ha ocurrido un error de red, revisa la conexión a internet");
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    private void imageRequest(String imageURL) {
        ImageRequest imageRequest = new ImageRequest(imageURL,
                response -> {
                    if (response != null) {
                        profilePicture.setImageBitmap(response);
                    }
                }, 150, 150, null, null,
                error -> {
                    if (error instanceof ServerError) {
                        Log.i("Volley", "Error del servidor");
                        Toast.makeText(MainActivity.this, "Error del servidor", Toast.LENGTH_LONG).show();

                    }
                    if (error instanceof NoConnectionError) {
                        Log.i("Volley", "Error, no hay conexión a internet");
                        Toast.makeText(MainActivity.this, "Sin conexión a internet", Toast.LENGTH_LONG).show();
                    }
                    if (error instanceof NetworkError) { //Salta cuando no hay conexión a internet, cuando hay problemas de DNS o cuando falla el socket
                        Log.i("Volley", "Ha ocurrido un error de red, revisa la conexión a internet");
                        Toast.makeText(MainActivity.this, "Ha ocurrido un error de red, revisa la conexión a internet", Toast.LENGTH_LONG).show();
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