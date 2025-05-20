package com.example.androidproyectounivalle.activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproyectounivalle.R;
import com.example.androidproyectounivalle.adapters.IntermedioAdapter;
import com.example.androidproyectounivalle.models.Item;

import java.util.ArrayList;
import java.util.List;

public class EjemploIntermedioActivity extends AppCompatActivity implements IntermedioAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private IntermedioAdapter adapter;
    private List<Item> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejemplo_intermedio);

        recyclerView = findViewById(R.id.recyclerView);

        // Inicializar la lista de items
        initItems();

        // Configurar el RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new IntermedioAdapter(items, this);
        recyclerView.setAdapter(adapter);
    }

    private void initItems() {
        items = new ArrayList<>();

        // Agregar algunos elementos de ejemplo
        items.add(new Item("Elemento 1", "Descripción detallada del elemento 1", R.drawable.ic_launcher_foreground, true));
        items.add(new Item("Elemento 2", "Descripción detallada del elemento 2", R.drawable.ic_launcher_foreground, false));
        items.add(new Item("Elemento 3", "Descripción detallada del elemento 3", R.drawable.ic_launcher_foreground, true));
        items.add(new Item("Elemento 4", "Descripción detallada del elemento 4", R.drawable.ic_launcher_foreground, false));
        items.add(new Item("Elemento 5", "Descripción detallada del elemento 5", R.drawable.ic_launcher_foreground, true));
        items.add(new Item("Elemento 6", "Descripción detallada del elemento 6", R.drawable.ic_launcher_foreground, false));
        items.add(new Item("Elemento 7", "Descripción detallada del elemento 7", R.drawable.ic_launcher_foreground, true));
    }

    @Override
    public void onItemClick(Item item, int position) {
        Toast.makeText(this, "Seleccionaste: " + item.getTitle(), Toast.LENGTH_SHORT).show();
    }
}
