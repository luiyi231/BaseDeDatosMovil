package com.example.androidproyectounivalle.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.androidproyectounivalle.R;
import com.example.androidproyectounivalle.adapters.PokemonAdapter;
import com.example.androidproyectounivalle.models.Pokemon;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PokemonApiActivity extends AppCompatActivity implements PokemonAdapter.OnPokemonClickListener {

    private RecyclerView rvPokemon;
    private PokemonAdapter adapter;
    private List<Pokemon> pokemonList;
    private ProgressBar progressBar;
    private Button btnCargar;
    private RequestQueue requestQueue;
    private final String BASE_URL = "https://pokeapi.co/api/v2/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_api);

        // Inicializar vistas
        rvPokemon = findViewById(R.id.rvPokemon);
        progressBar = findViewById(R.id.progressBar);
        btnCargar = findViewById(R.id.btnCargar);

        // Inicializar Volley Request Queue
        requestQueue = Volley.newRequestQueue(this);

        // Inicializar lista y adaptador
        pokemonList = new ArrayList<>();
        adapter = new PokemonAdapter(pokemonList, this, this);

        // Configurar RecyclerView
        rvPokemon.setLayoutManager(new LinearLayoutManager(this));
        rvPokemon.setAdapter(adapter);

        // Configurar botón de carga
        btnCargar.setOnClickListener(v -> cargarPokemones());
    }

    private void cargarPokemones() {
        // Mostrar ProgressBar
        progressBar.setVisibility(View.VISIBLE);

        // Limpiar lista actual
        pokemonList.clear();

        // Crear solicitud para obtener los primeros 20 Pokémon
        String url = BASE_URL + "pokemon?limit=20";

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    try {
                        JSONArray results = response.getJSONArray("results");

                        // Para cada resultado, obtener detalles del Pokémon
                        for (int i = 0; i < results.length(); i++) {
                            JSONObject pokemonData = results.getJSONObject(i);
                            String pokemonUrl = pokemonData.getString("url");
                            obtenerDetallesPokemon(pokemonUrl);
                        }
                    } catch (JSONException e) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(this, "Error al procesar datos: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(this, "Error de red: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
        );

        // Añadir solicitud a la cola
        requestQueue.add(request);
    }

    private void obtenerDetallesPokemon(String url) {
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    try {
                        // Extraer datos básicos
                        int id = response.getInt("id");
                        String name = response.getString("name");
                        String imageUrl = response.getJSONObject("sprites")
                                .getString("front_default");

                        // Obtener el primer tipo del Pokémon
                        JSONArray types = response.getJSONArray("types");
                        String type = types.getJSONObject(0)
                                .getJSONObject("type")
                                .getString("name");

                        // Crear objeto Pokémon y agregarlo a la lista
                        Pokemon pokemon = new Pokemon(id, name, imageUrl, type);
                        pokemonList.add(pokemon);

                        // Ordenar la lista por ID
                        pokemonList.sort((p1, p2) -> Integer.compare(p1.getId(), p2.getId()));

                        // Actualizar adaptador
                        adapter.notifyDataSetChanged();

                        // Ocultar ProgressBar si es el último Pokémon
                        if (pokemonList.size() >= 20) {
                            progressBar.setVisibility(View.GONE);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    error.printStackTrace();
                    // No ocultamos el progressBar aquí porque podría haber otras solicitudes en proceso
                }
        );

        // Añadir solicitud a la cola
        requestQueue.add(request);
    }

    @Override
    public void onPokemonClick(Pokemon pokemon, int position) {
        Toast.makeText(this, "Seleccionaste a " + pokemon.getName(), Toast.LENGTH_SHORT).show();
    }
}
