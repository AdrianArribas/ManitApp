package gonzalberdricorp.manitapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import JavaBean.DatosPersona;
import modelo.GestionComunicacion;

import static gonzalberdricorp.manitapp.R.id.ratingBar;

public class PuntuaProf extends AppCompatActivity {
    String[] Categorias= {"Electricista","Fontanero","Carpintero","Pintor","Mudanzas","Limpieza Domestica","Cerrajero","Técnico","Informñatico","Reformas"};
    ArrayList<String> Nombres=new ArrayList<>();
    String Categoria;
    int Estrellas=0;
    double latitude=40.3960965;
    double longitude=-3.743638;
    public ArrayList<DatosPersona> Arraydt;
    int marcador=0;
    String nombr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puntua_prof);
        Spinner spin=(Spinner)this.findViewById(R.id.spCategoria);
        ArrayAdapter<String> adp=new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,Categorias);
        spin.setAdapter(adp);
        addListenerOnRatingBar();

    }

    //---------------------------------BOTON BUSCAR CATEGORIA---------------------------------------
    public void buscaservicio(View v){
        TextView tv=(TextView)this.findViewById(android.R.id.text1);
        Categoria=(tv.getText().toString());
        ComunicacionDNI com=new ComunicacionDNI();
        com.execute();
    }
    //----------------------------------------------------------------------------------------------



    //-------------------------------------SEGUNDO SPINNER------------------------------------------
    public void spinnerpuntua(){
        ArrayAdapter<String> adp=new ArrayAdapter<>(PuntuaProf.this,android.R.layout.simple_spinner_item,Nombres);
        final Spinner spin2=(Spinner)PuntuaProf.this.findViewById(R.id.spNombre);
        spin2.setAdapter(adp);
        spin2.setVisibility(View.VISIBLE);
        RatingBar estrellas=(RatingBar)PuntuaProf.this.findViewById(R.id.puntuaEstrellas);
        estrellas.setVisibility(View.VISIBLE);
        spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String nombr=spin2.getSelectedItem().toString();
                TextView tvNombrempresa=(TextView)PuntuaProf.this.findViewById(R.id.tvDatosEmpresa);
                for (int i = 0; i < Arraydt.size(); i++) {
                    if(Arraydt.get(i).getNombre().equals(nombr)){
                        int estrellasN=Arraydt.get(i).getEstrellas();
                        RatingBar estrellas=(RatingBar)PuntuaProf.this.findViewById(R.id.puntuaEstrellas);
                        estrellas.setRating(estrellasN);
                        tvNombrempresa.setText("Nombre : "+(Arraydt.get(i).getNombre()+" Puntuacion media actual: "+(Arraydt.get(i).getEstrellas())));
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    //----------------------------------------------------------------------------------------------



    //----------------------------------------------------LISTENER RATING---------------------------

    public void addListenerOnRatingBar() {
        final RatingBar estrellas=(RatingBar)this.findViewById(R.id.puntuaEstrellas);
        //if rating value is changed,
        //display the current rating value in the result (textview) automatically
        estrellas.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
              Estrellas=Math.round(estrellas.getRating());
                Button puntua=(Button)PuntuaProf.this.findViewById(R.id.btnPuntuar);
                puntua.setText("Puntuar con "+Estrellas+" Estrellas");

            }
        });
    }
    //----------------------------------------------------------------------------------------------


    //---------------------------------------------------BOTON PUNTUAR------------------------------
    public void PUNTUA (View v){
        Spinner spin2=(Spinner)PuntuaProf.this.findViewById(R.id.spNombre);
        nombr=spin2.getSelectedItem().toString();
        ComunicacionPUNTUAR com=new ComunicacionPUNTUAR();
        com.execute();

    }
    //---------------------------------------------------------------------------------------------

    //------------------------------------TAREAS ASINCRONAS DE COMUNICACIONES-----------------------

    class ComunicacionDNI extends AsyncTask<Void,Void,ArrayList<DatosPersona>> {
        @Override
        protected void onPostExecute(ArrayList<DatosPersona> Arraydt) {
            for (int i = 0; i < Arraydt.size(); i++) {
                String nom;
                DatosPersona dat;
                dat = (Arraydt.get(i));
                nom=(dat.getNombre());
                System.out.println("Se introduce nombre : "+nom);
                Nombres.add(nom);
                System.out.println("Se introduce nombre : "+Nombres.get(i));
            }
            spinnerpuntua();

        }

        @Override
        protected ArrayList<DatosPersona> doInBackground(Void... params) {


            JSONObject job = new JSONObject();
            try {

                    job.put("Categoria",Categoria.toLowerCase());
                    job.put("check","todos");
                    job.put("latitude",latitude);
                    job.put("longitude",longitude);
                    System.out.println("Se ha generado para seleccion TODOS : "+job.toString());

            } catch (JSONException e) {
                e.printStackTrace();
            }
            String busqueda=(job.toString());
            GestionComunicacion gcom=new GestionComunicacion();
            Arraydt=gcom.buscarProfesionales(busqueda);
            return Arraydt;
        }
    }
    //----------------------------------------------------------------------------------

    class ComunicacionPUNTUAR extends AsyncTask<Void,Void,ArrayList<DatosPersona>> {
        @Override
        protected void onPostExecute(ArrayList<DatosPersona> Arraydt) {
            Toast.makeText(PuntuaProf.this,"La puntuación se ha enviado correctamente, ¡Gracias por votar!",Toast.LENGTH_LONG).show();
            Intent intent=new Intent(PuntuaProf.this,ManitApp.class);
            //cargamos actividad
            PuntuaProf.this.startActivity(intent);
        }

        @Override
        protected ArrayList<DatosPersona> doInBackground(Void... params) {

            for (int i = 0; i < Arraydt.size(); i++) {
                if (Arraydt.get(i).getNombre().equals(nombr)) {
                    marcador = (i);
                    Arraydt.get(i).setEstrellas(Estrellas);
                    GestionComunicacion gcom = new GestionComunicacion();
                    gcom.enviarRegistro(Arraydt.get(i));
                }
            }

            return Arraydt;
        }
    }

}
