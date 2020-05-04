package baseDatos;

import aeropuerto.FachadaAplicacion;
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
    
    public void obtenerResrvasCocheUsuario(String dniUsuario){
        
    }

}
