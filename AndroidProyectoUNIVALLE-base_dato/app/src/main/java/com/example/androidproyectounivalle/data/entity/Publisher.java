package com.example.androidproyectounivalle.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

// En Publisher.java, agregar:


@Entity(tableName = "publishers")
public class Publisher {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String country;
    private int foundationYear;
    private String phone;

    public Publisher(String name, String country, int foundationYear, String phone) {
        this.name = name;
        this.country = country;
        this.foundationYear = foundationYear;
        this.phone = phone;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
    public int getFoundationYear() { return foundationYear; }
    public void setFoundationYear(int foundationYear) { this.foundationYear = foundationYear; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    // En Publisher.java, agregar:
    @Override
    public String toString() {
        return name;
    }
}
