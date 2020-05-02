package baseDatos;

import aeropuerto.FachadaAplicacion;
import aeropuerto.elementos.Aerolinea;
import aeropuerto.elementos.Usuario;
import aeropuerto.elementos.Vuelo;
import aeropuerto.util.EstadisticasAerolinea;
import aeropuerto.util.Time;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
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
                for(int i=1; i<=rsVuelo.getInt("plazasNormal"); i++){
                    v.getAsientosNormalesDisponibles().put(i, true);
                }
                for(int i=rsVuelo.getInt("plazasNormal")+1; i<=rsVuelo.getInt("plazasNormal")+rsVuelo.getInt("plazasPremium"); i++){
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
    
    public void obtenerAsientos(Vuelo vuelo){
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
    public List<Vuelo> verSalidas(){
        List<Vuelo> resultado = new ArrayList<>();
        Connection con;
        Vuelo vueloActual;
        PreparedStatement stmVuelo = null;
        ResultSet rsVuelo;

        con = super.getConexion();

        try {
            stmVuelo = con.prepareStatement("select numVuelo, origen, destino, fechaSalidaReal, "
                    + "fechaSalidaReal-fechaSalidaTeorica as retraso,terminal, puertaembarque, cancelado " +
                    "from vuelo v " +
                    "where fechaSalidaReal>NOW() and v.origen=? "
                    + "ORDER BY fechaSalidaReal asc");
            stmVuelo.setString(1, "Folgoso do Courel");
            rsVuelo = stmVuelo.executeQuery();
            while (rsVuelo.next()) {
                vueloActual = new Vuelo(rsVuelo.getString("numvuelo"), rsVuelo.getString("origen"), rsVuelo.getString("destino"),
                        rsVuelo.getTimestamp("fechaSalidaReal"), null,rsVuelo.getInt("puertaembarque"), rsVuelo.getBoolean("cancelado"),
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
    public List<Vuelo> verLlegadas(){
        List<Vuelo> resultado = new ArrayList<>();
        Connection con;
        Vuelo vueloActual;
        PreparedStatement stmVuelo = null;
        ResultSet rsVuelo;

        con = super.getConexion();

        try {
            stmVuelo = con.prepareStatement("select numVuelo, origen, destino, fechaLlegadaReal,"
                    + " fechaLlegadaReal-fechaLlegadaTeorica as retraso,terminal, puertaembarque, cancelado " +
                      "from vuelo v " +
                      "where EXTRACT(YEAR FROM cast(v.fechaLlegadaReal as date))= EXTRACT(YEAR FROM cast(NOW()as date) ) and " +
                      "EXTRACT(MONTH FROM cast(v.fechaLlegadaReal as date))=EXTRACT(MONTH FROM cast(NOW()as date) ) " +
                      "and EXTRACT(DAY FROM cast(v.fechaLlegadaReal as date))=EXTRACT(DAY FROM cast(NOW()as date) ) " +
                      "and v.destino=? "
                    + "ORDER BY fechaLlegadaReal desc");
            stmVuelo.setString(1, "Folgoso do Courel");
            rsVuelo = stmVuelo.executeQuery();
            while (rsVuelo.next()) {
                vueloActual = new Vuelo(rsVuelo.getString("numvuelo"), rsVuelo.getString("origen"), rsVuelo.getString("destino"),
                        null, rsVuelo.getTimestamp("fechallegadareal"),rsVuelo.getInt("puertaembarque"), rsVuelo.getBoolean("cancelado"),
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
    
    public EstadisticasAerolinea obtenerEstAerolineas(String aer){
        
        EstadisticasAerolinea resultado=null;
        Connection con;
        PreparedStatement stmEst = null;
        ResultSet rsAviones;
        ResultSet rsRetrasos;
        ResultSet rsOcupacion;
        ResultSet rsPais;

        con = super.getConexion();

        try {
            //Estadisticas sobre las aerolineas y los aviones
            stmEst = con.prepareStatement("select sum(anhofabricacion)/count(*) as anhomedio, sum(capacidadpremium+capacidadnormal)/count(*) as capmedia, " +
                    "aerolinea, preciobasemaleta,pesobasemaleta from aerolinea a, avion av, modeloavion m " +
                    "where a.nombre=av.aerolinea and m.nombre=av.modeloavion and aerolinea=? " +
                    "group by aerolinea, preciobasemaleta, pesobasemaleta");
            stmEst.setString(1, aer);
            rsAviones = stmEst.executeQuery();
            
            //Estadisticas sobre retrasos
            stmEst=con.prepareStatement("select (cast (nRetrasos as float)/nVuelos)*100 as porcRetrasos, tiempoRetraso/nVuelos as tiempoMedioRetraso " +
            "from (select count(*) as nRetrasos, sum(fechasalidareal-fechasalidateorica) as tiempoRetraso " +
            "from aerolinea a, avion av, vuelo v " +
            "where av.aerolinea=a.nombre and v.avion=av.codigo and aerolinea=? " +
            "and v.fechallegadareal<NOW() and v.cancelado=false and fechasalidateorica!=fechasalidareal)as ret, " +
            "(select count(*) as nVuelos from aerolinea a, avion av, vuelo v " +
            "where av.aerolinea=a.nombre and v.avion=av.codigo and aerolinea=? " +
            "and v.fechallegadareal<NOW() and v.cancelado=false) as v");
            stmEst.setString(1, aer);
            stmEst.setString(2, aer);
            rsRetrasos=stmEst.executeQuery();
            
            //Estadisticas ocupacion
            stmEst=con.prepareStatement("select (CASE capNormal when 0 then 0 else (cast (ocNormal as float)/capNormal)*100 end) as porcOcNormal, " +
                "(CASE capPremium when 0 then 0 else (cast(ocPremium as float)/capPremium)*100 end) as porcOcPremium " +
                "from (select sum(capacidadNormal) as capNormal,sum(capacidadPremium) as capPremium " +
                "from avion av, aerolinea a, vuelo v, modeloavion m " +
                "where av.codigo=v.avion and av.aerolinea=a.nombre and m.nombre=av.modeloavion and aerolinea=? " +
                "and v.fechallegadareal<NOW() and v.cancelado=false) as total, " +
                "(select count(*) as ocNormal from avion av, aerolinea a, vuelo v, comprarbillete c " +
                "where av.aerolinea=a.nombre and av.codigo=v.avion and v.numvuelo=c.vuelo and aerolinea=? " +
                "and tipoasiento='normal' and v.fechallegadareal<NOW() and v.cancelado=false)as ocNormal, " +
                "(select count(*) as ocPremium from avion av, aerolinea a, vuelo v, comprarbillete c " +
                "where av.aerolinea=a.nombre and av.codigo=v.avion and v.numvuelo=c.vuelo and aerolinea=? " +
                "and tipoasiento='premium' and v.fechallegadareal<NOW() and v.cancelado=false) as ocPremium");
            stmEst.setString(1, aer);
            stmEst.setString(2, aer);
            stmEst.setString(3, aer);
            rsOcupacion=stmEst.executeQuery();
            
            //Estadisticas pais predominante
            stmEst=con.prepareStatement("select paisprocedencia from aerolinea a, avion av, vuelo v, comprarbillete c, usuario u " +
                "where a.nombre=av.aerolinea and av.codigo=v.avion and v.numvuelo=c.vuelo " +
                "and u.dni=c.usuario and fechallegadareal<NOW() and cancelado=false and aerolinea=? " +
                "group by paisprocedencia having count(*)>=all(select count(*) " +
                "from aerolinea a, avion av, vuelo v, comprarbillete c, usuario u " +
                "where a.nombre=av.aerolinea and av.codigo=v.avion and v.numvuelo=c.vuelo " +
                "and u.dni=c.usuario and fechallegadareal<NOW() and cancelado=false and aerolinea=? group by paisprocedencia)");
            stmEst.setString(1, aer);
            stmEst.setString(2, aer);
            rsPais=stmEst.executeQuery();
            
            
            if (rsAviones.next() && rsRetrasos.next() && rsOcupacion.next()) {
                resultado=new EstadisticasAerolinea(new Aerolinea(aer, rsAviones.getFloat("preciobasemaleta"),rsAviones.getFloat("pesobasemaleta")),
                rsRetrasos.getFloat("porcRetrasos"),rsRetrasos.getFloat("tiempoMedioRetraso"),rsOcupacion.getFloat("porcOcNormal"),
                rsOcupacion.getFloat("porcOcPremium"), rsAviones.getFloat("anhomedio"),rsAviones.getFloat("capMedia"));
            
                while(rsPais.next()){
                resultado.addNacionalidad(rsPais.getString("paisprocedencia"));
            }
            }
            

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            this.getFachadaAplicacion().mostrarError(e.getMessage());
        } finally {
            try {
                stmEst.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }

        return resultado;
        
    }
    }


