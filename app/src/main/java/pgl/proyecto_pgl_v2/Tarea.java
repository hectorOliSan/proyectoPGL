package pgl.proyecto_pgl_v2;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Tarea implements Serializable {
    private int idTarea, fav;
    private String titulo, descripcion, fecha;

    public Tarea(String tit, String des, String fe, int fav) {
        this.titulo = tit;
        this.descripcion = des;
        this.fecha = fe;
        this.fav = fav;
    }

    public Tarea(int idTarea, String tit, String des, String fe, int fav) {
        this.idTarea = idTarea;
        this.titulo = tit;
        this.descripcion = des;
        this.fecha = fe;
        this.fav = fav;
    }

    public  int getIdTarea(){
        return idTarea;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getFav() {
        return fav;
    }

    public void setFav(int fav) {
        this.fav = fav;
    }

    @NonNull
    @Override
    public String toString() {
        return "Tarea (" + idTarea + "), " + titulo +
                ", " + descripcion + ", " + fecha+ " - " + fav;
    }
}
