package com.hanson.android.recipe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class TempSearchResultActivity extends AppCompatActivity {

    ArrayList<Integer> sendRecipeList = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_search_result);

        //test dummy data
        sendRecipeList.add(1);
        sendRecipeList.add(2);
        sendRecipeList.add(3);
        sendRecipeList.add(4);

        Button send= (Button)findViewById(R.id.btn_sendRecipeList);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RecipeListActivity.class);
                intent.putExtra("title", "Matchgin 3 ingredients");
                intent.putIntegerArrayListExtra("list", sendRecipeList);
                startActivity(intent);
            }
        });
    }
}
