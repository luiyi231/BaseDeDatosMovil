package com.example.androidproyectounivalle.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproyectounivalle.R;
import com.example.androidproyectounivalle.adapters.ItemCrudAdapter;
import com.example.androidproyectounivalle.models.Item;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class ItemCrudActivity extends AppCompatActivity implements ItemCrudAdapter.OnItemCrudListener {

    private RecyclerView rvItems;
    private ItemCrudAdapter adapter;
    private List<Item> items;

    private TextInputEditText etTitle;
    private TextInputEditText etDescription;
    private CheckBox cbActive;
    private Button btnSave;
    private Button btnClear;
    private FloatingActionButton fabAdd;
    private TextView tvFormTitle;

    private int editingPosition = -1; // -1 significa que estamos creando un nuevo item

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_crud);

        // Inicializar lista de items
        items = new ArrayList<>();
        loadSampleItems();

        // Inicializar vistas
        rvItems = findViewById(R.id.rvItems);
        etTitle = findViewById(R.id.etTitle);
        etDescription = findViewById(R.id.etDescription);
        cbActive = findViewById(R.id.cbActive);
        btnSave = findViewById(R.id.btnSave);
        btnClear = findViewById(R.id.btnClear);
        fabAdd = findViewById(R.id.fabAdd);
        tvFormTitle = findViewById(R.id.tvFormTitle);

        // Configurar RecyclerView
        rvItems.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ItemCrudAdapter(items, this, this);
        rvItems.setAdapter(adapter);

        // Configurar listeners
        btnSave.setOnClickListener(v -> saveItem());
        btnClear.setOnClickListener(v -> clearForm());
        fabAdd.setOnClickListener(v -> showForm());
    }

    private void loadSampleItems() {
        // Cargar datos de ejemplo
        items.add(new Item("Producto Premium", "Producto de alta calidad disponible para envío inmediato",
                R.drawable.ic_launcher_foreground, true));
        items.add(new Item("Producto Básico", "Producto estándar con buena relación calidad-precio",
                R.drawable.ic_launcher_foreground, false));
        items.add(new Item("Producto Exclusivo", "Edición limitada con características especiales",
                R.drawable.ic_launcher_foreground, true));
    }

    private void saveItem() {
        String title = etTitle.getText().toString().trim();
        String description = etDescription.getText().toString().trim();
        boolean isActive = cbActive.isChecked();

        if (TextUtils.isEmpty(title)) {
            etTitle.setError("El título es obligatorio");
            return;
        }

        if (TextUtils.isEmpty(description)) {
            etDescription.setError("La descripción es obligatoria");
            return;
        }

        // Crear nuevo item o actualizar existente
        Item item = new Item(title, description, R.drawable.ic_launcher_foreground, isActive);

        if (editingPosition == -1) {
            // Agregar nuevo item
            adapter.addItem(item);
            Toast.makeText(this, "Item agregado con éxito", Toast.LENGTH_SHORT).show();
        } else {
            // Actualizar item existente
            adapter.updateItem(item, editingPosition);
            Toast.makeText(this, "Item actualizado con éxito", Toast.LENGTH_SHORT).show();
        }

        // Limpiar formulario
        clearForm();
    }

    private void clearForm() {
        etTitle.setText("");
        etDescription.setText("");
        cbActive.setChecked(false);
        editingPosition = -1;
        tvFormTitle.setText("Nuevo Item");
    }

    private void showForm() {
        // Asegurarse de que el formulario esté visible y en modo de creación
        clearForm();
    }

    @Override
    public void onEditClick(Item item, int position) {
        // Cargar datos del item en el formulario
        etTitle.setText(item.getTitle());
        etDescription.setText(item.getDescription());
        cbActive.setChecked(item.isActive());
        editingPosition = position;
        tvFormTitle.setText("Editar Item");
    }

    @Override
    public void onDeleteClick(Item item, int position) {
        // Mostrar diálogo de confirmación
        new AlertDialog.Builder(this)
                .setTitle("Eliminar Item")
                .setMessage("¿Estás seguro de que quieres eliminar este item?")
                .setPositiveButton("Eliminar", (dialog, which) -> {
                    adapter.removeItem(position);
                    Toast.makeText(this, "Item eliminado", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    @Override
    public void onItemClick(Item item, int position) {
        // Al hacer clic en un item, cargarlo en el formulario para edición
        onEditClick(item, position);
    }
}
