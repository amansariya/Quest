package com.example.trippify;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ADDEXPENSE extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    AutoCompleteTextView ac;
    EditText amount;
    Button add;
    String[] description={"Transportation","Food","Shopping"};
    long am,am1,am2,amount1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addexpense);

        add=(Button)findViewById(R.id.button);
        amount=(EditText)findViewById(R.id.editText2);
        ac=(AutoCompleteTextView)findViewById(R.id.autoCompleteTextView);
        ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.select_dialog_item,description);
        ac.setThreshold(0);
        ac.setAdapter(arrayAdapter);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String desc=ac.getText().toString().trim();
        DocumentReference docRef = firebaseFirestore.collection("Users").document(user.getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        if(desc.equalsIgnoreCase("Transportation"))
                        {
                            am=document.getLong("Transportation");
                        }
                        else if(desc.equalsIgnoreCase("Shopping"))
                            am1=document.getLong("Shopping");
                        else
                            am2=document.getLong("Food");
                    }
                } else {
                    String TAG="log";
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                amount1=Long.parseLong(amount.getText().toString());

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                DocumentReference docRef = firebaseFirestore.collection("Users").document(user.getUid());



                Map<String,Object> userMap= new HashMap<>();

                if(desc.equalsIgnoreCase("Transportation"))
                {
                    am=am+amount1;
                docRef.update("Transportation",am);

                }
                else if(desc.equalsIgnoreCase("Shopping"))
                {  am1=am1+amount1;
                    docRef.update("Shopping",am1);}
                else
                { am2=am2+amount1;
                    docRef.update("Food",am2);}
                Intent int1 = new Intent("com.example.trippify.Main5Activity" );
                startActivity(int1);
            }
        });






    }
}
