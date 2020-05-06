package baseDatos;

import aeropuerto.FachadaAplicacion;
import aeropuerto.elementos.Coche;
import aeropuerto.util.Reserva;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class daoReservas extends AbstractDAO {

    public daoReservas(Connection conexion, FachadaAplicacion fa) {
        super.setConexion(conexion);
        super.setFachadaAplicacion(fa);
    }

    public List<Reserva> obtenerReservasUsuario(String dniUs) {

        List<Reserva> resultado = new ArrayList<>();
        Connection con;
        Reserva resActual;
        PreparedStatement stmRes = null;
        ResultSet rsRes;

        con = super.getConexion();

        try {
            stmRes = con.prepareStatement("select fechainicioreserva as fechainicio, fechafinreserva as fechafin, 'Coche' as tipo, cocheAlquiler as matricula, "
                    + "null as terminal, null as piso, null as numplaza "
                    + "from reservar where usuario=? and fechainicioreserva>NOW() UNION "
                    + "select fechaentrada as fechainicio, fechafin, 'Parking' as tipo, matricula, terminal, piso, numplaza "
                    + " from reservarparking "
                    + "where usuario=? and fechaentrada>NOW()");
            stmRes.setString(1, dniUs);
            stmRes.setString(2, dniUs);
            rsRes = stmRes.executeQuery();
         

            while (rsRes.next()) {
                if (rsRes.getString("tipo").equals("Coche")) {
                    resActual = new Reserva(rsRes.getString("tipo"), rsRes.getTimestamp("fechainicio"), rsRes.getTimestamp("fechafin"), rsRes.getString("matricula"));
                } else {
                    resActual = new Reserva(rsRes.getString("tipo"), rsRes.getTimestamp("fechainicio"), rsRes.getTimestamp("fechafin"), rsRes.getString("matricula"),
                            rsRes.getInt("terminal"), rsRes.getInt("piso"), rsRes.getInt("numplaza"));
                }
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

    public Boolean cancelarReservaParking(Reserva res, String dniUsu) {
        Connection con;
        PreparedStatement stmRes = null;
        con = super.getConexion();
        Boolean correcto = true;

        try {
            stmRes = con.prepareStatement("delete from reservarparking where usuario=? and"
                    + " terminal=? and piso=? and numplaza=? and fechaEntrada=?");
            stmRes.setString(1, dniUsu);
            stmRes.setInt(2, res.getTerminal());
            stmRes.setInt(3, res.getPiso());
            stmRes.setInt(4, res.getNumPlaza());
            stmRes.setTimestamp(5, res.getInicio().toTimestamp());
            stmRes.executeUpdate();

        } catch (SQLException e) {
            correcto = false;
            System.out.println(e.getMessage());
            this.getFachadaAplicacion().mostrarError(e.getMessage());
        } finally {
            try {
                stmRes.close();
            } catch (SQLException e) {
                correcto = false;
                System.out.println("Imposible cerrar cursores");
            }
        }
        return correcto;
    }

    public Boolean cancelarReservaCoche(Reserva res, String dniUsu) {
        Connection con;
        PreparedStatement stmRes = null;
        con = super.getConexion();
        Boolean correcto = true;

        try {
            stmRes = con.prepareStatement("delete from reservar where usuario=? and"
                    + " cocheAlquiler=? and fechaInicioReserva=?");
            stmRes.setString(1, dniUsu);
            stmRes.setString(2, res.getMatricula());
            stmRes.setTimestamp(3, res.getInicio().toTimestamp());
            stmRes.executeUpdate();

        } catch (SQLException e) {
            correcto = false;
            System.out.println(e.getMessage());
            this.getFachadaAplicacion().mostrarError(e.getMessage());
        } finally {
            try {
                stmRes.close();
            } catch (SQLException e) {
                correcto = false;
                System.out.println("Imposible cerrar cursores");
            }
        }
        return correcto;
    }

    public Boolean reservarParking(Reserva res, String dniUsu) {
        Connection con;
        PreparedStatement stmRes = null;
        con = super.getConexion();
        Boolean correcto = true;

        try {
            stmRes = con.prepareStatement("insert into reservarParking values"
                    + "(?, ?, ?, ?, ?, ?, ?)");
            stmRes.setString(1, dniUsu);
            stmRes.setInt(2, res.getTerminal());
            stmRes.setInt(3, res.getPiso());
            stmRes.setInt(4, res.getNumPlaza());
            stmRes.setTimestamp(5, res.getInicio().toTimestamp());
            stmRes.setTimestamp(6, res.getFin().toTimestamp());
            stmRes.setString(7, res.getMatricula());
            stmRes.executeUpdate();

        } catch (SQLException e) {
            correcto = false;
            System.out.println(e.getMessage());
            this.getFachadaAplicacion().mostrarError(e.getMessage());
            
        } finally {
            try {
                stmRes.close();
            } catch (SQLException e) {
                correcto = false;
                System.out.println("Imposible cerrar cursores");
            }
        }
        return correcto;
    }

    public Boolean reservarCoche(Reserva res, String dniUsu) {
        Connection con;
        PreparedStatement stmRes = null;
        con = super.getConexion();
        Boolean correcto = true;

        try {
            stmRes = con.prepareStatement("insert into reservar values"
                    + "(?, ?, ?, ?)");
            stmRes.setTimestamp(1, res.getInicio().toTimestamp());
            stmRes.setTimestamp(2, res.getFin().toTimestamp());
            stmRes.setString(3, dniUsu);
            stmRes.setString(4, res.getMatricula());
            stmRes.executeUpdate();

        } catch (SQLException e) {
            correcto = false;
            System.out.println(e.getMessage());
            this.getFachadaAplicacion().mostrarError(e.getMessage());
        } finally {
            try {
                stmRes.close();
            } catch (SQLException e) {
                correcto = false;
                System.out.println("Imposible cerrar cursores");
            }
        }
        return correcto;
    }
    
    public List<Reserva> obtenerReservasCocheUsuario(String dniUsuario){
        List<Reserva> resultado = new ArrayList<>();
        Connection con;
        PreparedStatement stmRes = null;
        con = super.getConexion();
        ResultSet rsRes;
        
        try {
            stmRes = con.prepareStatement("SELECT cast(res.fechainicioreserva as date), cast(res.fechafinreserva as date), res.cochealquiler as matricula, "
                    + "coche.preciopordia as preciodia, coche.modelo as modelo, "
                    + "'sin alquilar' as estado "
                    + "FROM reservar as res, cochealquiler as coche "
                    + "WHERE res.usuario=? and cast(fechainicioreserva as date)=cast(NOW() as date) and coche.matricula=res.cochealquiler "
                    + "and NOT EXISTS(SELECT * "
                    + "           FROM alquilar as alq "
                    + "           WHERE (cast(res.fechainicioreserva as date)-cast(alq.fechaalquiler as date))=0 "
                    + "           and res.cochealquiler=alq.matricula "
                    + "           and res.usuario=alq.usuario) "
                    + "UNION "
                    + "SELECT cast(res.fechainicioreserva as date), cast(res.fechafinreserva as date), res.cochealquiler as matricula, "
                    + "coche.preciopordia as preciodia, coche.modelo as modelo, "
                    + "'alquilado' as estado "
                    + "FROM reservar as res, cochealquiler as coche "
                    + "WHERE res.usuario=? and cast(fechainicioreserva as date)=cast(NOW() as date) and coche.matricula=res.cochealquiler "
                    + "and EXISTS(SELECT * "
                    + "           FROM alquilar as alq "
                    + "           WHERE (cast(res.fechainicioreserva as date)-cast(alq.fechaalquiler as date))=0 "
                    + "           and res.cochealquiler=alq.matricula "
                    + "           and res.usuario=alq.usuario) ");
            stmRes.setString(1, dniUsuario);
            stmRes.setString(2, dniUsuario);
            rsRes=stmRes.executeQuery();
            while(rsRes.next()){
                Reserva reserva = new Reserva(rsRes.getTimestamp("fechainicioreserva"),rsRes.getTimestamp("fechafinreserva"),
                "coche", rsRes.getString("matricula"),rsRes.getString("modelo"),rsRes.getFloat("preciodia"), rsRes.getString("estado"));
                resultado.add(reserva);
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
    
    public Boolean introducirAlquiler(Reserva reserva, String dni){
        Connection con;
        PreparedStatement stmRes = null;
        con = super.getConexion();
        Boolean correcto = true;

        try {
            stmRes = con.prepareStatement("insert into alquilar values"
                    + "(NOW(),?, ?, ?, null)");
            stmRes.setString(1, dni);
            stmRes.setString(2, reserva.getMatricula());
            stmRes.setTimestamp(3, reserva.getFin().toTimestamp());
            stmRes.executeUpdate();

        } catch (SQLException e) {
            correcto = false;
            System.out.println(e.getMessage());
            this.getFachadaAplicacion().mostrarError(e.getMessage());
        } finally {
            try {
                stmRes.close();
            } catch (SQLException e) {
                correcto = false;
                System.out.println("Imposible cerrar cursores");
            }
        }
        return correcto;
    }

}
