package com.example.newsmartparkingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.newsmartparkingsystem.Common.Common;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {
    public Marker markerA;
    public Marker markerB;
    public Marker markerC;
    public Marker markerD;
    public Marker markerE;
    public Marker markerF;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

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

        // Add a marker in Sydney and move the camera

        markerA = mMap.addMarker(new MarkerOptions().position(new LatLng(31.480831,74.323236)).title("Model Town"));
        markerB = mMap.addMarker(new MarkerOptions().position(new LatLng(31.462783,74.331247)).title("Kot Lukhpat"));
        markerC = mMap.addMarker(new MarkerOptions().position(new LatLng(31.451717,74.355676)).title("Chungi Amar Sadhu"));
        markerD = mMap.addMarker(new MarkerOptions().position(new LatLng(31.474965, 74.373786)).title("DHA Phase 3"));
        markerE = mMap.addMarker(new MarkerOptions().position(new LatLng(31.504081, 74.365281)).title("Cavalary Ground"));
        markerF = mMap.addMarker(new MarkerOptions().position(new LatLng(31.513684, 74.333072)).title("Gaddafi Stadium"));

            LatLng Model_Town = new LatLng(31.480831, 74.323236);
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15.0f));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Model_Town, 12.0f));




            mMap.setOnInfoWindowClickListener(this);


                   /* mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                        @Override
                        public boolean onMarkerClick(Marker marker) {
                            Intent intent = new Intent(MapsActivity.this, ModelTownInfo.class);
                            startActivity(intent);
                            return false;
                        }
                    });*/


        }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.map_options, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Change the map type based on the user's selection.
        switch (item.getItemId()) {
            case R.id.normal_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                return true;
            case R.id.hybrid_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                return true;
            case R.id.satellite_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                return true;
            case R.id.terrain_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                return true;
            case R.id.userHistory:
                startActivity(new Intent(MapsActivity.this,HistoryActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onInfoWindowClick(Marker marker) {

        String title = marker.getTitle();
        Common.parkingTitle = title;
        Toast.makeText(this, ""+Common.parkingTitle, Toast.LENGTH_SHORT).show();

        if (marker.equals(markerA)) {
            Intent intent = new Intent(MapsActivity.this, ModelTownInfo.class);
            startActivity(intent);
            // action fired when markerA is clicked
        }else if(marker.equals(markerB)){
            Intent intent = new Intent(MapsActivity.this, KotLukhpatInfo.class);
            startActivity(intent);
            // action fired when markerB is clicked
        }else if(marker.equals(markerC)){
            Intent intent = new Intent(MapsActivity.this, ChungiAmarSadhuInfo.class);
            startActivity(intent);
            // action fired when markerC is clicked
        }else if(marker.equals(markerD)){
            Intent intent = new Intent(MapsActivity.this, Dha_Phase_3_Info.class);
            startActivity(intent);
            // action fired when markerD is clicked
        }else if(marker.equals(markerE)){
            Intent intent = new Intent(MapsActivity.this, CavalaryGroundInfo.class);
            startActivity(intent);
            // action fired when markerD is clicked
        }else if(marker.equals(markerF)){
            Intent intent = new Intent(MapsActivity.this, GaddafiStadiumInfo.class);
            startActivity(intent);
            // action fired when markerD is clicked
        }
        

//        openParkingSlotsActivity(title);

    }

    private void openParkingSlotsActivity(String title) {
    }
}
