package com.example.androidproyectounivalle.repository;

import android.content.Context;

import com.example.androidproyectounivalle.data.database.AppDatabase;
import com.example.androidproyectounivalle.data.entity.Author;
import com.example.androidproyectounivalle.data.entity.Book;
import com.example.androidproyectounivalle.data.entity.Publisher;
import com.example.androidproyectounivalle.data.entity.BookWithAuthorAndPublisher;

import java.util.List;

public class LibraryRepository {
    private final AppDatabase database;

    public LibraryRepository(Context context) {
        database = AppDatabase.getDatabase(context);
    }

    public List<Author> getAllAuthors() {
        return database.authorDao().getAllAuthors();
    }

    public List<Publisher> getAllPublishers() {
        return database.publisherDao().getAllPublishers();
    }

    public Book getBookById(int id) {
        return database.bookDao().getBookById(id);
    }

    public long insertBook(Book book) {
        return database.bookDao().insert(book);
    }

    public void updateBook(Book book) {
        database.bookDao().update(book);
    }

    public void deleteBook(Book book) {
        database.bookDao().delete(book);
    }

    public List<BookWithAuthorAndPublisher> getBooksWithAuthorAndPublisher() {
        return database.bookDao().getBooksWithAuthorAndPublisher();
    }
    public void insertInitialData(Runnable onComplete) {
        new Thread(() -> {
            try {
                // Verificar si ya hay datos
                if (database.authorDao().getAllAuthors().isEmpty()) {
                    // Insertar autores
                    Author author1 = new Author("Gabriel", "García Márquez", "Colombiano", "1927-03-06");
                    Author author2 = new Author("Mario", "Vargas Llosa", "Peruano", "1936-03-28");
                    Author author3 = new Author("Isabel", "Allende", "Chilena", "1942-08-02");

                    database.authorDao().insert(author1);
                    database.authorDao().insert(author2);
                    database.authorDao().insert(author3);
                }

                if (database.publisherDao().getAllPublishers().isEmpty()) {
                    // Insertar editoriales
                    Publisher publisher1 = new Publisher("Editorial Planeta", "España", 1949, "+34912345678");
                    Publisher publisher2 = new Publisher("Penguin Random House", "Estados Unidos", 1927, "+12123456789");
                    Publisher publisher3 = new Publisher("Grupo Santillana", "España", 1960, "+34913456789");

                    database.publisherDao().insert(publisher1);
                    database.publisherDao().insert(publisher2);
                    database.publisherDao().insert(publisher3);
                }

                // Notificar que la inserción ha terminado
                if (onComplete != null) {
                    onComplete.run();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}