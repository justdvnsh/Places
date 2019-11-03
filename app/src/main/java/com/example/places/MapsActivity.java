package com.example.places;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.places.R;
import com.example.places.data.placesContract;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener {

    private GoogleMap mMap;
    LocationManager locationManager;
    LocationListener locationListener;

    private long addNewPlace(List<Address> addresses) {
        Address address = addresses.get(0);
        String feature = address.getFeatureName();
        String admin = address.getAdminArea();
        String subAdmin = address.getSubAdminArea();
        String locality = address.getLocality();
        String thoroughFare = address.getThoroughfare();
        String country = address.getCountryName();
        String postalCode = address.getPostalCode();
        String latitude = String.valueOf(address.getLatitude());
        String longitude = String.valueOf(address.getLongitude());

        ContentValues cv = new ContentValues();
        cv.put(placesContract.placesEntry.COLUMN_FEATURE, feature);
        cv.put(placesContract.placesEntry.COLUMN_ADMIN, admin);
        cv.put(placesContract.placesEntry.COLUMN_SUB_ADMIN, subAdmin);
        cv.put(placesContract.placesEntry.COLUMN_LOCALITY, locality);
        cv.put(placesContract.placesEntry.COLUMN_THOROUGHFARE, thoroughFare);
        cv.put(placesContract.placesEntry.COLUMN_COUNTRY_NAME, country);
        cv.put(placesContract.placesEntry.COLUMN_POSTAL_CODE, postalCode);
        cv.put(placesContract.placesEntry.COLUMN_LATITUDE, latitude);
        cv.put(placesContract.placesEntry.COLUMN_LONGITUDE, longitude);

        return MainActivity.mDb.insert(placesContract.placesEntry.TABLE_NAME, null, cv);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ) {

                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    public void centerMapLocation(LatLng latLng) {
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        // Add a marker in Sydney and move the camera
        mMap.clear();
        LatLng userLocation = new LatLng(latLng.latitude, latLng.longitude);
        mMap.addMarker(new MarkerOptions().position(userLocation).title("Hello"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 10));
    }

    public void getAddress(LatLng latLng) {

        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

        try {

            List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);

            if ( addresses != null && addresses.size() > 0 ) {

                Log.i("Address", addresses.get(0).toString());
                addNewPlace(addresses);
                MainActivity.mAdapter.swapCursor(MainActivity.getAllPlaces());
                Toast.makeText(this, "Location Saved" , Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMapLongClick(LatLng latLng) {

        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(latLng).title(String.valueOf(latLng.latitude + " " + String.valueOf(latLng.longitude))));
        getAddress(latLng);

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapLongClickListener(this);
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        Intent intent = getIntent();
        if (intent.hasExtra("lat") && intent.hasExtra("long")) {
            LatLng latLng = new LatLng(intent.getDoubleExtra("lat", 0.0), intent.getDoubleExtra("long", 0.0));
            centerMapLocation(latLng);
        } else {

            locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            locationListener = new LocationListener() {

                @Override
                public void onLocationChanged(final Location location) {
                    Log.i("location", location.toString());

                    LatLng latlngLocation = new LatLng(location.getLatitude(), location.getLongitude());
                    getAddress(latlngLocation);
                    centerMapLocation(latlngLocation);

                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {
                    // if the location status is on or off
                }

                @Override
                public void onProviderEnabled(String s) {
                    // if GPS is enabled
                }

                @Override
                public void onProviderDisabled(String s) {
                    // if GPS is disabled
                }
            };


            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

            } else {

                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

                Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (lastKnownLocation != null) {
                    LatLng latLngLastKnown = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
                    centerMapLocation(latLngLastKnown);
                }
            }

        }
    }
}
