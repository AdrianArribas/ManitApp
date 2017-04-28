package gonzalberdricorp.manitapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import JavaBean.DatosPersona;
import modelo.GestionComunicacion;

public class Mapa extends AppCompatActivity {
    String nombre;
    String busqueda;
    double longitud,latitud;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);
        //nombre=intent.getStringExtra("nombre");
        //longitud=Double.parseDouble(intent.getStringExtra("longitud"));
        //latitud=Double.parseDouble(intent.getStringExtra("latitud"));

        //recuperamos el valor del nombre enviado
        //desde la actividad principal
        Intent intent=this.getIntent();
        busqueda=intent.getStringExtra("busqueda");

        ComunicacionMAPA1 com=new ComunicacionMAPA1();
        com.execute();
        longitud=40.397070;
        latitud=-3.743800;

        //obtenemos una referencia al fragmento que contiene el mapa
        FragmentManager fm=this.getSupportFragmentManager();
        SupportMapFragment smf=(SupportMapFragment)fm.findFragmentById(R.id.mapa);
        System.out.println("!!!!!!!!!!!!!!!!!carga del mapa");

        //------------------clase anonima--------------------------
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


    //------------------------------------TAREAS ASINCRONAS DE COMUNICACIONES-------------

    class ComunicacionMAPA1 extends AsyncTask<Void,Void,ArrayList<DatosPersona>> {
        @Override
        protected void onPostExecute(ArrayList<DatosPersona> datosPersonas) {
            super.onPostExecute(datosPersonas);

        }

        @Override
        protected ArrayList<DatosPersona> doInBackground(Void... params) {
            ArrayList<DatosPersona> Arraydt=new ArrayList<>();
            //nos conectamos con el servidor para pedirle la lista de personas
            GestionComunicacion gcom=new GestionComunicacion();
            Arraydt=gcom.buscarProfesionales(busqueda);
            return Arraydt;
        }
    }
    //----------------------------------------------------------------------------------
}
