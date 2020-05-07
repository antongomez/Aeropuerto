package baseDatos;

import aeropuerto.FachadaAplicacion;
import aeropuerto.elementos.Coche;
import aeropuerto.util.reservas.Reserva;
import aeropuerto.util.Time;
import aeropuerto.util.reservas.ReservaCoche;
import aeropuerto.util.reservas.ReservaParking;
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

    public List<ReservaParking> obterResParkingUsuario(String dniUs) {

        List<ReservaParking> resultado = new ArrayList<>();
        Connection con;
        ReservaParking resActual;
        PreparedStatement stmRes = null;
        ResultSet rsRes;

        con = super.getConexion();

        try {
            stmRes = con.prepareStatement("select fechaentrada as fechainicio, fechafin, matricula, terminal, piso, numplaza \n"
                    + "from reservarparking \n"
                    + "where usuario = ? \n"
                    + "  and fechaentrada > NOW()");
            stmRes.setString(1, dniUs);
            rsRes = stmRes.executeQuery();

            while (rsRes.next()) {
                resActual = new ReservaParking(rsRes.getTimestamp("fechainicio"), rsRes.getTimestamp("fechafin"), rsRes.getString("matricula"),
                        rsRes.getInt("terminal"), rsRes.getInt("piso"), rsRes.getInt("numplaza"));

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

    public List<ReservaCoche> obterResCocheUsuario(String dniUs) {

        List<ReservaCoche> resultado = new ArrayList<>();
        Connection con;
        ReservaCoche resActual;
        PreparedStatement stmRes = null;
        ResultSet rsRes;

        con = super.getConexion();

        try {
            stmRes = con.prepareStatement("select fechainicioreserva as fechainicio, fechafinreserva as fechafin, cocheAlquiler as matricula\n"
                    + "from reservar \n"
                    + "where usuario = ? \n"
                    + "  and fechainicioreserva > NOW()");
            stmRes.setString(1, dniUs);
            rsRes = stmRes.executeQuery();

            while (rsRes.next()) {
                resActual = new ReservaCoche(rsRes.getTimestamp("fechainicio"), rsRes.getTimestamp("fechafin"), rsRes.getString("matricula"));
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

    public Boolean cancelarReservaParking(ReservaParking res, String dniUsu) {
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

    public Boolean cancelarReservaCoche(ReservaCoche res, String dniUsu) {
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

    public Boolean reservarParking(ReservaParking res, String dniUsu) {
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

    public Boolean reservarCoche(ReservaCoche res, String dniUsu) {
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

    public List<ReservaCoche> obtenerReservasCocheUsuario(String dniUsuario) {
        List<ReservaCoche> resultado = new ArrayList<>();
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
            rsRes = stmRes.executeQuery();
            while (rsRes.next()) {
                ReservaCoche reserva = new ReservaCoche(rsRes.getTimestamp("fechainicioreserva"), rsRes.getTimestamp("fechafinreserva"), rsRes.getString("matricula"), rsRes.getString("modelo"), rsRes.getFloat("preciodia"), rsRes.getString("estado"));
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

    public Boolean introducirAlquiler(String matricula, Time fin, String dni) {
        Connection con;
        PreparedStatement stmRes = null;
        con = super.getConexion();
        Boolean correcto = true;

        try {
            stmRes = con.prepareStatement("insert into alquilar values"
                    + "(NOW(),?, ?, ?, null,true)");
            stmRes.setString(1, dni);
            stmRes.setString(2, matricula);
            stmRes.setTimestamp(3, fin.toTimestamp());
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

    public ReservaCoche buscarAlquilerDevolucion(String matricula) {
        Connection con;
        PreparedStatement stmAlquiler = null;
        ResultSet rsAlquiler;
        ReservaCoche alquiler = null;

        con = this.getConexion();

        try {
            stmAlquiler = con.prepareStatement("select alq.fechaAlquiler as inicio, alq.fechaTeoricaDevolucion as fin, "
                    + "alq.usuario as usuario, coche.precioPorDia as precioDia "
                    + "from alquilar as alq, cocheAlquiler as coche "
                    + "where alq.matricula=? and alq.matricula=coche.matricula and alq.fechaDevolucion is null ");
            stmAlquiler.setString(1, matricula);
            rsAlquiler = stmAlquiler.executeQuery();

            if (rsAlquiler.next()) {
                alquiler = new ReservaCoche(new Time(rsAlquiler.getTimestamp("inicio")), new Time(rsAlquiler.getTimestamp("fin")),
                        matricula, rsAlquiler.getFloat("precioDia"), rsAlquiler.getString("usuario"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            this.getFachadaAplicacion().mostrarError(e.getMessage());
        } finally {
            try {
                stmAlquiler.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
        return alquiler;
    }

    public Boolean devolucionCoche(Reserva alquiler) {
        Connection con;
        PreparedStatement stmUsuario = null;
        PreparedStatement stmBorrado = null;
        Boolean correcto;

        con = super.getConexion();

        try {

            stmUsuario = con.prepareStatement("update alquilar "
                    + "set fechaDevolucion=NOW() "
                    + "where matricula=? and fechaAlquiler=? and usuario=? ");

            stmUsuario.setString(1, alquiler.getMatricula());
            stmUsuario.setTimestamp(2, alquiler.getInicio().toTimestamp());
            stmUsuario.setString(3, alquiler.getUsuario());

            stmUsuario.executeUpdate();
            correcto = true;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            this.getFachadaAplicacion().mostrarError(e.getMessage());
            correcto = false;
        } finally {
            try {
                stmUsuario.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
        return correcto;
    }

}
