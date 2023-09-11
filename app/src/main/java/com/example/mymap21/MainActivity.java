package com.example.mymap21;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.libraries.places.api.Places;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnMap;

    Button btnMuestraMapa;

    Button exportarImg;

    public static final int REQUEST_CODE = 1;

    TextView txtRetorno;

    ImageView imgMapa;

    String LAT;
    String LNG;


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

        txtRetorno = findViewById(R.id.txt_retorno);

        // OBTENER MAPA
        imgMapa = findViewById(R.id.img_lugar);

        btnMuestraMapa = findViewById(R.id.boton_mostrar_imagen);
        btnMuestraMapa.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //descargar mapa
                String latitude = "";
                String longitude = "";
                String url = "http://maps.google.com/maps/api/staticmap?center=" + latitude + "," + longitude + "&zoom=15&size=300x300&sensor=false&key=AIzaSyBZh06cwnenMVLz207Da36kSLosSPtg_RI";

                

                //mostrar mapa
                new GetMap().execute();
            }
        });

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

                this.LAT = inf.get(0);
                this.LNG = inf.get(1);

            }
        }
    }

    private class GetMap extends AsyncTask<Void, Void, Bitmap>{

        @Override
        protected Bitmap doInBackground(Void... voids) {
            //background statements here
            Bitmap image = null;

            //send request to static map api

            try{
                URL mapurl = new URL("https://maps.google.com/maps/api/staticmap?center=" + LAT +"," + LNG + "&zoom=15&size=300x300&sensor=false&key=AIzaSyBZh06cwnenMVLz207Da36kSLosSPtg_RI");

                InputStream stream = (InputStream) mapurl.openConnection().getContent();

                //BitmapFactory.decodeStream(stream);

                image = BitmapFactory.decodeStream(stream);


            } catch (MalformedURLException e){
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //Receive bitmap

            return image;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap){
            super.onPostExecute(bitmap);

            imgMapa.setImageBitmap(bitmap);
        }
    }

}