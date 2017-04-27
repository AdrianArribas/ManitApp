package gonzalberdricorp.manitapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import JavaBean.DatosPersona;
import modelo.GestionComunicacion;

public class Registro extends AppCompatActivity {
    DatosPersona dt;
    Spinner sp;
    String DNI;
    String[] Categorias= {"Electricista","Fontanero","Carpintero","Pintor","Mudanzas","Limpieza Domestica","Cerrajero","Técnico","Informñatico","Reformas"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        //------------------------------------------SPINNER-----------------------------
        sp=(Spinner)this.findViewById(R.id.spReg);
        ArrayAdapter adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item, Categorias);
        sp.setAdapter(adapter);
        //------------------------------------------------------------------------------
    }

    //-------------------------------------COMPROBAR DNI REPETIDO Y DISPARAR RESPUESTA--
    public void comprobarDNI (View v){

        EditText edt1 = (EditText) this.findViewById(R.id.proDni);

        DNI=(edt1.getText().toString());
        ComunicacionDNI com=new ComunicacionDNI();
        com.execute();


    }
    //-----------------------------------------------------------------------------------

    //------------------------------------ACTUALIZAR CONTENIDO DEL FORMULARIO------------
    public void Actualizar (View v){

        LinearLayout LinearRegistro=(LinearLayout)this.findViewById(R.id.LinearRegistro);
        LinearRegistro.setVisibility(View.VISIBLE);
        Button Botondni=(Button)Registro.this.findViewById(R.id.button3);
        Botondni.setVisibility(View.INVISIBLE);

        EditText proNombre=(EditText)this.findViewById(R.id.proNombre);
        EditText proDirec=(EditText)this.findViewById(R.id.proDirec);
        EditText proTlf=(EditText)this.findViewById(R.id.proTlf);
        EditText proMail=(EditText)this.findViewById(R.id.proMail);

        proNombre.setText(dt.getNombre().toString());
        proDirec.setText(dt.getDireccion().toString());
        proTlf.setText(dt.getTelefono().toString());
        proMail.setText(dt.getMail().toString());

        LinearLayout ADNI=(LinearLayout)this.findViewById((R.id.alertaDNI));
        ADNI.setVisibility(View.INVISIBLE);

    }
    //----------------------------------------------------------------------------------

    public void ENVIAR (View v){

        EditText edt1 = (EditText) this.findViewById(R.id.proDni);
        EditText proNombre=(EditText)this.findViewById(R.id.proNombre);
        EditText proDirec=(EditText)this.findViewById(R.id.proDirec);
        EditText proTlf=(EditText)this.findViewById(R.id.proTlf);
        EditText proMail=(EditText)this.findViewById(R.id.proMail);
        TextView tv=(TextView)this.findViewById(android.R.id.text1);

        String Categoria=tv.getText().toString();

        dt.setCategoria(Categoria.toLowerCase());
        dt.setDNI(edt1.getText().toString());
        dt.setNombre(proNombre.getText().toString());
        dt.setDireccion(proDirec.getText().toString());
        dt.setTelefono(proTlf.getText().toString());
        dt.setMail(proMail.getText().toString());
        LinearLayout LinearRegistro=(LinearLayout)this.findViewById(R.id.LinearRegistro);
        LinearRegistro.setVisibility(View.INVISIBLE);
        Button Botondni=(Button)Registro.this.findViewById(R.id.button3);
        Botondni.setVisibility(View.VISIBLE);

        ComunicacionRegistro com=new ComunicacionRegistro();
        com.execute();
    }

    //-----------------------------------RETORNO A MAIN---------------------------------
    public void noact (View v){
        LinearLayout ADNI=(LinearLayout)this.findViewById((R.id.alertaDNI));
        ADNI.setVisibility(View.INVISIBLE);
        this.startActivity(new Intent(this,ManitApp.class));
    }
    //----------------------------------------------------------------------------------

    //------------------------------------TAREAS ASINCRONAS DE COMUNICACIONES-------------

    class ComunicacionDNI extends AsyncTask<Void,Void,DatosPersona> {
        @Override
        protected void onPostExecute(DatosPersona dt) {
            System.out.println(dt.toString());
            if (dt.getNombre().equals("none")){
                LinearLayout LinearRegistro=(LinearLayout)Registro.this.findViewById(R.id.LinearRegistro);
                LinearRegistro.setVisibility(View.VISIBLE);
                LinearLayout ADNI=(LinearLayout)Registro.this.findViewById((R.id.alertaDNI));
                ADNI.setVisibility(View.INVISIBLE);
                Button Botondni=(Button)Registro.this.findViewById(R.id.button3);
                Botondni.setVisibility(View.INVISIBLE);
            }else{
                LinearLayout LinearRegistro=(LinearLayout)Registro.this.findViewById(R.id.LinearRegistro);
                LinearRegistro.setVisibility(View.INVISIBLE);
                LinearLayout ADNI=(LinearLayout)Registro.this.findViewById((R.id.alertaDNI));
                ADNI.setVisibility(View.VISIBLE);
                Button Botondni=(Button)Registro.this.findViewById(R.id.button3);
                Botondni.setVisibility(View.INVISIBLE);
            }

        }

        @Override
        protected DatosPersona doInBackground(Void... params) {
            //nos conectamos con el servidor para pedirle la lista de personas
            GestionComunicacion gcom=new GestionComunicacion();
            dt=gcom.enviarDNI(DNI);
            return dt;
        }
    }
    //----------------------------------------------------------------------------------
    class ComunicacionRegistro extends AsyncTask<Void,Void,DatosPersona> {
        String Respuesta;
        @Override
        protected void onPostExecute(DatosPersona dt) {
            System.out.println(dt.toString());
            Toast.makeText(Registro.this,Respuesta,Toast.LENGTH_LONG).show();
            LinearLayout ADNI=(LinearLayout)Registro.this.findViewById((R.id.alertaDNI));
            ADNI.setVisibility(View.INVISIBLE);
            LinearLayout LinearRegistro=(LinearLayout)Registro.this.findViewById(R.id.LinearRegistro);
            LinearRegistro.setVisibility(View.INVISIBLE);
            Button Botondni=(Button)Registro.this.findViewById(R.id.button3);
            Botondni.setVisibility(View.VISIBLE);

            Registro.this.startActivity(new Intent(Registro.this,ManitApp.class));
        }

        @Override
        protected DatosPersona doInBackground(Void... params) {
            //nos conectamos con el servidor para pedirle la lista de personas
            GestionComunicacion gcom=new GestionComunicacion();
            Respuesta=gcom.enviarRegistro(dt);
            return dt;
        }
    }
    //----------------------------------------------------------------------------------

}
