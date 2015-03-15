package com.example.mauro.barcelonamg;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class ItemClickat extends ActionBarActivity {
    private String value;
    private ParseObject item;

    private TextView nom,text;
    private ImageView icono,imatge;
    private Button likeBut, comBut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_clickat);

        nom = (TextView) findViewById(R.id.nomGran);
        text = (TextView) findViewById(R.id.textGran);
        icono = (ImageView) findViewById(R.id.iconoGran);
        imatge = (ImageView) findViewById(R.id.imatgeGran);
        likeBut = (Button) findViewById(R.id.likeButton2);
        comBut = (Button) findViewById(R.id.commentButton2);

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

        imatge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent llencarFlayerFullScreen= new Intent(getApplicationContext(),FlayerFullScreen.class);
                llencarFlayerFullScreen.putExtra("KEY", value);
                startActivity(llencarFlayerFullScreen);
            }
        });

        likeBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseQuery<ParseObject> query = ParseQuery.getQuery("Leisure");
                query.getInBackground(value, new GetCallback<ParseObject>() {
                    public void done(ParseObject object, ParseException e) {
                        if (e == null) {
                            object.increment("numLikes");
                            object.saveInBackground();
                            Number numlikes = object.getNumber("numLikes");
                            likeBut.setText("Likes " + numlikes);
                        } else {
                            Log.e("error like", "no sha pogut posar el me gusta");
                        }
                    }
                });
            }
        });

        comBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent llencarItemClick = new Intent(getApplicationContext(), Comments.class);
                llencarItemClick.putExtra("KEY", value);
                startActivity(llencarItemClick);
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
