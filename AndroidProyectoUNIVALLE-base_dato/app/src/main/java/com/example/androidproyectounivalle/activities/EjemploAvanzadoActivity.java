package com.example.androidproyectounivalle.activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproyectounivalle.R;
import com.example.androidproyectounivalle.adapters.AvanzadoAdapter;
import com.example.androidproyectounivalle.models.Item;

import java.util.ArrayList;
import java.util.List;

public class EjemploAvanzadoActivity extends AppCompatActivity implements AvanzadoAdapter.OnItemActionListener {

    private RecyclerView recyclerView;
    private AvanzadoAdapter adapter;
    private List<Item> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejemplo_avanzado);

        recyclerView = findViewById(R.id.recyclerView);

        // Inicializar la lista de items
        initItems();

        // Configurar el RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AvanzadoAdapter(items, this);
        recyclerView.setAdapter(adapter);
    }

    private void initItems() {
        items = new ArrayList<>();

        // Agregar algunos elementos de ejemplo con distintos estados
        items.add(new Item("Producto Premium", "Producto de alta calidad disponible para envío inmediato",
                R.drawable.ic_launcher_foreground, true));
        items.add(new Item("Producto Básico", "Producto estándar con buena relación calidad-precio",
                R.drawable.ic_launcher_foreground, false));
        items.add(new Item("Producto Exclusivo", "Edición limitada con características especiales",
                R.drawable.ic_launcher_foreground, true));
        items.add(new Item("Producto Especial", "Producto con funcionalidades adicionales",
                R.drawable.ic_launcher_foreground, false));
        items.add(new Item("Producto Económico", "La opción más asequible de nuestra gama",
                R.drawable.ic_launcher_foreground, true));
        items.add(new Item("Producto Profesional", "Diseñado para uso intensivo en entornos exigentes",
                R.drawable.ic_launcher_foreground, false));
    }

    @Override
    public void onItemClick(Item item, int position) {
        Toast.makeText(this, "Seleccionaste: " + item.getTitle(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActionButtonClick(Item item, int position) {
        // Cambiamos el estado del item y notificamos al adaptador
        item.setActive(!item.isActive());
        adapter.notifyItemChanged(position);

        String mensaje = item.isActive() ? "Activado: " : "Desactivado: ";
        Toast.makeText(this, mensaje + item.getTitle(), Toast.LENGTH_SHORT).show();
    }
}
