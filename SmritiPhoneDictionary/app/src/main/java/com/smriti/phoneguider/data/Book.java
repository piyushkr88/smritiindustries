package com.smriti.phoneguider.data;

/**
 * Created by mustafid on 2/1/2017.
 */

public class Book {
    private String name;
    private int imageSource;

    public Book(int imageSource, String name) {
        this.name = name;
        this.imageSource = imageSource;
    }

    public String getName() {
        return name;
    }

    public int getImageSource() {
        return imageSource;
    }
}