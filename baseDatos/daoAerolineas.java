package baseDatos;

import aeropuerto.FachadaAplicacion;
import aeropuerto.elementos.Aerolinea;
import aeropuerto.util.EstadisticasAerolinea;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class daoAerolineas extends AbstractDAO {

    public daoAerolineas(Connection conexion, FachadaAplicacion fa) {
        super.setConexion(conexion);
        super.setFachadaAplicacion(fa);
    }

    public EstadisticasAerolinea obtenerEstAerolineas(String aer) {

        EstadisticasAerolinea resultado = null;
        Connection con;
        PreparedStatement stmEst1 = null;
        PreparedStatement stmEst2 = null;
        PreparedStatement stmEst3 = null;
        PreparedStatement stmEst4 = null;
        PreparedStatement stmEst5 = null;
        ResultSet rsAviones;
        ResultSet rsRetrasos;
        ResultSet rsOcupacion;
        ResultSet rsPais;

        con = super.getConexion();

        try {
            //Estadisticas sobre las aerolineas y los aviones
            stmEst1 = con.prepareStatement("SELECT (CASE count(*) when 0 then 0 else sum(anhofabricacion)/count(*) end) as anhomedio, \n"
                    + "(CASE count(*) when 0 then 0 else sum(capacidadpremium+capacidadnormal)/count(*) end) as capmedia, \n"
                    + "paissede, preciobasemaleta,pesobasemaleta \n"
                    + "FROM aerolinea a, avion av, modeloavion m \n"
                    + "WHERE a.nombre=av.aerolinea and m.nombre=av.modeloavion and aerolinea=? \n"
                    + "GROUP BY paissede, preciobasemaleta, pesobasemaleta");
            stmEst1.setString(1, aer);
            rsAviones = stmEst1.executeQuery();

            //Estadisticas sobre retrasos
            stmEst2 = con.prepareStatement("SELECT (CASE nVuelos when 0 then 0 else (cast (nRetrasos as float)/nVuelos)*100 end) as porcRetrasos, \n"
                    + "(CASE nVuelos when 0 then '00:00:00' else tiempoRetraso/nVuelos end) as tiempoMedioRetraso \n"
                    + "FROM (SELECT count(*) as nRetrasos, (CASE count(*) when 0 then '00:00:00' else sum(fechasalidareal-fechasalidateorica) end) as tiempoRetraso \n"
                    + "      FROM aerolinea a, avion av, vuelo v \n"
                    + "      WHERE av.aerolinea=a.nombre and v.avion=av.codigo and aerolinea=? \n"
                    + "      and v.fechallegadareal<NOW() and v.cancelado=false and fechasalidateorica!=fechasalidareal)as ret, \n"
                    + "     (SELECT count(*) as nVuelos from aerolinea a, avion av, vuelo v \n"
                    + "      WHERE av.aerolinea=a.nombre and v.avion=av.codigo and aerolinea=? \n"
                    + "      and v.fechallegadareal<NOW() and v.cancelado=false) as v \n");
            stmEst2.setString(1, aer);
            stmEst2.setString(2, aer);
            rsRetrasos = stmEst2.executeQuery();

            //Estadisticas ocupacion
            stmEst3 = con.prepareStatement("SELECT (CASE capNormal when 0 then 0 else (cast (ocNormal as float)/capNormal)*100 end) as porcOcNormal, \n"
                    + "(CASE capPremium when 0 then 0 else (cast(ocPremium as float)/capPremium)*100 end) as porcOcPremium \n"
                    + "FROM (SELECT sum(capacidadNormal) as capNormal, sum(capacidadPremium) as capPremium \n"
                    + "      FROM avion av, aerolinea a, vuelo v, modeloavion m \n"
                    + "      WHERE av.codigo=v.avion and av.aerolinea=a.nombre and m.nombre=av.modeloavion and aerolinea=? \n"
                    + "      and v.fechallegadareal<NOW() and v.cancelado=false) as total, \n"
                    + "     (SELECT count(*) as ocNormal \n"
                    + "      FROM avion av, aerolinea a, vuelo v, comprarbillete c \n"
                    + "      WHERE av.aerolinea=a.nombre and av.codigo=v.avion and v.numvuelo=c.vuelo and aerolinea=? \n"
                    + "      and tipoasiento='normal' and v.fechallegadareal<NOW() and v.cancelado=false) as ocNormal, \n"
                    + "     (SELECT count(*) as ocPremium \n"
                    + "      FROM avion av, aerolinea a, vuelo v, comprarbillete c \n"
                    + "      WHERE av.aerolinea=a.nombre and av.codigo=v.avion and v.numvuelo=c.vuelo and aerolinea=? \n"
                    + "      and tipoasiento='premium' and v.fechallegadareal<NOW() and v.cancelado=false) as ocPremium");
            stmEst3.setString(1, aer);
            stmEst3.setString(2, aer);
            stmEst3.setString(3, aer);
            rsOcupacion = stmEst3.executeQuery();

            //Estadisticas pais predominante
            stmEst4 = con.prepareStatement("SELECT paisprocedencia \n"
                    + "FROM aerolinea a, avion av, vuelo v, comprarbillete c, usuario u \n"
                    + "WHERE a.nombre=av.aerolinea and av.codigo=v.avion and v.numvuelo=c.vuelo and u.dni=c.usuario \n"
                    + "and fechallegadareal<NOW() and cancelado=false and aerolinea=? \n"
                    + "GROUP BY paisprocedencia \n"
                    + "HAVING count(*)>=all(SELECT count(*) \n"
                    + "                     FROM aerolinea a, avion av, vuelo v, comprarbillete c, usuario u \n"
                    + "                     WHERE a.nombre=av.aerolinea and av.codigo=v.avion and v.numvuelo=c.vuelo \n"
                    + "                     and u.dni=c.usuario and fechallegadareal<NOW() and cancelado=false \n"
                    + "                     and aerolinea=? group by paisprocedencia)");
            stmEst4.setString(1, aer);
            stmEst4.setString(2, aer);
            rsPais = stmEst4.executeQuery();

            if (rsAviones.next() && rsRetrasos.next() && rsOcupacion.next()) {
                resultado = new EstadisticasAerolinea(new Aerolinea(aer, rsAviones.getString("paissede"), rsAviones.getFloat("preciobasemaleta"), rsAviones.getFloat("pesobasemaleta")),
                        rsRetrasos.getFloat("porcRetrasos"), rsRetrasos.getString("tiempoMedioRetraso"), rsOcupacion.getFloat("porcOcNormal"),
                        rsOcupacion.getFloat("porcOcPremium"), rsAviones.getFloat("anhomedio"), rsAviones.getFloat("capMedia"));

                while (rsPais.next()) {
                    resultado.addNacionalidad(rsPais.getString("paisprocedencia"));
                }
            }
            

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            this.getFachadaAplicacion().mostrarError(e.getMessage());
        } finally {
            try {
                stmEst1.close();
                stmEst2.close();
                stmEst3.close();
                stmEst4.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }

        return resultado;

    }

    public List<String> obtenerAerolineasConVuelos() {
        Connection con;
        PreparedStatement stmRes = null;
        ResultSet rsRes;
        con = super.getConexion();
        ArrayList<String> aerolineas = new ArrayList<>();

        try {
            stmRes = con.prepareStatement("SELECT distinct nombre \n"
                    + "FROM aerolinea a, vuelo v, avion av \n"
                    + "WHERE a.nombre=av.aerolinea and v.avion=av.codigo \n"
                    + "and v.fechallegadareal<NOW() and cancelado=false \n");
            rsRes = stmRes.executeQuery();
            while (rsRes.next()) {
                aerolineas.add(rsRes.getString("nombre"));
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
        return aerolineas;
    }

}
