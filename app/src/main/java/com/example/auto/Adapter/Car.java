package com.example.auto.Adapter;

import android.graphics.Bitmap;


public class Car {

    public String   owner,brand,name,
                    class_type, type,
                    transmission;

    public int  id,price,speed,engine,
                date, fuel;

    public Bitmap bitmap;

    public Car( String owner,String brand ,String name,String type,String class_type , String transmission,
         int id, int price, int date, int engine, int speed, int fuel, Bitmap bitmap){

        this.owner = owner;
        this.brand = brand;
        this.type = type;
        this.class_type = class_type;
        this.transmission = transmission;
        this.name = name;

        this.id = id;
        this.price = price;
        this.date = date;
        this.engine = engine;
        this.speed = speed;
        this.fuel = fuel;

        this.bitmap = bitmap;
    }



}
