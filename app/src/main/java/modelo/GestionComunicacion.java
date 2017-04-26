package modelo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;

import JavaBean.DatosPersona;

/**
 * Created by USUARIO on 25/04/2017.
 */

public class GestionComunicacion {
    DatosPersona dt;
    boolean registro=false;
    public DatosPersona enviarDNI(String DNI){
        try {
            //creamos objeto JSON con el DNI
            //cuyos datos queremos comprobar
            JSONObject job = new JSONObject();
            job.put("Dni", DNI);
            job.put("registro",registro);
            Socket sc=new Socket("192.168.0.187",8000);
            PrintStream salida=new PrintStream(sc.getOutputStream());
            BufferedReader bf=new BufferedReader(new InputStreamReader(sc.getInputStream()));
            //enviamos objeto al servidor
            salida.println(job.toString());

            //recuperamos objeto con respuesta
            //---------------------------------CONSTRUIMOS OBJETO JB--------------------------
            JSONObject jobRespuesta=new JSONObject(bf.readLine());
            dt=new DatosPersona(jobRespuesta.getString("Categoria"),
                    jobRespuesta.getString("Dni"),
                    jobRespuesta.getString("Nombre"),
                    jobRespuesta.getString("Direccion"),
                    jobRespuesta.getString("Telefono"),
                    jobRespuesta.getString("Mail"),
                    jobRespuesta.getInt("Estrellas"),
                    jobRespuesta.getDouble("X"),
                    jobRespuesta.getDouble("Y"),
                    jobRespuesta.getString("Extra"));
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
}