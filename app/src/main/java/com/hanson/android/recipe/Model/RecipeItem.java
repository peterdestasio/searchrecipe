package com.hanson.android.recipe.Model;

import java.io.Serializable;

import static android.R.attr.name;

/**
 * Created by lily on 2017-03-12.
 * Recipe Item Data Model
 */

public class RecipeItem implements Serializable {
    private String id;
    private String category;
    private String recipeName;
    private String author;
    private String uploadDate;
    private String howTo;
    private String description;
    private int thumbnail;
    private String mainImg;
    private int likeCount;

    public String get_id(){ return id; }
    public String get_category(){return category;}
    public String get_recipeName(){return recipeName;}
    public String get_author(){return author;}
    public String get_uploadDate(){return uploadDate;}
    public String get_howTo(){return howTo;}
    public String get_Description(){return description;}
    public int get_thumbnail(){return thumbnail;}
    public String get_mainImg(){return mainImg;}
    public int get_likeCount(){return likeCount;}

    public RecipeItem(String id, String category, String recipeName, String author, String uploadDate,
                      String howTo, String description, int thumbnail, String mainImg, int likeCount) {
        this.id = id;
        this.category = category;
        this.recipeName = recipeName;
        this.author = author;
        this.uploadDate = uploadDate;
        this.howTo = howTo;
        this.description = description;
        this.thumbnail = thumbnail;
        this.mainImg = mainImg;
        this.likeCount = likeCount;
    }

}
