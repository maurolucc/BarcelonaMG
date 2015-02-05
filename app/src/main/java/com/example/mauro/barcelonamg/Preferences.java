package com.example.mauro.barcelonamg;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class Preferences extends ActionBarActivity {

    private Button Bar,Disco,Festival;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);
        Bar = (Button) findViewById(R.id.bars);
        Disco= (Button) findViewById(R.id.discos);
        Festival= (Button) findViewById(R.id.festival);

        Bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("Preference","Bar");
                startActivity(intent);
            }
        });
        Disco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("Preference","Disco");
                startActivity(intent);
            }
        });
        Festival.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("Preference","Festival");
                startActivity(intent);
            }
        });

    }
}
