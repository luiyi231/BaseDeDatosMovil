package com.example.androidproyectounivalle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.androidproyectounivalle.activities.BookFormActivity;
import com.example.androidproyectounivalle.activities.BookListActivity;
import com.example.androidproyectounivalle.activities.EjemploAvanzadoActivity;
import com.example.androidproyectounivalle.activities.EjemploIntermedioActivity;
import com.example.androidproyectounivalle.activities.EjemploSimpleActivity;
import com.example.androidproyectounivalle.activities.ExamplePostActivity;
import com.example.androidproyectounivalle.activities.ItemCrudActivity;
import com.example.androidproyectounivalle.activities.NoteCrudActivity;
import com.example.androidproyectounivalle.activities.PokemonApiActivity;
import com.example.androidproyectounivalle.adapters.MenuAdapter;
import com.example.androidproyectounivalle.models.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private GridView gridViewMenu;
    private MenuAdapter menuAdapter;
    private List<MenuItem> menuItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicializar GridView
        gridViewMenu = findViewById(R.id.gridViewMenu);

        // Crear elementos del menú
        initMenuItems();

        // Configurar adaptador
        menuAdapter = new MenuAdapter(this, menuItems);
        gridViewMenu.setAdapter(menuAdapter);

        // Configurar evento de clic
        gridViewMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Manejar la selección del menú
                handleMenuSelection(position);
            }
        });
    }

    private void initMenuItems() {
        menuItems = new ArrayList<>();

        // Agregar opciones del menú
        menuItems.add(new MenuItem("Ejemplo Simple", android.R.drawable.ic_menu_view));
        menuItems.add(new MenuItem("Ejemplo Intermedio", android.R.drawable.ic_menu_sort_by_size));
        menuItems.add(new MenuItem("Ejemplo Avanzado", android.R.drawable.ic_menu_slideshow));
        menuItems.add(new MenuItem("Pokémon API", android.R.drawable.ic_menu_compass));
        menuItems.add(new MenuItem("POST API", android.R.drawable.ic_menu_compass));
        menuItems.add(new MenuItem("CRUD Items", android.R.drawable.ic_menu_edit));
        menuItems.add(new MenuItem("Room Database", android.R.drawable.ic_menu_save));

        menuItems.add(new MenuItem("Book Form", android.R.drawable.ic_menu_save));
        menuItems.add(new MenuItem("Book List", android.R.drawable.ic_menu_save));


        // Reservamos espacio para opciones futuras
        // menuItems.add(new MenuItem("Opción Futura 1", android.R.drawable.ic_menu_search));
    }

    private void handleMenuSelection(int position) {
        Intent intent = null;

        switch (position) {
            case 0: // Ejemplo Simple
                intent = new Intent(this, EjemploSimpleActivity.class);
                break;
            case 1: // Ejemplo Intermedio
                intent = new Intent(this, EjemploIntermedioActivity.class);
                break;
            case 2: // Ejemplo Avanzado
                intent = new Intent(this, EjemploAvanzadoActivity.class);
                break;
            case 3: // Pokémon API
                intent = new Intent(this, PokemonApiActivity.class);
                break;
            case 4: // Post API
                intent = new Intent(this, ExamplePostActivity.class);
                break;
            case 5: // CRUD Items
                intent = new Intent(this, ItemCrudActivity.class);
                break;
            case 6: // Room Database
                intent = new Intent(this, NoteCrudActivity.class);
                break;
            case 7: // Room Database
                intent = new Intent(this, BookFormActivity.class);
                break;
            case 8: // Room Database
                intent = new Intent(this, BookListActivity.class);
                break;
            default:
                Toast.makeText(this, "Opción no implementada aún", Toast.LENGTH_SHORT).show();
                return;
        }

        if (intent != null) {
            startActivity(intent);
        }
    }
}
