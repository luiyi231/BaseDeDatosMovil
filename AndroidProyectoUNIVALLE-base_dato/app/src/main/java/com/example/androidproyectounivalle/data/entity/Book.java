package com.example.androidproyectounivalle.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ForeignKey;
import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "books",
        foreignKeys = {
                @ForeignKey(entity = Author.class,
                        parentColumns = "id",
                        childColumns = "authorId",
                        onDelete = CASCADE),
                @ForeignKey(entity = Publisher.class,
                        parentColumns = "id",
                        childColumns = "publisherId",
                        onDelete = CASCADE)
        })
public class Book {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String isbn;
    private int publicationYear;
    private float price;
    private int authorId;
    private int publisherId;

    public Book(String title, String isbn, int publicationYear, float price, int authorId, int publisherId) {
        this.title = title;
        this.isbn = isbn;
        this.publicationYear = publicationYear;
        this.price = price;
        this.authorId = authorId;
        this.publisherId = publisherId;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public int getPublicationYear() { return publicationYear; }
    public void setPublicationYear(int publicationYear) { this.publicationYear = publicationYear; }
    public float getPrice() { return price; }
    public void setPrice(float price) { this.price = price; }
    public int getAuthorId() { return authorId; }
    public void setAuthorId(int authorId) { this.authorId = authorId; }
    public int getPublisherId() { return publisherId; }
    public void setPublisherId(int publisherId) { this.publisherId = publisherId; }
}
