package com.hanson.android.recipe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import java.util.ArrayList;

public class SearchResult extends AppCompatActivity {
    ArrayList<String> listItems = new ArrayList<>();
    GridView gridView;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        //get the intent
        Intent intent = getIntent();

        //get the resources from the intent
        final ArrayList<Integer> reciveRecipeList =  intent.getIntegerArrayListExtra("idrecipes");
        final ArrayList<Integer> receiveMatches = intent.getIntegerArrayListExtra("matches");
        ArrayList<String> receiveMatchesString = createString(receiveMatches);

        gridView = (GridView)findViewById(R.id.idGridSearchResult);
        adapter = new ArrayAdapter<String>(this, R.layout.searchresult_custom,receiveMatchesString);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // find the position of the view clicked
                Integer intero = gridView.getPositionForView(view);
                Intent intent = new Intent(getApplicationContext(),RecipeListActivity.class);
                //send the id of the recipes related to the position of the view clicked
               // intent.putExtra("id", Integer.toString(reciveRecipeList.get(intero)));
                intent.putExtra("title", "Matching: "+ Integer.toString(receiveMatches.get(intero)) + " ingredients");
                ArrayList<Integer> idtosend = new ArrayList<Integer>();
                idtosend.add(reciveRecipeList.get(intero));
                intent.putExtra("list",idtosend);
                startActivity(intent);

            }
        });
    }


    /*
    Method that build the format of the string to show on the grid view
     */
    public ArrayList<String> createString(ArrayList<Integer> array){
        ArrayList<String> stringToShow = new ArrayList<>();
        for(int i=0;i<array.size();i++){
            stringToShow.add("Matches\n" + array.get(i) + "\ningredients");
        }
        return  stringToShow;
    }
}

