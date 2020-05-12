package baseDatos;

import aeropuerto.FachadaAplicacion;
import aeropuerto.elementos.Tienda;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class daoTiendas extends AbstractDAO {

    public daoTiendas(Connection conexion, FachadaAplicacion fa) {
        super.setConexion(conexion);
        super.setFachadaAplicacion(fa);
    }

    public List<String> obterTipoVentas() {
        Connection con;
        PreparedStatement stmRes = null;
        ResultSet rsRes;
        con = super.getConexion();
        ArrayList<String> terminais = new ArrayList<>();

        try {
            stmRes = con.prepareStatement("SELECT distinct tipoVentas \n"
                    + "FROM tienda");
            rsRes = stmRes.executeQuery();
            while (rsRes.next()) {
                terminais.add(rsRes.getString("tipoVentas"));
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
        return terminais;
    }

    public List<Tienda> buscarTiendas(String nombre, String tipo, String terminal) {
        List<Tienda> resultado = new ArrayList<>();
        Tienda tiendaActual;
        Connection con;
        PreparedStatement stmTiendas = null;
        ResultSet rsTiendas;

        con = this.getConexion();

        try {
            String consulta = "SELECT nombre, tipoventas, terminal \n"
                    + "FROM tienda \n"
                    + "WHERE nombre like ? \n";

            if (tipo != null) {
                consulta += "  and tipoventas = ? \n";
            }
            if (terminal != null) {
                consulta += "  and terminal = ? \n";
            }
            //Ordenamos os voos por data de saida ascendente
            consulta += "ORDER BY terminal asc, tipoventas asc, nombre asc ";

            stmTiendas = con.prepareStatement(consulta);
            stmTiendas.setString(1, "%" + nombre + "%");
            if (tipo != null) {
                stmTiendas.setString(2, tipo);
                if (terminal != null) {
                    stmTiendas.setInt(3, Integer.parseInt(terminal));
                }
            } else if (terminal != null) {
                stmTiendas.setInt(2, Integer.parseInt(terminal));
            }

            rsTiendas = stmTiendas.executeQuery();
            while (rsTiendas.next()) {
                tiendaActual = new Tienda(rsTiendas.getString("nombre"),
                        rsTiendas.getString("tipoventas"),
                        rsTiendas.getInt("terminal"));

                resultado.add(tiendaActual);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            this.getFachadaAplicacion().mostrarError(e.getMessage());
        } finally {
            try {
                stmTiendas.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
        return resultado;
    }
}
