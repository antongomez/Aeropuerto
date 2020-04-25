/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baseDatos;

import aeropuerto.FachadaAplicacion;
import aeropuerto.elementos.Vuelo;
import aeropuerto.util.Reserva;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Esther
 */
public class daoReservas extends AbstractDAO {
    
    public daoReservas(Connection conexion, FachadaAplicacion fa) {
        super.setConexion(conexion);
        super.setFachadaAplicacion(fa);
    }
    
    public List<Reserva> obtenerReservasUsuario(String dniUs){
        
        List<Reserva> resultado = new ArrayList<>();
        Connection con;
        Reserva resActual;
        PreparedStatement stmRes = null;
        ResultSet rsRes;

        con = super.getConexion();

        try {
            stmRes = con.prepareStatement("select fechainicioreserva as fechainicio, fechafinreserva as fechafin, 'Coche' as tipo, cocheAlquiler as matricula " +
            "from reservar where usuario=? and fechainicioreserva>NOW() UNION " +
            "select fechaentrada as fechainicio, fechafin, 'Parking' as tipo, matricula from reservarparking " +
            "where usuario=? and fechaentrada>NOW()");
            stmRes.setString(1, dniUs);
            stmRes.setString(2, dniUs);
            rsRes = stmRes.executeQuery();
            while (rsRes.next()) {
                resActual = new Reserva(rsRes.getString("tipo"),rsRes.getTimestamp("fechainicio"),rsRes.getTimestamp("fechafin"),rsRes.getString("matricula"));
                
                resultado.add(resActual);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            this.getFachadaAplicacion().mostrarError(e.getMessage());
        } finally {
            try {
                stmRes.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }

        return resultado;
    }
        
        
        
    }

