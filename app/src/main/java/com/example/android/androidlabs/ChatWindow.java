package com.example.android.androidlabs;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
                list.add(messageBox.getText().toString());
                messageWindowAdapter.notifyDataSetChanged();
                messageBox.setText("");

            }
        });

    }//end onCreate()

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
