/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.modelo;

import aeropuerto.FachadaAplicacion;
import aeropuerto.Usuario;

/**
 *
 * @author Esther
 */
public class Modelo {
    
    private FachadaAplicacion fa; //Desde aqui llamaremos a la fachada

    public Modelo(FachadaAplicacion fa) {
        this.fa = fa;
    }
    
    public void registrarUsuario(Usuario us){
        fa.registrarUsuario(us);  
    }
}
