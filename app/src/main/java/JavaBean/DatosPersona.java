package JavaBean;

/**
 * Created by USUARIO on 25/04/2017.
 */

public class DatosPersona {
    String Categoria;
    String DNI;
    String Nombre;
    String Direccion;
    String Telefono;
    String Mail;
    int Estrellas;
    double X;
    double Y;
    String extra;

    public DatosPersona(String categoria, String DNI, String nombre, String direccion, String telefono, String mail, int estrellas, double x, double y, String extra) {
        Categoria = categoria;
        this.DNI = DNI;
        Nombre = nombre;
        Direccion = direccion;
        Telefono = telefono;
        Mail = mail;
        Estrellas = estrellas;
        X = x;
        Y = y;
        this.extra = extra;
    }

    public String getCategoria() {
        return Categoria;
    }

    public void setCategoria(String categoria) {
        Categoria = categoria;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String direccion) {
        Direccion = direccion;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String telefono) {
        Telefono = telefono;
    }

    public String getMail() {
        return Mail;
    }

    public void setMail(String mail) {
        Mail = mail;
    }

    public int getEstrellas() {
        return Estrellas;
    }

    public void setEstrellas(int estrellas) {
        Estrellas = estrellas;
    }

    public double getX() {
        return X;
    }

    public void setX(double x) {
        X = x;
    }

    public double getY() {
        return Y;
    }

    public void setY(double y) {
        Y = y;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    @Override
    public String toString() {
        return Categoria+"|"+DNI+"|"+Nombre+"|"+Direccion+"|"+Telefono+"|"+Mail+"|"+Estrellas+"|"+X+"|"+Y+"|"+extra;
    }
}
