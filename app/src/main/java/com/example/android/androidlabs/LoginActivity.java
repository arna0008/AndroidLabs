package com.example.android.androidlabs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static android.R.attr.button;


public class LoginActivity extends AppCompatActivity {
    //for tagging
    protected static final String ACTIVITY_NAME = "LoginActivity";

    //email login
    EditText emailField; //enter email
    Button loginButton; //click to login


    //for storing saved preference
    SharedPreferences prefs;
    SharedPreferences.Editor prefsEditor; //used to write a preference

    //email ID
    String emailId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        Log.i(ACTIVITY_NAME, "onCreate()");

        /*  LOGIN button, and email field   */
        loginButton = (Button) findViewById(R.id.loginButton);
        emailField = (EditText) findViewById(R.id.inputEmail);
        prefs = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this); //gets default shared prefs for Login Activity class


        loginButtonClick(emailField);

    }//end onCreate



    protected void loginButtonClick(View view)
    {
        Log.i("Message", "Login Button Clicked");

       /* prefsEditor = prefs.edit(); //use editor to write to prefs object
        prefsEditor.putString("email", emailField.getText().toString());
        prefsEditor.commit(); //commit changes to prefs
        */
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get an editor object for writing to the file:
                SharedPreferences.Editor prefsEditor = prefs.edit();

                prefsEditor.putString("email", emailField.getText().toString());

                //Write the file:
                prefsEditor.commit();

                Intent intent = new Intent(LoginActivity.this, StartActivity.class);
                startActivity(intent);

            }
        });
    }







    @Override
    protected void onResume() {
        super.onResume();
        Log.i(ACTIVITY_NAME, "In onResume()");

        if(prefs != null && emailId != null){
            emailId = prefs.getString("email", "No Email");
        }else{
            emailId = prefs.getString("DefaultEmail", "email@domain.com");
        }

        //set the text for the email field
        emailField.setText(emailId);
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.i(ACTIVITY_NAME, "In onStart()");

        if(prefs != null && emailId != null){
            emailId = prefs.getString("DefaultEmail", "No Email");
        }else{
            emailId = prefs.getString("DefaultEmail", "email@domain.com");
        }

        //set the text for the email field
        emailField.setText(emailId);
    }



    @Override
    protected void onPause() {
        super.onPause();
        Log.i(ACTIVITY_NAME, "In onPause()");


        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        prefsEditor = prefs.edit();
        prefsEditor.putString("DefaultEmail",emailField.getText().toString());
        prefsEditor.commit();
    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.i(ACTIVITY_NAME, "In onStop()");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }
}
