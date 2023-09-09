package com.example.mymap21;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.libraries.places.api.Places;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnMap;

    public static final int REQUEST_CODE = 1;

    TextView txtRetorno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //MAPA
        btnMap = findViewById(R.id.btn_Geo);

        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(MainActivity.this, MapGeoActivity.class);
                //startActivity(intent);
                abrirMapGeoActivity();
            }
        });

        //
        txtRetorno = findViewById(R.id.txt_retorno);

    }

    public void abrirMapGeoActivity(){
        Intent intent = new Intent(MainActivity.this, MapGeoActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("RETORNO INFO", "LLEGO A OnActivityResult");
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE){
            if(resultCode == RESULT_OK){
                //obtener datos de fragment
                //String informacion = data.getStringExtra("informacao");
                ArrayList<String> inf = data.getStringArrayListExtra("informacao");

                Log.i("RETORNO INFO", "informacion de retorno: " + inf.get(0));

                String asdasd = "";

                for(int i = 0; i < inf.size(); i++){
                    asdasd += inf.get(i) + "\n";
                }

                txtRetorno.setText(asdasd);

            }
        }
    }

}