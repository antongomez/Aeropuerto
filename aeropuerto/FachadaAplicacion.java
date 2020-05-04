package aeropuerto;

import aeropuerto.elementos.Coche;
import aeropuerto.elementos.Parking;
import aeropuerto.elementos.PersonalExterno;
import aeropuerto.elementos.PersonalLaboral;
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
    
    public EstadisticasUsuario obtenerEstadisticasGlobalesUsuario(String dniUs) {
        return gu.obtenerEstadisticasGlobalesUsuario(dniUs);
    }

    /*Estas cuatro funciones devuelven false si no se encontró el usuario o el vuelo indicados*/
    public Boolean pasarControlPersExt(String dni) {
        return gu.pasarControlPersExt(dni);
    }

    public Boolean salirControlPersExt(String dni) {
        return gu.salirControlPersExt(dni);
    }

    public Boolean pasarControlBillete(String dni, String vuelo) {
        return gv.pasarControlBillete(dni, vuelo);
    }

    public Boolean salirControlBillete(String dni, String vuelo) {
        return gv.salirControlBillete(dni, vuelo);
    }

    //Vuelos
    public Boolean insertarVuelo(Vuelo v) {
        return gv.insertarVuelo(v);
    }

    public List<Vuelo> buscarVuelos(String numVuelo, String origen, String destino, Time fechaSalida, Time fechaLlegada) {
        return gv.buscarVuelos(numVuelo, origen, destino, fechaSalida, fechaLlegada);
    }

    public List<Vuelo> verSalidas() {
        return gv.verSalidas();
    }

    public List<Vuelo> verLlegadas() {
        return gv.verLlegadas();
    }
    
    public Boolean facturarMaleta(String dni, String vuelo, Float peso){
        return gv.facturarMaleta(dni,vuelo,peso);
    }
    public Float getPrecioExtraDespuesFacturar(String dni, String vuelo, Float peso){
        return gv.getPrecioExtraDespuesFacturar(dni,vuelo, peso);
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

    public EstadisticasAerolinea obtenerEstAerolineas(String aer) {
        return gv.obtenerEstAerolineas(aer);
    }

    //Devuelve la lista de aerolineas que ya han realizado algún vuelo
    public List<String> obtenerAerolineasConVuelos() {
        return gv.obtenerAerolineasConVuelos();
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

    public void obtenerAsientos(Vuelo vuelo) {
        gv.obtenerAsientos(vuelo);
    }

    public Boolean comprarBilletes(ObservableList<Usuario> usuarios) {
        return gv.comprarBilletes(usuarios);
    }

    public Boolean plazoDevolucion(String vuelo) {
        return gv.plazoDevolucion(vuelo);
    }

    public Boolean vueloRealizado(String vuelo) {
        return gv.vueloRealizado(vuelo);
    }

    public Boolean devolverBillete(String vuelo, String dni) {
        return gv.devolverBillete(vuelo, dni);
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

    public Boolean reservarCoche(Reserva reserva, String dniUsuario) {
        return gr.reservarCoche(reserva, dniUsuario);
    }

    public Boolean reservarParking(Reserva reserva, String dniUsuario) {
        return gr.reservarParking(reserva, dniUsuario);
    }

    public List<String> obtenerAnhosViajados(String dni) {
        return gv.obtenerAnhosViajados(dni);
    }
    public Boolean usuarioViajado(String dni){
        return gu.usuarioViajado(dni);
    }
    public Boolean estaDentroPersLaboral(PersonalLaboral us){
        return gu.estaDentroPersLaboral(us);
    }
    public void entrarPersLaboral(PersonalLaboral usu){
        gu.entrarPersLaboral(usu);
    }
    public void salirPersLaboral(PersonalLaboral usu){
        gu.salirPersLaboral(usu);
    }
    public void obtenerHistorialPersLaboral(PersonalLaboral usu, Time fechaInicio, Time fechaFin){
        gu.obtenerHistorialPersLaboral(usu, fechaInicio, fechaFin);
    }

}
