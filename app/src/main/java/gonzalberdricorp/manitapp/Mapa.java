package gonzalberdricorp.manitapp;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Mapa extends AppCompatActivity {
    String nombre;
    double longitud,latitud;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);
        Intent intent=this.getIntent();
        //nombre=intent.getStringExtra("nombre");
        //longitud=Double.parseDouble(intent.getStringExtra("longitud"));
        //latitud=Double.parseDouble(intent.getStringExtra("latitud"));
        longitud=40.397070;
        latitud=-3.743800;

        //obtenemos una referencia al fragmento que contiene el mapa
        FragmentManager fm=this.getSupportFragmentManager();
        SupportMapFragment smf=(SupportMapFragment)fm.findFragmentById(R.id.mapa);
        System.out.println("!!!!!!!!!!!!!!!!!carga del mapa");
        smf.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                //posicionamos la cámara en las coordenadas
                //recibidas
                LatLng pos=new LatLng(longitud,latitud);
                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pos,18));
                googleMap.getUiSettings().setZoomControlsEnabled(true);
                //creamos un marcador en la posición indicada
                MarkerOptions marcador=new MarkerOptions();
                marcador.position(pos);
                marcador.title(nombre);
                googleMap.addMarker(marcador);

            }
        });
    }
}
