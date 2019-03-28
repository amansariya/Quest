package com.example.trippify;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Main5Activity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    TextView display1,display2,display;
    long amt,amt1,amt2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        display=(TextView) findViewById(R.id.textView7);
        display1=(TextView)findViewById(R.id.textView11);
        display2=(TextView)findViewById(R.id.textView13);
        firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore=FirebaseFirestore.getInstance();

        DocumentReference docRef = firebaseFirestore.collection("Users").document(user.getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        amt=document.getLong("Transportation");
                        String ans=Long.toString(amt);
                        display.setText("Rs. "+ans);

                        amt1=document.getLong("Shopping");
                        String ans1=Long.toString(amt1);
                        display1.setText("Rs. "+ans1);

                        amt2=document.getLong("Food");
                        String ans2=Long.toString(amt2);
                        display2.setText("Rs. "+ans2);

                    }
                } else {
                    String TAG="log";
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

    }


    public void addExpense(View v)
    {
        Intent in1=new Intent("com.example.trippify.ADDEXPENSE");
        startActivity(in1);
    }
}