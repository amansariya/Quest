package com.example.trippify;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Main3Activity extends AppCompatActivity implements LocationListener{

    TextView time_show;
    TextView locationText;
    LocationManager locationManager;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    TextView name;
    String username;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        time_show = (TextView) findViewById(R.id.time_disp);

        firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore=FirebaseFirestore.getInstance();
        name=(TextView)findViewById(R.id.textView3);


        DocumentReference docRef = firebaseFirestore.collection("Users").document(user.getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        username=document.getString("Name");
                        name.setText("Welcome "+username);



                    }
                } else {
                    String TAG="log";
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });


        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        time_show.setText(currentDateTimeString);
        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);

        }
        getLocation();

    }


        public void getLocation() {
            try {
                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5, this);
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }

    @Override
    public void onLocationChanged(Location location) {
        //locationText.setText("Latitude: " + location.getLatitude() + "\n Longitude: " + location.getLongitude());

        /*try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            locationText.setText(""+locationText.getText() + "\n" + addresses.get(0).getAddressLine(0) + ", " +
                    addresses.get(0).getAddressLine(1) + ".");
        } catch (Exception e) {

        }*/
    }


    public void weaWeather(View v)
    {
        Intent int1 = new Intent("com.example.trippify.Weather2");
        startActivity(int1);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(Main3Activity.this, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();

    }

    public void checkWeather(View v){
        Intent int1 =new Intent("com.example.trippify.Main4Activity");
        startActivity(int1);
    }

    public void viewAmenities(View v){
        Intent int1 =new Intent("com.example.trippify.MapsActivity");
        startActivity(int1);
}

    public void checkBudget(View v){
        Intent int1 =new Intent("com.example.trippify.Main5Activity");
        startActivity(int1);
    }

    public void blogs(View v)
    {
        Intent int1 = new Intent("com.example.trippify.Blogs");
        startActivity(int1);
    }
}


