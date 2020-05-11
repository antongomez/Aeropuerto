package baseDatos;

import aeropuerto.FachadaAplicacion;
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
            stmRes = con.prepareStatement("SELECT fechaentrada as fechainicio, fechafin, matricula, terminal, piso, numplaza, \n"
                    + "false as enCurso \n"
                    + "FROM reservarparking \n"
                    + "WHERE usuario = ? \n"
                    + "and cast(fechaEntrada as date)-cast(NOW() as date)>0 \n"
                    + "UNION \n"
                    + "SELECT fechaentrada as fechainicio, fechafin, matricula, terminal, piso, numplaza, \n"
                    + "true as enCurso \n"
                    + "FROM reservarparking \n"
                    + "WHERE usuario = ? \n"
                    + "and cast(fechaEntrada as date)-cast(NOW() as date)<=0 \n"
                    + "and cast(fechaFin as date)-cast(NOW() as date)>=0 \n");
            stmRes.setString(1, dniUs);
            stmRes.setString(2, dniUs);
            rsRes = stmRes.executeQuery();

            while (rsRes.next()) {
                resActual = new ReservaParking(rsRes.getTimestamp("fechainicio"), rsRes.getTimestamp("fechafin"), rsRes.getString("matricula"),
                        rsRes.getInt("terminal"), rsRes.getInt("piso"), rsRes.getInt("numplaza"), rsRes.getBoolean("enCurso"));

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
            stmRes = con.prepareStatement("SELECT r.fechainicioreserva as fechainicio, r.fechafinreserva as fechafin, \n"
                    + "r.cocheAlquiler as matricula, false as enCurso, false as devRetraso \n"
                    + "FROM reservar as r \n"
                    + "WHERE usuario = ? \n"
                    + "and cast(fechainicioreserva as date)-cast(NOW() as date)>=0 \n"
                    + "and NOT EXISTS (SELECT * \n"
                    + "                FROM alquilar as a \n"
                    + "                WHERE (cast(r.fechainicioreserva as date)-cast(a.fechaalquiler as date))=0 \n"
                    + "                and r.cochealquiler=a.matricula \n"
                    + "                and r.usuario=a.usuario) \n"
                    + "UNION \n"
                    + "SELECT fechaAlquiler as fechainicio, fechaTeoricaDevolucion as fechafin, \n"
                    + "matricula as matricula, true as enCurso, "
                    + "(CASE when (cast(fechaTeoricaDevolucion as date)-(cast(NOW() as date)))<0 then true else false end) as devRetraso \n"
                    + "FROM alquilar \n"
                    + "WHERE usuario = ? \n"
                    + "and fechaDevolucion is null");
            stmRes.setString(1, dniUs);
            stmRes.setString(2, dniUs);
            rsRes = stmRes.executeQuery();

            while (rsRes.next()) {
                resActual = new ReservaCoche(rsRes.getTimestamp("fechainicio"), rsRes.getTimestamp("fechafin"),
                        rsRes.getString("matricula"), rsRes.getBoolean("enCurso"), rsRes.getBoolean("devRetraso"));
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
            stmRes = con.prepareStatement("DELETE from reservarparking \n"
                    + "WHERE usuario=? "
                    + "and terminal=? and piso=? and numplaza=? and fechaEntrada=?");
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
            stmRes = con.prepareStatement("DELETE from reservar \n"
                    + "WHERE usuario=? "
                    + "and cocheAlquiler=? and fechaInicioReserva=?");
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
            stmRes = con.prepareStatement("INSERT into reservarParking \n"
                    + "VALUES(?, ?, ?, ?, ?, ?, ?)");
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
            stmRes = con.prepareStatement("INSERT into reservar \n"
                    + "VALUES(?, ?, ?, ?)");
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
            stmRes = con.prepareStatement("SELECT cast(res.fechainicioreserva as date), cast(res.fechafinreserva as date), \n"
                    + "res.cochealquiler as matricula, coche.preciopordia as preciodia, coche.modelo as modelo, \n"
                    + "'sin alquilar' as estado \n"
                    + "FROM reservar as res, cochealquiler as coche \n"
                    + "WHERE res.usuario=? and cast(fechainicioreserva as date)=cast(NOW() as date) \n"
                    + "and coche.matricula=res.cochealquiler and coche.retirado=false \n"
                    + "and NOT EXISTS(SELECT * \n"
                    + "               FROM alquilar as alq \n"
                    + "               WHERE (cast(res.fechainicioreserva as date)-cast(alq.fechaalquiler as date))=0 \n"
                    + "               and res.cochealquiler=alq.matricula \n"
                    + "               and res.usuario=alq.usuario) \n"
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
                    + "           and res.usuario=alq.usuario) "
                    + "ORDER BY fechainicioreserva desc");
            stmRes.setString(1, dniUsuario);
            stmRes.setString(2, dniUsuario);
            rsRes = stmRes.executeQuery();
            while (rsRes.next()) {
                ReservaCoche reserva = new ReservaCoche(rsRes.getTimestamp("fechainicioreserva"), rsRes.getTimestamp("fechafinreserva"),
                        rsRes.getString("matricula"), rsRes.getString("modelo"), rsRes.getFloat("preciodia"), rsRes.getString("estado"));
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
            stmRes = con.prepareStatement("INSERT into alquilar "
                    + "VALUES(?,?, ?, ?, null)");
            stmRes.setTimestamp(1, Time.diaActual().toTimestamp());
            stmRes.setString(2, dni);
            stmRes.setString(3, matricula);
            stmRes.setTimestamp(4, fin.toTimestamp());
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
            stmAlquiler = con.prepareStatement("SELECT alq.fechaAlquiler as inicio, alq.fechaTeoricaDevolucion as fin, \n"
                    + "alq.usuario as usuario, coche.precioPorDia as precioDia \n"
                    + "FROM alquilar as alq, cocheAlquiler as coche \n"
                    + "WHERE alq.matricula=? and alq.matricula=coche.matricula and alq.fechaDevolucion is null ");
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

            stmUsuario = con.prepareStatement("UPDATE alquilar \n"
                    + "SET fechaDevolucion=NOW() \n"
                    + "WHERE matricula=? \n"
                    + "and cast(fechaAlquiler as date)-cast(? as date)=0 \n"
                    + "and usuario=? ");

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
    
    public Boolean sePuedeAmpliarReservaCoche(Time fechaFinOriginal, Time fechaFinNueva, String matricula, String usuario){
        Connection con;
        PreparedStatement stmAlquiler = null;
        ResultSet rsAlquiler;
        Boolean result = true;

        con = this.getConexion();

        try {
            stmAlquiler = con.prepareStatement("(select cochealquiler "
                    + "from reservar where cast(fechainicioreserva as date) between cast(? as date) and cast(? as date) "
                    + "and cochealquiler=?) UNION "
                    + "(select matricula from alquilar where fechaalquiler between cast(? as date) and cast(? as date) "
                    + "and matricula=?) ");
            stmAlquiler = con.prepareStatement("(select cochealquiler " +
            "from reservar where cast(fechainicioreserva as date) between cast(? as date) and cast(? as date) " +
            "and cochealquiler=? "
          + "and usuario <> ?) UNION " +
            "(select matricula from alquilar where fechaalquiler between cast(? as date) and cast(? as date) " +
            "and matricula=?"
          + "and usuario <> ?) ");
            stmAlquiler.setTimestamp(1, fechaFinOriginal.toTimestamp());
            stmAlquiler.setTimestamp(2, fechaFinNueva.toTimestamp());
            stmAlquiler.setString(3, matricula);
            stmAlquiler.setString(4, usuario);
            stmAlquiler.setTimestamp(5, fechaFinOriginal.toTimestamp());
            stmAlquiler.setTimestamp(6, fechaFinNueva.toTimestamp());
            stmAlquiler.setString(7, matricula);
            stmAlquiler.setString(8, usuario);
            rsAlquiler = stmAlquiler.executeQuery();

            if (rsAlquiler.next()) {
                result = false;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            this.getFachadaAplicacion().mostrarError(e.getMessage());
            result = false;
        } finally {
            try {
                stmAlquiler.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
                result = false;
            }
        }
        return result;
    }

}
