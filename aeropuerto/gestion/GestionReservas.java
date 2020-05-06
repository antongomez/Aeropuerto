/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aeropuerto.gestion;

import aeropuerto.util.Reserva;
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

    public List<Reserva> obtenerReservasUsuario(String dniUs) {
        return fbd.obtenerReservasUsuario(dniUs);
    }

    public Boolean cancelarReserva(Reserva res, String dniUs) {

        if (res.getTipo().equals("Parking")) {
            return fbd.cancelarReservaParking(res, dniUs);
        } else {
            return fbd.cancelarReservaCoche(res, dniUs);
        }

    }

    public Boolean reservarParking(Reserva reserva, String dniUsuario) {
        return fbd.reservarParking(reserva, dniUsuario);
    }

    public Boolean reservarCoche(Reserva reserva, String dniUsuario) {
        return fbd.reservarCoche(reserva, dniUsuario);
    }
    
    public List<Reserva> obtenerReservasCocheUsuario(String dniUsuario){
        return fbd.obtenerReservasCocheUsuario(dniUsuario);
    }
    
    public Boolean introducirAlquiler(Reserva reserva, String dni){
        return fbd.introducirAlquiler(reserva, dni);
    }

}
