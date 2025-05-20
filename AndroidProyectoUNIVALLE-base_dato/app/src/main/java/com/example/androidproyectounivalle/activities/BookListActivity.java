package com.example.androidproyectounivalle.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproyectounivalle.R;
import com.example.androidproyectounivalle.adapters.BookAdapter;
import com.example.androidproyectounivalle.data.entity.BookWithAuthorAndPublisher;
import com.example.androidproyectounivalle.repository.LibraryRepository;

import java.util.ArrayList;
import java.util.List;

public class BookListActivity extends AppCompatActivity implements BookAdapter.OnBookListener {
    private RecyclerView recyclerView;
    private BookAdapter adapter;
    private LibraryRepository repository;
    private List<BookWithAuthorAndPublisher> books = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        repository = new LibraryRepository(this);
        setupRecyclerView();

        // Primero insertar datos iniciales y luego cargar los libros
        repository.insertInitialData(() -> {
            try {
                Thread.sleep(500); // Pequeña pausa para asegurar que los datos se guarden
                loadBooks();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.recyclerViewBooks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BookAdapter(books, this);
        recyclerView.setAdapter(adapter);
    }

    private void loadBooks() {
        new Thread(() -> {
            List<BookWithAuthorAndPublisher> result = repository.getBooksWithAuthorAndPublisher();
            runOnUiThread(() -> {
                books.clear();
                books.addAll(result);
                adapter.notifyDataSetChanged();
            });
        }).start();
    }

    public void onAddBookClick(View view) {
        startActivity(new Intent(this, BookFormActivity.class));
    }

    @Override
    public void onEditClick(int position) {
        BookWithAuthorAndPublisher book = books.get(position);
        Intent intent = new Intent(this, BookFormActivity.class);
        intent.putExtra("book_id", book.book.getId());
        startActivity(intent);
    }

    @Override
    public void onDeleteClick(int position) {
        BookWithAuthorAndPublisher book = books.get(position);
        new AlertDialog.Builder(this)
                .setTitle("Eliminar Libro")
                .setMessage("¿Estás seguro de que deseas eliminar este libro?")
                .setPositiveButton("Eliminar", (dialog, which) -> {
                    new Thread(() -> {
                        repository.deleteBook(book.book);
                        runOnUiThread(() -> {
                            loadBooks();
                            Toast.makeText(BookListActivity.this, "Libro eliminado", Toast.LENGTH_SHORT).show();
                        });
                    }).start();
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadBooks();
    }
}