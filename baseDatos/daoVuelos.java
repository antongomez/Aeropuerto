package baseDatos;

import aeropuerto.FachadaAplicacion;
import aeropuerto.elementos.Usuario;
import aeropuerto.elementos.Vuelo;
import aeropuerto.util.Time;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.ObservableList;

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
            stmVuelo.setString(1, v.getNumVuelo());
            stmVuelo.setString(2, v.getOrigen());
            stmVuelo.setString(3, v.getDestino());
            stmVuelo.setString(4, v.getFechasalidaTeo().getStringSql());
            stmVuelo.setString(5, v.getFechasalidaReal().getStringSql());
            stmVuelo.setString(6, v.getFechallegadaTeo().getStringSql());
            stmVuelo.setString(7, v.getFechallegadaReal().getStringSql());
            stmVuelo.setFloat(8, v.getPrecioActual());
            stmVuelo.setInt(9, v.getPuertaEmbarque());
            stmVuelo.setBoolean(10, v.getCancelado());
            stmVuelo.setInt(11, v.getTerminal());
            stmVuelo.setString(12, v.getAvion().getCodigo());

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

    public List<Vuelo> buscarVuelos(String numVuelo, String origen, String destino, Time fechaSalida, Time fechaLlegada) {
        List<Vuelo> resultado = new ArrayList<>();
        Vuelo vueloActual;
        Connection con;
        PreparedStatement stmVuelo = null;
        ResultSet rsVuelo;

        con = this.getConexion();

        try {
            String consulta = "select numvuelo,origen,destino,fechasalidateorica, fechasalidareal,"
                    + " fechallegadateorica, fechallegadareal, precioactual, puertaembarque, cancelado,"
                    + " terminal, avion "
                    + "from vuelo "
                    + "where numvuelo like ? "
                    + "  and origen like ? "
                    + "  and destino like ? ";

            if (fechaSalida != null) {
                consulta += "  and fechasalidateorica >= ? ";
            }
            if (fechaLlegada != null) {
                consulta += "  and fechallegadateorica >= ? ";
            }
            //Ordenamos os voos por data de saida ascendente
            consulta += "order by fechasalidateorica asc ";

            stmVuelo = con.prepareStatement(consulta);
            stmVuelo.setString(1, "%" + numVuelo + "%");
            stmVuelo.setString(2, "%" + origen + "%");
            stmVuelo.setString(3, "%" + destino + "%");

            if ((fechaSalida != null) && (fechaLlegada != null)) {
                stmVuelo.setTimestamp(4, fechaSalida.toTimestamp());
                stmVuelo.setTimestamp(5, fechaLlegada.toTimestamp());

            } else if ((fechaSalida != null) && (fechaLlegada == null)) {
                stmVuelo.setTimestamp(4, fechaSalida.toTimestamp());

            } else if ((fechaSalida == null) && (fechaLlegada != null)) {
                stmVuelo.setTimestamp(4, fechaLlegada.toTimestamp());
            }

            rsVuelo = stmVuelo.executeQuery();
            while (rsVuelo.next()) {
                vueloActual = new Vuelo(rsVuelo.getString("numvuelo"), rsVuelo.getString("origen"),
                        rsVuelo.getString("destino"), rsVuelo.getTimestamp("fechasalidateorica"),
                        rsVuelo.getTimestamp("fechasalidareal"), rsVuelo.getTimestamp("fechallegadateorica"),
                        rsVuelo.getTimestamp("fechallegadareal"), rsVuelo.getFloat("precioactual"),
                        rsVuelo.getInt("puertaembarque"), rsVuelo.getBoolean("cancelado"),
                        rsVuelo.getInt("terminal"), rsVuelo.getString("avion"));

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

    public List<Vuelo> obtenerVuelosUsuario(String dniUs) {
        List<Vuelo> resultado = new ArrayList<>();
        Connection con;
        Vuelo vueloActual;
        PreparedStatement stmVuelo = null;
        ResultSet rsVuelo;

        con = super.getConexion();

        try {
            stmVuelo = con.prepareStatement("select v.numvuelo as numvuelo, v.origen as origen, "
                    + "v.destino as destino, v.fechasalidareal as fechasalidareal, v.fechallegadareal as fechallegadareal, "
                    + "c.preciobillete as preciobillete, v.cancelado as cancelado "
                    + "from usuario u, vuelo v, comprarBillete c "
                    + "where u.dni=c.usuario and v.numVuelo=c.vuelo and u.dni=?");
            stmVuelo.setString(1, dniUs);
            rsVuelo = stmVuelo.executeQuery();
            while (rsVuelo.next()) {
                vueloActual = new Vuelo(rsVuelo.getString("numvuelo"), rsVuelo.getString("origen"), rsVuelo.getString("destino"),
                        rsVuelo.getTimestamp("fechasalidareal"), rsVuelo.getTimestamp("fechasalidareal"), rsVuelo.getTimestamp("fechallegadareal"), rsVuelo.getTimestamp("fechallegadareal"),
                        rsVuelo.getFloat("preciobillete"), null, rsVuelo.getBoolean("cancelado"),
                        null, null);

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

    public void obtenerDatosAvionVuelo(Vuelo v) {
        String aer;
        Connection con;
        PreparedStatement stmVuelo = null;
        ResultSet rsVuelo;

        con = super.getConexion();

        try {
            stmVuelo = con.prepareStatement("select distinct vuelo.aerolinea as aerolinea, "
                    + "vuelo.preciobasemaleta as precioMaleta, vuelo.pesoBaseMaleta as pesoMaleta, "
                    + "cN-nN as plazasRestantesNormal, cP-nP as plazasRestantesPremium, cN as plazasNormal, "
                    + "cP as plazasPremium from "
                    + "(select count(*) as nN from vuelo v, comprarbillete c "
                    + "where v.numvuelo=c.vuelo and v.numvuelo=? and tipoasiento='normal') as nN,"
                    + "(select count(*) as nP from vuelo v, comprarbillete c "
                    + "where v.numvuelo=c.vuelo and v.numvuelo=? and tipoasiento='premium') as nP,"
                    + "(select aerolinea, precioBaseMaleta, pesoBaseMaleta, m.capacidadnormal as cN, m.capacidadPremium as cP "
                    + "from vuelo v, aerolinea a, avion av, modeloAvion m "
                    + "where avion=av.codigo and av.aerolinea=a.nombre and m.nombre=av.modeloavion "
                    + "and v.numvuelo=?) as vuelo");
            stmVuelo.setString(1, v.getNumVuelo());
            stmVuelo.setString(2, v.getNumVuelo());
            stmVuelo.setString(3, v.getNumVuelo());
            rsVuelo = stmVuelo.executeQuery();
            if (rsVuelo.next()) {
                v.setAerolinea(rsVuelo.getString("aerolinea"), rsVuelo.getFloat("precioMaleta"), rsVuelo.getFloat("pesoMaleta"));
                v.setPlazasNormal(rsVuelo.getInt("plazasRestantesNormal"));
                v.setPlazasPremium(rsVuelo.getInt("plazasRestantesPremium"));
                v.getAvion().setAsientosNormales(rsVuelo.getInt("plazasNormal"));
                v.getAvion().setAsientosPremium(rsVuelo.getInt("plazasPremium"));
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

    }

    public void comprarBilletes(ObservableList<Usuario> usuarios) {
        Connection con;
        PreparedStatement stmBillete = null;

        con = super.getConexion();

        try {
            stmBillete = con.prepareStatement("insert into comprarbillete(usuario,vuelo,numAsiento,"
                    + "tipoAsiento, numMaletasReserva, tenerAcompanhante, "
                    + "precioBillete) "
                    + "values (?,?,?,?,?,?,?)");
            for (Usuario usuario : usuarios) {
                stmBillete.setString(1, usuario.getDni());
                stmBillete.setString(2, usuario.getVueloEnEspera().getNumVuelo());
                stmBillete.setInt(3, usuario.getVueloEnEspera().getAsiento());
                if(usuario.getVueloEnEspera().getPremium()){
                    stmBillete.setString(4, "premium");
                }
                else{
                    stmBillete.setString(4, "normal");
                }
                stmBillete.setInt(5, usuario.getVueloEnEspera().getNumMaletas());
                stmBillete.setBoolean(6, usuario.getVueloEnEspera().getAcompanante());
                stmBillete.setFloat(7, usuario.getVueloEnEspera().getPrecio());

                stmBillete.executeUpdate();
            }

        } catch (SQLException e) {
            getFachadaAplicacion().mostrarError(e.getMessage());
        } finally {
            try {
                stmBillete.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
    }

}
