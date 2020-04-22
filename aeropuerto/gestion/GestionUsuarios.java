package aeropuerto.gestion;

import aeropuerto.elementos.Usuario;
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

    public Boolean registrarUsuario(Usuario us) {
        return fbd.insertarUsuario(us);
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
    
}
