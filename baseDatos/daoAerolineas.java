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
            stmEst = con.prepareStatement("select (CASE count(*) when 0 then 0 else sum(anhofabricacion)/count(*) end) as anhomedio,(CASE count(*) when 0 then 0 else sum(capacidadpremium+capacidadnormal)/count(*) end) as capmedia, "
                    + "paissede, preciobasemaleta,pesobasemaleta from aerolinea a, avion av, modeloavion m "
                    + "where a.nombre=av.aerolinea and m.nombre=av.modeloavion and aerolinea=? "
                    + "group by paissede, preciobasemaleta, pesobasemaleta");
            stmEst.setString(1, aer);
            rsAviones = stmEst.executeQuery();

            //Estadisticas sobre retrasos
            stmEst = con.prepareStatement("select (CASE nVuelos when 0 then 0 else (cast (nRetrasos as float)/nVuelos)*100 end) as porcRetrasos,(CASE nVuelos when 0 then '00:00:00' else tiempoRetraso/nVuelos end) as tiempoMedioRetraso "
                    + "from (select count(*) as nRetrasos, (CASE count(*) when 0 then '00:00:00' else sum(fechasalidareal-fechasalidateorica) end) as tiempoRetraso "
                    + "from aerolinea a, avion av, vuelo v "
                    + "where av.aerolinea=a.nombre and v.avion=av.codigo and aerolinea=? "
                    + "and v.fechallegadareal<NOW() and v.cancelado=false and fechasalidateorica!=fechasalidareal)as ret, "
                    + "(select count(*) as nVuelos from aerolinea a, avion av, vuelo v "
                    + "where av.aerolinea=a.nombre and v.avion=av.codigo and aerolinea=? "
                    + "and v.fechallegadareal<NOW() and v.cancelado=false) as v");
            stmEst.setString(1, aer);
            stmEst.setString(2, aer);
            rsRetrasos = stmEst.executeQuery();

            //Estadisticas ocupacion
            stmEst = con.prepareStatement("select (CASE capNormal when 0 then 0 else (cast (ocNormal as float)/capNormal)*100 end) as porcOcNormal, "
                    + "(CASE capPremium when 0 then 0 else (cast(ocPremium as float)/capPremium)*100 end) as porcOcPremium "
                    + "from (select sum(capacidadNormal) as capNormal,sum(capacidadPremium) as capPremium "
                    + "from avion av, aerolinea a, vuelo v, modeloavion m "
                    + "where av.codigo=v.avion and av.aerolinea=a.nombre and m.nombre=av.modeloavion and aerolinea=? "
                    + "and v.fechallegadareal<NOW() and v.cancelado=false) as total, "
                    + "(select count(*) as ocNormal from avion av, aerolinea a, vuelo v, comprarbillete c "
                    + "where av.aerolinea=a.nombre and av.codigo=v.avion and v.numvuelo=c.vuelo and aerolinea=? "
                    + "and tipoasiento='normal' and v.fechallegadareal<NOW() and v.cancelado=false)as ocNormal, "
                    + "(select count(*) as ocPremium from avion av, aerolinea a, vuelo v, comprarbillete c "
                    + "where av.aerolinea=a.nombre and av.codigo=v.avion and v.numvuelo=c.vuelo and aerolinea=? "
                    + "and tipoasiento='premium' and v.fechallegadareal<NOW() and v.cancelado=false) as ocPremium");
            stmEst.setString(1, aer);
            stmEst.setString(2, aer);
            stmEst.setString(3, aer);
            rsOcupacion = stmEst.executeQuery();

            //Estadisticas pais predominante
            stmEst = con.prepareStatement("select paisprocedencia from aerolinea a, avion av, vuelo v, comprarbillete c, usuario u "
                    + "where a.nombre=av.aerolinea and av.codigo=v.avion and v.numvuelo=c.vuelo "
                    + "and u.dni=c.usuario and fechallegadareal<NOW() and cancelado=false and aerolinea=? "
                    + "group by paisprocedencia having count(*)>=all(select count(*) "
                    + "from aerolinea a, avion av, vuelo v, comprarbillete c, usuario u "
                    + "where a.nombre=av.aerolinea and av.codigo=v.avion and v.numvuelo=c.vuelo "
                    + "and u.dni=c.usuario and fechallegadareal<NOW() and cancelado=false and aerolinea=? group by paisprocedencia)");
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
            stmRes = con.prepareStatement("select distinct nombre from aerolinea a, vuelo v, avion av "
                    + "where a.nombre=av.aerolinea and v.avion=av.codigo and v.fechallegadareal<NOW() and cancelado=false ");
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
