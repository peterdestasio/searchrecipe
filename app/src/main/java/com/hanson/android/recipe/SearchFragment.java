package com.hanson.android.recipe;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements View.OnClickListener {

    EditText editText;
    Button addButton;
    Button buttonSearch;
    GridView gridView;

    //LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
    ArrayList<String> listItems=new ArrayList<String>();

    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE GRIDTVIEW
    ArrayAdapter<String> adapter;

    //RECORDING HOW MANY TIMES THE BUTTON HAS BEEN CLICKED
    int clickCounter=0;


    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        editText = (EditText)view.findViewById(R.id.typeIngredient);
        addButton = (Button)view.findViewById(R.id.addIngredient);
        buttonSearch = (Button)view.findViewById(R.id.buttonSearch);
        gridView = (GridView)view.findViewById(R.id.gridSearch);

        //adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, listItems);
         adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, listItems);

        gridView.setAdapter(adapter);


        addButton.setOnClickListener(this);
        buttonSearch.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {

        if(v.getId()==addButton.getId()){
            listItems.add(editText.getText().toString());
            adapter.notifyDataSetChanged();

        }

        if(v.getId()==buttonSearch.getId()){

            //to do
            Toast toast = Toast.makeText(getContext(),"I'm searching", Toast.LENGTH_SHORT);
            toast.show();

        }

    }

}
