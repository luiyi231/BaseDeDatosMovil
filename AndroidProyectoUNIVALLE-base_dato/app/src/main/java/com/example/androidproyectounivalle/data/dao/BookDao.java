package com.example.androidproyectounivalle.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Update;
import androidx.room.Delete;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.androidproyectounivalle.data.entity.Book;
import com.example.androidproyectounivalle.data.entity.BookWithAuthorAndPublisher;

import java.util.List;

@Dao
public interface BookDao {
    @Insert
    long insert(Book book);

    @Update
    void update(Book book);

    @Delete
    void delete(Book book);

    @Query("SELECT * FROM books")
    List<Book> getAllBooks();

    @Transaction
    @Query("SELECT * FROM books")
    List<BookWithAuthorAndPublisher> getBooksWithAuthorAndPublisher();

    @Query("SELECT * FROM books WHERE id = :id")
    Book getBookById(int id);
}