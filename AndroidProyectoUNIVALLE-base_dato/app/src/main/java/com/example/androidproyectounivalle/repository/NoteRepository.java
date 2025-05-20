package com.example.androidproyectounivalle.repository;

import android.content.Context;

import com.example.androidproyectounivalle.data.database.AppDatabase;
import com.example.androidproyectounivalle.data.entity.Note;

import java.util.List;

public class NoteRepository {
    private final AppDatabase database;

    public NoteRepository(Context context) {
        database = AppDatabase.getDatabase(context);
    }

    public List<Note> getAllNotes() {
        return database.noteDao().getAllNotes();
    }

    public long insertNote(Note note) {
        return database.noteDao().insert(note);
    }

    public void updateNote(Note note) {
        database.noteDao().update(note);
    }

    public void deleteNote(Note note) {
        database.noteDao().delete(note);
    }
}