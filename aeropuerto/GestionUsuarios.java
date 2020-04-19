/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aeropuerto;

import baseDatos.FachadaBaseDatos;
import gui.FachadaGui;

/**
 *
 * @author Esther
 */
public class GestionUsuarios {
    FachadaGui fgui;
    FachadaBaseDatos fbd;

    public GestionUsuarios(FachadaGui fgui, FachadaBaseDatos fbd) {
        this.fgui = fgui;
        this.fbd = fbd;
    }
    public Boolean registrarUsuario(Usuario us){
        
        return fbd.insertarUsuario(us);
        
    }
    public Usuario credencialesCorrectos(String id, String cont){
        return fbd.comprobarCredenciales(id,cont);

    }
    }
      

