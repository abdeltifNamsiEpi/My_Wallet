package com.example.mywallet;

import androidx.annotation.NonNull;

public class Category {
    String name;
    int icon;


    public Category(String name, int icon) {
        this.name = name;
        this.icon = icon;
    }

    @NonNull
    @Override
    public String toString(){
        return  name;
    }
}
