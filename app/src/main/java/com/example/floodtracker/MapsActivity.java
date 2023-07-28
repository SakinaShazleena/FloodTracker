package com.example.floodtracker;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.floodtracker.Flood;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Vector;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    MarkerOptions marker;
    LatLng location;
    Vector<MarkerOptions> markerOptions;

    final String URL = "http://192.168.0.105/flood/all.php";
    RequestQueue queue;
    Gson gson;
    Flood[] floods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        location = new LatLng(3.0, 101);

        markerOptions = new Vector<>();

        gson = new GsonBuilder().create();

        markerOptions.add(new MarkerOptions().title("Kluang, Johor")
                .position(new LatLng(2.0681, 103.3356))
                .snippet("Air Naik,24/1/2023,1:55AM,Song Yan")
        );

        markerOptions.add(new MarkerOptions().title("Jerantut, Pahang")
                .position(new LatLng(3.9374, 102.3620))
                .snippet("Heavy Rain,1/3/2023,12:23AM,Ashrafar")
        );

        markerOptions.add(new MarkerOptions().title("Alor Gajah, Melaka")
                .position(new LatLng(2.3822, 102.2116))
                .snippet("Banjir ,2/3/2023,5:36AM,Muhammad")
        );

        markerOptions.add(new MarkerOptions().title("Yong Peng, Johor")
                .position(new LatLng(2.0120, 103.0582))
                .snippet("Banjir Kilat,4/3/2023,1:45AM,Yusuf")
        );

        markerOptions.add(new MarkerOptions().title("Batu Pahat, Johor")
                .position(new LatLng(1.8494, 102.9288))
                .snippet("Banjir,6/3/2023,16:40PM, Aisynur")
        );
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


        for (MarkerOptions mark : markerOptions) {
            mMap.addMarker(mark);
        }

        enableMyLocation();

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 8));
        sendRequest();
    }

    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            if (mMap != null) {
                mMap.setMyLocationEnabled(true);
            }
        } else {
            String perms[] = {"android.permission.ACCESS_FINE_LOCATION"};
            ActivityCompat.requestPermissions(this, perms, 200);


        }
    }

    public void sendRequest() {
        queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, onSuccess, onError);
        queue.add(stringRequest);


    }

    public Response.Listener<String> onSuccess = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            floods = gson.fromJson(response, Flood[].class);
            Log.d("floods", "number of floods data point" + floods.length);

            if(floods.length<1){
                Toast.makeText(getApplicationContext(),"Problem retrieving JSON data",Toast.LENGTH_LONG).show();
                return;

            }
            for (Flood info : floods) {
                String title = info.location;
                String snippet = info.description;
                Double lat = Double.parseDouble(String.valueOf(info.latitud));
                Double lng = Double.parseDouble(String.valueOf(info.longitud));


                MarkerOptions marker = new MarkerOptions().position(new LatLng(lat, lng))
                        .title(title)
                        .snippet(snippet)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

                mMap.addMarker(marker);

            }


        }

    };
    public Response.ErrorListener onError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
        }
    };
}




