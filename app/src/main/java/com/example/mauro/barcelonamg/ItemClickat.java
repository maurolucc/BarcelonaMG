package com.example.mauro.barcelonamg;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mauro.barcelonamg.adapter.DiscotecaListAdapter;
import com.example.mauro.barcelonamg.model.Discoteca;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;


public class ItemClickat extends ActionBarActivity {
    private ParseQueryAdapter<Discoteca> todoListAdapter;
    private String value;
    private ParseObject item;

    private TextView nom,text;
    private ImageView icono,imatge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_clickat);

        nom = (TextView) findViewById(R.id.nomGran);
        text = (TextView) findViewById(R.id.textGran);
        icono = (ImageView) findViewById(R.id.iconoGran);
        imatge = (ImageView) findViewById(R.id.imatgeGran);

        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            value = extras.getString("KEY");
        }

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Leisure");
        query.fromLocalDatastore();
        query.getInBackground(value, new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    item = object;
                    Log.e("eeeeee","cccccccccccccccccccccccccccccccccccccccccccccccc");

                    nom.setText(item.getString("Name"));
                    text.setText(item.getString("Descripcio"));

                    Bitmap icon = null;
                    try {
                        byte[] data = item.getParseFile("Icon").getData();
                        icon = BitmapFactory.decodeByteArray(data, 0, data.length);
                    } catch (ParseException t) {
                        t.printStackTrace();
                    }
                    icono.setImageBitmap(icon);

                    Bitmap image = null;
                    try {
                        byte[] data = item.getParseFile("Image").getData();
                        image = BitmapFactory.decodeByteArray(data, 0, data.length);
                    } catch (ParseException g) {
                        g.printStackTrace();
                    }
                    imatge.setImageBitmap(image);

                } else {
                    Log.e("error",e.getMessage());
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_item_clickat, menu);
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
