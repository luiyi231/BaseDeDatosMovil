package com.example.androidproyectounivalle.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproyectounivalle.R;
import com.example.androidproyectounivalle.data.entity.Note;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private List<Note> notes;
    private final NoteListener listener;

    // Interfaz para manejar eventos de los items
    public interface NoteListener {
        void onEditClick(Note note);
        void onDeleteClick(Note note);
    }

    public NoteAdapter(List<Note> notes, NoteListener listener) {
        this.notes = notes;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.bind(note);
    }

    @Override
    public int getItemCount() {
        return notes == null ? 0 : notes.size();
    }

    // Método para actualizar la lista de notas
    public void updateNotes(List<Note> newNotes) {
        this.notes = newNotes;
        notifyDataSetChanged();
    }

    // ViewHolder para los items de notas
    class NoteViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvNoteTitle;
        private final TextView tvNoteContent;
        private final Button btnEditNote;
        private final Button btnDeleteNote;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNoteTitle = itemView.findViewById(R.id.tvNoteTitle);
            tvNoteContent = itemView.findViewById(R.id.tvNoteContent);
            btnEditNote = itemView.findViewById(R.id.btnEditNote);
            btnDeleteNote = itemView.findViewById(R.id.btnDeleteNote);
        }

        void bind(final Note note) {
            tvNoteTitle.setText(note.getTitle());
            tvNoteContent.setText(note.getContent());

            // Configurar listeners
            btnEditNote.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onEditClick(note);
                }
            });

            btnDeleteNote.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onDeleteClick(note);
                }
            });
        }
    }
}
