package com.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class Register extends AppCompatActivity {

    Button register,signIn;
    TextInputLayout Uname,Umail,Upassword,Uconfirmpswd,Umob;
    Spinner role;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register=findViewById(R.id.regi);
        signIn=findViewById(R.id.sign_In);
        Uname=(TextInputLayout)findViewById(R.id.name);
        Umail=(TextInputLayout)findViewById(R.id.mail);
        Upassword=(TextInputLayout)findViewById(R.id.pswrd);
        Uconfirmpswd=(TextInputLayout)findViewById(R.id.cnfrpswrd);
        Umob=(TextInputLayout)findViewById(R.id.mob);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Register.this, Login.class);
                startActivity(i);
            }
        });
    }
}