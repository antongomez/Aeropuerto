package aeropuerto;

import aeropuerto.elementos.Coche;
import aeropuerto.elementos.Parking;
import aeropuerto.elementos.Vuelo;
import aeropuerto.elementos.Usuario;
import aeropuerto.gestion.GestionParking;
import aeropuerto.gestion.GestionReservas;
import aeropuerto.gestion.GestionVuelos;
import aeropuerto.gestion.GestionUsuarios;
import aeropuerto.util.EstadisticasAerolinea;
import aeropuerto.util.EstadisticasUsuario;
import aeropuerto.util.PorcentajeDisponibilidad;
import aeropuerto.util.Reserva;
import aeropuerto.util.Time;
import baseDatos.FachadaBaseDatos;
import gui.FachadaGui;
import java.util.List;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

public class FachadaAplicacion extends Application {

    FachadaGui fgui;
    FachadaBaseDatos fbd;
    GestionUsuarios gu;
    GestionVuelos gv;
    GestionReservas gr;
    GestionParking gp;

    public FachadaAplicacion() {
        fgui = new gui.FachadaGui(this);
        fbd = new baseDatos.FachadaBaseDatos(this);
        gu = new GestionUsuarios(fgui, fbd);
        gv = new GestionVuelos(fgui, fbd);
        gr = new GestionReservas(fgui, fbd);
        gp = new GestionParking(fgui, fbd);
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void iniciaInterfazUsuario(Stage stage) throws Exception {
        fgui.iniciarVista(stage);
    }

    @Override
    public void start(Stage stage) {
        FachadaAplicacion fa;
        fa = new FachadaAplicacion();
        try {
            fa.iniciaInterfazUsuario(stage);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());;
        }
    }

    //Usuarios
    public Boolean registrarUsuario(Usuario us, String clave) {//true si se registra y false si no
        return gu.registrarUsuario(us, clave);
    }

    public Boolean modificarContrasenha(String idUsuario, String clave) {
        return gu.modificarContrasenha(idUsuario, clave);
    }

    public Usuario credencialesCorrectos(String id, String cont) {
        return gu.credencialesCorrectos(id, cont);
    }

    public Boolean modificarUsuario(Usuario us) {
        return gu.modificarUsuario(us);
    }

    public boolean eliminarUsuario(String dni) {
        return gu.eliminarUsuario(dni);
    }

    /*Rellena los campos dni y nombre del usuario encontrado. Si no se encuentra devuelve null*/
    public Usuario obtenerUsuario(String dni) {
        return gu.obtenerUsuario(dni);
    }

    //en la interfaz falta poner si está o no cancelado y cuánto falta para su salida
    public List<Vuelo> obtenerVuelosUsuario(String dniUs) {//no está probada
        return gv.obtenerVuelosUsuario(dniUs);
    }

    /*tipo: año/mes/estación
    tipo=anho => num= año concreto
    tipo=mes => num=1,...,12 dependiendo del mes concreto
    tipo= estacion => num=1,...,4 dependiendo de la estación concreta en este orden: primavera, verano, otoño, invierno*/
    public EstadisticasUsuario obtenerEstadisticasUsuario(String dniUs, String tipo, Integer num) {
        return gu.obtenerEstadisticasUsuario(dniUs, tipo, num);
    }

    //Vuelos
    public Boolean insertarVuelo(Vuelo v) {
        return gv.insertarVuelo(v);
    }

    public List<Vuelo> buscarVuelos(String numVuelo, String origen, String destino, Time fechaSalida, Time fechaLlegada) {
        return gv.buscarVuelos(numVuelo, origen, destino, fechaSalida, fechaLlegada);
    }
    public List<Vuelo> verSalidas(){
        return gv.verSalidas();
    }
    public List<Vuelo> verLlegadas(){
        return gv.verLlegadas();
    }

    //Reservas
    /*Muestra las reservas de un usuario que aún no han sido vencidas*/
    public List<Reserva> obtenerReservasUsuario(String dniUs) {
        return gr.obtenerReservasUsuario(dniUs);
    }

    public Boolean cancelarReserva(Reserva res, String dniUs) {
        return gr.cancelarReserva(res, dniUs);
    }
    //Aerolineas
    
    public EstadisticasAerolinea obtenerEstAerolineas(String aer){
        return gv.obtenerEstAerolineas(aer);
    }
    public List<String> obtenerAerolineas(){
        return gv.obtenerAerolineas();
    }

    //Erros
    public void mostrarError(String menseje) {
        fgui.mostrarError(menseje);
    }

    //Getters
    public FachadaGui getFgui() {
        return fgui;
    }

    public void obtenerDatosAvionVuelo(Vuelo v) {
        gv.obtenerDatosAvionVuelo(v);
    }
    
    public void obtenerAsientos(Vuelo vuelo){
        gv.obtenerAsientos(vuelo);
    }
    
    public Boolean comprarBilletes(ObservableList<Usuario> usuarios){
        return gv.comprarBilletes(usuarios);
    }

    public List<Integer> buscarTerminais() {
        return gp.buscarTerminais();
    }

    public Parking buscarParking(Integer terminal, Time inicio, Time fin) {
        return gp.buscarParking(terminal, inicio, fin);
    }
    
    public List<Coche> buscarCoches(Time llegada, Time retorno, Integer numPlazas) {
        return fbd.buscarCoches(llegada, retorno, numPlazas);
    }

    public Integer obterPrazaLibre(Integer numTerminal, Integer piso, Time inicio, Time fin) {
        return gp.obterPrazaLibre(numTerminal, piso, inicio, fin);
    }

    public PorcentajeDisponibilidad obterPrazasRestantesParkingTerminal(Integer numTerminal, Time inicio, Time fin) {
        return gp.obterPrazasRestantesParkingTerminal(numTerminal, inicio, fin);
    }

    public Boolean reservarParking(Reserva reserva, String dniUsuario) {
        return gr.reservarParking(reserva, dniUsuario);
    }

}
