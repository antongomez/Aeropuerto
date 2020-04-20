package aeropuerto.elementos;

import java.util.Date;

public class PersonalLaboral extends Usuario {

    private String labor;
    private String descripcionTarea;
    private Date fechaInicio;
    //private ArrayList<ElemHistorial> historialTrabajo; creo que este atributo sobra

    public PersonalLaboral(String dni, String id, String email, String contrasenha, String nombre, String ap1, String ap2, String paisProcedencia, Integer telefono, String sexo, String labor, String descripcionTarea) {
        super(dni, id, email, contrasenha, nombre, ap1, ap2, paisProcedencia, telefono, sexo);
        this.labor = labor;
        this.descripcionTarea = descripcionTarea;
        this.fechaInicio = new Date(); //la fecha de inicio es la fecha de creación
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

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setLabor(String labor) {
        this.labor = labor;
    }

    public void setDescripcionTarea(String descripcionTarea) {
        this.descripcionTarea = descripcionTarea;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

}
