package com.example.androidproyectounivalle.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.androidproyectounivalle.R;

import org.json.JSONException;
import org.json.JSONObject;

public class ExamplePostActivity extends AppCompatActivity {

    private static final String TAG = "ExamplePostActivity";
    private static final String API_URL = "https://d6ab734e-086e-4513-9276-d6af8d21a59c.mock.pstmn.io/binance"; // Reemplazar con la URL real


    private RequestQueue requestQueue;
    private ProgressBar progressBar;
    private TextView tvResponse;
    private Button btnSendRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_example_post);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        requestQueue = Volley.newRequestQueue(this);

        progressBar = findViewById(R.id.progressBar);
        tvResponse = findViewById(R.id.tvResponse);
        btnSendRequest = findViewById(R.id.btnSendRequest);

        btnSendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendPostRequest();
            }
        });
    }

    private void sendPostRequest() {
        progressBar.setVisibility(View.VISIBLE);

        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("distance", 700);
            requestBody.put("pumps", 5);
            requestBody.put("fuelAmount", 100000);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST,
                    API_URL,
                    requestBody,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            progressBar.setVisibility(View.GONE);

                            tvResponse.setText("Respuesta: " + response.toString());
                            Log.d(TAG, "Respuesta exitosa: " + response.toString());
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressBar.setVisibility(View.GONE);

                            String errorMessage = "Error: " +
                                    (error.getMessage() != null ? error.getMessage() : "Desconocido");
                            tvResponse.setText(errorMessage);
                            Toast.makeText(ExamplePostActivity.this,
                                    errorMessage, Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "Error en la solicitud", error);
                        }
                    });

            requestQueue.add(jsonObjectRequest);

        } catch (JSONException e) {
            progressBar.setVisibility(View.GONE);
            Log.e(TAG, "Error al crear JSON", e);
            Toast.makeText(this, "Error al crear la solicitud", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Cancelar todas las solicitudes pendientes al salir
        if (requestQueue != null) {
            requestQueue.cancelAll(TAG);
        }
    }
}
