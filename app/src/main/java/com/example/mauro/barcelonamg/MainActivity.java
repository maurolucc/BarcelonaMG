package com.example.mauro.barcelonamg;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.apache.commons.logging.Log;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    private ListView mListView;
    private ArrayAdapter<String> adapter;
    private List<ParseObject> onlineData;
    private ArrayList<String> spots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView) findViewById(R.id.listView);
        spots=new ArrayList<>();

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "3PZjEECwDOYgxpqkzRPseHbistE33bEBC0cDl8DC", "m5uh4bkVr9HWkZ5jhK5tfcscBuA7xtyeYCILI1t0");


        fetchData("Name");

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
             //expand the Item Selected in a second Activity
            }
        });

        mListView.setAdapter(adapter);

    }

    public void fetchData(String key){
        ParseQuery<ParseObject> query =  ParseQuery.getQuery("Leisure");
        try{
            onlineData= query.find();
            for(ParseObject object: onlineData){
                String clau= object.get(key).toString();
                spots.add(clau);
            }
            adapter= new ArrayAdapter<String>(getApplicationContext(),R.layout.custom_item, R.id.text1,spots);

        }catch(ParseException e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"Error on fetching data",Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
