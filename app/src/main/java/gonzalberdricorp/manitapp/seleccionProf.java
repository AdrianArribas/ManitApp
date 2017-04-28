package gonzalberdricorp.manitapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class seleccionProf extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion_prof);
    }

    public void Buscamiento (View v){
        this.startActivity(new Intent(this,Mapa.class));
    }
}
