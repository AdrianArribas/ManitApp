package gonzalberdricorp.manitapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import JavaBean.DatosPersona;
import modelo.GestionComunicacion;

public class Mapa extends AppCompatActivity implements
        GoogleMap.OnMarkerClickListener,
        OnMapReadyCallback {
    String nombre;
    String busqueda;
    double longitud = 40.397070;
    double latitud = -3.743800;
    ArrayList<DatosPersona> Arraydt;

    private GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);
        ComunicacionMAPA1 com = new ComunicacionMAPA1();
        com.execute();
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapa);
        mapFragment.getMapAsync(this);
        //recuperamos el valor del nombre enviado
        //desde la actividad principal
        Intent intent = this.getIntent();
        busqueda = intent.getStringExtra("busqueda");

    }

    /** Called when the map is ready. */
    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;



        // Add some markers to the map, and add a data object to each marker.
        for (int i = 0; i < Arraydt.size(); i++) {
            DatosPersona dat;
            dat = (Arraydt.get(i));
            LatLng coords = new LatLng(dat.getX(), dat.getY());
            googleMap.addMarker(new MarkerOptions()
                    .position(coords)
                    .title("Nombre: "+dat.getNombre().toString()))
                    .setSnippet("Nº-"+ (i+1)+"-\n"+"Tlf: "+(Arraydt.get(i).getTelefono())+"\n"+"Email: "+Arraydt.get(i).getMail());
        }
            // Set a listener for marker click.
        googleMap.setOnMarkerClickListener(this);
    }

    /** Called when the user clicks a marker. */
    @Override
    public boolean onMarkerClick(final Marker marker) {

        // Retrieve the data from the marker.
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 18));
        Integer clickCount = (Integer) marker.getTag();
        TextView tvnombre = (TextView) Mapa.this.findViewById(R.id.tvMapNombre);
        TextView tvmail = (TextView) Mapa.this.findViewById(R.id.tvMapMail);
        TextView tvtlf = (TextView) Mapa.this.findViewById(R.id.tvMapTlf);
        TextView tvcalle = (TextView) Mapa.this.findViewById(R.id.tvMapCalle);

        String snip=(marker.getSnippet().toString());
        String direcBrutosnip[] = snip.split("[-]");
        int marca = Integer.parseInt(direcBrutosnip[1]);
        marca=marca-1;
        System.out.println("------------------------------LA MARCA ES ESTA"+marca);

        tvnombre.setText(Arraydt.get(marca).getNombre());
        tvmail.setText(Arraydt.get(marca).getMail());
        tvtlf.setText(Arraydt.get(marca).getTelefono());

        String direcBruto = (Arraydt.get(marca).getDireccion());
        String direcBrutoAr[] = direcBruto.split("[|]");
        tvcalle.setText(direcBrutoAr[0] + " Nº:" + direcBrutoAr[1]);


        // Check if a click count was set, then display the click count.
        if (clickCount != null) {
            clickCount = clickCount + 1;
            marker.setTag(clickCount);
            Toast.makeText(this,
                    marker.getTitle() +
                            " has been clicked " + clickCount + " times.",
                    Toast.LENGTH_SHORT).show();
        }

        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false;
    }


    //------------------------------------TAREAS ASINCRONAS DE COMUNICACIONES-------------

    class ComunicacionMAPA1 extends AsyncTask<Void, Void, ArrayList<DatosPersona>> {
        @Override
        protected void onPostExecute(ArrayList<DatosPersona> Arraydt) {


        }

        @Override
        protected ArrayList<DatosPersona> doInBackground(Void... params) {
            //nos conectamos con el servidor para pedirle la lista de personas
            GestionComunicacion gcom = new GestionComunicacion();
            Arraydt = gcom.buscarProfesionales(busqueda);
            System.out.println(Arraydt.get(0).toString());
            return Arraydt;
        }
    }
    //----------------------------------------------------------------------------------
}
