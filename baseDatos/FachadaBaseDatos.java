package baseDatos;

import aeropuerto.FachadaAplicacion;
import aeropuerto.elementos.Aerolinea;
import aeropuerto.elementos.Coche;
import aeropuerto.elementos.Parking;
import aeropuerto.elementos.PersonalLaboral;
import aeropuerto.elementos.Tienda;
import aeropuerto.elementos.Usuario;
import aeropuerto.elementos.Vuelo;
import aeropuerto.util.EstadisticasAerolinea;
import aeropuerto.util.EstadisticasUsuario;
import aeropuerto.util.PorcentajeDisponibilidad;
import aeropuerto.util.reservas.Reserva;
import aeropuerto.util.Time;
import aeropuerto.util.reservas.ReservaCoche;
import aeropuerto.util.reservas.ReservaParking;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import javafx.collections.ObservableList;

public class FachadaBaseDatos {

    private FachadaAplicacion fa;
    private java.sql.Connection conexion;
    private daoUsuarios daoUsuarios;
    private daoVuelos daoVuelos;
    private daoReservas daoReservas;
    private daoTerminal daoTerminal;
    private daoCoches daoCoches;
    private daoAerolineas daoAerolineas;
    private daoTiendas daoTiendas;

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
            daoVuelos = new daoVuelos(conexion, fa);
            daoReservas = new daoReservas(conexion, fa);
            daoTerminal = new daoTerminal(conexion, fa);
            daoCoches = new daoCoches(conexion, fa);
            daoAerolineas = new daoAerolineas(conexion, fa);
            daoTiendas = new daoTiendas(conexion, fa);

        } catch (FileNotFoundException f) {
            fa.mostrarError(f.getMessage());
        } catch (IOException i) {
            fa.mostrarError(i.getMessage());
        } catch (java.sql.SQLException e) {
            fa.mostrarError(e.getMessage());
        }

    }

    public Boolean insertarUsuario(Usuario u, String clave) {
        return daoUsuarios.insertarUsuario(u, clave);
    }

    public Boolean modificarUsuario(Usuario us) {
        return daoUsuarios.modificarUsuario(us);
    }

    public Boolean modificarContrasenha(String idUsuario, String clave) {
        return daoUsuarios.modificarContrasenha(idUsuario, clave);
    }

    public boolean eliminarUsuario(String dni) {
        return daoUsuarios.eliminarUsuario(dni);
    }

    public Usuario comprobarCredenciales(String id, String cont) {
        return daoUsuarios.comprobarCredenciales(id, cont);
    }

    public Boolean insertarVuelo(Vuelo v) {
        return daoVuelos.insertarVuelo(v);
    }

    public List<Vuelo> buscarVuelos(String numVuelo, String origen, String destino, Time fechaSalida, Time fechaLlegada) {
        return daoVuelos.buscarVuelos(numVuelo, origen, destino, fechaSalida, fechaLlegada);
    }

    public List<Vuelo> obtenerVuelosUsuario(String dniUs) {
        return daoVuelos.obtenerVuelosUsuario(dniUs);
    }

    public List<Vuelo> obtenerVuelosRealizadosUsuario(String dniUs) {
        return daoVuelos.obtenerVuelosRealizadosUsuario(dniUs);
    }

    public List<Vuelo> obtenerVuelosFuturosUsuario(String dniUs) {
        return daoVuelos.obtenerVuelosFuturosUsuario(dniUs);
    }

    public EstadisticasUsuario obtenerEstadisticasUsuario(String dniUs, String tipo, Integer num) {
        return daoUsuarios.obtenerEstadisticasUsuario(dniUs, tipo, num);
    }

    public EstadisticasUsuario obtenerEstadisticasGlobalesUsuario(String dniUs) {
        return daoUsuarios.obtenerEstadisticasGlobalesUsuario(dniUs);
    }

    public List<ReservaParking> obterResParkingUsuario(String dniUs) {
        return daoReservas.obterResParkingUsuario(dniUs);
    }

    public List<ReservaCoche> obterResCocheUsuario(String dniUs) {
        return daoReservas.obterResCocheUsuario(dniUs);
    }

    public Boolean cancelarReservaParking(ReservaParking res, String dniUs) {
        return daoReservas.cancelarReservaParking(res, dniUs);
    }

    public Boolean cancelarReservaCoche(ReservaCoche res, String dniUs) {
        return daoReservas.cancelarReservaCoche(res, dniUs);
    }

    public void obtenerDatosAvionVuelo(Vuelo v) {

        daoVuelos.obtenerDatosAvionVuelo(v);
    }

    public void obtenerAsientos(Vuelo vuelo) {
        daoVuelos.obtenerAsientos(vuelo);
    }

    public Boolean comprarBilletes(ObservableList<Usuario> usuarios) {
        return daoVuelos.comprarBilletes(usuarios);
    }

    public Boolean vueloRealizado(String vuelo) {
        return daoVuelos.vueloRealizado(vuelo);
    }

    public Boolean devolverBillete(String vuelo, String dni) {
        return daoVuelos.devolverBillete(vuelo, dni);
    }

    public Usuario obtenerUsuario(String dni) {
        return daoUsuarios.obtenerUsuario(dni);
    }

    public Parking buscarParking(Integer terminal, Time inicio, Time fin) {
        return daoTerminal.buscarParking(terminal, inicio, fin);
    }

    public Integer obterNumPlazasParking(Integer numTerminal, Integer piso) {
        return daoTerminal.obterNumPlazasParking(numTerminal, piso);
    }

    public List<Integer> obterPlazasOcupadas(Integer numTerminal, Integer piso, Time inicio, Time fin) {
        return daoTerminal.obterPlazasOcupadas(numTerminal, piso, inicio, fin);
    }

    public List<Integer> buscarTerminais() {
        return daoTerminal.buscarTerminais();
    }

    public PorcentajeDisponibilidad obterPrazasRestantesParkingTerminal(Integer numTerminal, Time inicio, Time fin) {
        return daoTerminal.obterPrazasRestantesParkingTerminal(numTerminal, inicio, fin);
    }

    public Boolean reservarParking(ReservaParking reserva, String dniUsuario) {
        return daoReservas.reservarParking(reserva, dniUsuario);
    }

    public Boolean reservarCoche(ReservaCoche reserva, String dniUsuario) {
        return daoReservas.reservarCoche(reserva, dniUsuario);
    }

    public List<Coche> buscarCoches(Time llegada, Time retorno, Integer numPlazas) {
        return daoCoches.buscarCoches(llegada, retorno, numPlazas);
    }

    public List<Coche> buscarCoches(Time llegada, Time retorno, Integer numPlazas, String modelo, String matricula) {
        return daoCoches.buscarCoches(llegada, retorno, numPlazas, modelo, matricula);
    }

    public List<Tienda> buscarTiendas(String nombre, String tipo, String terminal) {
        return daoTiendas.buscarTiendas(nombre, tipo, terminal);
    }

    public List<Vuelo> verSalidas() {
        return daoVuelos.verSalidas();
    }

    public List<Vuelo> verLlegadas() {
        return daoVuelos.verLlegadas();
    }

    public EstadisticasAerolinea obtenerEstAerolineas(String aer) {
        return daoAerolineas.obtenerEstAerolineas(aer);
    }

    public List<String> obtenerAerolineasConVuelos() {
        return daoAerolineas.obtenerAerolineasConVuelos();
    }

    public Boolean pasarControlPersExt(String dni) {
        return daoUsuarios.pasarControlPersExt(dni);
    }

    public Boolean salirControlPersExt(String dni) {
        return daoUsuarios.salirControlPersExt(dni);
    }

    public Boolean pasarControlBillete(String dni, String vuelo) {
        return daoVuelos.pasarControlBillete(dni, vuelo);
    }

    public Boolean salirControlBillete(String dni, String vuelo) {
        return daoVuelos.salirControlBillete(dni, vuelo);
    }

    public Aerolinea obtenerDatosAerolinea(String vuelo) {
        return daoVuelos.obtenerDatosAerolinea(vuelo);
    }

    public Integer numeroMaletasDisponibles(String dni, String vuelo) {
        return daoVuelos.numeroMaletasDisponibles(dni, vuelo);
    }

    public Boolean facturarMaleta(String dni, String vuelo, Float peso) {
        return daoVuelos.facturarMaleta(dni, vuelo, peso);
    }

    public List<String> obterTipoVentas() {
        return daoTiendas.obterTipoVentas();
    }

    public List<String> obtenerAnhosViajados(String dni) {
        return daoVuelos.obtenerAnhosViajados(dni);
    }

    public Boolean estaDentroPersLaboral(String dni) {
        return daoUsuarios.estaDentroPersLaboral(dni);
    }

    public void entrarPersLaboral(String dni) {
        daoUsuarios.entrarPersLaboral(dni);
    }

    public void salirPersLaboral(String dni) {
        daoUsuarios.salirPersLaboral(dni);
    }

    public Boolean obtenerHistorialPersLaboral(PersonalLaboral usu, Time fechaInicio, Time fechaFin) {
        return daoUsuarios.obtenerHistorialPersLaboral(usu, fechaInicio, fechaFin);
    }

    public List<ReservaCoche> obtenerReservasCocheUsuario(String dniUsuario) {
        return daoReservas.obtenerReservasCocheUsuario(dniUsuario);
    }

    public Boolean introducirAlquiler(String matricula, Time fin, String dni) {
        return daoReservas.introducirAlquiler(matricula, fin, dni);
    }

    public Boolean comprobarRegistrado(String dni) {
        return daoUsuarios.comprobarRegistrado(dni);
    }

    public ReservaCoche buscarAlquilerDevolucion(String matricula) {
        return daoReservas.buscarAlquilerDevolucion(matricula);
    }

    public Boolean devolucionCoche(Reserva alquiler) {
        return daoReservas.devolucionCoche(alquiler);
    }

    public Boolean obtenerDatosPersLab(PersonalLaboral trab) {
        return daoUsuarios.obtenerDatosPersLab(trab);
    }
    public Boolean sePuedeAmpliarReservaCoche(Time fechaFinOriginal, Time fechaFinNueva, String matricula){
        return daoReservas.sePuedeAmpliarReservaCoche(fechaFinOriginal, fechaFinNueva, matricula);
    }
}
