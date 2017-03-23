package com.hanson.android.recipe;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hanson.android.recipe.Helper.DBHelper;
import com.hanson.android.recipe.Model.RecipeItem;

import java.util.ArrayList;

public class RecipeListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        //Show backbutton
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        Intent intent = getIntent();
        String category = intent.getStringExtra("category");
        DBHelper dbHelper = new DBHelper(this, "Recipes.db", null, 1);
        final ArrayList<RecipeItem> recipes = dbHelper.recipes_SelectByCategory(category);
        ListView listView = (ListView)findViewById(R.id.listview_recipelist);
        listView.setAdapter(new RecipeList_Adapter(this,recipes, R.layout.activity_recipe_list_item));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                RecipeItem selectRecipe = recipes.get(position);
                Intent intent = new Intent(getApplicationContext(), RecipeActivity.class);
                intent.putExtra("recipe", selectRecipe.get_recipeName());
                startActivity(intent);
                //Toast.makeText(view.getContext(),selectRecipe.get_recipName(),Toast.LENGTH_SHORT).show();
            }
        });

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
