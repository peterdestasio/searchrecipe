package com.hanson.android.recipe;

import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hanson.android.recipe.Helper.DBHelper;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class RegistrationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        final TextView tv_enterAcc = (TextView) findViewById(R.id.tv_enterAcc);
        final TextView tv_enterPass = (TextView) findViewById(R.id.tv_enterPass);
        final TextView tv_repeatPass = (TextView) findViewById(R.id.tv_repeatPass);
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
                final int accLength = username.length();
                final int passLength = password.length();
                DBHelper dbHelper = new DBHelper(v.getContext(), "Recipes.db", null, 1);
                if (accLength > 3) {
                    if (accLength < 19) {
                        if (passLength > 3) {
                            if (passLength < 19) {
                                if (password.equals(password2)) {
                                    if (dbHelper.user_IsUsernameFree(username)) {
                                        dbHelper.user_Insert(username, sha256(password));
                                        Toast.makeText(v.getContext(), "Registration Complete!", Toast.LENGTH_SHORT).show();
                                        tv_enterPass.setTextColor(Color.BLACK);
                                        tv_enterAcc.setTextColor(Color.BLACK);
                                        tv_repeatPass.setTextColor(Color.BLACK);
                                    } else {
                                        Toast.makeText(v.getContext(), "This username is already exist!", Toast.LENGTH_SHORT).show();
                                        tv_enterPass.setTextColor(Color.BLACK);
                                        tv_enterAcc.setTextColor(Color.RED);
                                        tv_repeatPass.setTextColor(Color.BLACK);
                                    }
                                } else {
                                    Toast.makeText(v.getContext(), "Password mismatch!", Toast.LENGTH_SHORT).show();
                                    tv_enterPass.setTextColor(Color.BLACK);
                                    tv_enterAcc.setTextColor(Color.BLACK);
                                    tv_repeatPass.setTextColor(Color.RED);
                                }
                            } else {
                                Toast.makeText(v.getContext(), "Password can't be more than 18 symbols!", Toast.LENGTH_SHORT).show();
                                tv_enterPass.setTextColor(Color.RED);
                                tv_repeatPass.setTextColor(Color.BLACK);
                                tv_enterAcc.setTextColor(Color.BLACK);
                            }
                        } else {
                            Toast.makeText(v.getContext(), "Password must be at least 4 symbols!", Toast.LENGTH_SHORT).show();
                            tv_enterPass.setTextColor(Color.RED);
                            tv_repeatPass.setTextColor(Color.BLACK);
                            tv_enterAcc.setTextColor(Color.BLACK);
                        }
                    } else {
                        Toast.makeText(v.getContext(), "Account name can't be more than 18 symbols!", Toast.LENGTH_SHORT).show();
                        tv_enterPass.setTextColor(Color.BLACK);
                        tv_repeatPass.setTextColor(Color.BLACK);
                        tv_enterAcc.setTextColor(Color.RED);
                    }
                } else {
                    Toast.makeText(v.getContext(), "Account name must be at least 4 symbols!", Toast.LENGTH_SHORT).show();
                    tv_enterAcc.setTextColor(Color.RED);
                    tv_enterPass.setTextColor(Color.BLACK);
                    tv_repeatPass.setTextColor(Color.BLACK);
                }
            }
        });

    }

    public static String sha256(String password) {
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
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

