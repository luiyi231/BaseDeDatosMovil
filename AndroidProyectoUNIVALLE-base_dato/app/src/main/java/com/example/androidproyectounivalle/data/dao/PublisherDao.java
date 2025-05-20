package com.example.androidproyectounivalle.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.androidproyectounivalle.data.entity.Publisher;

import java.util.List;

@Dao
public interface PublisherDao {
    @Insert
    long insert(Publisher publisher);

    @Query("SELECT * FROM publishers")
    List<Publisher> getAllPublishers();

    @Query("SELECT * FROM publishers WHERE id = :id")
    Publisher getPublisherById(int id);
}