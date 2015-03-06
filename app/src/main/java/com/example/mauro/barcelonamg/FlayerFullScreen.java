package com.example.mauro.barcelonamg;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;


public class FlayerFullScreen extends ActionBarActivity {
    private String value;
    private ParseObject item;

    private ImageView imatge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flayer_full_screen);


        imatge = (ImageView) findViewById(R.id.imatgeFlayer);

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

                    Bitmap image = null;
                    try {
                        byte[] data = item.getParseFile("Image").getData();
                        image = BitmapFactory.decodeByteArray(data, 0, data.length);
                    } catch (ParseException g) {
                        g.printStackTrace();
                    }
                    imatge.setImageBitmap(image);

                } else {
                    Log.e("error", e.getMessage());
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_flayer_full_screen, menu);
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
