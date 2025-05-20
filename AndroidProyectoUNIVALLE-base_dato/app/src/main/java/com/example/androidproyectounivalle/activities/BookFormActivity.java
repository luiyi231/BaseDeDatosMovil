package com.example.androidproyectounivalle.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidproyectounivalle.R;
import com.example.androidproyectounivalle.data.entity.Author;
import com.example.androidproyectounivalle.data.entity.Book;
import com.example.androidproyectounivalle.data.entity.Publisher;
import com.example.androidproyectounivalle.repository.LibraryRepository;

import java.util.ArrayList;
import java.util.List;

public class BookFormActivity extends AppCompatActivity {
    private EditText etTitle, etIsbn, etYear, etPrice;
    private Spinner spAuthor, spPublisher;
    private Button btnSave;
    private LibraryRepository repository;
    private List<Author> authors = new ArrayList<>();
    private List<Publisher> publishers = new ArrayList<>();
    private Book currentBook = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_form);

        repository = new LibraryRepository(this);
        initViews();
        loadData();
    }
    private void loadBookData(int bookId) {
        new Thread(() -> {
            try {
                Book book = repository.getBookById(bookId);
                if (book != null) {
                    currentBook = book;
                    runOnUiThread(() -> {
                        etTitle.setText(book.getTitle());
                        etIsbn.setText(book.getIsbn());
                        etYear.setText(String.valueOf(book.getPublicationYear()));
                        etPrice.setText(String.valueOf(book.getPrice()));

                        // Seleccionar el autor en el spinner
                        for (int i = 0; i < authors.size(); i++) {
                            if (authors.get(i).getId() == book.getAuthorId()) {
                                spAuthor.setSelection(i);
                                break;
                            }
                        }

                        // Seleccionar la editorial en el spinner
                        for (int i = 0; i < publishers.size(); i++) {
                            if (publishers.get(i).getId() == book.getPublisherId()) {
                                spPublisher.setSelection(i);
                                break;
                            }
                        }
                    });
                }
            } catch (Exception e) {
                runOnUiThread(() ->
                        Toast.makeText(this, "Error al cargar el libro: " + e.getMessage(),
                                Toast.LENGTH_SHORT).show()
                );
            }
        }).start();
    }

    private void initViews() {
        etTitle = findViewById(R.id.etBookTitle);
        etIsbn = findViewById(R.id.etBookIsbn);
        etYear = findViewById(R.id.etBookYear);
        etPrice = findViewById(R.id.etBookPrice);
        spAuthor = findViewById(R.id.spBookAuthor);
        spPublisher = findViewById(R.id.spBookPublisher);
        btnSave = findViewById(R.id.btnSaveBook);

        btnSave.setOnClickListener(v -> onSaveClick());
    }

    private void loadData() {
        // Asegurarse de que los datos iniciales existan
        repository.insertInitialData(() -> {
            try {
                Thread.sleep(500); // Pequeña pausa para asegurar que los datos se guarden
                new Thread(() -> {
                    try {
                        authors = repository.getAllAuthors();
                        publishers = repository.getAllPublishers();

                        if (authors.isEmpty() || publishers.isEmpty()) {
                            runOnUiThread(() -> {
                                Toast.makeText(this, "Error al cargar datos. Intente nuevamente.",
                                        Toast.LENGTH_LONG).show();
                                finish();
                            });
                            return;
                        }

                        runOnUiThread(() -> {
                            setupSpinners();
                            if (getIntent().hasExtra("book_id")) {
                                int bookId = getIntent().getIntExtra("book_id", -1);
                                loadBookData(bookId);
                            }
                        });
                    } catch (Exception e) {
                        runOnUiThread(() -> {
                            Toast.makeText(this, "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                            finish();
                        });
                    }
                }).start();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    private void setupSpinners() {
        ArrayAdapter<Author> authorAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, authors);
        authorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spAuthor.setAdapter(authorAdapter);

        ArrayAdapter<Publisher> publisherAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, publishers);
        publisherAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPublisher.setAdapter(publisherAdapter);
    }

    private void onSaveClick() {
        if (!validateForm()) {
            return;
        }

        try {
            String title = etTitle.getText().toString().trim();
            String isbn = etIsbn.getText().toString().trim();
            int year = Integer.parseInt(etYear.getText().toString().trim());
            float price = Float.parseFloat(etPrice.getText().toString().trim());
            Author selectedAuthor = (Author) spAuthor.getSelectedItem();
            Publisher selectedPublisher = (Publisher) spPublisher.getSelectedItem();

            if (selectedAuthor == null || selectedPublisher == null) {
                Toast.makeText(this, "Seleccione autor y editorial", Toast.LENGTH_SHORT).show();
                return;
            }

            new Thread(() -> {
                try {
                    if (currentBook == null) {
                        Book newBook = new Book(title, isbn, year, price,
                                selectedAuthor.getId(), selectedPublisher.getId());
                        long result = repository.insertBook(newBook);
                        if (result > 0) {
                            runOnUiThread(() -> {
                                Toast.makeText(this, "Libro guardado", Toast.LENGTH_SHORT).show();
                                finish();
                            });
                        }
                    } else {
                        currentBook.setTitle(title);
                        currentBook.setIsbn(isbn);
                        currentBook.setPublicationYear(year);
                        currentBook.setPrice(price);
                        currentBook.setAuthorId(selectedAuthor.getId());
                        currentBook.setPublisherId(selectedPublisher.getId());
                        repository.updateBook(currentBook);
                        runOnUiThread(() -> {
                            Toast.makeText(this, "Libro actualizado", Toast.LENGTH_SHORT).show();
                            finish();
                        });
                    }
                } catch (Exception e) {
                    runOnUiThread(() ->
                            Toast.makeText(this, "Error al guardar: " + e.getMessage(),
                                    Toast.LENGTH_SHORT).show());
                }
            }).start();
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Formato de número inválido", Toast.LENGTH_SHORT).show();
        }
    }


    private boolean validateForm() {
        boolean isValid = true;

        if (etTitle.getText().toString().trim().isEmpty()) {
            etTitle.setError("El título es requerido");
            isValid = false;
        }

        if (etIsbn.getText().toString().trim().isEmpty()) {
            etIsbn.setError("El ISBN es requerido");
            isValid = false;
        }

        if (etYear.getText().toString().trim().isEmpty()) {
            etYear.setError("El año es requerido");
            isValid = false;
        }

        if (etPrice.getText().toString().trim().isEmpty()) {
            etPrice.setError("El precio es requerido");
            isValid = false;
        }

        return isValid;
    }

    private void setupAuthorSpinner() {
        ArrayAdapter<Author> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, authors);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spAuthor.setAdapter(adapter);
    }

    private void setupPublisherSpinner() {
        ArrayAdapter<Publisher> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, publishers);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPublisher.setAdapter(adapter);
    }
}