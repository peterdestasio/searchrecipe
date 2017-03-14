package com.hanson.android.recipe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;

/**
 * Created by lily on 2017-03-13.
 */

public class AddIngredientAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private ArrayList<String> ingredientList;
    private int layout;

    public AddIngredientAdapter(Context context, ArrayList<String> ingredientList, int layout) {
        this.inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.ingredientList = ingredientList;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return ingredientList.size();
    }

    @Override
    public Object getItem(int position) {
        return ingredientList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView=inflater.inflate(layout,parent,false);
        }

        String ingredientItem = ingredientList.get(position);

        TextView name=(TextView)convertView.findViewById(R.id.txt_ingredient);
        name.setText(ingredientItem);
        return convertView;
    }


}
