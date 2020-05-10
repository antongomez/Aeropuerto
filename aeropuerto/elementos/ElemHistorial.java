package aeropuerto.elementos;

import aeropuerto.util.Time;

//clase creada para guardar el formato de los elementos del historial de trabajo del personal laboral
public class ElemHistorial {

    private Time fechaEntrada;
    private Time fechaSalida;

    //cuando se construye un elemento inicialmente solo se sabe la fecha de entrada
    public ElemHistorial(Time fechaEntrada, Time fechaSalida) {
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
    }

    public Time getFechaEntrada() {
        return fechaEntrada;
    }

    public Time getFechaSalida() {
        return fechaSalida;
    }

}
