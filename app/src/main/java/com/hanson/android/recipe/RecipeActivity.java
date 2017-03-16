package com.hanson.android.recipe;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hanson.android.recipe.Helper.DBHelper;
import com.hanson.android.recipe.Helper.ImageHelper;
import com.hanson.android.recipe.Model.RecipeItem;

import java.util.ArrayList;

public class RecipeActivity extends AppCompatActivity {
    ImageHelper imageHelper = new ImageHelper();
    RecipeItem recipeItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        //Connect DB
        DBHelper dbHelper = new DBHelper(this, "Recipes.db", null, 1);

        //Show backbutton
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        //connect with view
        TextView recipeName = (TextView)findViewById(R.id.txt_recipeName);
        TextView author = (TextView)findViewById(R.id.txt_recipeAuthor);
        TextView uploadDate = (TextView)findViewById(R.id.txt_recipeUploaddate);
        TextView country = (TextView)findViewById(R.id.txt_recipeCountry);
        TextView ingredients = (TextView)findViewById(R.id.txt_recipeIngredients);
        TextView description = (TextView)findViewById(R.id.txt_recipeDescription);
        TextView howto = (TextView)findViewById(R.id.txt_recipeHowto);
        ImageView mainImg = (ImageView)findViewById(R.id.img_recipeMainImg);

        //set for received data
        Intent intent = getIntent();
        String name = intent.getStringExtra("recipe");
        Toast.makeText(this,name,Toast.LENGTH_SHORT).show();
        //set to selected recipe data
        //recipeItem = (RecipeItem)intent.getSerializableExtra("recipe");


        recipeItem = dbHelper.recipes_SelectByName(name);

        if (recipeItem != null)
        {
            //data bind
            mainImg.setImageBitmap(imageHelper.getBitmapFromByteArray(recipeItem.get_mainImg()));
            recipeName.setText(recipeItem.get_recipeName());
            author.setText(recipeItem.get_author());
            uploadDate.setText(recipeItem.get_uploadDate());
            country.setText(recipeItem.get_category());
            description.setText(recipeItem.get_Description());
            howto.setText(recipeItem.get_howTo());

            ArrayList<String> ingredentList = dbHelper.ingredients_SelectByRecipeId(recipeItem.get_id());
            String tempIngre = "";

            for (int i = 0; i < ingredentList.size(); i++)
            {
                tempIngre += ingredentList.get(i).toString();
                if (i != (ingredentList.size() -1))
                    tempIngre += " / ";
            }

            ingredients.setText(tempIngre);

        }
        else
        {
            Toast.makeText(this,"error",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // NavUtils.navigateUpFromSameTask(this);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
