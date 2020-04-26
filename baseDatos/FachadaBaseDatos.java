package baseDatos;

import aeropuerto.FachadaAplicacion;
import aeropuerto.elementos.Usuario;
import aeropuerto.elementos.Vuelo;
import aeropuerto.util.EstadisticasUsuario;
import aeropuerto.util.Reserva;
import aeropuerto.util.Time;
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
    private daoReservas daoReservas;

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
            daoReservas = new daoReservas(conexion,fa);

        } catch (FileNotFoundException f) {
            fa.mostrarError(f.getMessage());
        } catch (IOException i) {
            fa.mostrarError(i.getMessage());
        } catch (java.sql.SQLException e) {
            fa.mostrarError(e.getMessage());
        }

    }

    public Boolean insertarUsuario(Usuario u, String clave) {
        return daoUsuarios.insertarUsuario(u,clave);
    }
    
    public Boolean modificarUsuario(Usuario us){
        return daoUsuarios.modificarUsuario(us);
    }
    public Boolean modificarContrasenha(String idUsuario, String clave){
        return daoUsuarios.modificarContrasenha(idUsuario, clave);
    }
    
    
    public boolean eliminarUsuario(String dni){
       return daoUsuarios.eliminarUsuario(dni);
    }

    public Usuario comprobarCredenciales(String id, String cont) {
        return daoUsuarios.comprobarCredenciales(id, cont);
    }

    public Boolean insertarVuelo(Vuelo v) {
        return daoVuelos.insertarVuelo(v);
    }

    public List<Vuelo> buscarVuelos(String numVuelo, String origen, String destino, Time fechaSalida, Time fechaLlegada) {
        return daoVuelos.buscarVuelos(numVuelo, origen, destino, fechaSalida, fechaLlegada);
    }
    
    public List<Vuelo> obtenerVuelosUsuario(String dniUs){
        return daoVuelos.obtenerVuelosUsuario(dniUs);
    }
    public EstadisticasUsuario obtenerEstadisticasUsuario(String dniUs, String tipo, Integer num){
        return daoUsuarios.obtenerEstadisticasUsuario(dniUs, tipo, num);
    }
    public List<Reserva> obtenerReservasUsuario(String dniUs){
        return daoReservas.obtenerReservasUsuario(dniUs);
    }
    
    public Boolean cancelarReservaParking(Reserva res,String dniUs){
        return daoReservas.cancelarReservaParking(res, dniUs);
    }
    public Boolean cancelarReservaCoche(Reserva res, String dniUs){
        return daoReservas.cancelarReservaCoche(res, dniUs);
    }
}
