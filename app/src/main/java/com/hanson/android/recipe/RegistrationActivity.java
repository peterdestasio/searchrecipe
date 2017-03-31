package com.hanson.android.recipe;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hanson.android.recipe.Helper.DBHelper;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        final EditText etRegAcc = (EditText) findViewById(R.id.etRegAcc);
        final EditText etRegPass = (EditText) findViewById(R.id.etRegPass);
        final EditText etRegRepeatPass= (EditText) findViewById(R.id.etRegRepeatPass);
        final Button bRegSubmit = (Button) findViewById(R.id.bRegSubmit);

        bRegSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = etRegAcc.getText().toString();
                final String password = etRegPass.getText().toString();
                final String password2 = etRegRepeatPass.getText().toString();
                DBHelper dbHelper = new DBHelper(v.getContext(), "Recipes.db", null, 1);
                if (password.equals(password2)) {
                    if (dbHelper.user_IsUsernameFree(username))
                    {
                        dbHelper.user_Insert(username, password);
                        dbHelper.user_Insert(username, password);
                        Toast.makeText(v.getContext(), "Registration Complete!", Toast.LENGTH_SHORT).show();

                    }
                    else
                    {
                        Toast.makeText(v.getContext(), "This username is already exist", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(v.getContext(), "Password mismatch", Toast.LENGTH_SHORT).show();

                }

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

