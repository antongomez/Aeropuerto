package aeropuerto.elementos;

import aeropuerto.util.Time;
import java.sql.Timestamp;
import java.util.ArrayList;

public class PersonalLaboral extends Usuario {

    private String labor;
    private String descripcionTarea;
    private Time fechaInicio;
    private Boolean estaDentro;
    private ArrayList<ElemHistorial> historialTrabajo;

    public PersonalLaboral(String dni, String id, String email, String nombre,
            String ap1, String ap2, String paisProcedencia, Integer telefono,
            String sexo, String labor, String descripcionTarea, Timestamp fechaInicio) {

        super(dni, id, email, nombre, ap1, ap2, paisProcedencia, telefono, sexo);
        this.labor = labor;
        this.descripcionTarea = descripcionTarea;
        this.fechaInicio = new Time(fechaInicio); //la fecha de inicio es la fecha de creación
        this.historialTrabajo = new ArrayList<>();
    }

    public ArrayList<ElemHistorial> getHistorialTrabajo() {
        return historialTrabajo;
    }

    public void borrarHistorial() {
        historialTrabajo.clear();
    }

    public void addElemHistorial(ElemHistorial elem) {
        this.historialTrabajo.add(elem);
    }

    public String getLabor() {
        return labor;
    }

    public String getDescripcionTarea() {
        return descripcionTarea;
    }

    public Time getFechaInicio() {
        return fechaInicio;
    }

    public void setLabor(String labor) {
        this.labor = labor;
    }

    public void setDescripcionTarea(String descripcionTarea) {
        this.descripcionTarea = descripcionTarea;
    }

    public void setFechaInicio(Time fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public void setEstaDentro(Boolean dentro) {
        this.estaDentro = dentro;
    }

    public Boolean estaDentro() {
        return this.estaDentro;
    }
}
