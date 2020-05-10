package aeropuerto.gestion;

import aeropuerto.elementos.Tienda;
import baseDatos.FachadaBaseDatos;
import gui.FachadaGui;
import java.util.List;

public class GestionTiendas {

    private FachadaGui fgui;
    private FachadaBaseDatos fbd;

    public GestionTiendas(FachadaGui fgui, FachadaBaseDatos fbd) {
        this.fgui = fgui;
        this.fbd = fbd;
    }

    public List<String> obterTipoVentas() {
        return fbd.obterTipoVentas();
    }

    public List<Tienda> buscarTiendas(String nombre, String tipo, String terminal) {
        if (tipo.toLowerCase().equals("todos")) {
            tipo = null;
        }
        if (terminal.toLowerCase().equals("todas")) {
            terminal = null;
        }
        return fbd.buscarTiendas(nombre, tipo, terminal);
    }
}
