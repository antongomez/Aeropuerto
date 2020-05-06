package baseDatos;

import aeropuerto.FachadaAplicacion;
import aeropuerto.elementos.Coche;
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
            String consulta = "select *\n"
                    + "from cocheAlquiler \n"
                    + "where matricula not in ((select cochealquiler as matricula\n"
                    + "						from reservar\n"
                    + "						where fechainicioreserva <= ? \n"
                    + "						  and fechafinreserva >= ?) \n"
                    + "						UNION\n"
                    + "						(select matricula\n"
                    + "						from alquilar\n"
                    + "						where fechaalquiler <= ? \n"
                    + "						  and fechadevolucion is null))\n"
                    + "  and retirado = false \n";
            if (numPlazas != null) {
                consulta += "  and nplazas = ? \n";
            }
            consulta += "order by nplazas desc, precioPorDia asc, caballos desc, nPuertas asc";
            stmCoches = con.prepareStatement(consulta);

            stmCoches.setTimestamp(1, retorno.toTimestamp());
            stmCoches.setTimestamp(2, llegada.toTimestamp());
            stmCoches.setTimestamp(3, retorno.toTimestamp());

            if (numPlazas != null) {
                stmCoches.setInt(4, numPlazas);
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
