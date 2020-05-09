package baseDatos;

import aeropuerto.FachadaAplicacion;
import aeropuerto.elementos.Coche;
import aeropuerto.util.reservas.Reserva;
import aeropuerto.util.Time;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class daoCoches extends AbstractDAO {

    public daoCoches(Connection conexion, FachadaAplicacion fa) {
        super.setConexion(conexion);
        super.setFachadaAplicacion(fa);
    }

    public List<Coche> buscarCoches(Time llegada, Time retorno, Integer numPlazas) {
        PreparedStatement stmCoches = null;
        ResultSet rsCoches;
        ArrayList<Coche> coches = new ArrayList<>();
        Connection con = super.getConexion();

        try {
            String consulta = "SELECT * \n"
                    + "FROM cocheAlquiler \n"
                    + "WHERE matricula not in ((SELECT cochealquiler as matricula \n"
                    + "			       FROM reservar \n"
                    + "			       WHERE cast(fechainicioreserva as date) <= cast(? as date) \n"
                    + "                         and cast(fechafinreserva as date) >= cast(? as date)) \n"
                    + "                         UNION \n"
                    + "                        (SELECT matricula \n"
                    + "                         FROM alquilar \n"
                    + "                         WHERE cast(fechaalquiler as date) <= cast(? as date) \n"
                    + "                         and (((cast(? as date)-cast (fechateoricadevolucion as date))<=5 \n" +
"                                               and cast(? as date)>cast(fechateoricadevolucion as date)) \n" +
"                                               or cast(? as date)<=cast(fechateoricadevolucion as date)))) \n"
                    + "and retirado = false \n";
            if (numPlazas != null) {
                consulta += "  and nplazas = ? \n";
            }
            consulta += "ORDER BY nplazas desc, precioPorDia asc, caballos desc, nPuertas asc";
            stmCoches = con.prepareStatement(consulta);

            stmCoches.setTimestamp(1, retorno.toTimestamp());
            stmCoches.setTimestamp(2, llegada.toTimestamp());
            stmCoches.setTimestamp(3, retorno.toTimestamp());
            stmCoches.setTimestamp(4, llegada.toTimestamp());
            stmCoches.setTimestamp(5, llegada.toTimestamp());
            stmCoches.setTimestamp(6, llegada.toTimestamp());
            if (numPlazas != null) {
                stmCoches.setInt(7, numPlazas);
            }

            rsCoches = stmCoches.executeQuery();
            while (rsCoches.next()) {
                coches.add(new Coche(rsCoches.getString("matricula"),
                        rsCoches.getString("modelo"),
                        rsCoches.getInt("caballos"),
                        rsCoches.getInt("nPlazas"),
                        rsCoches.getInt("nPuertas"),
                        rsCoches.getFloat("precioPorDia"),
                        rsCoches.getString("tipoCombustible"),
                        rsCoches.getBoolean("retirado")));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            this.getFachadaAplicacion().mostrarError(e.getMessage());
        } finally {
            try {
                stmCoches.close();
            } catch (SQLException e) {

                System.out.println("Imposible cerrar cursores");
            }
        }

        return coches;
    }
    
    public List<Coche> buscarCoches(Time llegada, Time retorno, Integer numPlazas, String modelo, String matricula) {
        PreparedStatement stmCoches = null;
        ResultSet rsCoches;
        ArrayList<Coche> coches = new ArrayList<>();
        Connection con = super.getConexion();

        try {
            String consulta = "SELECT * \n"
                    + "FROM cocheAlquiler \n"
                    + "WHERE matricula not in ((SELECT cochealquiler as matricula \n"
                    + "			       FROM reservar \n"
                    + "			       WHERE cast(fechainicioreserva as date) <= cast(? as date) \n"
                    + "                         and cast(fechafinreserva as date) >= cast(? as date)) \n"
                    + "                         UNION \n"
                    + "                        (SELECT matricula \n"
                    + "                         FROM alquilar \n"
                    + "                         WHERE cast(fechaalquiler as date) <= cast(? as date) \n"
                    + "                         and (((cast(? as date)-cast (fechateoricadevolucion as date))<=5 \n" +
"                                               and cast(? as date)>cast(fechateoricadevolucion as date)) \n" +
"                                               or cast(? as date)<=cast(fechateoricadevolucion as date)))) \n"
                    + "and retirado = false \n"
                    + "and modelo like ? \n"
                    + "and matricula like ? \n";
            if (numPlazas != null) {
                consulta += "  and nplazas = ? \n";
            }
            
            consulta += "ORDER BY nplazas desc, precioPorDia asc, caballos desc, nPuertas asc";
            stmCoches = con.prepareStatement(consulta);

            stmCoches.setTimestamp(1, retorno.toTimestamp());
            stmCoches.setTimestamp(2, llegada.toTimestamp());
            stmCoches.setTimestamp(3, retorno.toTimestamp());
            stmCoches.setTimestamp(4, llegada.toTimestamp());
            stmCoches.setTimestamp(5, llegada.toTimestamp());
            stmCoches.setTimestamp(6, llegada.toTimestamp());
            stmCoches.setString(7,"%"+modelo+"%");
            stmCoches.setString(8, "%"+matricula+"%");

            if(numPlazas!=null){
                stmCoches.setInt(9, numPlazas);
            }

            rsCoches = stmCoches.executeQuery();
            while (rsCoches.next()) {
                coches.add(new Coche(rsCoches.getString("matricula"),
                        rsCoches.getString("modelo"),
                        rsCoches.getInt("caballos"),
                        rsCoches.getInt("nPlazas"),
                        rsCoches.getInt("nPuertas"),
                        rsCoches.getFloat("precioPorDia"),
                        rsCoches.getString("tipoCombustible"),
                        rsCoches.getBoolean("retirado")));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            this.getFachadaAplicacion().mostrarError(e.getMessage());
        } finally {
            try {
                stmCoches.close();
            } catch (SQLException e) {

                System.out.println("Imposible cerrar cursores");
            }
        }

        return coches;
    }
    
    
}
