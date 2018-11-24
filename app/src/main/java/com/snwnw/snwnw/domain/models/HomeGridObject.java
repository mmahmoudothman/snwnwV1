package com.snwnw.snwnw.domain.models;

import java.io.Serializable;

/**
 * Created by elmalah on 3/2/2017.
 */

public class HomeGridObject implements Serializable {
    public String name;
    public int img;
    public int Category_Id;
    public String Name;
    public String NotifiedTime;
    public boolean Favorite;
    public String URLImage;
    public String FullURLImage;
    public boolean IsPublished;
    public int ItemsCount;

    public HomeGridObject(String name, int img){
        this.name=name;
        this.img=img;
    }
}
