package modelo;

import android.location.Address;
import android.location.Geocoder;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import JavaBean.DatosPersona;

/**
 * Created by USUARIO on 25/04/2017.
 */

public class GestionComunicacion {

    boolean registro=false;
    public DatosPersona enviarDNI(String DNI){
        DatosPersona dt=new DatosPersona("fallo",
                "fallo",
                "fallo",
                "fallo",
                "fallo",
                "fallo",
                0,
                0,
                0,
                "fallo");

        try {
            //creamos objeto JSON con el DNI
            //cuyos datos queremos comprobar
            JSONObject jobRespuesta = new JSONObject();
            jobRespuesta.put("Dni", DNI);
            jobRespuesta.put("registro",registro);
            Socket sc=new Socket("192.168.0.188",8000);
            PrintStream salida=new PrintStream(sc.getOutputStream());
            BufferedReader bf=new BufferedReader(new InputStreamReader(sc.getInputStream()));
            //enviamos objeto al servidor
            salida.println(jobRespuesta.toString());

            //recuperamos objeto con respuesta
            //---------------------------------CONSTRUIMOS OBJETO JB--------------------------
            JSONObject jobRespuesta2=new JSONObject(bf.readLine());
            String nombreJOB=((String)jobRespuesta2.get("Nombre"));
            if (nombreJOB.equals("none")){
                dt=new DatosPersona("none",
                        "none",
                        "none",
                        "none",
                        "none",
                        "none",
                        0,
                        0,
                        0,
                        "none");

                System.out.println("LLEGAMOS AL IF 1");
            }else{
                dt=new DatosPersona(
                    ((String)jobRespuesta2.get("Categoria")),
                    ((String)jobRespuesta2.get("Dni")),
                    ((String)jobRespuesta2.get("Nombre")),
                    ((String)jobRespuesta2.get("Direccion")),
                    ((String)jobRespuesta2.get("Telefono")),
                    ((String)jobRespuesta2.get("Email")),
                    ((Integer)jobRespuesta2.get("Estrellas")).intValue(),
                    ((Double)jobRespuesta2.get("X")),
                    ((Double)jobRespuesta2.get("Y")),
                    ((String)jobRespuesta2.get("Extra")));

                System.out.println("LLEGAMOS AL ELSE 1");
            }


            //-------------------------------------------------------------------------------
            //cierre del socket
            sc.close();
        }
        catch(IOException ex){
            ex.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dt;

    }
    //-------------------------------REGISTRO DE PROFESIONAL------------------------------------
    public String enviarRegistro(DatosPersona dt){
        String checkreg="vacio";
        try {
            //creamos objeto JSON con el DNI
            //cuyos datos queremos ENVIAR
            JSONObject job = new JSONObject();
            job.put("Categoria",(dt.getCategoria()));
            job.put("Dni",dt.getDNI());
            job.put("Nombre",dt.getNombre());
            job.put("Direccion",dt.getDireccion());
            job.put("Telefono",dt.getTelefono());
            job.put("Mail",dt.getMail());
            job.put("Estrellas",0);
            job.put("X",dt.getX());
            job.put("Y",dt.getY());
            job.put("Extra","desconocido");
            job.put("registro",registro=true);
            Socket sc=new Socket("192.168.0.188",8000);
            PrintStream salida=new PrintStream(sc.getOutputStream());
            BufferedReader bf=new BufferedReader(new InputStreamReader(sc.getInputStream()));
            //enviamos objeto al servidor
            salida.println(job.toString());

            //recuperamos objeto con respuesta
            //---------------------------------LEEMOS RESPUESTA--------------------------
            checkreg=(bf.readLine());

            //-------------------------------------------------------------------------------
            //cierre del socket
            sc.close();
        }
        catch(IOException ex){
            ex.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return checkreg;

    }
    //-------------------------------------------------------------------------------

    public ArrayList<DatosPersona> buscarProfesionales(String busqueda){
        ArrayList<DatosPersona> Arraydt=new ArrayList<>();
        try{
            JSONObject job = new JSONObject(busqueda);
            //creamos objeto JSON con el DNI
            //cuyos datos queremos comprobar
            Socket sc=new Socket("192.168.0.188",9000);
            PrintStream salida=new PrintStream(sc.getOutputStream());
            BufferedReader bf=new BufferedReader(new InputStreamReader(sc.getInputStream()));
            //enviamos objeto al servidor
            salida.println(job.toString());

            //recuperamos objeto con respuesta
            //---------------------------------CONSTRUIMOS OBJETO JB--------------------------
            JSONArray JobArespuesta=new JSONArray(bf.readLine());
            DatosPersona dt;
            for(int i=0;i<JobArespuesta.length();i++) {
                dt=new DatosPersona(
                        ((String)job.get("Categoria")),
                        ((String)job.get("Dni")),
                        ((String)job.get("Nombre")),
                        ((String)job.get("Direccion")),
                        ((String)job.get("Telefono")),
                        ((String)job.get("Email")),
                        ((Integer)job.get("Estrellas")).intValue(),
                        ((Double)job.get("X")),
                        ((Double)job.get("Y")),
                        ((String)job.get("Extra")));
                Arraydt.add(dt);
            }
            //-------------------------------------------------------------------------------
            //cierre del socket
            sc.close();
        }
        catch(IOException ex){
            ex.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return Arraydt;

        }
    }




/*

    public DatosPersona recuperarDatosPersona(String nombre){
        DatosPersona dt=null;
        try {
            //creamos objeto JSON con el nombre de la persona
            //cuyos datos queremos recuperar
            JSONObject job = new JSONObject();
            job.put("nombre", nombre);
            Socket sc=new Socket("192.168.0.187",9000);
            PrintStream salida=new PrintStream(sc.getOutputStream());
            BufferedReader bf=new BufferedReader(new InputStreamReader(sc.getInputStream()));
            //enviamos objeto al servidor
            salida.println(job.toString());
            //recuperamos objeto con respuesta
            JSONObject jobRespuesta=new JSONObject(bf.readLine());
            dt=new DatosPersona(jobRespuesta.getString("nombre"),
                    jobRespuesta.getDouble("longitud"),
                    jobRespuesta.getDouble("latitud"));
            //cierre del socket
            sc.close();
        }
        catch(IOException ex){
            ex.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dt;

    }
    public ArrayList<DatosPersona> recuperarTodas(){
        ArrayList<DatosPersona> todos=new ArrayList<>();
        try {
            JSONObject job = new JSONObject();
            job.put("nombre", "");
            Socket sc=new Socket("192.168.0.187",9000);
            PrintStream salida=new PrintStream(sc.getOutputStream());
            BufferedReader bf=new BufferedReader(new InputStreamReader(sc.getInputStream()));
            //enviamos objeto al servidor
            salida.println(job.toString());
            //recogemos el array JSON con los datos de todas las personas
            JSONArray jarray=new JSONArray(bf.readLine());
            //y lo transformamos en un arraylist de objetos DatosPersona
            for(int i=0;i<jarray.length();i++){
                JSONObject aux=jarray.getJSONObject(i);
                DatosPersona dt=new DatosPersona(aux.getString("nombre"),
                        aux.getDouble("longitud"),
                        aux.getDouble("latitud"));
                todos.add(dt);

            }
            sc.close();
        }
        catch(IOException ex){
            ex.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return todos;
    }

    */
