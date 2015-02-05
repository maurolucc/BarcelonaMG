package com.example.mauro.barcelonamg;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mauro.barcelonamg.adapter.MyCustomAdapter;
import com.example.mauro.barcelonamg.model.Dades;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    private ListView mListView;
    private ArrayAdapter<String> adapter;
    private List<ParseObject> onlineData;
    private List<Dades> dades;
    final private String NAME = "Name";
    final private String TYPE = "Type";
    final private String ICON = "Icon";
    final private String IMAGE = "Image";
    final private String DESCRIPCIO = "Descripcio";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView) findViewById(R.id.listView);
        dades = new ArrayList<>();

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "3PZjEECwDOYgxpqkzRPseHbistE33bEBC0cDl8DC", "m5uh4bkVr9HWkZ5jhK5tfcscBuA7xtyeYCILI1t0");


        fetchData(NAME,DESCRIPCIO,TYPE,ICON,IMAGE);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
             //expand the Item Selected in a second Activity
            }
        });

        mListView.setAdapter(adapter);

    }

    public void fetchData(String key1,String key2, String key3, String key4, String key5){
        ParseQuery<ParseObject> query =  ParseQuery.getQuery("Leisure");
        try{
            onlineData= query.find();
            for(ParseObject object: onlineData){
                String nom= object.get(key1).toString();
                String descrip= object.get(key2).toString();
                String type= object.get(key3).toString();

                ParseFile icn2 = object.getParseFile(key4);
                byte[] data = icn2.getData();
                Bitmap icn = BitmapFactory.decodeByteArray(data, 0, data.length);

                ParseFile img2 = object.getParseFile(key5);
                byte[] date = img2.getData();
                Bitmap img = BitmapFactory.decodeByteArray(date,0,date.length);
                //Bitmap img= (Bitmap) object.get(key5);

                dades.add(new Dades(nom,descrip,icn,img,type));
            }
            adapter= new MyCustomAdapter(getApplicationContext(), dades);

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
