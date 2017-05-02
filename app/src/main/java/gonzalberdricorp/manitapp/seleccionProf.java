package gonzalberdricorp.manitapp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class seleccionProf extends AppCompatActivity {
    String[] Categorias= {"Electricista","Fontanero","Carpintero","Pintor","Mudanzas","Limpieza Domestica","Cerrajero","Técnico","Informñatico","Reformas"};
    Spinner sp;
    GoogleMap googleMap;
    LatLng pos=new LatLng(40.3960965,-3.743638);
    double latitude=40.3960965;
    double longitude=-3.743638;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion_prof);

        //------------------------------------------SPINNER-----------------------------
        sp=(Spinner)this.findViewById(R.id.spinnerprof);
        ArrayAdapter adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item, Categorias);
        sp.setAdapter(adapter);
        //------------------------------------------------------------------------------


        //--------------------------------COMPROBAMOS NUESTA UBICACION------------------------------
        LocationManager lm=(LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
        try {
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new CambioLocalizacion());
        }
        catch(SecurityException ex){
            ex.printStackTrace();
        }


    }//------------on create END-----------------

    class CambioLocalizacion implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            System.out.println("MOSTRANDO COORDENADAS-----------------"+latitude+"  "+longitude);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }

    }
    //----------------------------------------------------------------------------------------------




    public void Buscamiento (View v){
        JSONObject job = new JSONObject();
        TextView tv=(TextView)this.findViewById(android.R.id.text1);
        String Categoria=tv.getText().toString();
        RadioGroup rg=(RadioGroup)this.findViewById(R.id.Radiogroup);
        RadioButton rb1km=(RadioButton)this.findViewById(R.id.radio1km);
        RadioButton rbtodos=(RadioButton)this.findViewById(R.id.radiocualquier);
        try {
            if(rb1km.isChecked()){
                job.put("Categoria",Categoria);
                job.put("check","1km");
                job.put("latitude",latitude);
                job.put("longitude",longitude);
                System.out.println("Se ha generado para seleccion 1KM : "+job.toString());
            }
            if(rbtodos.isChecked()){
                job.put("Categoria",Categoria);
                job.put("check","todos");
                job.put("latitude",latitude);
                job.put("longitude",longitude);
                System.out.println("Se ha generado para seleccion TODOS : "+job.toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //creamos un objeto Intent asociado a la actividad que vamos a cargar
        Intent intent=new Intent(seleccionProf.this,Mapa.class);
        String busqueda=(job.toString());
        intent.putExtra("busqueda",busqueda);
        //cargamos actividad
        seleccionProf.this.startActivity(intent);
    }
}
