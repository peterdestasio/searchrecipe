package com.hanson.android.recipe;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.hanson.android.recipe.Model.RecipeItem;

public class RecipeActivity extends AppCompatActivity {

    RecipeItem recipeItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        //Show backbutton
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        //connect with view
        TextView recipeName = (TextView)findViewById(R.id.txt_recipeName);
        TextView author = (TextView)findViewById(R.id.txt_recipeAuthor);
        TextView howto = (TextView)findViewById(R.id.txt_recipeHowto);

        //set for received data
        Intent intent = getIntent();

        //set to selected recipe data
        recipeItem = (RecipeItem)intent.getSerializableExtra("recipe");

        if (recipeItem != null)
        {
            //data bind
            recipeName.setText(recipeItem.get_recipeName());
            author.setText(recipeItem.get_author());
            howto.setText(recipeItem.get_howTo());
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
