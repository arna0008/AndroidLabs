package com.example.android.androidlabs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import static android.R.attr.data;

public class ListItemsActivity extends AppCompatActivity {

    //customized tagging
    protected static final String ACTIVITY_NAME = "StartActivity";

    //Image button to access camera
    ImageButton goToCamera;

    //Switch Object
    Switch switchButton;

    //checkbox Object
    CheckBox checkBox;

    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);
        Log.i(ACTIVITY_NAME, "In onCreate");


        //ImageButton object
        goToCamera = (ImageButton) findViewById(R.id.imageButtonId);



        //Click button to go open Camera App
        goToCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    //open camera app using Intent
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                    }

            }
        });

        //switch button object
        switchButton = (Switch) findViewById(R.id.switch1);

        //When switch is on, toast to show on, else, show off
        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    CharSequence text =  "Switch is ON";
                    int duration= Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(getApplicationContext() , text, duration); //this is the ListActivity
                    toast.show();

                }
                else
                {
                    CharSequence text = "Switch is OFF";
                    int duration= Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(getApplicationContext() , text, duration); //this is the ListActivity
                    toast.show();
                }
            }
        });

        //Checkbox object instantiation
        checkBox = (CheckBox) findViewById(R.id.checkBox);

        //checkbox to quit the activity
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ListItemsActivity.this);

                // 2. Chain together various setter methods to set the dialog characteristics
                 builder.setMessage(R.string.dialog_message) //Add a dialog message to strings.xml (To Quit or Not to Quit?)
                        .setTitle(R.string.dialog_title) //Would you like to quit?
                         //click OK
                         .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {  //Converts buttons to uppercase

                            public void onClick(DialogInterface dialog, int id) {
                                // User clicked OK button
                                Intent resultIntent = new Intent( );
                                resultIntent.putExtra("Response", getResources().getString(R.string.quit_message));  //displays the string from quit_message
                                setResult(Activity.RESULT_OK, resultIntent);

                                finish();


                            }
                        })
                         //click Cancel
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                                dialog.dismiss();
                            }
                        })
                        .show();

            }//end of onCheckChage
        });//end of OnCheckedChangeListener


    }





    //set thumbnail to image
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            goToCamera.setImageBitmap(imageBitmap);
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
