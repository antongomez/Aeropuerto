package gui.modelo;

import aeropuerto.FachadaAplicacion;
import aeropuerto.elementos.Coche;
import aeropuerto.elementos.Parking;
import aeropuerto.elementos.PersonalLaboral;
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
import javafx.stage.Stage;

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

    public void mostrarError(String mensaje, Stage pai) {
        Stage stage = new Stage();
        stage.initOwner(pai);
        VErrorController controlador = ((VErrorController) loadWindow(getClass().
                getResource("/gui/vista/vError.fxml"), "Error", stage));
        controlador.mostrarMensaje(mensaje);
        controlador.getVenta().setResizable(false);
    }

    public void mostrarError(String mensaje) {
        VErrorController controlador = ((VErrorController) loadWindow(getClass().
                getResource("/gui/vista/vError.fxml"), "Error", null));
        controlador.mostrarMensaje(mensaje);
        controlador.getVenta().setResizable(false);
    }

    public Usuario credencialesCorrectos(String id, String cont) {
        return fa.credencialesCorrectos(id, cont);
    }

    public void mostrarNotificacion(String mensaje, Stage pai) {
        Stage stage = new Stage();
        stage.initOwner(pai);
        VNotificacionController controlador = ((VNotificacionController) loadWindow(getClass().
                getResource("/gui/vista/vNotificacion.fxml"), "Notificación", stage));
        controlador.mostrarMensaje(mensaje);
        controlador.getVenta().setResizable(false);
    }

    public void mostrarConfirmacion(String mensaje, Stage pai) {
        Stage stage = new Stage();
        stage.initOwner(pai);
        vConfirmacionControlador controlador = ((vConfirmacionControlador) loadWindow(getClass().
                getResource("/gui/vista/vConfirmacion.fxml"), "Confirmación", stage));
        controlador.mostrarMensaje(mensaje);
        controlador.getVenta().setResizable(false);
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

    public EstadisticasUsuario obtenerEstadisticasGlobalesUsuario(String dniUs) {
        return fa.obtenerEstadisticasGlobalesUsuario(dniUs);
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

    public Boolean plazoDevolucion(String vuelo) {
        return fa.plazoDevolucion(vuelo);
    }

    public Boolean vueloRealizado(String vuelo) {
        return fa.vueloRealizado(vuelo);
    }

    public Boolean devolverBillete(String vuelo, String dni) {
        return fa.devolverBillete(vuelo, dni);
    }

    public Usuario obtenerUsuario(String dni) {
        return fa.obtenerUsuario(dni);
    }

    public List<Integer> buscarTerminais() {
        return fa.buscarTerminais();
    }

    public List<String> obterTipoVentas() {
        return fa.obterTipoVentas();
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

    public Boolean reservarCoche(Reserva reserva, String dniUsuario) {
        return fa.reservarCoche(reserva, dniUsuario);
    }

    public List<Vuelo> mostrarSalidas() {
        return fa.verSalidas();
    }

    public List<Vuelo> mostrarLlegadas() {
        return fa.verLlegadas();
    }

    public List<String> obtenerAerolineasConVuelos() {
        return fa.obtenerAerolineasConVuelos();
    }

    public EstadisticasAerolinea obtenerEstadisticasAerolinea(String aer) {
        return fa.obtenerEstAerolineas(aer);
    }

    public Boolean pasarControlPersExt(String dni) {
        return fa.pasarControlPersExt(dni);
    }

    public Boolean salirControlPersExt(String dni) {
        return fa.salirControlPersExt(dni);
    }

    public Boolean pasarControlBillete(String dni, String vuelo) {
        return fa.pasarControlBillete(dni, vuelo);
    }

    public Boolean salirControlBillete(String dni, String vuelo) {
        return fa.salirControlBillete(dni, vuelo);
    }

    public Boolean facturarMaleta(String dni, String vuelo, Float peso) {
        return fa.facturarMaleta(dni, vuelo, peso);
    }

    /*Se debe llamar después de facturar una maleta*/
    public Float getPrecioExtraDespuesFacturar(String dni, String vuelo, Float peso) {
        return fa.getPrecioExtraDespuesFacturar(dni, vuelo, peso);
    }

    public List<String> obtenerAnhosViajados(String dni) {
        return fa.obtenerAnhosViajados(dni);
    }

    public Boolean usuarioViajado(String dni) {
        return fa.usuarioViajado(dni);
    }

    public Boolean estaDentroPersLab(PersonalLaboral us) {
        return fa.estaDentroPersLaboral(us);
    }

    public void entrarPersLaboral(PersonalLaboral usu) {
        fa.entrarPersLaboral(usu);
    }

    public void salirPersLaboral(PersonalLaboral usu) {
        fa.salirPersLaboral(usu);
    }

    public void obtenerHistorialPersLaboral(PersonalLaboral usu, Time fechaInicio, Time fechaFin) {
        fa.obtenerHistorialPersLaboral(usu, fechaInicio, fechaFin);
    }
}
