package com.example.mauro.barcelonamg;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mauro.barcelonamg.adapter.CustomCommentAdapter;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;


public class Comments extends ActionBarActivity {
    private ListView llista;
    private EditText comentari;
    private Button send;

    private String value;
    private CustomCommentAdapter urgent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        llista = (ListView) findViewById(R.id.llistacomments);
        comentari = (EditText) findViewById(R.id.write);
        send = (Button) findViewById(R.id.send);

        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            value = extras.getString("KEY");
        }

        // Set the ListActivity's adapter to be the PQA
        urgent = new CustomCommentAdapter(this,value);

        llista.setAdapter(urgent);
        urgent.loadObjects();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(comentari.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Edit Text Empty", Toast.LENGTH_SHORT).show();

                }else{
                    ParseObject testObject = new ParseObject("Comments");
                    String qui = ParseUser.getCurrentUser().getUsername();
                    String que = comentari.getText().toString();

                    testObject.put("user", qui);
                    testObject.put("comment", que);
                    testObject.put("referenceObjectId", value);
                    try {
                        testObject.save();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    testObject.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if(e==null){
                                comentari.setText("");
                                urgent.loadObjects();
                            }else{
                                Toast.makeText(getApplicationContext(), "No connection", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_comments, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
