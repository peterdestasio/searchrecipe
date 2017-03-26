package com.hanson.android.recipe.Model;

/**
 * Created by asus on 24/03/2017.
 */

public class IngredientItem {

    private String nameIngredient;

    public IngredientItem(String nameIngredient){
        this.nameIngredient= nameIngredient;
    }

    public String getNameIngredient(){
        return  this.nameIngredient;
    }
}
