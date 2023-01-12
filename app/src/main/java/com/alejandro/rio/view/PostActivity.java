package com.alejandro.rio.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alejandro.rio.R;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PostActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    private EditText nameEdt, jobEdt;
    private Button registerBtn;
    private TextView apiResponseTV;
    private ProgressBar loadingPB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        setup();
    }

    private void setup() {
        setUpNavBar();
        nameEdt = findViewById(R.id.post_activity_content1);
        jobEdt = findViewById(R.id.post_activity_content2);
        registerBtn = findViewById(R.id.post_activity_send_button);
        apiResponseTV = findViewById(R.id.post_activity_response_field);
        loadingPB = findViewById(R.id.post_activity_progress_bar);

        registerBtn.setOnClickListener(v -> {
            if (nameEdt.getText().toString().isBlank() || jobEdt.getText().toString().isBlank()) {
                Toast.makeText(PostActivity.this, "No dejes nada en blanco", Toast.LENGTH_SHORT).show();
            } else {
                sendPostRequest(nameEdt.getText().toString().trim(), jobEdt.getText().toString().trim());
            }
        });
    }

    private void sendPostRequest(String name, String job) {
        // https://reqres.in/
        final String URL = "https://my-json-server.typicode.com/typicode/demo/posts";
        loadingPB.setVisibility(View.VISIBLE);

        RequestQueue requestQueue = Volley.newRequestQueue(PostActivity.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loadingPB.setVisibility(View.GONE);
                        nameEdt.setText("");
                        jobEdt.setText("");
                        Toast.makeText(PostActivity.this, "Usuario registrado", Toast.LENGTH_SHORT).show();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String responseUserNameId = jsonObject.getString("id");
                            Log.d("REVOPOST",responseUserNameId);
                            apiResponseTV.setText("Usuario registrado con ID "+responseUserNameId);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley POST", error.toString());
                Toast.makeText(PostActivity.this, "Fallo al realizar la petición ", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("title",name);
//                params.put("job",job);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void setUpNavBar() {
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.post);

        bottomNavigationView.setOnItemReselectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.get:
                    startActivity(new Intent(PostActivity.this, MainActivity.class));
                    finish();
                    overridePendingTransition(0, 0);
                    break;
                case R.id.post:
                    break;
            }
        });
    }
}