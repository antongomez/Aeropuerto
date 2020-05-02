package aeropuerto.elementos;

import aeropuerto.util.Time;
import java.sql.Timestamp;

public class PersonalLaboral extends Usuario {

    private String labor;
    private String descripcionTarea;
    private Time fechaInicio;
    
    //private ArrayList<ElemHistorial> historialTrabajo; creo que este atributo sobra

    public PersonalLaboral(String dni, String id, String email, String nombre,
            String ap1, String ap2, String paisProcedencia, Integer telefono,
            String sexo, String labor, String descripcionTarea, Timestamp fechaInicio) {

        super(dni, id, email, nombre, ap1, ap2, paisProcedencia, telefono, sexo);
        this.labor = labor;
        this.descripcionTarea = descripcionTarea;
        this.fechaInicio = new Time(fechaInicio); //la fecha de inicio es la fecha de creación
        //this.historialTrabajo = new ArrayList<>(); //inicialmente no hay historial de trabajo
    }

    /*
    public void ficharEntrada(){
        
        //condición para poder fichar la entrada (primera vez que trabaja o salió del último turno)
        if(historialTrabajo.isEmpty() || historialTrabajo.get(historialTrabajo.size()-1).getFechaSalida()!=null){
            
            historialTrabajo.add(new ElemHistorial(new Date()));
            //acceso a base de datos
        }
        else{
            System.out.println("Debes salir antes del turno anterior");
        }
    
     */
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

}
