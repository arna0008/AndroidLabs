package com.example.android.androidlabs;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class StartActivity extends AppCompatActivity {

    //customized tagging
    protected static final String ACTIVITY_NAME = "StartActivity";

    //Instantiate Button to get to ListItemActivity
    Button goToListActivityButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        //a log
        Log.i(ACTIVITY_NAME,"In onCreate");

        //set button to id correct id
        goToListActivityButton = (Button) findViewById(R.id.goToLoginActivityButtonId);


        //Click button to go to ListItemsActivity
        goToListActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Intent to go from StartActivity(this) to ListItemsActivity
                Intent intentToListItems = new Intent(StartActivity.this, ListItemsActivity.class);

                //receive information back from the ListItemsActivity class
                startActivityForResult(intentToListItems, 5);


            }
        });

        //************************** START CHAT *********************************//

        //Start chat button
        Button startChatButton = (Button) findViewById(R.id.start_chat_button);

        //onClick handling for startChatButton
        startChatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(ACTIVITY_NAME, "User clicked Start Chat");

                Intent goToChatIntent = new Intent(StartActivity.this, ChatWindow.class);

                if(goToChatIntent.resolveActivity(getPackageManager()) != null){
                    startActivity(goToChatIntent);
                }
            }
        });



    }//end of onCreate

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 5 && resultCode == Activity.RESULT_OK) {

            Log.i(ACTIVITY_NAME, "Returned to StartActivity.onActivityResult");

            //get String passed from ListItemsActivity
            String messagePassed = data.getStringExtra("Response");

            Toast.makeText(this, messagePassed, Toast.LENGTH_LONG).show();
        }
    }


        @Override
    protected void onStart() {
        super.onStart();
        Log.i(ACTIVITY_NAME, "In onStart");
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.i(ACTIVITY_NAME, "In onResume");
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.i(ACTIVITY_NAME, "In onPause");
    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.i(ACTIVITY_NAME, "In onStop");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "In onDestroy");
    }



}
