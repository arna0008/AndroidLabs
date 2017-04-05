package com.example.android.androidlabs;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatWindow extends AppCompatActivity {

    ListView messageWindow;
    EditText messageBox;
    Button sendButton;
    ArrayList<String> list;
    ChatAdapter messageWindowAdapter;

    //for temp object
    ChatDatabaseHelper temp;
    SQLiteDatabase db;

    //for logging purposed
    static final String ACTIVITY_NAME = "ChatWindow";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        /* Initialize Chat Window Objects */
        messageWindow = (ListView) findViewById(R.id.message_window);
        messageBox = (EditText) findViewById(R.id.message_box);
        sendButton = (Button) findViewById(R.id.send_button);
        list = new ArrayList<String>();

         messageWindowAdapter = new ChatAdapter(this);  //set the data source of the listView to be a new ChatAdapter object:
         messageWindow.setAdapter(messageWindowAdapter);

        /* When button is clicked,  */
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //get text in messageBox and add it to the arrayList
                String typedText = messageBox.getText().toString();
                list.add(typedText);
                messageWindowAdapter.notifyDataSetChanged();
                messageBox.setText("");

                //using a ContentValues object to put the new message into the database **note, ID is auto-inc so no need to specify
                ContentValues values = new ContentValues();
                values.put(ChatDatabaseHelper.KEY_MESSAGE, typedText);
                db.insert(ChatDatabaseHelper.TABLE_NAME, null, values);

            }
        });




        //temp ChatDatabaseHelperObject, which gets a write-able database and stores that as an instance variable
        temp = new ChatDatabaseHelper(this);
        db = temp.getWritableDatabase();

        //after opening the database, execute a query for any existing chat messages and add them into ArrayList of messages from Lab 4
        //Cursor Object
        Cursor cursor = db.query(false, ChatDatabaseHelper.TABLE_NAME, new String[]{ChatDatabaseHelper.KEY_MESSAGE}, null, null, null, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE);

        while(!cursor.isAfterLast()){
            String text = cursor.getString(columnIndex);
            list.add(text);
            Log.i(ACTIVITY_NAME, "SQL MESSAGE: " + cursor.getString( cursor.getColumnIndex( ChatDatabaseHelper.KEY_MESSAGE) ) );
            Log.i(ACTIVITY_NAME, "Cursor's column count = " + cursor.getColumnCount() );
            cursor.moveToNext();
        }

        //for loop to print out column name
        for(int i = 0; i < cursor.getColumnCount(); i++){
            Log.i(ACTIVITY_NAME, "COLUMN NAME = " + cursor.getColumnName(i));
        }

        cursor.close(); //close cursor after getting column name


    }//end onCreate()


    //close db from onCreate(), and call onDestroy from superclass
    protected void onDestroy(){
        super.onDestroy();
        db.close();
    }

        /*Inner class for ChatAdapter */
        class ChatAdapter extends ArrayAdapter<String>{

            public ChatAdapter(Context ctx){
                super(ctx, 0);
            }

            public int getCount(){
                return list.size();//returns number of strings in ArrayList Object
            }

            public String getItem(int position){
                return list.get(position); //returns the item to show in list at specified position
            }


            public View getView(int position, View convertView, ViewGroup parent){

                LayoutInflater inflater = ChatWindow.this.getLayoutInflater();

                //this will recreate the View in the resource file
                View result = null;
                if(position%2 == 0) //if even, inflate incoming chat
                    result = inflater.inflate(R.layout.chat_row_incoming, null);
                else //else, outgoing
                    result = inflater.inflate(R.layout.chat_row_outgoing, null);

                TextView message = (TextView) result.findViewById(R.id.message_text);
                message.setText(getItem(position));//get the string at position
                    return result;
            }
        }//end inner class ChatAdapter



}//end class ChatWindow
