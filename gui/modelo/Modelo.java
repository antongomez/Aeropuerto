package gui.modelo;

import aeropuerto.FachadaAplicacion;
import aeropuerto.elementos.Coche;
import aeropuerto.elementos.Parking;
import aeropuerto.elementos.Usuario;
import aeropuerto.elementos.Vuelo;
import aeropuerto.util.EstadisticasAerolinea;
import aeropuerto.util.EstadisticasUsuario;
import aeropuerto.util.PorcentajeDisponibilidad;
import aeropuerto.util.Reserva;
import aeropuerto.util.Time;
import gui.FachadaGui;
import static gui.controlador.Controlador.loadWindow;
import gui.controlador.VErrorController;
import gui.controlador.VNotificacionController;
import gui.controlador.vConfirmacionControlador;
import java.util.List;
import javafx.collections.ObservableList;

public class Modelo {

    private static Modelo mod = null; //Instancia do modelo
    private final FachadaAplicacion fa; //Desde aqui llamaremos a la fachada
    private final FachadaGui fgui;

    private Modelo(FachadaAplicacion fa) {
        this.fa = fa;
        this.fgui = fa.getFgui();
    }

    //Desta forma, creamos unha vez a clase modelo e podemola ir obtendo a traves das clases
    public static Modelo newModelo(FachadaAplicacion fa) {
        if (mod == null) {
            mod = new Modelo(fa);
        }

        return mod;
    }

    public static Modelo getInstanceModelo() {
        return mod;
    }

    public Boolean registrarUsuario(Usuario us, String clave) {
        return fa.registrarUsuario(us, clave);
    }

    public Boolean modificarContrasenha(String idUsuario, String clave) {
        return fa.modificarContrasenha(idUsuario, clave);
    }

    public void mostrarError(String mensaje) {
        ((VErrorController) loadWindow(getClass().
                getResource("/gui/vista/vError.fxml"), "Error", null)).mostrarMensaje(mensaje);
    }

    public Usuario credencialesCorrectos(String id, String cont) {
        return fa.credencialesCorrectos(id, cont);
    }

    public void mostrarNotificacion(String mensaje) {
        ((VNotificacionController) loadWindow(getClass().
                getResource("/gui/vista/vNotificacion.fxml"), "Notificación", null)).mostrarMensaje(mensaje);
    }

    public void mostrarConfirmacion(String mensaje) {
        ((vConfirmacionControlador) loadWindow(getClass().getResource("/gui/vista/vConfirmacion.fxml"), "Confirmación", null)).mostrarMensaje(mensaje);
    }

    //Vuelos
    public Boolean insertarVuelo(Vuelo v) {
        return fa.insertarVuelo(v);
    }

    public List<Vuelo> buscarVuelos(String numVuelo, String origen, String destino, Time fechaSalida, Time fechaLlegada) {
        return fa.buscarVuelos(numVuelo, origen, destino, fechaSalida, fechaLlegada);
    }

    public List<Vuelo> obtenerVuelosUsuario(String dniUs) {
        return fa.obtenerVuelosUsuario(dniUs);
    }

    //Función cambiar datos
    public boolean modificarUsuario(Usuario usuario) {
        return fa.modificarUsuario(usuario);
    }

    public boolean eliminarUsuario(String dni) {
        return fa.eliminarUsuario(dni);
    }

    public EstadisticasUsuario mostrarEstadisticasUsuario(String dniUs, String tipo, Integer num) {
        return fa.obtenerEstadisticasUsuario(dniUs, tipo, num);
    }

    public List<Reserva> obtenerReservasUsuario(String dniUs) {
        return fa.obtenerReservasUsuario(dniUs);
    }

    public Boolean cancelarReserva(Reserva res, String dniUs) {
        return fa.cancelarReserva(res, dniUs);
    }

    public void obtenerDatosAvionVuelo(Vuelo v) {
        fa.obtenerDatosAvionVuelo(v);
    }

    public void obtenerAsientos(Vuelo vuelo) {
        fa.obtenerAsientos(vuelo);
    }

    public Boolean comprarBilletes(ObservableList<Usuario> usuarios) {
        return fa.comprarBilletes(usuarios);
    }

    public Usuario obtenerUsuario(String dni) {
        return fa.obtenerUsuario(dni);
    }

    public List<Integer> buscarTerminais() {
        return fa.buscarTerminais();
    }

    public PorcentajeDisponibilidad obterPrazasRestantesParkingTerminal(Integer numTerminal, Time inicio, Time fin) {
        return fa.obterPrazasRestantesParkingTerminal(numTerminal, inicio, fin);
    }

    public Parking buscarParking(Integer terminal, Time inicio, Time fin) {
        return fa.buscarParking(terminal, inicio, fin);
    }

    public List<Coche> buscarCoches(Time llegada, Time retorno, Integer numPlazas) {
        return fa.buscarCoches(llegada, retorno, numPlazas);
    }

    public Integer obterPrazaLibre(Integer numTerminal, Integer piso, Time inicio, Time fin) {
        return fa.obterPrazaLibre(numTerminal, piso, inicio, fin);
    }

    public Boolean reservarParking(Reserva reserva, String dniUsuario) {
        return fa.reservarParking(reserva, dniUsuario);
    }

    public List<Vuelo> mostrarSalidas() {
        return fa.verSalidas();
    }

    public List<Vuelo> mostrarLlegadas() {
        return fa.verLlegadas();
    }
    public List<String> obtenerAerolineasConVuelos(){
        return fa.obtenerAerolineasConVuelos();
    }
    public EstadisticasAerolinea obtenerEstadisticasAerolinea(String aer){
        return fa.obtenerEstAerolineas(aer);
    }
}
