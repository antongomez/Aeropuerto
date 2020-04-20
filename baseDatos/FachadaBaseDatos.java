package baseDatos;

import aeropuerto.FachadaAplicacion;
import aeropuerto.elementos.Usuario;
import aeropuerto.elementos.Vuelo;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class FachadaBaseDatos {

    private FachadaAplicacion fa;
    private java.sql.Connection conexion;
    private daoUsuarios daoUsuarios;
    private daoVuelos daoVuelos;

    public FachadaBaseDatos(FachadaAplicacion fa) {

        Properties configuracion = new Properties();
        this.fa = fa;
        FileInputStream arqConfiguracion;

        try {
            arqConfiguracion = new FileInputStream("baseDatos.properties");
            configuracion.load(arqConfiguracion);
            arqConfiguracion.close();

            Properties usuario = new Properties();

            String gestor = configuracion.getProperty("gestor");

            usuario.setProperty("user", configuracion.getProperty("usuario"));
            usuario.setProperty("password", configuracion.getProperty("clave"));
            this.conexion = java.sql.DriverManager.getConnection("jdbc:" + gestor + "://"
                    + configuracion.getProperty("servidor") + ":"
                    + configuracion.getProperty("puerto") + "/"
                    + configuracion.getProperty("baseDatos"),
                    usuario);

            daoUsuarios = new daoUsuarios(conexion, fa);
            daoVuelos = new daoVuelos(conexion, fa);

        } catch (FileNotFoundException f) {
            fa.mostrarError(f.getMessage());
        } catch (IOException i) {
            fa.mostrarError(i.getMessage());
        } catch (java.sql.SQLException e) {
            fa.mostrarError(e.getMessage());
        }

    }

    public Boolean insertarUsuario(Usuario u) {
        return daoUsuarios.insertarUsuario(u);
    }

    public Usuario comprobarCredenciales(String id, String cont) {
        return daoUsuarios.comprobarCredenciales(id, cont);
    }

    public Boolean insertarVuelo(Vuelo v) {
        return daoVuelos.insertarVuelo(v);
    }

    public List<Vuelo> buscarVuelos(String numVuelo, String origen, String destino) {
        return daoVuelos.buscarVuelos(numVuelo, origen, destino);
    }
}
