package com.example.authenticatorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Register extends AppCompatActivity {
    EditText mFullName, mEmail, mPassword;
    TextView mGoToLogin;
    Button mRegisterBtn;
    Spinner mSpinner;
    DBHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setupUIViews();
        DB = new DBHelper(this);


        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    //Upload data to database
                    String name = mFullName.getText().toString().trim();
                    String email = mEmail.getText().toString().trim();
                    String password = mPassword.getText().toString().trim();
                    Spinner mSpinner = findViewById(R.id.spinner);
                    String accountType = mSpinner.getSelectedItem().toString();
                    Boolean checkemail = DB.checkemail(email);
                    if(checkemail==false){
                        Boolean insert = DB.insertData(email,name,password,accountType);
                        if(insert==true){
                            Toast.makeText(Register.this,"Register successfully",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Register.this, Login.class);//Login will change into MainActivity
                            intent.putExtra("username",name);
                            intent.putExtra("email",email);
                            intent.putExtra("job",accountType);
                            startActivity(intent);
                        }else{
                            Toast.makeText(Register.this,"Register failed",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(Register.this,"User already existed, please sign in",Toast.LENGTH_SHORT).show();
                    }


                }else {
                    Toast.makeText(Register.this,"Please enter valid email",Toast.LENGTH_SHORT).show();
                }
            }
        });

        mGoToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Going to a new activity to sign up
                Intent intent = new Intent(Register.this , Login.class);
                startActivity(intent);
            }
        });

    }

    @SuppressLint("WrongViewCast")
    protected void setupUIViews(){
        mFullName = findViewById(R.id.fullName);
        mEmail = findViewById(R.id.Email);
        mPassword = findViewById(R.id.password);
        mRegisterBtn = findViewById(R.id.registerBtn);
        mGoToLogin = findViewById(R.id.goToLogin);
        mSpinner = findViewById(R.id.spinner);


    }


    public boolean isValidEmail(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher =pattern.matcher(email);
        return matcher.matches();
    }

    private Boolean validate(){
        Boolean result = false;

        String name = mFullName.getText().toString().trim();
        String email = mEmail.getText().toString().trim();
        String password = mPassword.getText().toString().trim();
        Spinner mSpinner = findViewById(R.id.spinner);
        String accountType = mSpinner.getSelectedItem().toString();

        if(name.isEmpty() || email.isEmpty() || password.isEmpty()){
            Toast.makeText(this,"Please enter all details", Toast.LENGTH_SHORT).show();
        } else {
            result = true;
        }
        return result&&isValidEmail(email);

    }


}