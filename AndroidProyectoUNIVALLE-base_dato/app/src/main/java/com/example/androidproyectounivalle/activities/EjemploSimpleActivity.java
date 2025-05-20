package com.example.androidproyectounivalle.activities;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidproyectounivalle.R;
import com.example.androidproyectounivalle.adapters.SimpleAdapter;
import com.example.androidproyectounivalle.models.Item;

import java.util.ArrayList;
import java.util.List;

public class EjemploSimpleActivity extends AppCompatActivity {

    private ListView listView;
    private SimpleAdapter adapter;
    private List<Item> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejemplo_simple);

        listView = findViewById(R.id.listView);

        // Inicializar la lista de items
        initItems();

        // Configurar el adaptador
        adapter = new SimpleAdapter(this, items);
        listView.setAdapter(adapter);
    }

    private void initItems() {
        items = new ArrayList<>();

        // Agregar algunos elementos de ejemplo
        items.add(new Item("Item 1", "Descripción del item 1", R.drawable.ic_launcher_foreground, true));
        items.add(new Item("Item 2", "Descripción del item 2", R.drawable.ic_launcher_foreground, false));
        items.add(new Item("Item 3", "Descripción del item 3", R.drawable.ic_launcher_foreground, true));
        items.add(new Item("Item 4", "Descripción del item 4", R.drawable.ic_launcher_foreground, false));
        items.add(new Item("Item 5", "Descripción del item 5", R.drawable.ic_launcher_foreground, true));
    }
}
