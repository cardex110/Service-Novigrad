package com.example.authenticatorapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class Fill_Activity extends AppCompatActivity {

    TextView service;
    TextView forms;
    TextView documents;
    Button submit;
    EditText formsResponse;
    DBHelper DB;

    private ImageView image1,image2;
    private final int CODE_IMG_GALLERY =1;
    private final int CODE_MULTIPLE_IMG_GALLERY =2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_);

        init();
        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 1 image
                startActivityForResult(Intent.createChooser(new Intent().setAction(Intent.ACTION_GET_CONTENT)
                        .setType("image/*"),"Select an image"),CODE_IMG_GALLERY);
            }
        });

        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Multiple images
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"select multiple images"),CODE_MULTIPLE_IMG_GALLERY);

            }
        });

        DB = new DBHelper(this);
        service = (TextView) findViewById(R.id.textViewService);
        forms = (TextView) findViewById(R.id.textViewForms);
        documents = (TextView) findViewById(R.id.textViewDocuments);
        submit = findViewById(R.id.buttonSubmit);
        formsResponse = (EditText) findViewById(R.id.editTextTextMultiLine);

        final String serviceName = getIntent().getStringExtra("service");
        viewData(serviceName);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reponse = formsResponse.getText().toString().trim();
                if(reponse.isEmpty()){
                    Toast.makeText(Fill_Activity.this,"please enter all details",Toast.LENGTH_SHORT).show();
                }else{
                    String mail =getIntent().getStringExtra("email");
                    DB.sendRequest(serviceName,getIntent().getStringExtra("username"),"Pending",mail);
                    Toast.makeText(Fill_Activity.this,"Submitted successfully",Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

        });

    }
    private void viewData(String s){
        Cursor cursor = DB.viewService(s);

        if (cursor.getCount() ==0){
            Toast.makeText(this,"No data to show", Toast.LENGTH_SHORT).show();

        }else {
            if (cursor.moveToNext()) {
                service.setText(cursor.getString(0));
                forms.setText(cursor.getString(1));
                documents.setText(cursor.getString(2)+" "+"(click on the image below to upload)");
            }
        }
    }

    private void init(){
        this.image1=findViewById(R.id.image1);
        this.image2=findViewById(R.id.image2);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CODE_IMG_GALLERY && resultCode == RESULT_OK){
            Uri imageUri = data.getData();
            if(imageUri != null){
                image1.setImageURI(imageUri);
            }

        }else if(requestCode == CODE_MULTIPLE_IMG_GALLERY && resultCode == RESULT_OK){
            ClipData clipData = data.getClipData();
            if(clipData!=null){
                image1.setImageURI(clipData.getItemAt(0).getUri());
                image2.setImageURI(clipData.getItemAt(1).getUri());


                for(int i=0; i<clipData.getItemCount();i++){
                    ClipData.Item item = clipData.getItemAt(i);
                    Uri uri = item.getUri();
                    Log.e("my images",uri.toString());
                }

            }
        }
    }
}