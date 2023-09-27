package com.example.mymap21;

import android.content.Intent;
import android.graphics.Camera;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.ArrayList;
import java.util.Arrays;

public class MapGeoActivity extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap mMap;
    PlacesClient placesClient;

    Button btnSeleccion;

    ArrayList<String> infoRef;

    FrameLayout mapViewFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_geo);

        //MAPS
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapa);

        mapFragment.getMapAsync(this); //Llama onMapReady()

        //PLACES
        String apikey = "AIzaSyBZh06cwnenMVLz207Da36kSLosSPtg_RI";
        if(!Places.isInitialized()){
            Places.initialize(getApplicationContext(), apikey);
        }
        placesClient = Places.createClient(this);
        //SupportMapFragment mapFragment1
        final AutocompleteSupportFragment autocompleteSupportFragment =
                (AutocompleteSupportFragment) getSupportFragmentManager().findFragmentById(R.id.auto_complete_fragment);

        autocompleteSupportFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.LAT_LNG, Place.Field.NAME));
        autocompleteSupportFragment.setCountries("PE");
        autocompleteSupportFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onError(@NonNull Status status) {

            }

            @Override
            public void onPlaceSelected(@NonNull Place place) {
                final LatLng latLng = place.getLatLng();
                Log.i("PlacesAPI", "onPlaceSelected: " + latLng.latitude + "\n" + latLng.longitude);
                mMap.addMarker(new MarkerOptions().position(latLng).title("lugar"));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));

                //Setear info en string para retorno
                infoRef = new ArrayList<>(); //O Latitud, 1 Longitud, 2 Direccion, 3 Nombre ubic
                //place.
                infoRef.add(""+latLng.latitude);
                infoRef.add(""+latLng.longitude);
                infoRef.add("Direccion:" + place.getAddress());
                infoRef.add(place.getName());
                //String asqweqw = place.get

            }
        });

        //BOTON RETORNO Y CAPTURAR PANTALLA
        btnSeleccion = findViewById(R.id.btn_seleccion);

        mapViewFrame = findViewById(R.id.map_view);
        //SupportMapFragment mapFragment1 = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_view);
        btnSeleccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Intentando capturar el screenshot
                String informacion = "HOLA VENGO DEL SEGUNDO FRAGMENT";
                Intent intent = new Intent();

                intent.putStringArrayListExtra("informacao", infoRef);

                Log.i("RETORNO INFO", "informacion de retorno: " + informacion);

                setResult(RESULT_OK, intent);
                finish();
            }
        });


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(-12.0823349,-76.9768766), 10);

        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setRotateGesturesEnabled(true);
        mMap.moveCamera(cameraUpdate);
    }

    /*
    public class infoReferencia implements Parcelable {
        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(@NonNull Parcel parcel, int i) {

        }
    }
     */
}
