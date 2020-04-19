/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baseDatos;

import aeropuerto.FachadaAplicacion;
import aeropuerto.Usuario;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author Esther
 */
public class FachadaBaseDatos {
    private FachadaAplicacion fa;
    private java.sql.Connection conexion;
    private daoUsuarios daoUsuarios; 


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


        } catch (FileNotFoundException f) {
            fa.mostrarError(f.getMessage());
        } catch (IOException i) {
            fa.mostrarError(i.getMessage());
        } catch (java.sql.SQLException e) {
            fa.mostrarError(e.getMessage());
        }

    }
    
    public Boolean insertarUsuario(Usuario u){
        return daoUsuarios.insertarUsuario(u);
    }
    public Usuario comprobarCredenciales(String id, String cont){
       return daoUsuarios.comprobarCredenciales(id, cont);
    }
}
