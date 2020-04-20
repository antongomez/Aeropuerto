package baseDatos;

import aeropuerto.FachadaAplicacion;
import aeropuerto.elementos.Vuelo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class daoVuelos extends AbstractDAO {

    public daoVuelos(Connection conexion, FachadaAplicacion fa) {
        super.setConexion(conexion);
        super.setFachadaAplicacion(fa);
    }

    public Boolean insertarVuelo(Vuelo v) {//true si se insertó y false si no
        Connection con;
        PreparedStatement stmVuelo = null;
        Boolean correcto;

        con = super.getConexion();

        try {
            stmVuelo = con.prepareStatement("insert into vuelo(numvuelo,origen,destino,"
                    + "fechasalidateorica, fechasalidareal, fechallegadateorica,"
                    + " fechallegadareal, precioactual, puertaembarque, cancelado,"
                    + "terminal,avion) "
                    + "values (?,?,?,?,?,?,?,?,?,?,?,?)");
            stmVuelo.setInt(1, v.getNumVuelo());
            stmVuelo.setString(2, v.getOrigen());
            stmVuelo.setString(3, v.getDestino());
            stmVuelo.setTimestamp(4, v.getFechasalidaTeo());
            stmVuelo.setTimestamp(5, v.getFechasalidaReal());
            stmVuelo.setTimestamp(6, v.getFechallegadaTeo());
            stmVuelo.setTimestamp(7, v.getFechallegadaReal());
            stmVuelo.setFloat(8, v.getPrecioActual());
            stmVuelo.setInt(9, v.getPuertaEmbarque());
            stmVuelo.setBoolean(10, v.getCancelado());
            stmVuelo.setInt(11, v.getTerminal());
            stmVuelo.setInt(12, v.getAvion());

            stmVuelo.executeUpdate();
            correcto = true;

        } catch (SQLException e) {
            getFachadaAplicacion().mostrarError(e.getMessage());
            correcto = false;
        } finally {
            try {
                stmVuelo.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
                correcto = false;
            }
        }
        return correcto;
    }

    public List<Vuelo> buscarVuelos(String numVuelo, String origen, String destino) {
        List<Vuelo> resultado = new ArrayList<>();
        Vuelo vueloActual;
        Connection con;
        PreparedStatement stmVuelo = null;
        ResultSet rsVuelo;

        con = this.getConexion();

        try {
            stmVuelo = con.prepareStatement("select numvuelo,origen,destino,fechasalidateorica, fechasalidareal,"
                    + " fechallegadateorica, fechallegadareal, precioactual, puertaembarque, cancelado,"
                    + " terminal, avion "
                    + "from vuelo "
                    + "where numvuelo like ? "
                    + "  and origen like ? "
                    + "  and destino like ? ");

            stmVuelo.setString(1, "%" + numVuelo + "%");
            stmVuelo.setString(2, "%" + origen + "%");
            stmVuelo.setString(3, "%" + destino + "%");
            rsVuelo = stmVuelo.executeQuery();
            while (rsVuelo.next()) {
                vueloActual = new Vuelo(rsVuelo.getInt("numvuelo"), rsVuelo.getString("origen"),
                        rsVuelo.getString("destino"), rsVuelo.getTimestamp("fechasalidateorica"),
                        rsVuelo.getTimestamp("fechasalidareal"), rsVuelo.getTimestamp("fechallegadateorica"),
                        rsVuelo.getTimestamp("fechallegadareal"), rsVuelo.getFloat("precioactual"),
                        rsVuelo.getInt("puertaembarque"), rsVuelo.getBoolean("cancelado"),
                        rsVuelo.getInt("terminal"), rsVuelo.getInt("avion"));

                resultado.add(vueloActual);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            this.getFachadaAplicacion().mostrarError(e.getMessage());
        } finally {
            try {
                stmVuelo.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
        return resultado;
    }
}
