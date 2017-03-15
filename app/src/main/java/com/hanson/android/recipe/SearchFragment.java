package com.hanson.android.recipe;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.anton46.collectionitempicker.CollectionPicker;
import com.anton46.collectionitempicker.Item;
import com.anton46.collectionitempicker.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements View.OnClickListener {

    EditText editText;
    Button addButton;
    Button buttonSearch;

    List<Item> items = new ArrayList<>();
    CollectionPicker picker;



    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search, container, false);
        editText = (EditText)view.findViewById(R.id.typeIngredient);
        addButton = (Button)view.findViewById(R.id.addIngredient);
        picker = (CollectionPicker)view.findViewById(R.id.collection_item_picker);
        buttonSearch = (Button)view.findViewById(R.id.buttonSearch);

        addButton.setOnClickListener(this);
        buttonSearch.setOnClickListener(this);

        return  view;
    }


    @Override
    public void onClick(View v) {

        if(v.getId()==addButton.getId()){
            items.add(new Item("item1", editText.getText().toString()));
            picker.setItems(items);
            picker.drawItemView();


            picker.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onClick(Item item, int position) {
                    //to do
                }
            });

        }

        if(v.getId()==buttonSearch.getId()){

            //to do

        }

    }




}
