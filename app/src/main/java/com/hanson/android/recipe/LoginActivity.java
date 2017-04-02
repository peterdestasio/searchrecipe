package com.hanson.android.recipe;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hanson.android.recipe.Helper.DBHelper;

public class LoginActivity extends AppCompatActivity {
    // EditText userName, userPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Show backbutton
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        final EditText etRegAcc = (EditText) findViewById(R.id.etRegAcc);
        final EditText etRegPass = (EditText) findViewById(R.id.etRegPass);
        final Button buttonRegLogin = (Button) findViewById(R.id.bRegLogin);
        final TextView registerLink = (TextView) findViewById(R.id.tvRegLink);

        buttonRegLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = etRegAcc.getText().toString();
                final String password = etRegPass.getText().toString();
                DBHelper dbHelper = new DBHelper(v.getContext(), "Recipes.db", null, 1);
                if (dbHelper.user_Login(username, RegistrationActivity.sha256(password))) {
                    Toast.makeText(v.getContext(), "Login Successful!", Toast.LENGTH_SHORT).show();
                    SharedPreferences pref = getSharedPreferences("Login", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("userID", username);
                    editor.commit();
                } else {
                    Toast.makeText(v.getContext(), "Wrong username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //registration page access
        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, RegistrationActivity.class);
                LoginActivity.this.startActivity(registerIntent);
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
