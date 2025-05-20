package com.example.androidproyectounivalle.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproyectounivalle.R;
import com.example.androidproyectounivalle.data.entity.BookWithAuthorAndPublisher;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
    private List<BookWithAuthorAndPublisher> books;
    private final OnBookListener listener;

    public interface OnBookListener {
        void onEditClick(int position);
        void onDeleteClick(int position);
    }

    public BookAdapter(List<BookWithAuthorAndPublisher> books, OnBookListener listener) {
        this.books = books;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        BookWithAuthorAndPublisher book = books.get(position);
        holder.bind(book);
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public void updateBooks(List<BookWithAuthorAndPublisher> newBooks) {
        books = newBooks;
        notifyDataSetChanged();
    }

    static class BookViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvTitle, tvAuthor, tvPublisher, tvYear;
        private final Button btnEdit, btnDelete;

        public BookViewHolder(@NonNull View itemView, OnBookListener listener) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvBookTitle);
            tvAuthor = itemView.findViewById(R.id.tvBookAuthor);
            tvPublisher = itemView.findViewById(R.id.tvBookPublisher);
            tvYear = itemView.findViewById(R.id.tvBookYear);
            btnEdit = itemView.findViewById(R.id.btnEditBook);
            btnDelete = itemView.findViewById(R.id.btnDeleteBook);

            btnEdit.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.onEditClick(position);
                }
            });

            btnDelete.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.onDeleteClick(position);
                }
            });
        }

        void bind(BookWithAuthorAndPublisher book) {
            tvTitle.setText(book.book.getTitle());
            tvAuthor.setText(String.format("%s %s", book.author.getFirstName(), book.author.getLastName()));
            tvPublisher.setText(book.publisher.getName());
            tvYear.setText(String.valueOf(book.book.getPublicationYear()));
        }
    }
}