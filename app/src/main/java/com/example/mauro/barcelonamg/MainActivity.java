package com.example.mauro.barcelonamg;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.mauro.barcelonamg.adapter.DiscotecaListAdapter;
import com.example.mauro.barcelonamg.model.Discoteca;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.List;


public class MainActivity extends ActionBarActivity{

    private ListView mListView;
    private ParseQueryAdapter<Discoteca> todoListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ParseObject.registerSubclass(Discoteca.class);

        if (savedInstanceState == null) {
            // Enable Local Datastore.
            Parse.enableLocalDatastore(getApplicationContext());
            Parse.initialize(this, "3PZjEECwDOYgxpqkzRPseHbistE33bEBC0cDl8DC", "m5uh4bkVr9HWkZ5jhK5tfcscBuA7xtyeYCILI1t0");
        }

        mListView = (ListView) findViewById(R.id.listView);

        // Set up the Parse query to use in the adapter
        ParseQueryAdapter.QueryFactory<Discoteca> factory = new ParseQueryAdapter.QueryFactory<Discoteca>() {
            public ParseQuery<Discoteca> create() {
                ParseQuery<Discoteca> query = Discoteca.getQuery();
                query.fromLocalDatastore();
                return query;
            }
        };

        // Set up the adapter
        todoListAdapter = new DiscotecaListAdapter(this, factory);

        // Attach the query adapter to the view
        mListView.setAdapter(todoListAdapter);

        ParseUser currentUser = ParseUser.getCurrentUser();


        if(currentUser==null){
            Intent intent= new Intent(getApplicationContext(),Login.class);
            startActivity(intent);
            loadFromParse();
        }
    }

    private void loadFromParse() {
        ParseQuery<Discoteca> query = Discoteca.getQuery();
        query.findInBackground(new FindCallback<Discoteca>() {
            public void done(List<Discoteca> todos, ParseException e) {
                if (e == null) {
                    ParseObject.pinAllInBackground(todos,
                            new SaveCallback() {
                                public void done(ParseException e) {
                                    if (e == null) {
                                        todoListAdapter.loadObjects();
                                    } else {
                                        Log.i("TodoListActivity",
                                                "Error pinning todos: "
                                                        + e.getMessage());
                                    }
                                }
                            });
                } else {
                    Log.i("TodoListActivity",
                            "loadFromParse: Error finding pinned todos: "
                                    + e.getMessage());
                }
            }
        });
    }
}
