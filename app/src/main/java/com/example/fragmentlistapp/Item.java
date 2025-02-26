package com.example.fragmentlistapp;

import java.io.Serializable;

public class Item implements Serializable {
    private String text;
    private int imageResId;

    public Item(String text, int imageResId) {
        this.text = text;
        this.imageResId = imageResId;
    }

    public String getText() {
        return text;
    }

    public int getImageResId() {
        return imageResId;
    }
}
