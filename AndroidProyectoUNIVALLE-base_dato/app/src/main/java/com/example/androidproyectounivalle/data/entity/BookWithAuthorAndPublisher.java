package com.example.androidproyectounivalle.data.entity;


import androidx.room.Embedded;
import androidx.room.Relation;

public class BookWithAuthorAndPublisher {
    @Embedded
    public Book book;

    @Relation(parentColumn = "authorId", entityColumn = "id")
    public Author author;

    @Relation(parentColumn = "publisherId", entityColumn = "id")
    public Publisher publisher;
}
