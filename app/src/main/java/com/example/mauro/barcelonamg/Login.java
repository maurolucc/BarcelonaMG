package com.example.mauro.barcelonamg;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseUser;


public class Login extends ActionBarActivity {

    private Button signupButton, loginButton;
    private EditText passwordEditText, usernameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initComponents();

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent mIntent = new Intent(getApplicationContext(),SignUp.class);
                    startActivity(mIntent);
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(getApplicationContext(),"Empty input", Toast.LENGTH_SHORT).show();
                } else {
                    ParseUser.logInInBackground(username, password, new LogInCallback() {
                        public void done(ParseUser user, com.parse.ParseException e) {
                            if (user != null) {
                                //Toast.makeText(getApplicationContext(), "logged in", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), "Signup failed.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    public void initComponents(){
        signupButton = (Button) findViewById(R.id.signUp);
        loginButton= (Button) findViewById(R.id.login);
        passwordEditText= (EditText) findViewById(R.id.password);
        usernameEditText= (EditText) findViewById(R.id.userName);
    }

}
