package aeropuerto.gestion;

import aeropuerto.util.reservas.Reserva;
import aeropuerto.util.Time;
import aeropuerto.util.reservas.ReservaCoche;
import aeropuerto.util.reservas.ReservaParking;
import baseDatos.FachadaBaseDatos;
import gui.FachadaGui;
import java.util.List;

public class GestionReservas {

    private FachadaGui fgui;
    private FachadaBaseDatos fbd;

    public GestionReservas(FachadaGui fgui, FachadaBaseDatos fbd) {
        this.fgui = fgui;
        this.fbd = fbd;
    }

    public List<ReservaParking> obterResParkingUsuario(String dniUs) {
        return fbd.obterResParkingUsuario(dniUs);
    }

    public List<ReservaCoche> obterResCocheUsuario(String dniUs) {
        return fbd.obterResCocheUsuario(dniUs);
    }

    public Boolean cancelarReserva(Reserva res, String dniUs) {

        if (res instanceof ReservaParking) {
            return fbd.cancelarReservaParking((ReservaParking) res, dniUs);
        } else if (res instanceof ReservaCoche) {
            return fbd.cancelarReservaCoche((ReservaCoche) res, dniUs);
        } else {
            return false;
        }

    }

    public Boolean reservarParking(ReservaParking reserva, String dniUsuario) {
        return fbd.reservarParking(reserva, dniUsuario);
    }

    public Boolean reservarCoche(ReservaCoche reserva, String dniUsuario) {
        return fbd.reservarCoche(reserva, dniUsuario);
    }

    public List<ReservaCoche> obtenerReservasCocheUsuario(String dniUsuario) {
        return fbd.obtenerReservasCocheUsuario(dniUsuario);
    }

    public Boolean introducirAlquiler(String matricula, Time fin, String dni) {
        return fbd.introducirAlquiler(matricula, fin, dni);
    }

    public ReservaCoche buscarAlquilerDevolucion(String matricula) {
        return fbd.buscarAlquilerDevolucion(matricula);
    }

    public Boolean devolucionCoche(Reserva alquiler) {
        return fbd.devolucionCoche(alquiler);
    }
<<<<<<< HEAD

    public Boolean sePuedeAmpliarReservaCoche(Time fechaFinOriginal, Time fechaFinNueva, String matricula) {
        return fbd.sePuedeAmpliarReservaCoche(fechaFinOriginal, fechaFinNueva, matricula);
=======
    public Boolean sePuedeAmpliarReservaCoche(Time fechaFinOriginal, Time fechaFinNueva, String matricula, String usuario){
        return fbd.sePuedeAmpliarReservaCoche(fechaFinOriginal, fechaFinNueva, matricula, usuario);
>>>>>>> Solucion errores
    }

}
