package baseDatos;

import aeropuerto.FachadaAplicacion;
import aeropuerto.elementos.Aerolinea;
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
            stmVuelo = con.prepareStatement("select v.numvuelo, v.origen, "
                    + "v.destino, v.fechasalidateorica, v.fechasalidareal, v.fechallegadateorica, v.fechallegadareal, "
                    + "c.preciobillete as preciobillete, v.cancelado as cancelado "
                    + "from usuario u, vuelo v, comprarBillete c "
                    + "where u.dni=c.usuario and v.numVuelo=c.vuelo and u.dni=? "
                    + "order by v.fechasalidateorica asc ");
            stmVuelo.setString(1, dniUs);
            rsVuelo = stmVuelo.executeQuery();
            while (rsVuelo.next()) {
                vueloActual = new Vuelo(rsVuelo.getString("numvuelo"), rsVuelo.getString("origen"), rsVuelo.getString("destino"),
                        rsVuelo.getTimestamp("fechasalidateorica"), rsVuelo.getTimestamp("fechasalidareal"), rsVuelo.getTimestamp("fechallegadateorica"), rsVuelo.getTimestamp("fechallegadareal"),
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

    public List<Vuelo> obtenerVuelosRealizadosUsuario(String dniUs) {
        List<Vuelo> resultado = new ArrayList<>();
        Connection con;
        Vuelo vueloActual;
        PreparedStatement stmVuelo = null;
        ResultSet rsVuelo;

        con = super.getConexion();

        try {
            stmVuelo = con.prepareStatement("select v.numvuelo, v.origen, "
                    + "v.destino, v.fechasalidateorica, v.fechasalidareal, v.fechallegadateorica, v.fechallegadareal, "
                    + "c.preciobillete, v.cancelado "
                    + "from usuario u, vuelo v, comprarBillete c "
                    + "where u.dni=c.usuario and v.numVuelo=c.vuelo and u.dni=? "
                    + "  and v.fechallegadareal < now()"
                    + "order by v.fechasalidateorica asc ");
            stmVuelo.setString(1, dniUs);
            rsVuelo = stmVuelo.executeQuery();
            while (rsVuelo.next()) {
                vueloActual = new Vuelo(rsVuelo.getString("numvuelo"), rsVuelo.getString("origen"), rsVuelo.getString("destino"),
                        rsVuelo.getTimestamp("fechasalidateorica"), rsVuelo.getTimestamp("fechasalidareal"), rsVuelo.getTimestamp("fechallegadateorica"), rsVuelo.getTimestamp("fechallegadareal"),
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

    public List<Vuelo> obtenerVuelosFuturosUsuario(String dniUs) {
        List<Vuelo> resultado = new ArrayList<>();
        Connection con;
        Vuelo vueloActual;
        PreparedStatement stmVuelo = null;
        ResultSet rsVuelo;

        con = super.getConexion();

        try {
            stmVuelo = con.prepareStatement("select v.numvuelo, v.origen, "
                    + "v.destino, v.fechasalidateorica, v.fechasalidareal, v.fechallegadateorica, v.fechallegadareal, "
                    + "c.preciobillete, v.cancelado "
                    + "from usuario u, vuelo v, comprarBillete c "
                    + "where u.dni=c.usuario and v.numVuelo=c.vuelo and u.dni=? "
                    + "  and v.fechallegadareal >= now()"
                    + "order by v.fechasalidateorica asc ");
            stmVuelo.setString(1, dniUs);
            rsVuelo = stmVuelo.executeQuery();
            while (rsVuelo.next()) {
                vueloActual = new Vuelo(rsVuelo.getString("numvuelo"), rsVuelo.getString("origen"), rsVuelo.getString("destino"),
                        rsVuelo.getTimestamp("fechasalidateorica"), rsVuelo.getTimestamp("fechasalidareal"), rsVuelo.getTimestamp("fechallegadateorica"), rsVuelo.getTimestamp("fechallegadareal"),
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
                for (int i = 1; i <= rsVuelo.getInt("plazasNormal"); i++) {
                    v.getAsientosNormalesDisponibles().put(i, true);
                }
                for (int i = rsVuelo.getInt("plazasNormal") + 1; i <= rsVuelo.getInt("plazasNormal") + rsVuelo.getInt("plazasPremium"); i++) {
                    v.getAsientosPremiumDisponibles().put(i, true);
                }
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

    public void obtenerAsientos(Vuelo vuelo) {
        Connection con;
        PreparedStatement stmAsientos = null;
        ResultSet rsAsientos;

        con = super.getConexion();

        try {
            stmAsientos = con.prepareStatement("select numAsiento "
                    + "from comprarbillete "
                    + "where vuelo = ? and tipoAsiento = 'normal' ");
            stmAsientos.setString(1, vuelo.getNumVuelo());
            rsAsientos = stmAsientos.executeQuery();
            while (rsAsientos.next()) {
                vuelo.getAsientosNormalesDisponibles().replace(rsAsientos.getInt("numAsiento"), false);
            }
            stmAsientos = con.prepareStatement("select numAsiento "
                    + "from comprarbillete "
                    + "where vuelo = ? and tipoAsiento = 'premium' ");
            stmAsientos.setString(1, vuelo.getNumVuelo());
            rsAsientos = stmAsientos.executeQuery();
            while (rsAsientos.next()) {
                vuelo.getAsientosPremiumDisponibles().replace(rsAsientos.getInt("numAsiento"), false);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            this.getFachadaAplicacion().mostrarError(e.getMessage());
        } finally {
            try {
                stmAsientos.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
    }

    public Boolean comprarBilletes(ObservableList<Usuario> usuarios) {
        Connection con;
        PreparedStatement stmBillete = null;
        Boolean correcto;

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
                if (usuario.getVueloEnEspera().getPremium()) {
                    stmBillete.setString(4, "premium");
                } else {
                    stmBillete.setString(4, "normal");
                }
                stmBillete.setInt(5, usuario.getVueloEnEspera().getNumMaletas());
                stmBillete.setBoolean(6, usuario.getVueloEnEspera().getAcompanante());
                stmBillete.setFloat(7, usuario.getVueloEnEspera().getPrecio());

                stmBillete.executeUpdate();
            }
            correcto = true;
        } catch (SQLException e) {
            getFachadaAplicacion().mostrarError(e.getMessage());
            correcto = false;
        } finally {
            try {
                stmBillete.close();
            } catch (SQLException e) {
                correcto = false;
                System.out.println("Imposible cerrar cursores");
            }
        }
        return correcto;
    }

    public Boolean plazoDevolucion(String vuelo) {
        Connection con;
        PreparedStatement stmVuelo = null;
        ResultSet rsVuelo;

        Boolean enPlazo = false;

        con = super.getConexion();

        try {
            stmVuelo = con.prepareStatement("select * "
                    + "from vuelo "
                    + "where numvuelo=? and fechasalidareal<(SELECT DATEADD(DAY,15,NOW()))");
            stmVuelo.setString(1, vuelo);
            rsVuelo = stmVuelo.executeQuery();
            if (rsVuelo.next()) {
                enPlazo = false;
            } else {
                enPlazo = true;
            }
        } catch (SQLException e) {
            getFachadaAplicacion().mostrarError(e.getMessage());
        } finally {
            try {
                stmVuelo.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }

        return enPlazo;
    }

    public Boolean vueloRealizado(String vuelo) {
        Connection con;
        PreparedStatement stmVuelo = null;
        ResultSet rsVuelo;

        Boolean realizado = false;

        con = super.getConexion();

        try {
            stmVuelo = con.prepareStatement("select * "
                    + "from vuelo "
                    + "where numvuelo=? and fechasalidareal<NOW()");
            stmVuelo.setString(1, vuelo);
            rsVuelo = stmVuelo.executeQuery();
            if (rsVuelo.next()) {
                realizado = true;
            } else {
                realizado = false;
            }
        } catch (SQLException e) {
            getFachadaAplicacion().mostrarError(e.getMessage());
        } finally {
            try {
                stmVuelo.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }

        return realizado;
    }

    public Boolean devolverBillete(String vuelo, String dni) {
        Connection con;
        PreparedStatement stmBillete = null;
        Boolean correcto;

        con = super.getConexion();

        try {
            stmBillete = con.prepareStatement("delete from comprarbillete "
                    + "where vuelo=? and usuario=?");
            stmBillete.setString(1, vuelo);
            stmBillete.setString(2, dni);
            stmBillete.executeUpdate();
            correcto = true;
        } catch (SQLException e) {
            getFachadaAplicacion().mostrarError(e.getMessage());
            correcto = false;
        } finally {
            try {
                stmBillete.close();
            } catch (SQLException e) {
                correcto = false;
                System.out.println("Imposible cerrar cursores");
            }
        }
        return correcto;
    }

    public List<Vuelo> verSalidas() {
        List<Vuelo> resultado = new ArrayList<>();
        Connection con;
        Vuelo vueloActual;
        PreparedStatement stmVuelo = null;
        ResultSet rsVuelo;

        con = super.getConexion();

        try {
            stmVuelo = con.prepareStatement("select numVuelo, origen, destino, fechaSalidaReal, "
                    + "fechaSalidaReal-fechaSalidaTeorica as retraso,terminal, puertaembarque, cancelado "
                    + "from vuelo v "
                    + "where fechaSalidaReal>NOW() and v.origen=? "
                    + "ORDER BY fechaSalidaReal asc");
            stmVuelo.setString(1, "Folgoso do Courel");
            rsVuelo = stmVuelo.executeQuery();
            while (rsVuelo.next()) {
                vueloActual = new Vuelo(rsVuelo.getString("numvuelo"), rsVuelo.getString("origen"), rsVuelo.getString("destino"),
                        rsVuelo.getTimestamp("fechaSalidaReal"), null, rsVuelo.getInt("puertaembarque"), rsVuelo.getBoolean("cancelado"),
                        rsVuelo.getInt("terminal"), rsVuelo.getString("retraso"));;

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

    public List<Vuelo> verLlegadas() {
        List<Vuelo> resultado = new ArrayList<>();
        Connection con;
        Vuelo vueloActual;
        PreparedStatement stmVuelo = null;
        ResultSet rsVuelo;

        con = super.getConexion();

        try {
            stmVuelo = con.prepareStatement("select numVuelo, origen, destino, fechaLlegadaReal,"
                    + " fechaLlegadaReal-fechaLlegadaTeorica as retraso,terminal, puertaembarque, cancelado "
                    + "from vuelo v "
                    + "where EXTRACT(YEAR FROM cast(v.fechaLlegadaReal as date))= EXTRACT(YEAR FROM cast(NOW()as date) ) and "
                    + "EXTRACT(MONTH FROM cast(v.fechaLlegadaReal as date))=EXTRACT(MONTH FROM cast(NOW()as date) ) "
                    + "and EXTRACT(DAY FROM cast(v.fechaLlegadaReal as date))=EXTRACT(DAY FROM cast(NOW()as date) ) "
                    + "and v.destino=? "
                    + "ORDER BY fechaLlegadaReal desc");
            stmVuelo.setString(1, "Folgoso do Courel");
            rsVuelo = stmVuelo.executeQuery();
            while (rsVuelo.next()) {
                vueloActual = new Vuelo(rsVuelo.getString("numvuelo"), rsVuelo.getString("origen"), rsVuelo.getString("destino"),
                        null, rsVuelo.getTimestamp("fechallegadareal"), rsVuelo.getInt("puertaembarque"), rsVuelo.getBoolean("cancelado"),
                        rsVuelo.getInt("terminal"), rsVuelo.getString("retraso"));

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

    public Boolean pasarControlBillete(String dni, String vuelo) {
        Connection con;
        PreparedStatement stmUsuario = null;
        Boolean correcto = false;

        con = super.getConexion();

        try {

            stmUsuario = con.prepareStatement("update comprarbillete set pasarcontrol=true "
                    + "where usuario=? and vuelo=?");

            stmUsuario.setString(1, dni);
            stmUsuario.setString(2, vuelo);

            if (stmUsuario.executeUpdate() > 0) {
                correcto = true;
            }

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

    public Boolean salirControlBillete(String dni, String vuelo) {
        Connection con;
        PreparedStatement stmUsuario = null;
        Boolean correcto = false;

        con = super.getConexion();

        try {

            stmUsuario = con.prepareStatement("update comprarbillete set pasarcontrol=false "
                    + "where usuario=? and vuelo=?");

            stmUsuario.setString(1, dni);
            stmUsuario.setString(2, vuelo);

            if (stmUsuario.executeUpdate() > 0) {
                correcto = true;
            }

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

    public Aerolinea obtenerDatosAerolinea(String num) {

        Connection con;
        PreparedStatement stmVuelo = null;
        ResultSet rsVuelo;
        Aerolinea result = null;

        con = super.getConexion();

        try {
            stmVuelo = con.prepareStatement("select pesobasemaleta,preciobasemaleta "
                    + "from vuelo v, avion av, aerolinea a "
                    + "where v.avion=av.codigo and av.aerolinea=a.nombre and numvuelo=?");
            stmVuelo.setString(1, num);
            rsVuelo = stmVuelo.executeQuery();
            if (rsVuelo.next()) {
                result = new Aerolinea(null, rsVuelo.getFloat("preciobasemaleta"), rsVuelo.getFloat("pesobasemaleta"));
            }
        } catch (SQLException e) {
            getFachadaAplicacion().mostrarError(e.getMessage());
        } finally {
            try {
                stmVuelo.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
        return result;
    }

    public Integer numeroMaletasDisponibles(String dni, String vuelo) {

        Connection con;
        PreparedStatement stmVuelo = null;
        ResultSet rsVuelo;
        Integer result = 0;

        con = super.getConexion();

        try {
            stmVuelo = con.prepareStatement("select nummaletasreserva-numfact as malDisp "
                    + "from (select nummaletasreserva from comprarbillete \n"
                    + "where vuelo=? and usuario=?) r, "
                    + "(select count(*) as numfact from facturarmaleta where vuelo=? and usuario=?) s");
            stmVuelo.setString(1, vuelo);
            stmVuelo.setString(2, dni);
            stmVuelo.setString(3, vuelo);
            stmVuelo.setString(4, dni);
            rsVuelo = stmVuelo.executeQuery();
            if (rsVuelo.next()) {
                result = rsVuelo.getInt("malDisp");
            }
        } catch (SQLException e) {
            getFachadaAplicacion().mostrarError(e.getMessage());
        } finally {
            try {
                stmVuelo.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
        return result;

    }

    public Boolean facturarMaleta(String dni, String vuelo, Float peso) {
        Connection con;
        PreparedStatement stmVuelo = null;
        Boolean correcto = true;

        con = super.getConexion();

        try {

            stmVuelo = con.prepareStatement("insert into facturarmaleta (usuario,vuelo,peso)"
                    + " values (?,?,?)");

            stmVuelo.setString(1, dni);
            stmVuelo.setString(2, vuelo);
            stmVuelo.setFloat(3, peso);

            stmVuelo.executeUpdate();

        } catch (SQLException e) {
            if (!e.getMessage().contains("(usuario, vuelo)")) {

                System.out.println(e.getMessage());
                this.getFachadaAplicacion().mostrarError(e.getMessage());

            }
            correcto = false;
        }
        return correcto;
    }

    public List<String> obtenerAnhosViajados(String dni) {
        List<String> anhos = new ArrayList<>();
        Connection con;
        PreparedStatement stmVuelo = null;
        ResultSet rsVuelo;

        con = this.getConexion();

        try {
            String consulta = "select distinct date_part('year',fechasalidateorica) as anhos\n"
                    + "from vuelo\n"
                    + "where numvuelo in (select vuelo\n"
                    + "					from comprarbillete\n"
                    + "					where usuario = ?)\n"
                    + "order by anhos desc";

            stmVuelo = con.prepareStatement(consulta);
            stmVuelo.setString(1, dni);

            rsVuelo = stmVuelo.executeQuery();
            while (rsVuelo.next()) {
                Integer ano = rsVuelo.getInt("anhos");
                anhos.add(ano.toString());
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
        return anhos;
    }

}
