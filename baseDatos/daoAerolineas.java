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
        PreparedStatement stmEst = null;
        ResultSet rsAviones;
        ResultSet rsRetrasos;
        ResultSet rsOcupacion;
        ResultSet rsPais;

        con = super.getConexion();

        try {
            //Estadisticas sobre las aerolineas y los aviones
            stmEst = con.prepareStatement("select (CASE count(*) when 0 then 0 else sum(anhofabricacion)/count(*) end) as anhomedio, \n"
                    + "(CASE count(*) when 0 then 0 else sum(capacidadpremium+capacidadnormal)/count(*) end) as capmedia, \n"
                    + "paissede, preciobasemaleta,pesobasemaleta from aerolinea a, avion av, modeloavion m \n"
                    + "where a.nombre=av.aerolinea and m.nombre=av.modeloavion and aerolinea=? \n"
                    + "group by paissede, preciobasemaleta, pesobasemaleta");
            stmEst.setString(1, aer);
            rsAviones = stmEst.executeQuery();

            //Estadisticas sobre retrasos
            stmEst = con.prepareStatement("select (CASE nVuelos when 0 then 0 else (cast (nRetrasos as float)/nVuelos)*100 end) as porcRetrasos, \n"
                    + "(CASE nVuelos when 0 then '00:00:00' else tiempoRetraso/nVuelos end) as tiempoMedioRetraso \n"
                    + "from (select count(*) as nRetrasos, (CASE count(*) when 0 then '00:00:00' else sum(fechasalidareal-fechasalidateorica) end) as tiempoRetraso \n"
                    + "      from aerolinea a, avion av, vuelo v \n"
                    + "      where av.aerolinea=a.nombre and v.avion=av.codigo and aerolinea=? \n"
                    + "      and v.fechallegadareal<NOW() and v.cancelado=false and fechasalidateorica!=fechasalidareal)as ret, \n"
                    + "     (select count(*) as nVuelos from aerolinea a, avion av, vuelo v \n"
                    + "      where av.aerolinea=a.nombre and v.avion=av.codigo and aerolinea=? \n"
                    + "      and v.fechallegadareal<NOW() and v.cancelado=false) as v \n");
            stmEst.setString(1, aer);
            stmEst.setString(2, aer);
            rsRetrasos = stmEst.executeQuery();

            //Estadisticas ocupacion
            stmEst = con.prepareStatement("select (CASE capNormal when 0 then 0 else (cast (ocNormal as float)/capNormal)*100 end) as porcOcNormal, \n"
                    + "(CASE capPremium when 0 then 0 else (cast(ocPremium as float)/capPremium)*100 end) as porcOcPremium \n"
                    + "from (select sum(capacidadNormal) as capNormal, sum(capacidadPremium) as capPremium \n"
                    + "      from avion av, aerolinea a, vuelo v, modeloavion m \n"
                    + "      where av.codigo=v.avion and av.aerolinea=a.nombre and m.nombre=av.modeloavion and aerolinea=? \n"
                    + "      and v.fechallegadareal<NOW() and v.cancelado=false) as total, \n"
                    + "     (select count(*) as ocNormal \n"
                    + "      from avion av, aerolinea a, vuelo v, comprarbillete c \n"
                    + "      where av.aerolinea=a.nombre and av.codigo=v.avion and v.numvuelo=c.vuelo and aerolinea=? \n"
                    + "      and tipoasiento='normal' and v.fechallegadareal<NOW() and v.cancelado=false) as ocNormal, \n"
                    + "     (select count(*) as ocPremium \n"
                    + "      from avion av, aerolinea a, vuelo v, comprarbillete c \n"
                    + "      where av.aerolinea=a.nombre and av.codigo=v.avion and v.numvuelo=c.vuelo and aerolinea=? \n"
                    + "      and tipoasiento='premium' and v.fechallegadareal<NOW() and v.cancelado=false) as ocPremium");
            stmEst.setString(1, aer);
            stmEst.setString(2, aer);
            stmEst.setString(3, aer);
            rsOcupacion = stmEst.executeQuery();

            //Estadisticas pais predominante
            stmEst = con.prepareStatement("select paisprocedencia \n"
                    + "from aerolinea a, avion av, vuelo v, comprarbillete c, usuario u \n"
                    + "where a.nombre=av.aerolinea and av.codigo=v.avion and v.numvuelo=c.vuelo and u.dni=c.usuario \n"
                    + "and fechallegadareal<NOW() and cancelado=false and aerolinea=? \n"
                    + "group by paisprocedencia \n"
                    + "having count(*)>=all(select count(*) \n"
                    + "                     from aerolinea a, avion av, vuelo v, comprarbillete c, usuario u \n"
                    + "                     where a.nombre=av.aerolinea and av.codigo=v.avion and v.numvuelo=c.vuelo \n"
                    + "                     and u.dni=c.usuario and fechallegadareal<NOW() and cancelado=false \n"
                    + "                     and aerolinea=? group by paisprocedencia)");
            stmEst.setString(1, aer);
            stmEst.setString(2, aer);
            rsPais = stmEst.executeQuery();

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
                stmEst.close();
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
            stmRes = con.prepareStatement("select distinct nombre \n"
                    + "from aerolinea a, vuelo v, avion av \n"
                    + "where a.nombre=av.aerolinea and v.avion=av.codigo \n"
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
