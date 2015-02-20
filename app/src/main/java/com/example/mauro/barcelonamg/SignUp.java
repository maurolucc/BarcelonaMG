package com.example.mauro.barcelonamg;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


public class SignUp extends ActionBarActivity {

    private Button signupButton;
    private EditText password, username, passwordCheck, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initComponents();

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name= username.getText().toString();
                String pass= password.getText().toString();
                String passwordcheck= passwordCheck.getText().toString();
                String em= email.getText().toString();

                //Tractem els casos en que l'usuari no ha escrit algun dels camps
                if(name.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "THERE'S AN EMPTY INPUT, PLEASE FILL THE NAME!", Toast.LENGTH_SHORT).show();
                }else if(pass.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "THERE'S AN EMPTY INPUT, PLEASE FILl THE PASSWORD!", Toast.LENGTH_SHORT).show();
                }else if(passwordcheck.isEmpty()){
                    Toast.makeText(getApplicationContext(), "THERE'S AN EMPTY INPUT, PLEASE FILL THE PASSWORD CHECK!", Toast.LENGTH_SHORT).show();
                }else if(em.isEmpty()){
                    Toast.makeText(getApplicationContext(), "THERE'S AN EMPTY INPUT, PLEASE FILL THE EMAIL !", Toast.LENGTH_SHORT).show();
                }else if (!passwordcheck.equals(pass)) {
                    Toast.makeText(getApplicationContext(), "The password doesn't match!", Toast.LENGTH_SHORT).show();
                }else{

                    ParseUser user = new ParseUser();
                    user.setUsername(name);
                    user.setPassword(pass);
                    user.setEmail(em);

                    user.signUpInBackground(new SignUpCallback() {
                        public void done(ParseException e) {
                            if (e == null) {
                                // Hooray! Let them use the app now.
                                SignUp.this.finish();

                            } else {
                                Toast.makeText(getApplicationContext(), "Sign up didn't succeed.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });

    }

    public void initComponents(){
        signupButton = (Button) findViewById(R.id.signUp);
        password= (EditText) findViewById(R.id.password);
        passwordCheck= (EditText) findViewById(R.id.passwordchecker);
        username= (EditText) findViewById(R.id.userName);
        email= (EditText) findViewById(R.id.email);
    }
}
