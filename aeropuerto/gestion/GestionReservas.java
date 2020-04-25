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

/**
 *
 * @author Esther
 */
public class GestionReservas {
    FachadaGui fgui;
    FachadaBaseDatos fbd;

    public GestionReservas(FachadaGui fgui, FachadaBaseDatos fbd) {
        this.fgui = fgui;
        this.fbd = fbd;
    }
    public List<Reserva> obtenerReservasUsuario(String dniUs){
        return fbd.obtenerReservasUsuario(dniUs);
    }
}
