package com.hanson.android.recipe.Model;

/**
 * Created by lily on 2017-03-27.
 */

public class SearchResultItem {
    private int ingrCount;
    private int recipeId;

    public int get_ingrCount(){return ingrCount;}
    public int get_recipeId(){return recipeId;}

    public SearchResultItem(int recipeId, int ingrCount) {
        this.ingrCount = ingrCount;
        this.recipeId = recipeId;
    }
}
