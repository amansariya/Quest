package com.example.trippify;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap;
    LocationRequest request;
    GoogleApiClient client;
    LatLng latLngcurrent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    public void findRestaurants(View v) {


            StringBuilder stringBuilder = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");

        /*stringBuilder.append("location="+latLngcurrent.latitude +"," +latLngcurrent.longitude);
        stringBuilder.append("&radius"+1000);
        stringBuilder.append("&sensor=true");
        stringBuilder.append("&keyword="+"restaurant");
        stringBuilder.append("&key="+getResources().getString(R.string.google_places_key));*/

            stringBuilder.append("location=").append(latLngcurrent.latitude).append(",").append(latLngcurrent.longitude);
            stringBuilder.append("&radius=1000");
            stringBuilder.append("&type=restaurant");
            stringBuilder.append("&key=").append(getResources().getString(R.string.google_places_key));

            String url = stringBuilder.toString();

            Object dataTransfer[] = new Object[2];
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;

            GetNearbyPlaces getNearbyPlaces = new GetNearbyPlaces();
            getNearbyPlaces.execute(dataTransfer);

    }

    public void findTransport(View v) {


        StringBuilder stringBuilder = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");

        /*stringBuilder.append("location="+latLngcurrent.latitude +"," +latLngcurrent.longitude);
        stringBuilder.append("&radius"+1000);
        stringBuilder.append("&sensor=true");
        stringBuilder.append("&keyword="+"restaurant");
        stringBuilder.append("&key="+getResources().getString(R.string.google_places_key));*/

        stringBuilder.append("location=").append(latLngcurrent.latitude).append(",").append(latLngcurrent.longitude);
        stringBuilder.append("&radius=1000");
        stringBuilder.append("&type=bus_stations");
        stringBuilder.append("&key=").append(getResources().getString(R.string.google_places_key));

        String url = stringBuilder.toString();

        Object dataTransfer[] = new Object[2];
        dataTransfer[0] = mMap;
        dataTransfer[1] = url;

        GetNearbyPlaces getNearbyPlaces = new GetNearbyPlaces();
        getNearbyPlaces.execute(dataTransfer);

    }

    public void findHospitals(View v) {


        StringBuilder stringBuilder = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");

        /*stringBuilder.append("location="+latLngcurrent.latitude +"," +latLngcurrent.longitude);
        stringBuilder.append("&radius"+1000);
        stringBuilder.append("&sensor=true");
        stringBuilder.append("&keyword="+"restaurant");
        stringBuilder.append("&key="+getResources().getString(R.string.google_places_key));*/

        stringBuilder.append("location=").append(latLngcurrent.latitude).append(",").append(latLngcurrent.longitude);
        stringBuilder.append("&radius=1000");
        stringBuilder.append("&type=hospital");
        stringBuilder.append("&key=").append(getResources().getString(R.string.google_places_key));

        String url = stringBuilder.toString();

        Object dataTransfer[] = new Object[2];
        dataTransfer[0] = mMap;
        dataTransfer[1] = url;

        GetNearbyPlaces getNearbyPlaces = new GetNearbyPlaces();
        getNearbyPlaces.execute(dataTransfer);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.setTrafficEnabled(false);
        mMap.setIndoorEnabled(false);
        mMap.setBuildingsEnabled(false);
        mMap.getUiSettings().setZoomControlsEnabled(true);

        client = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        client.connect();

    }

    @Override
    public void onLocationChanged(Location location) {
        if(location==null)
        {
            Toast.makeText(getApplicationContext(),"Location not found,",Toast.LENGTH_LONG).show();
        }
        else
        {
            latLngcurrent = new LatLng(location.getLatitude(),location.getLongitude());
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLngcurrent,15);
            mMap.animateCamera(update);
            MarkerOptions options = new MarkerOptions();
            options.position(latLngcurrent);
            options.title("Current Location");
            mMap.addMarker(options);
        }

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        request = new LocationRequest().create();
        request.setInterval(1000);
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(client, request, this);

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
