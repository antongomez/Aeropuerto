package aeropuerto.gestion;

import aeropuerto.elementos.Usuario;
import aeropuerto.util.EstadisticasUsuario;
import baseDatos.FachadaBaseDatos;
import gui.FachadaGui;
import java.util.List;

public class GestionUsuarios {

    FachadaGui fgui;
    FachadaBaseDatos fbd;

    public GestionUsuarios(FachadaGui fgui, FachadaBaseDatos fbd) {
        this.fgui = fgui;
        this.fbd = fbd;
    }

    public Boolean registrarUsuario(Usuario us, String clave) {
        return fbd.insertarUsuario(us,clave);
    }
    public Boolean modificarContrasenha(String idUsuario, String clave){
        return fbd.modificarContrasenha(idUsuario, clave);
    }

    public Usuario credencialesCorrectos(String id, String cont) {
        return fbd.comprobarCredenciales(id, cont);
    }
    
    public Boolean modificarUsuario(Usuario us){
        return fbd.modificarUsuario(us);
    }
    
    public boolean eliminarUsuario(String dni){
       return fbd.eliminarUsuario(dni);
    }
    public EstadisticasUsuario obtenerEstadisticasUsuario(String dniUs, String tipo, Integer num){
        if(tipo.equals("estacion") || tipo.equals("mes") || tipo.equals("anho")){
        return fbd.obtenerEstadisticasUsuario(dniUs, tipo, num);
        }
        else{
            System.out.println("Error en los parámetros de entrada");
            return null;
        }
    }
    
}
