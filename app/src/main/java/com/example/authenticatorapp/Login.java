package com.example.authenticatorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {



    private EditText email;
    private EditText password;
    private Button login;
    private TextView signUp;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.editEmail);
        password =findViewById(R.id.editPassword);
        login = findViewById(R.id.btnLogin);
        signUp = findViewById(R.id.clickSignup);
        DB = new DBHelper(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailEntered = email.getText().toString();
                String passwordEntered = password.getText().toString();


                String username = getIntent().getStringExtra("username");
                String job = getIntent().getStringExtra("job");

                //Cursor cursor = DB.loginCheck(emailEntered);
                //cursor.moveToFirst();



//                String username = getIntent().getStringExtra("username");
//                String job = getIntent().getStringExtra("job");
                //Cursor cursor = DB.loginCheck(emailEntered);
                //cursor.moveToFirst();


                if(emailEntered.isEmpty()){
                    Toast.makeText(Login.this, "Please enter your email", Toast.LENGTH_LONG ).show();
                } else if(passwordEntered.isEmpty()) {
                    Toast.makeText(Login.this, "Please enter your password", Toast.LENGTH_LONG).show();
                }else{
                    Boolean checkemailpassword = DB.checkemailpassword(emailEntered,passwordEntered);
                    if(checkemailpassword==true){
                        Cursor cursor =DB.ViewRole(emailEntered);
                        String role="";
                        while (cursor.moveToNext()){
                            role = cursor.getString(0);
                        }
                        if (role.equals("Employee")){
                            Toast.makeText(Login.this, "Sign in successfully", Toast.LENGTH_LONG).show();
                            //String username = cursor.getString(0);
                            //String job = cursor.getString(1);
                            Intent intent = new Intent(Login.this , employeeWelcome.class);//Register will change into MainActivity
                            intent.putExtra("username",emailEntered);
                            intent.putExtra("job",job);
                            startActivity(intent);
                        }else if (role.equals("Customer")){

                            Toast.makeText(Login.this, "Sign in successfully", Toast.LENGTH_LONG).show();
                            //String username = cursor.getString(0);
                            //String job = cursor.getString(1);
                            Intent intent = new Intent(Login.this , CustomerWelcome.class);//Register will change into MainActivity
                            intent.putExtra("username",username);
                            intent.putExtra("job",job);
                            intent.putExtra("email",emailEntered);
                            startActivity(intent);
                        }


                    }else if((emailEntered.equals("admin")&&passwordEntered.equals("admin"))){
                        Intent intent1 = new Intent(Login.this, adminWelcome.class);
                        startActivity(intent1);
                        Toast.makeText(Login.this, "Sign in successfully", Toast.LENGTH_LONG).show();
                    }

                    else{
                        Toast.makeText(Login.this, "Sign in failed", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Going to a new activity to sign up
                Intent intent = new Intent(Login.this , Register.class);
                startActivity(intent);
            }
        });
    }


}
