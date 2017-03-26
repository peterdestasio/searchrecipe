package com.hanson.android.recipe;


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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;


/**
 * A simple {@link Fragment} subclass.
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
            testMaxDuplicates();
            //testingredients_selectIdRecipeByIngredientName();

            //testCountIngredients();
             /*
            DBHelper dbHelper = new DBHelper(v.getContext(), "Recipes.db", null, 1);

            ArrayList<String> interi = dbHelper.ingredients_selectIdRecipeByIngredientName("rice");

            ArrayList<String> stringhe = dbHelper.ingredients_SelectByRecipeId(1);

            for (int i = 0; i < stringhe.size(); i++) {
                Log.d("AAAAAAAAAAAAAAAAAAa: ", stringhe.get(i).toString());
            }

            if(interi.size()>0) {
                for (int i = 0; i < interi.size(); i++) {
                    Log.d("MyTag", interi.get(i).toString());
                }


            }

            for(int i=0; i<listItems.size(); i++){

                Log.d("My Tag: ",listItems.get(i));
            }
            */
            //to do
            Toast toast = Toast.makeText(getContext(), "I'm searching", Toast.LENGTH_SHORT);
            toast.show();

        }

    }

    //TO FIX!!!!!!!!!!1
    public void testingredients_selectIdRecipeByIngredientName(){
        DBHelper dbHelper = new DBHelper(getContext(), "Recupes.db",null,1);
        ArrayList<String> ingredientsName = new ArrayList<>();
        ingredientsName.add("onion");
        ArrayList<Integer> idRecipes = dbHelper.ingredients_selectIdRecipeByIngredientName(ingredientsName);
        Log.d("TEST: ", Integer.toString(idRecipes.get(0)));


    }


        //to FIX!
    public void testCountIngredients(){

        DBHelper dbHelper = new DBHelper(getContext(), "Recipes.db", null, 1);
        int contatore = dbHelper.countIngredientsPerRecipes(88);
        Log.d("aaaaaa",Integer.toString(contatore));

    }

    /*
    a function that search witch is the name with the maximux duplicates and his uccurrencies
    @param idRecipes an array of Int that represents all of the possibles idRecipes
     */
    public Map.Entry<Integer, Integer> findMaxDuplicates(ArrayList<Integer> idRecipes){
        TreeMap<Integer, Integer> tmap = new TreeMap<Integer, Integer>();
        int i = 0;
        int j = 1;

        while (i<idRecipes.size()){
            int count = 1;
            while(j<idRecipes.size()){
                if(idRecipes.get(i)==idRecipes.get(j)){
                    count++;
                    idRecipes.remove(j);
                }
                else{
                    j++;
                }
            }
            tmap.put(idRecipes.get(i),count);

            i++;
            j=i+1;
        }


        Set set = tmap.entrySet();
        Iterator iterator = set.iterator();
        Map.Entry<Integer, Integer> maxEntry = null;

        for (Map.Entry<Integer, Integer> entry : tmap.entrySet())
        {
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0)
            {
                maxEntry = entry;
            }
        }
        Log.d(Integer.toString(maxEntry.getKey()) +"++++++", Integer.toString(maxEntry.getValue())+"RRRRRRRRR");
    return maxEntry;
    }


    //METHOD THAT TEST MAX DUPLICATES
    public void testMaxDuplicates(){
        ArrayList<Integer> integers = new ArrayList<>();
        integers.add(1);
        integers.add(1);
        integers.add(3);
        integers.add(1);
        integers.add(2);
        integers.add(2);
        integers.add(2);
        integers.add(2);
        integers.add(1);
        Map.Entry<Integer,Integer> mappa = findMaxDuplicates(integers);
        Log.d("testo i duplicati:" , Integer.toString(mappa.getValue()));
    }

    /*
    TO TEST!
    researchRecipes method found the possible match
    @param arrayIngredients (take from user the list of ingredients
     */
    public void researchRecipe(ArrayList<String> arrayIngredients){
        //call the dbHelper
        DBHelper dbHelper = new DBHelper(getContext(), "Recipes.db", null, 1);

        //find possible recipes calling a query from a db
        ArrayList<Integer> idPossibleRecipes = new ArrayList<Integer>();
        idPossibleRecipes = dbHelper.ingredients_selectIdRecipeByIngredientName(listItems);

        //if the query return null we find no recipes
        if(idPossibleRecipes.isEmpty()){
            Log.d("Error", "NOT FOUND");
        }
        //else check
        else{
            int possibleRecipe = findMaxDuplicates(idPossibleRecipes).getKey(); // find the id of the possible recipe
            int maxDuplicates = findMaxDuplicates(idPossibleRecipes).getValue(); //find the number of the ingredients that we have
            if(dbHelper.countIngredientsPerRecipes(possibleRecipe)!=maxDuplicates){ //if the occurrencies of the recipe are different from our ingredients
                Log.d("Error2", "NOT FOUND");
            }
            else{
                Log.d("RECIPE FOUND,ID IS", Integer.toString(possibleRecipe));
            }
        }


    }

}
