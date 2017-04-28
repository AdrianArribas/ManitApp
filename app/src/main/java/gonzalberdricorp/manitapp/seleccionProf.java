package gonzalberdricorp.manitapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class seleccionProf extends AppCompatActivity {
    String[] Categorias= {"Electricista","Fontanero","Carpintero","Pintor","Mudanzas","Limpieza Domestica","Cerrajero","Técnico","Informñatico","Reformas"};
    Spinner sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion_prof);
        //------------------------------------------SPINNER-----------------------------
        sp=(Spinner)this.findViewById(R.id.spinnerprof);
        ArrayAdapter adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item, Categorias);
        sp.setAdapter(adapter);
        //------------------------------------------------------------------------------
    }

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
            }
            if(rbtodos.isChecked()){
                job.put("Categoria",Categoria);
                job.put("check","todos");
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
