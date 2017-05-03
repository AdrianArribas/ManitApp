package gonzalberdricorp.manitapp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import JavaBean.DatosPersona;
import modelo.GestionComunicacion;

public class Mapa extends AppCompatActivity implements
        GoogleMap.OnMarkerClickListener,
        OnMapReadyCallback {
    //----------------DECLARAMOS VARIABLES GLOBALES-------------------------------------------------
    String busqueda;
    ArrayList<DatosPersona> Arraydt;
    private GoogleMap googleMap;
    double latitude=40.3960965;
    double longitude=-3.743638;
    LatLng pos=new LatLng(latitude,longitude);

    //----------------------------------------------------------------------------------------------


    //--------------------------------ON CREATE-----------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
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
    //----------------------------------------------------------------------------------------------



    //-------------------------------SE INICIA CON LA CARGA DEL MAPA COMPLETO-----------------------
    @Override
    public void onMapReady(GoogleMap map) {

        map.getUiSettings().setZoomControlsEnabled(true);
        //habilitar barra de herramientas
        map.getUiSettings().setMapToolbarEnabled(true);
        googleMap = map;
        String distancia = "";
        String cat = "";
        try {
            JSONObject pref = new JSONObject(busqueda);
            //cat = (String) pref.get("Categoria");
            distancia = (String) pref.get("check");
            System.out.println(distancia);
            latitude=((double) pref.get("latitude"));
            longitude=((double) pref.get("longitude"));
            pos=new LatLng(latitude,longitude);
            System.out.println("HASTA LA POYA YA COÑO "+distancia+"lat "+latitude+"long "+longitude+"latlong "+pos);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    //----------------------------------------------------------------------------------------------



        //------------------------GENERAMOS LOS MARCADORES SOLICITADOS------------------------------
        googleMap.setOnMarkerClickListener(this);
        if (distancia.equals("todos")) {
            for (int i = 0; i < Arraydt.size(); i++) {
                DatosPersona dat;
                dat = (Arraydt.get(i));
                LatLng coords = new LatLng(dat.getX(), dat.getY());
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pos,10));
                googleMap.addMarker(new MarkerOptions()
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                        .position(coords)
                        .title("Nombre: " + dat.getNombre().toString()))
                        .setSnippet("Tlf: " + (Arraydt.get(i).getTelefono()) + "\n" + "Nº-" + (i + 1) + "-\n" + "Email: " + Arraydt.get(i).getMail());
            }

        } else {
            googleMap.addMarker(new MarkerOptions()
                    .position(pos)
                    .title("Estas Aquí")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pos,12));
            for (int i = 0; i < Arraydt.size(); i++) {
                DatosPersona dat;
                dat = (Arraydt.get(i));
                //-----------------------CALCULO DE DISTANCIAS--------------------------------
                LatLng coords = new LatLng(dat.getX(), dat.getY());
                Location locationA = new Location("punto A");

                locationA.setLatitude(coords.latitude);
                locationA.setLongitude(coords.longitude);

                Location locationB = new Location("punto B");

                locationB.setLatitude(pos.latitude);
                locationB.setLongitude(pos.longitude);

                float distance = locationA.distanceTo(locationB);
                //----------------------------------------------------------------------
                if(distance<10000){
                    googleMap.addMarker(new MarkerOptions()
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                            .position(coords)
                            .title("Nombre: " + dat.getNombre().toString()))
                            .setSnippet("Tlf: " + (Arraydt.get(i).getTelefono()) + "\n" + "Nº-" + (i + 1) + "-\n" + "Email: " + Arraydt.get(i).getMail());
                }

            }

        }

    }

    //----------------------------------------------------------------------------------------------



    //-------------------------------EVENTO CLICK EN EL MARCADOR------------------------------------
    /** Called when the user clicks a marker. */
    @Override
    public boolean onMarkerClick(final Marker marker) {
        if(marker.getTitle().equals("Estas Aquí")){
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 18));
            Button SMS=(Button)Mapa.this.findViewById(R.id.button10);
            SMS.setVisibility(View.INVISIBLE);

        }else{
            //---DECLARAMOS OBJETOS GOOGLEMAP Y OBJETOS DEL INTERFACE GRAFICO----
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 18));
            Integer clickCount = (Integer) marker.getTag();
            TextView tvnombre = (TextView) Mapa.this.findViewById(R.id.tvMapNombre);
            TextView tvmail = (TextView) Mapa.this.findViewById(R.id.tvMapMail);
            TextView tvtlf = (TextView) Mapa.this.findViewById(R.id.tvMapTlf);
            TextView tvcalle = (TextView) Mapa.this.findViewById(R.id.tvMapCalle);
            RatingBar Estrellas=(RatingBar)Mapa.this.findViewById(R.id.ratingBar);
            Button SMS=(Button)Mapa.this.findViewById(R.id.button10);
            SMS.setVisibility(View.VISIBLE);
            ImageView iconoMapa=(ImageView)Mapa.this.findViewById(R.id.iconoMapa);

            //---DESCOMPONEMOS EL ARRAYLIST Y RELLENAMOS LOS CAMPOS-------------
            String snip=(marker.getSnippet().toString());
            String direcBrutosnip[] = snip.split("[-]");
            int marca = Integer.parseInt(direcBrutosnip[1]);
            marca=marca-1;
            System.out.println("------------------------------LA MARCA ES ESTA"+marca);

            tvnombre.setText("Nombre: "+Arraydt.get(marca).getNombre());
            tvmail.setText("E-Mail: "+Arraydt.get(marca).getMail());
            tvtlf.setText("Telefono: "+Arraydt.get(marca).getTelefono());

            String direcBruto = (Arraydt.get(marca).getDireccion());
            String direcBrutoAr[] = direcBruto.split("[|]");
            tvcalle.setText("Dirección: "+direcBrutoAr[0] + " Nº:" + direcBrutoAr[1]);

            Estrellas.setRating(Arraydt.get(marca).getEstrellas());

            //-------------------controlador de imagenes-------------------------------------

            if (Arraydt.get(marca).getCategoria().toLowerCase().equals("electricista")){
                iconoMapa.setImageResource(R.mipmap.prof_electricista);
            }
            if (Arraydt.get(marca).getCategoria().toLowerCase().equals("fontanero")){
                iconoMapa.setImageResource(R.mipmap.prof_fontanero);
            }
            if (Arraydt.get(marca).getCategoria().toLowerCase().equals("carpintero")){
                iconoMapa.setImageResource(R.mipmap.prof_carpintero);
            }
            if (Arraydt.get(marca).getCategoria().toLowerCase().equals("cerrajero")){
                iconoMapa.setImageResource(R.mipmap.prof_cerrajero);
            }
            if (Arraydt.get(marca).getCategoria().toLowerCase().equals("informatico")){
                iconoMapa.setImageResource(R.mipmap.prof_informatico);
            }
            if (Arraydt.get(marca).getCategoria().toLowerCase().equals("limpiezadomestica")){
                iconoMapa.setImageResource(R.mipmap.prof_limpieza);
            }
            if (Arraydt.get(marca).getCategoria().toLowerCase().equals("mudanzas")){
                iconoMapa.setImageResource(R.mipmap.prof_mudanzas);
            }
            if (Arraydt.get(marca).getCategoria().toLowerCase().equals("pintor")){
                iconoMapa.setImageResource(R.mipmap.prof_pintor);
            }
            if (Arraydt.get(marca).getCategoria().toLowerCase().equals("reformas")){
                iconoMapa.setImageResource(R.mipmap.prof_reformas);
            }
            if (Arraydt.get(marca).getCategoria().toLowerCase().equals("tecnico")){
                iconoMapa.setImageResource(R.mipmap.prof_tecnico);
            }
            //----------------------------------------------------------------------------------



            if (clickCount != null) {
                clickCount = clickCount + 1;
                marker.setTag(clickCount);
                Toast.makeText(this,
                        marker.getTitle() +
                                " has been clicked " + clickCount + " times.",
                        Toast.LENGTH_SHORT).show();
            }

        }
        return false;
    }
    //----------------------------------------------------------------------------------------------



    //------------------------------------ENVIAR SMS-----------------------------------------------

    public void SMS(View v){

        TextView tvtlf = (TextView) Mapa.this.findViewById(R.id.tvMapTlf);
        TextView tvnombre = (TextView) Mapa.this.findViewById(R.id.tvMapNombre);


        Uri sms_uri = Uri.parse("smsto:+" + (tvtlf.getText().toString()));
        Intent sms_intent = new Intent(Intent.ACTION_SENDTO, sms_uri);
        sms_intent.putExtra("sms_body","Se requiere el servicio de "+(tvnombre.getText().toString())+ " , pongase en contacto con este numero");
        startActivity(sms_intent);

    }
    //----------------------------------------------------------------------------------------------



    //------------------------------------TAREAS ASINCRONAS DE COMUNICACIONES-----------------------

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
    //----------------------------------------------------------------------------------------------


}
