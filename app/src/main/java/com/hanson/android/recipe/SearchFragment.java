package com.hanson.android.recipe;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.hanson.android.recipe.Helper.DBHelper;
import com.hanson.android.recipe.Model.IngredientItem;
import com.hanson.android.recipe.Model.RecipeItem;
import com.hanson.android.recipe.Model.SearchResultItem;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;


/**
 * Pierluigi De Stasio
 * SearchFragment Subclass
 */
public class SearchFragment extends Fragment implements View.OnClickListener {

    EditText editText;
    Button addButton;
    Button buttonSearch;
    GridView gridView;

    //LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
    ArrayList<String> listItems = new ArrayList<String>();

    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE GRIDTVIEW
    ArrayAdapter<String> adapter;

    //RECORDING HOW MANY TIMES THE BUTTON HAS BEEN CLICKED
    int clickCounter = 0;


    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        editText = (EditText) view.findViewById(R.id.typeIngredient);
        addButton = (Button) view.findViewById(R.id.addIngredient);
        buttonSearch = (Button) view.findViewById(R.id.buttonSearch);
        gridView = (GridView) view.findViewById(R.id.gridSearch);

        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, listItems);
        gridView.setAdapter(adapter);

        //added a click listener on the item of the gridview
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) { //delete element onclick
                Object o = gridView.getItemAtPosition(position);
                listItems.remove(position);
                clickCounter--;
                adapter.notifyDataSetChanged();

            }
        });

        addButton.setOnClickListener(this);
        buttonSearch.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == addButton.getId()) {
            if (clickCounter < 20) { //set max 20 ingredients
                if (editText.getText().toString().trim().length() > 0) {  //check if inserted a blank string
                      if(listItems.contains(editText.getText().toString())){
                        Toast toast = Toast.makeText(getContext(), "ingredient duplicated", Toast.LENGTH_SHORT);
                        toast.show();
                    }else {
                        listItems.add(editText.getText().toString());
                        adapter.notifyDataSetChanged();
                        clickCounter++;
                        editText.setText("");
                    }
                } else {
                    Toast toast = Toast.makeText(getContext(), "Please insert the text", Toast.LENGTH_SHORT);
                    toast.show();
                }
            } else {
                addButton.setClickable(false);
                Toast toast = Toast.makeText(getContext(), "Max number of ingredients reached", Toast.LENGTH_SHORT);
                toast.show();
            }

        }

        if (v.getId() == buttonSearch.getId()) {

            if (listItems.isEmpty()){ //if user didin't put any ingredient display error message
                Toast toast = Toast.makeText(getContext(), "You didn't insert any ingredient", Toast.LENGTH_SHORT);
                toast.show();
            }
            else {
                //finding recipeid and count by ingredient
                DBHelper dbHelper = new DBHelper(v.getContext(), "Recipes.db", null, 1);
                ArrayList<SearchResultItem> resultList = dbHelper.ingredients_selectRecipeByIngredientName(listItems);

                if(resultList.isEmpty()){ //if didn't find anything display error message and reccomend best recipes
                    ArrayList<Integer> advices = new ArrayList<>();
                    advices = findBestRecipes();

                    Intent intent = new Intent(getActivity(), RecipeListActivity.class);
                    intent.putExtra("title", "Sorry, we didn't match any ingredient! Take a look of our best Recipes!");

                    intent.putExtra("list",advices);
                    startActivity(intent);
                    dbHelper.close();

                }else{
                    ArrayList<Integer> idRecipes = new ArrayList<>();
                    ArrayList<Integer> matches = new ArrayList<>();


                    //filling the array of idRecipes
                    for(int i=0;i<resultList.size();i++){
                        idRecipes.add(resultList.get(i).get_recipeId());
                    }

                    //filling the array of ingredients matches
                    for (int j=0;j<resultList.size();j++){
                        matches.add(resultList.get(j).get_ingrCount());
                    }
                    ArrayList<Integer> matchesNoDuplicates = removeDuplicates(matches);
                    /*
                    ArrayList<Integer> positionsfound = findPosition(matches, findMax(matches));
                    ArrayList<Integer> idRecipesSelected = new ArrayList<>();

                    //filling the array of the id only for the selected Recipes
                    for(int k=0; k<positionsfound.size();k++){
                        idRecipesSelected.add(idRecipes.get(positionsfound.get(k)));
                    }


                        Intent intent = new Intent(getActivity(), RecipeListActivity.class);
                        intent.putExtra("title", "Matching: "+ findMax(matches) + " ingredients");
                        intent.putExtra("list",idRecipesSelected);
                        startActivity(intent);

                        */
                    //sent to Search Result intent idRecipes, matches and also the number of matches without duplicates
                    Intent intent = new Intent(getActivity(), SearchResult.class);
                    intent.putExtra("idrecipes",idRecipes);

                    intent.putExtra("matchesNoDuplicates", matchesNoDuplicates);
                    intent.putExtra("matches",matches);

                    startActivity(intent);



                    dbHelper.close();
                }



            }



        }

    }

    /*
    a function that search witch is the max in an array of int
    used for retrives the maximum of ingredients matched
    @param numMatches an array of Int that numbers of the matches
     */
    public int findMax(ArrayList<Integer> numMatches){
        int maxValue = numMatches.get(0);

        for (int i = 0; i < numMatches.size(); i++) {
            if (numMatches.get(i) > maxValue) {
                maxValue = numMatches.get(i);
            }
        }
        return maxValue;
    }

    /*
   a function that search witch are the positions of the array in case of maxvalues duplicates
   used for retrives the positions of the maximum an then the id of recipes
   @param numMatches an array of Int that numbers of the matches
   return an array of int that shows the positions of the maximum
    */
    public ArrayList<Integer> findPosition(ArrayList<Integer> matches, int maxvalue){
        ArrayList<Integer> positions = new ArrayList<>();
        for(int i =0; i<matches.size();i++){
            if(matches.get(i)==maxvalue){
                positions.add(i);
            }
        }
        for (int j =0; j<positions.size(); j++){
            System.out.println(positions.get(j));
        }
        return positions;
    }

    /*
    this function call the db to find the best recipes and retun an arraylist of integer -> id of best recipes
     */
    public ArrayList<Integer> findBestRecipes(){
        ArrayList<Integer> bestRecipes = new ArrayList<>();
        DBHelper dbHelper = new DBHelper(getContext(), "Recipes.db", null, 1);
        ArrayList<RecipeItem> results = dbHelper.recipes_SelectBest();
        if(results.isEmpty()){

        }
        else {
            for(int i=0;i<results.size();i++){
                bestRecipes.add(results.get(i).get_id());
            }
        }
        return  bestRecipes;
    }

    /*
    function that removes the duplicates in an array of Integer
     */
    public ArrayList<Integer> removeDuplicates(ArrayList<Integer> array){
        ArrayList<Integer> noduplicates = new ArrayList<>();
        Set<Integer> withoutDuplicates = new LinkedHashSet<Integer>(array);
        noduplicates.clear();
        noduplicates.addAll(withoutDuplicates);
        return noduplicates;
    }




}
