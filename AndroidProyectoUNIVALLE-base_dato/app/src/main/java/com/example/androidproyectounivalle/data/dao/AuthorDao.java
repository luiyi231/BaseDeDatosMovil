package com.example.androidproyectounivalle.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.androidproyectounivalle.data.entity.Author;

import java.util.List;

@Dao
public interface AuthorDao {
    @Insert
    long insert(Author author);

    @Query("SELECT * FROM authors")
    List<Author> getAllAuthors();

    @Query("SELECT * FROM authors WHERE id = :id")
    Author getAuthorById(int id);
}