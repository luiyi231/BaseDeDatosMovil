package com.example.androidproyectounivalle.models;

public class Pokemon {
    private int id;
    private String name;
    private String imageUrl;
    private String type;

    public Pokemon(int id, String name, String imageUrl, String type) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getType() {
        return type;
    }
}
