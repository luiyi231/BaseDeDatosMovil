package com.example.androidproyectounivalle.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproyectounivalle.R;
import com.example.androidproyectounivalle.adapters.NoteAdapter;
import com.example.androidproyectounivalle.data.entity.Note;
import com.example.androidproyectounivalle.repository.NoteRepository;

import java.util.ArrayList;
import java.util.List;

public class NoteCrudActivity extends AppCompatActivity implements NoteAdapter.NoteListener {

    private RecyclerView recyclerViewNotes;
    private NoteAdapter noteAdapter;
    private List<Note> noteList;
    private NoteRepository noteRepository;

    private EditText etNoteTitle;
    private EditText etNoteContent;
    private Button btnSaveNote;

    private Note currentEditingNote = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_crud);

        // Inicializar el repositorio
        noteRepository = new NoteRepository(this);

        // Inicializar vistas
        recyclerViewNotes = findViewById(R.id.recyclerViewNotes);
        etNoteTitle = findViewById(R.id.etNoteTitle);
        etNoteContent = findViewById(R.id.etNoteContent);
        btnSaveNote = findViewById(R.id.btnSaveNote);

        // Configurar RecyclerView
        noteList = new ArrayList<>();
        noteAdapter = new NoteAdapter(noteList, this);
        recyclerViewNotes.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewNotes.setAdapter(noteAdapter);

        // Configurar botón de guardar
        btnSaveNote.setOnClickListener(v -> saveNote());

        // Cargar notas desde la base de datos
        loadNotes();
    }

    private void loadNotes() {
        new Thread(() -> {
            List<Note> notes = noteRepository.getAllNotes();
            runOnUiThread(() -> {
                noteList.clear();
                if (notes != null) {
                    noteList.addAll(notes);
                }
                noteAdapter.updateNotes(noteList);
            });
        }).start();
    }

    private void saveNote() {
        String title = etNoteTitle.getText().toString().trim();
        String content = etNoteContent.getText().toString().trim();

        if (TextUtils.isEmpty(title)) {
            Toast.makeText(this, "El título no puede estar vacío", Toast.LENGTH_SHORT).show();
            return;
        }

        new Thread(() -> {
            if (currentEditingNote == null) {
                // Crear nueva nota
                Note newNote = new Note(title, content);
                noteRepository.insertNote(newNote);
                runOnUiThread(() -> {
                    clearInputFields();
                    loadNotes();
                    Toast.makeText(NoteCrudActivity.this, "Nota guardada", Toast.LENGTH_SHORT).show();
                });
            } else {
                // Actualizar nota existente
                currentEditingNote.setTitle(title);
                currentEditingNote.setContent(content);
                noteRepository.updateNote(currentEditingNote);

                runOnUiThread(() -> {
                    currentEditingNote = null;
                    btnSaveNote.setText("Guardar Nota");
                    clearInputFields();
                    loadNotes();
                    Toast.makeText(this, "Nota actualizada", Toast.LENGTH_SHORT).show();
                });
            }
        }).start();
    }

    private void clearInputFields() {
        etNoteTitle.setText("");
        etNoteContent.setText("");
    }

    @Override
    public void onEditClick(Note note) {
        // Cargar los datos en los campos de texto
        currentEditingNote = note;
        etNoteTitle.setText(note.getTitle());
        etNoteContent.setText(note.getContent());
        btnSaveNote.setText("Actualizar Nota");
    }

    @Override
    public void onDeleteClick(Note note) {
        // Mostrar diálogo de confirmación
        new AlertDialog.Builder(this)
                .setTitle("Eliminar Nota")
                .setMessage("¿Estás seguro de que deseas eliminar esta nota?")
                .setPositiveButton("Eliminar", (dialog, which) -> {
                    noteRepository.deleteNote(note);
                    loadNotes();
                    Toast.makeText(NoteCrudActivity.this, "Nota eliminada", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }
}
