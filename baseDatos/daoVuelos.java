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
            stmVuelo = con.prepareStatement("INSERT into vuelo(numvuelo,origen,destino, \n"
                    + "fechasalidateorica, fechasalidareal, fechallegadateorica, \n"
                    + "fechallegadareal, precioactual, puertaembarque, cancelado, \n"
                    + "terminal,avion) \n"
                    + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");
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

    public List<Vuelo> buscarVuelos(String numVuelo, String origen, String destino, Time fechaSalida) {
        List<Vuelo> resultado = new ArrayList<>();
        Vuelo vueloActual;
        Connection con;
        PreparedStatement stmVuelo = null;
        ResultSet rsVuelo;

        con = this.getConexion();

        try {
            String consulta = "SELECT numvuelo,origen,destino,fechasalidateorica, fechasalidareal, \n"
                    + "fechallegadateorica, fechallegadareal, precioactual, puertaembarque, cancelado, \n"
                    + "(CASE when fechasalidareal<=now() then true else false end) as enCurso, \n"
                    + "terminal, avion \n"
                    + "FROM vuelo \n"
                    + "WHERE numvuelo like ? \n"
                    + "and origen like ? \n"
                    + "and destino like ? \n";

            if (fechaSalida != null) {
                consulta += "  and fechallegadareal >= ? \n";
            } else {
                consulta += "  and fechallegadareal >= now() \n"; //+ '-30 min' ";
            }
            //Ordenamos os voos por data de saida ascendente
            consulta += "ORDER BY fechasalidateorica asc, fechallegadateorica desc";

            stmVuelo = con.prepareStatement(consulta);
            stmVuelo.setString(1, "%" + numVuelo + "%");
            stmVuelo.setString(2, "%" + origen + "%");
            stmVuelo.setString(3, "%" + destino + "%");

            if (fechaSalida != null) {
                stmVuelo.setTimestamp(4, fechaSalida.toTimestamp());

            }

            rsVuelo = stmVuelo.executeQuery();
            while (rsVuelo.next()) {
                vueloActual = new Vuelo(rsVuelo.getString("numvuelo"), rsVuelo.getString("origen"),
                        rsVuelo.getString("destino"), rsVuelo.getTimestamp("fechasalidateorica"),
                        rsVuelo.getTimestamp("fechasalidareal"), rsVuelo.getTimestamp("fechallegadateorica"),
                        rsVuelo.getTimestamp("fechallegadareal"), rsVuelo.getFloat("precioactual"),
                        rsVuelo.getInt("puertaembarque"), rsVuelo.getBoolean("cancelado"),
                        rsVuelo.getInt("terminal"), rsVuelo.getString("avion"), rsVuelo.getBoolean("enCurso"));

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
            stmVuelo = con.prepareStatement("SELECT v.numvuelo, v.origen, \n"
                    + "v.destino, v.fechasalidateorica, v.fechasalidareal, v.fechallegadateorica, v.fechallegadareal, \n"
                    + "c.preciobillete as preciobillete, v.cancelado as cancelado \n"
                    + "FROM usuario u, vuelo v, comprarBillete c \n"
                    + "WHERE u.dni=c.usuario and v.numVuelo=c.vuelo and u.dni=? \n"
                    + "ORDER BY v.fechasalidateorica asc");
            stmVuelo.setString(1, dniUs);
            rsVuelo = stmVuelo.executeQuery();
            while (rsVuelo.next()) {
                vueloActual = new Vuelo(rsVuelo.getString("numvuelo"), rsVuelo.getString("origen"), rsVuelo.getString("destino"),
                        rsVuelo.getTimestamp("fechasalidateorica"), rsVuelo.getTimestamp("fechasalidareal"), rsVuelo.getTimestamp("fechallegadateorica"), rsVuelo.getTimestamp("fechallegadareal"),
                        rsVuelo.getFloat("preciobillete"), null, rsVuelo.getBoolean("cancelado"),
                        null, null, null);

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
            stmVuelo = con.prepareStatement("SELECT v.numvuelo, v.origen, \n"
                    + "v.destino, v.fechasalidateorica, v.fechasalidareal, v.fechallegadateorica, v.fechallegadareal, \n"
                    + "c.preciobillete, v.cancelado \n"
                    + "FROM usuario u, vuelo v, comprarBillete c \n"
                    + "WHERE u.dni=c.usuario and v.numVuelo=c.vuelo and u.dni=? \n"
                    + "and v.fechallegadareal < now() \n"
                    + "ORDER BY v.fechasalidateorica desc ");
            stmVuelo.setString(1, dniUs);
            rsVuelo = stmVuelo.executeQuery();
            while (rsVuelo.next()) {
                vueloActual = new Vuelo(rsVuelo.getString("numvuelo"), rsVuelo.getString("origen"), rsVuelo.getString("destino"),
                        rsVuelo.getTimestamp("fechasalidateorica"), rsVuelo.getTimestamp("fechasalidareal"), rsVuelo.getTimestamp("fechallegadateorica"), rsVuelo.getTimestamp("fechallegadareal"),
                        rsVuelo.getFloat("preciobillete"), null, rsVuelo.getBoolean("cancelado"),
                        null, null, null);

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
            stmVuelo = con.prepareStatement("SELECT v.numvuelo, v.origen, \n"
                    + "v.destino, v.fechasalidateorica, v.fechasalidareal, v.fechallegadateorica, v.fechallegadareal, \n"
                    + "c.preciobillete, v.cancelado, \n"
                    + "(CASE when fechasalidareal<=now() then true else false end) as enCurso \n"
                    + "FROM usuario u, vuelo v, comprarBillete c \n"
                    + "WHERE u.dni=c.usuario and v.numVuelo=c.vuelo and u.dni=? \n"
                    + "and v.fechallegadareal >= now() \n"
                    + "ORDER BY v.fechasalidateorica asc");
            stmVuelo.setString(1, dniUs);
            rsVuelo = stmVuelo.executeQuery();
            while (rsVuelo.next()) {
                vueloActual = new Vuelo(rsVuelo.getString("numvuelo"), rsVuelo.getString("origen"), rsVuelo.getString("destino"),
                        rsVuelo.getTimestamp("fechasalidateorica"), rsVuelo.getTimestamp("fechasalidareal"), rsVuelo.getTimestamp("fechallegadateorica"), rsVuelo.getTimestamp("fechallegadareal"),
                        rsVuelo.getFloat("preciobillete"), null, rsVuelo.getBoolean("cancelado"),
                        null, null, rsVuelo.getBoolean("enCurso"));

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
            stmVuelo = con.prepareStatement("SELECT distinct vuelo.aerolinea as aerolinea, \n"
                    + "vuelo.preciobasemaleta as precioMaleta, vuelo.pesoBaseMaleta as pesoMaleta, \n"
                    + "cN-nN as plazasRestantesNormal, cP-nP as plazasRestantesPremium, cN as plazasNormal, \n"
                    + "cP as plazasPremium \n"
                    + "FROM (SELECT count(*) as nN \n"
                    + "      FROM vuelo v, comprarbillete c \n"
                    + "      WHERE v.numvuelo=c.vuelo and v.numvuelo=? and tipoasiento='normal') \n"
                    + "as nN, \n"
                    + "     (SELECT count(*) as nP \n"
                    + "      FROM vuelo v, comprarbillete c \n"
                    + "      WHERE v.numvuelo=c.vuelo and v.numvuelo=? and tipoasiento='premium') \n"
                    + "as nP, \n"
                    + "     (SELECT aerolinea, precioBaseMaleta, pesoBaseMaleta, m.capacidadnormal as cN, m.capacidadPremium as cP \n"
                    + "      FROM vuelo v, aerolinea a, avion av, modeloAvion m \n"
                    + "      WHERE avion=av.codigo and av.aerolinea=a.nombre and m.nombre=av.modeloavion \n"
                    + "      and v.numvuelo=?) \n"
                    + "as vuelo");
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
            stmAsientos = con.prepareStatement("SELECT numAsiento \n"
                    + "FROM comprarbillete \n"
                    + "WHERE vuelo = ? and tipoAsiento = 'normal' ");
            stmAsientos.setString(1, vuelo.getNumVuelo());
            rsAsientos = stmAsientos.executeQuery();
            while (rsAsientos.next()) {
                vuelo.getAsientosNormalesDisponibles().replace(rsAsientos.getInt("numAsiento"), false);
            }
            stmAsientos = con.prepareStatement("SELECT numAsiento \n"
                    + "FROM comprarbillete \n"
                    + "WHERE vuelo = ? and tipoAsiento = 'premium' ");
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
            stmBillete = con.prepareStatement("INSERT into comprarbillete(usuario,vuelo,numAsiento, \n"
                    + "tipoAsiento, numMaletasReserva, tenerAcompanhante, \n"
                    + "precioBillete) \n"
                    + "VALUES(?,?,?,?,?,?,?)");
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

    public Boolean vueloRealizado(String vuelo) {
        Connection con;
        PreparedStatement stmVuelo = null;
        ResultSet rsVuelo;

        Boolean realizado = false;

        con = super.getConexion();

        try {
            stmVuelo = con.prepareStatement("SELECT * \n"
                    + "FROM vuelo \n"
                    + "WHERE numvuelo=? and fechasalidareal<NOW()");
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
            stmBillete = con.prepareStatement("DELETE \n"
                    + "FROM comprarbillete \n"
                    + "WHERE vuelo=? and usuario=?");
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
            stmVuelo = con.prepareStatement("SELECT numVuelo, origen, destino, fechaSalidaReal, \n"
                    + "fechaSalidaReal-fechaSalidaTeorica as retraso, terminal, puertaembarque, cancelado, \n"
                    + "to_char(fechaSalidaReal-now(),'dd HH24:MI') as tiempoRestante  \n"
                    + "FROM vuelo v \n"
                    + "WHERE fechasalidareal between (now() + '-30 min') and (now() + '1 days') \n"
                    + "and v.origen = ? \n"
                    + "ORDER BY fechasalidareal asc");
            stmVuelo.setString(1, "Folgoso do Courel");
            rsVuelo = stmVuelo.executeQuery();
            while (rsVuelo.next()) {
                vueloActual = new Vuelo(rsVuelo.getString("numvuelo"), rsVuelo.getString("origen"), rsVuelo.getString("destino"),
                        rsVuelo.getTimestamp("fechaSalidaReal"), null, rsVuelo.getInt("puertaembarque"), rsVuelo.getBoolean("cancelado"),
                        rsVuelo.getInt("terminal"), rsVuelo.getString("retraso"), new Time(rsVuelo.getString("tiempoRestante")));;

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
            stmVuelo = con.prepareStatement("SELECT numVuelo, origen, destino, fechaLlegadaReal, \n"
                    + "fechaLlegadaReal-fechaLlegadaTeorica as retraso,terminal, puertaembarque, cancelado, \n"
                    + "to_char(fechaLlegadaReal-NOW(),'dd HH24:MI') as tiempoRestante \n"
                    + "FROM vuelo v \n"
                    + "WHERE fechallegadareal between (now() + '-30 min') and (now() + '1 days') \n"
                    + "and v.destino = ? \n"
                    + "ORDER BY fechaLlegadaReal desc");
            stmVuelo.setString(1, "Folgoso do Courel");
            rsVuelo = stmVuelo.executeQuery();
            while (rsVuelo.next()) {
                vueloActual = new Vuelo(rsVuelo.getString("numvuelo"), rsVuelo.getString("origen"), rsVuelo.getString("destino"),
                        null, rsVuelo.getTimestamp("fechallegadareal"), rsVuelo.getInt("puertaembarque"), rsVuelo.getBoolean("cancelado"),
                        rsVuelo.getInt("terminal"), rsVuelo.getString("retraso"), new Time(rsVuelo.getString("tiempoRestante")));

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
        PreparedStatement stmComprobacion = null;
        ResultSet rsComprobacion;

        con = super.getConexion();
        try {
            //Solo se puede pasar el control si el vuelo no salió aún o faltan más de 12 horas para su salida
            stmComprobacion = con.prepareStatement("SELECT numvuelo \n"
                    + "FROM vuelo \n"
                    + "WHERE numvuelo=? and fechasalidareal between NOW() and (NOW()+'12 hr')");
            stmComprobacion.setString(1, vuelo);
            rsComprobacion = stmComprobacion.executeQuery();
            if (rsComprobacion.next()) {

                try {

                    stmUsuario = con.prepareStatement("UPDATE comprarbillete \n"
                            + "SET pasarcontrol=true \n"
                            + "WHERE usuario=? and vuelo=?");

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
            } else {
                this.getFachadaAplicacion().mostrarError("Estos datos no corresponden con ningún billete actual");

                correcto = false;
            }
        } catch (SQLException e) {

            System.out.println(e.getMessage());
            this.getFachadaAplicacion().mostrarError(e.getMessage());

            correcto = false;
        } finally {
            try {
                stmComprobacion.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
                correcto = false;
            }
        }
        return correcto;
    }

    public Boolean salirControlBillete(String dni, String vuelo) {
        Connection con;
        PreparedStatement stmUsuario = null;
        Boolean correcto = false;
        PreparedStatement stmComprobacion = null;
        ResultSet rsComprobacion;

        con = super.getConexion();

        try {

            stmUsuario = con.prepareStatement("UPDATE comprarbillete \n"
                    + "SET pasarcontrol=false \n"
                    + "WHERE usuario=? and vuelo=?");

            stmUsuario.setString(1, dni);
            stmUsuario.setString(2, vuelo);

            if (stmUsuario.executeUpdate() > 0) {
                correcto = true;
            } else {
                this.getFachadaAplicacion().mostrarError("Estos datos no corresponden con ningún billete actual");
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
                correcto = false;
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
            stmVuelo = con.prepareStatement("SELECT a.nombre as nombre, a.pesobasemaleta \n"
                    + "as pesobasemaleta, a.preciobasemaleta as preciobasemaleta \n"
                    + "FROM vuelo v, avion av, aerolinea a \n"
                    + "WHERE v.avion=av.codigo and av.aerolinea=a.nombre and numvuelo=?");
            stmVuelo.setString(1, num);
            rsVuelo = stmVuelo.executeQuery();
            if (rsVuelo.next()) {
                result = new Aerolinea(rsVuelo.getString("nombre"), rsVuelo.getFloat("preciobasemaleta"), rsVuelo.getFloat("pesobasemaleta"));
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
            stmVuelo = con.prepareStatement("SELECT nummaletasreserva-numfact as malDisp \n"
                    + "FROM (SELECT nummaletasreserva \n"
                    + "      FROM comprarbillete \n"
                    + "      WHERE vuelo=? and usuario=?) \n"
                    + "as r, \n"
                    + "      (SELECT count(*) as numfact \n"
                    + "       FROM facturarmaleta \n"
                    + "       WHERE vuelo=? and usuario=?) \n"
                    + "as s");
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
        PreparedStatement stmComprobacion = null;
        ResultSet rsComprobacion;

        con = super.getConexion();

        try {
            //Solo se puede facturar si el vuelo no salió aún
            stmComprobacion = con.prepareStatement("SELECT numvuelo \n"
                    + "FROM vuelo \n"
                    + "WHERE numvuelo=? and fechasalidareal>NOW()");
            stmComprobacion.setString(1, vuelo);
            rsComprobacion = stmComprobacion.executeQuery();
            if (rsComprobacion.next()) {

                try {
                    stmVuelo = con.prepareStatement("INSERT into facturarmaleta(usuario,vuelo,peso) \n"
                            + "VALUES(?,?,?)");

                    stmVuelo.setString(1, dni);
                    stmVuelo.setString(2, vuelo);
                    stmVuelo.setFloat(3, peso);

                    stmVuelo.executeUpdate();

                } catch (SQLException e) {

                    if (e.getMessage().contains("(usuario, vuelo)")) {
                        this.getFachadaAplicacion().mostrarError("Estos datos no se corresponden con ningún billete actual");
                    } else {
                        System.out.println(e.getMessage());
                        this.getFachadaAplicacion().mostrarError(e.getMessage());
                    }

                    correcto = false;
                } finally {
                    try {
                        stmVuelo.close();
                    } catch (SQLException e) {
                        System.out.println("Imposible cerrar cursores");
                    }
                }
            } else {
                this.getFachadaAplicacion().mostrarError("Estos datos no se corresponden con ningún billete actual");

                correcto = false;
            }
        } catch (SQLException e) {

            System.out.println(e.getMessage());
            this.getFachadaAplicacion().mostrarError(e.getMessage());

            correcto = false;
        } finally {
            try {
                stmComprobacion.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
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
            String consulta = "SELECT distinct date_part('year',fechasalidateorica) as anhos \n"
                    + "FROM vuelo \n"
                    + "WHERE numvuelo in (SELECT vuelo \n"
                    + "                   FROM comprarbillete \n"
                    + "                   WHERE usuario = ?) \n"
                    + "ORDER BY anhos desc";

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
