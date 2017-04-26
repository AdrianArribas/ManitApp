package gonzalberdricorp.manitapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import JavaBean.DatosPersona;
import modelo.GestionComunicacion;

public class Registro extends AppCompatActivity {
    DatosPersona dt;
    Spinner sp;
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
        GestionComunicacion gCom=new GestionComunicacion();
        dt=gCom.enviarDNI(edt1.getText().toString());
        if (dt.getNombre()==null){
            LinearLayout ADNI=(LinearLayout)this.findViewById((R.id.alertaDNI));
            ADNI.setVisibility(View.VISIBLE);
        }else{
            LinearLayout LinearRegistro=(LinearLayout)this.findViewById(R.id.LinearRegistro);
            LinearRegistro.setVisibility(View.VISIBLE);
        }
    }
    //-----------------------------------------------------------------------------------

    //------------------------------------ACTUALIZAR CONTENIDO DEL FORMULARIO------------
    public void Actualizar (View v){

        LinearLayout LinearRegistro=(LinearLayout)this.findViewById(R.id.LinearRegistro);
        LinearRegistro.setVisibility(View.VISIBLE);

        EditText proNombre=(EditText)this.findViewById(R.id.proNombre);
        EditText proDirec=(EditText)this.findViewById(R.id.proDirec);
        EditText proTlf=(EditText)this.findViewById(R.id.proTlf);
        EditText proMail=(EditText)this.findViewById(R.id.proMail);

        proNombre.setText(dt.getNombre().toString());
        proDirec.setText(dt.getDireccion().toString());
        proTlf.setText(dt.getTelefono().toString());
        proMail.setText(dt.getMail().toString());

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

        dt.setCategoria(Categoria);
        dt.setDNI(edt1.getText().toString());
        dt.setNombre(proNombre.getText().toString());
        dt.setDireccion(proDirec.getText().toString());



    }

    //-----------------------------------RETORNO A MAIN---------------------------------
    public void noact (View v){
        this.startActivity(new Intent(this,ManitApp.class));
    }
    //---------------------------------------------------------------------------------
}
