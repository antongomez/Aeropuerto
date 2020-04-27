package aeropuerto.elementos;

import aeropuerto.util.Time;
import java.sql.Timestamp;

public class Administrador extends Usuario {

    private Time fechaInicio;
    private String curriculum;

    public Administrador(String dni, String id, String email, String nombre,
            String ap1, String ap2, String paisProcedencia, Integer telefono,
            String sexo, Timestamp fechainicio, String curriculum) {

        super(dni, id, email, nombre, ap1, ap2, paisProcedencia, telefono, sexo);
        this.fechaInicio = new Time(fechainicio);
        this.curriculum = curriculum;
    }

    public Administrador(String dni, String id, String email, String nombre,
            String ap1, String ap2, String paisProcedencia, Integer telefono,
            String sexo, String curriculum) {

        super(dni, id, email, nombre, ap1, ap2, paisProcedencia, telefono, sexo);
        this.curriculum = curriculum;
    }

    public void setFechaInicio(Time fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public void setCurriculum(String curriculum) {
        this.curriculum = curriculum;
    }

    public Time getFechaInicio() {
        return fechaInicio;
    }

    public String getCurriculum() {
        return curriculum;
    }

}
