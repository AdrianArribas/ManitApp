package gonzalberdricorp.manitapp;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.List;

public class ClienteSelecion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_selecion);
    }
    public void puntua (View v){
        this.startActivity(new Intent(this,Registro.class));
    }

    public void buscaprof (View v){
        this.startActivity(new Intent(this,seleccionProf.class));
    }



/*Geocoder geo=new Geocoder(this,new Locale("ES"));
    try{
        List<Address> lista= geo.getFromLocationName("calle,numero,ciudad,Nresultados");
        Toast.makeText(this,lista.get(0).getLatitude()+","+lista.get(0).getLongitude());
        //getfromlocation (lat,long,int max results);
    }*/

}
