package com.ytokmak.artbookjava;

import android.graphics.Bitmap;

import java.io.Serializable;

public class Details implements Serializable {
    String name;
    int id;

    public Details(String name, int id) {
        this.name = name;
        this.id = id;

    }
}
