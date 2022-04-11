package com.example.crud_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.crud_android.DBHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class SecondActivity extends AppCompatActivity {
    private Intent i ;
    private String s;
    private EditText txtEmail,txtNumber,txtUsername;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String user="Users";
    private String userName,email,number;
    private Button button;

    @Override
    public void onBackPressed() {
        Intent j = new Intent(SecondActivity.this,MainActivity.class);
        startActivity(j);
        finish();
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        button= findViewById(R.id.btnSubmit);
        txtEmail = (EditText)findViewById(R.id.editTxtEmail);
        txtNumber =(EditText)findViewById(R.id.editTxtNumber);
        txtUsername =(EditText)findViewById(R.id.editTxtUsername);
        firebaseDatabase =FirebaseDatabase.getInstance();
        databaseReference =firebaseDatabase.getReference(user);
        button.setText("Submit");
        i = getIntent();
        s=i.getStringExtra("Button");
        if (s.equals("Create")){
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    userName = txtUsername.getText().toString();
                    email=  txtEmail.getText().toString();
                    number = txtNumber.getText().toString();

                    databaseReference.child(userName).setValue(new DBHelper(userName,email,number));
                    Toast.makeText(SecondActivity.this,"Data Inserted",Toast.LENGTH_SHORT).show();
                    i = new Intent(SecondActivity.this,MainActivity.class);
                    startActivity(i);
                    finish();

                }
            });
        }else if (s.equals("Edit")){
            String temp;
            HashMap hashMap =  new HashMap();
            userName = i.getStringExtra("userName");
            temp = userName;
            email = i.getStringExtra("email");
            number = i.getStringExtra("number");
            txtUsername.setText(userName);
            txtEmail.setText(email);
            txtNumber.setText(number);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    userName = txtUsername.getText().toString();
                    email = txtEmail.getText().toString();
                    number = txtNumber.getText().toString();
                    hashMap.put("userName",userName);
                    hashMap.put("email",email);
                    hashMap.put("number",number);
                    databaseReference.child(temp).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            i = new Intent(SecondActivity.this,MainActivity.class);
                            if (task.isSuccessful()){
                                Toast.makeText(SecondActivity.this,"Data Updated",Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(SecondActivity.this,"Failed to update data",Toast.LENGTH_SHORT).show();
                            }
                            startActivity(i);
                            finish();
                        }
                    });
                }
            });
        }else if (s.equals("Read")){
            userName = i.getStringExtra("userName");
            email = i.getStringExtra("email");
            number = i.getStringExtra("number");
            button.setText("OK");
            txtUsername.setText(userName);
            txtEmail.setText(email);
            txtNumber.setText(number);
            txtEmail.setEnabled(false);
            txtNumber.setEnabled(false);
            txtUsername.setEnabled(false);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    i = new Intent(SecondActivity.this,MainActivity.class);
                    startActivity(i);
                    finish();
                }
            });
        }
    }
}