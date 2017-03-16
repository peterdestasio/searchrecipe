package com.hanson.android.recipe;

import android.content.Context;
import android.database.DataSetObserver;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.hanson.android.recipe.Helper.ImageHelper;
import com.hanson.android.recipe.Model.RecipeItem;

import java.util.ArrayList;

/**
 * Created by lily on 2017-03-12.
 */

public class MainRecipeAdapter extends BaseAdapter{

    private LayoutInflater inflater;
    private ArrayList<RecipeItem> recipeList;
    private int layout;
    private ImageHelper imageHelper = new ImageHelper();

    public MainRecipeAdapter(Context context, ArrayList<RecipeItem> recipeList, int layout) {
        this.inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.recipeList = recipeList;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return recipeList.size();
    }

    @Override
    public Object getItem(int position) {
        return recipeList.get(position).get_recipeName();
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

        RecipeItem recipeItem = recipeList.get(position);
        ImageView icon=(ImageView)convertView.findViewById(R.id.img_mainListItem);
        //icon.setImageURI(Uri.parse(recipeItem.get_thumbnail()));
        //icon.setImageResource(recipeItem.get_thumbnail());
        icon.setImageBitmap(imageHelper.getBitmapFromByteArray(recipeItem.get_thumbnail()));

        TextView name=(TextView)convertView.findViewById(R.id.txt_mainListItem);
        name.setText(recipeItem.get_recipeName());
        return convertView;
    }
}
