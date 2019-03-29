package com.example.trippify;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Main2Activity extends AppCompatActivity {

    private EditText username,useremail,userphoneno,userpassword;
    private Button sign;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main2);
        //getSupportActionBar().hide();

        username=(EditText)findViewById(R.id.editText);
        userphoneno=(EditText)findViewById(R.id.editText5);
        useremail=(EditText)findViewById(R.id.editText3);
        userpassword=(EditText)findViewById(R.id.editText4);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();

        sign =(Button)findViewById(R.id.button3);
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()) {

                    final String user_name=username.getText().toString().trim();
                    final String user_no=userphoneno.getText().toString().trim();
                    final String user_email=useremail.getText().toString().trim();
                    String user_password=userpassword.getText().toString().trim();

                    firebaseAuth.createUserWithEmailAndPassword(user_email,user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                Toast.makeText(Main2Activity.this, "Registration Successful", Toast.LENGTH_SHORT).show();

                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                String uid = user.getUid();

                                Map<String,Object> userMap= new HashMap<>();
                                userMap.put("Name", user_name);
                                userMap.put("Phone no",user_no);
                                userMap.put("Email",user_email);
                                userMap.put("Transportation",0);
                                userMap.put("Shopping",0);
                                userMap.put("Food",0);

                                firebaseFirestore.collection("Users").document(uid).set(userMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        startActivity(new Intent(Main2Activity.this,MainActivity.class));
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception a) {
                                        Toast.makeText(Main2Activity.this, "Upload failed: "+a.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }else
                            {
                                FirebaseAuthException e = (FirebaseAuthException )task.getException();
                                Toast.makeText(Main2Activity.this, "Failed Registration: "+e.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }
            }
        });

    }
    private Boolean validate()
    {
        Boolean result=false;
        String name=username.getText().toString();
        String email=useremail.getText().toString();
        String phone=userphoneno.getText().toString();
        String password=userpassword.getText().toString();

        if(name.isEmpty()||email.isEmpty()||phone.isEmpty()||password.isEmpty()||(phone.length()>10)||(phone.length()<10))
        {
            Toast.makeText(this,"Please enter all details correctly",Toast.LENGTH_SHORT ).show();
        }else
        {
            result=true;
        }
        return result;
    }


}
