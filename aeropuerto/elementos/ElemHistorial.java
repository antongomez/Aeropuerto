package aeropuerto.elementos;

import java.util.Date;

//clase creada para guardar el formato de los elementos del historial de trabajo del personal laboral
public class ElemHistorial {

    private Date fechaEntrada;
    private Date fechaSalida;

    //cuando se construye un elemento inicialmente solo se sabe la fecha de entrada
    public ElemHistorial(Date fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = null;
    }

    public Date getFechaEntrada() {
        return fechaEntrada;
    }

    public Date getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaEntrada(Date fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    public void setFechaSalida(Date fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

}
