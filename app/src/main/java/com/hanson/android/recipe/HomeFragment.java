package com.hanson.android.recipe;


import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.hanson.android.recipe.Model.RecipeItem;

import java.util.ArrayList;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    ArrayList<RecipeItem> bestList;
    ArrayList<RecipeItem> newList;

    Date today = new Date();

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home, container, false);
        GridView newGridView = (GridView)view.findViewById(R.id.GridView_New);

        newList =new ArrayList<RecipeItem>();
        newList.add(new RecipeItem("003", "Korea", "Bulgoggi", "shyjoo", today.toString(),
                "1. beef \n 2. vegetable and source \n 3. mix", "Korean traditional food2",
                R.drawable.ic_menu_camera,
                "", 0));
        newList.add(new RecipeItem("002", "Korea", "Kimbap", "shyjoo", today.toString(),
                "1. rice \n 2. hubs and egg \n 3. minx", "Korean traditional food",
                R.drawable.ic_menu_camera,
                "", 0));
        newList.add(new RecipeItem("001", "Korea", "Bibimbap", "shyjoo", today.toString(),
                "1. rice \n 2. hubs and egg \n 3. minx", "Korean traditional food",
                R.drawable.ic_menu_camera,
                "", 0));

        newGridView.setAdapter(new MainRecipeAdapter(this.getContext(), newList, R.layout.fragment_home_recipeitem));

        newGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                RecipeItem selectRecipe = newList.get(position);
                Intent intent = new Intent(getActivity(), RecipeActivity.class);
                intent.putExtra("recipe", selectRecipe);
                startActivity(intent);
                //Toast.makeText(view.getContext(),selectRecipe.get_recipName(),Toast.LENGTH_SHORT).show();
            }
        });


        //set Best recipes list
        GridView bestGridView = (GridView)view.findViewById(R.id.GridView_Best);

        bestList =new ArrayList<RecipeItem>();
        bestList.add(new RecipeItem("001", "Korea", "Bibimbap", "shyjoo", today.toString(),
                "1. rice \n 2. hubs and egg \n 3. minx", "Korean traditional food",
                R.drawable.ic_menu_camera,
                "", 0));
        bestList.add(new RecipeItem("002", "Korea", "Kimbap", "shyjoo", today.toString(),
                "1. rice \n 2. hubs and egg \n 3. minx", "Korean traditional food",
                R.drawable.ic_menu_camera,
                "", 0));
        bestList.add(new RecipeItem("003", "Korea", "Bulgoggi", "shyjoo", today.toString(),
                "1. beef \n 2. vegetable and source \n 3. mix", "Korean traditional food2",
                R.drawable.ic_menu_camera,
                "", 0));
        bestGridView.setAdapter(new MainRecipeAdapter(this.getContext(), bestList, R.layout.fragment_home_recipeitem));

        bestGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                RecipeItem selectRecipe = bestList.get(position);
                Intent intent = new Intent(getActivity(), RecipeActivity.class);
                intent.putExtra("recipe", selectRecipe);
                startActivity(intent);
                //Toast.makeText(view.getContext(),selectRecipe.get_recipName(),Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

}
