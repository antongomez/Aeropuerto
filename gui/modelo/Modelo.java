package gui.modelo;

import aeropuerto.FachadaAplicacion;
import aeropuerto.Usuario;

public class Modelo {

    private static Modelo mod = null; //Instancia do modelo
    private FachadaAplicacion fa; //Desde aqui llamaremos a la fachada

    private Modelo(FachadaAplicacion fa) {
        this.fa = fa;
    }

    //Desta forma, creamos unha vez a clase modelo e podemola ir obtendo a traves das clases
    public static Modelo newModelo(FachadaAplicacion fa) {
        if (mod != null) {
            mod = new Modelo(fa);
        }
        return mod;
    }

    public static Modelo getInstanceModelo() {
        return mod;
    }

    public void registrarUsuario(Usuario us) {
        fa.registrarUsuario(us);
    }
    
}
