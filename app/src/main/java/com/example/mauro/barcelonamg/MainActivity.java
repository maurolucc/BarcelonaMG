package com.example.mauro.barcelonamg;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mauro.barcelonamg.adapter.MyCustomAdapter;
import com.example.mauro.barcelonamg.interfices.NotifyFetch;
import com.example.mauro.barcelonamg.model.Dades;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity implements NotifyFetch {

    private ListView mListView;
    private MyCustomAdapter adapter;
    private List<ParseObject> onlineData;
    private List<Dades> dades;

    private int i = 0;

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
        adapter= new MyCustomAdapter(getApplicationContext(), dades, this);
        mListView.setAdapter(adapter);

        if (savedInstanceState == null) {
            // Enable Local Datastore.
            Parse.enableLocalDatastore(this);
            Parse.initialize(this, "3PZjEECwDOYgxpqkzRPseHbistE33bEBC0cDl8DC", "m5uh4bkVr9HWkZ5jhK5tfcscBuA7xtyeYCILI1t0");
        }

        ParseUser currentUser = ParseUser.getCurrentUser();


        if(currentUser==null){
            Intent intent= new Intent(getApplicationContext(),Login.class);
            startActivity(intent);
        }

        try {
            adapter.posarLimit(ParseQuery.getQuery("Leisure").count());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        fetchData(NAME, DESCRIPCIO, TYPE, ICON, IMAGE);
    }

    public void fetchData(String key1,String key2, String key3, String key4, String key5){
        ParseQuery<ParseObject> query =  ParseQuery.getQuery("Leisure").setLimit(10);

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

                dades.add(new Dades(nom,descrip,icn,img,type));
            }
            adapter.notifyDataSetChanged();

        }catch(ParseException e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"Error on fetching data",Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void notifyFetchOnline() {
        ArrayList<Dades> a = new ArrayList<>();
        try{
            for(ParseObject object: ParseQuery.getQuery("Leisure").setLimit(10).setSkip(10).find()){
                String nom= object.get(NAME).toString();
                String descrip= object.get(DESCRIPCIO).toString();
                String type= object.get(TYPE).toString();

                ParseFile icn2 = object.getParseFile(ICON);
                byte[] data = icn2.getData();
                Bitmap icn = BitmapFactory.decodeByteArray(data, 0, data.length);

                ParseFile img2 = object.getParseFile(IMAGE);
                byte[] date = img2.getData();
                Bitmap img = BitmapFactory.decodeByteArray(date,0,date.length);


                a.add(new Dades(nom, descrip, icn, img, type));
            }

            adapter.addAll(a);

        }catch(ParseException e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"Error on notifyFetchOnline",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void epp() {
        Toast.makeText(getApplicationContext(), "end of list", Toast.LENGTH_SHORT).show();
    }
}
