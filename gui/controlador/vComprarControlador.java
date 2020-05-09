package gui.controlador;

import aeropuerto.elementos.Usuario;
import aeropuerto.elementos.Vuelo;
import gui.modelo.Modelo;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class vComprarControlador extends Controlador implements Initializable {

    private Vuelo vuelo;
    private ObservableList<Usuario> pasajeros;
    private Integer plazasNormalesEnEspera;
    private Integer plazasPremiumEnEspera;
    final static Integer CARGO_EXTRA_ACOMPANHANTE = 30;

    //Campos del vuelo
    @FXML
    private TextField textFieldNumero;
    @FXML
    private TextField textFieldOrigen;
    @FXML
    private TextField textFieldSalida;
    @FXML
    private TextField textFieldTerminal;
    @FXML
    private TextField textFieldDestino;
    @FXML
    private TextField textFieldLlegada;
    @FXML
    private TextField textFieldCodAvion;
    @FXML
    private TextField textFieldPlazasNormales;
    @FXML
    private TextField textFieldPlazasPremium;
    @FXML
    private TextField textFieldAerolinea;
    @FXML
    private TextField textFieldPrecioNormal;
    @FXML
    private TextField textFieldPrecioPremium;
    @FXML
    private TextField txtFieldPrecioTotal;
    @FXML
    private TextField textFieldPrecioMaleta;
    @FXML
    private TextField textFieldPesoMaleta;

    //Tabla de pasajeros
    @FXML
    private TableView<Usuario> tablaPasajeros;
    @FXML
    private TableColumn<Usuario, String> columnaDNI;
    @FXML
    private TableColumn<Usuario, String> columnaNombre;

    //Añadir pasajero
    @FXML
    private TextField textFieldDNI;
    @FXML
    private Button btnAnhadir;

    //Personalizar billete
    @FXML
    private RadioButton radioBtnAcompanhante;
    @FXML
    private ComboBox<Integer> comboBoxNumMaletas;
    @FXML
    private RadioButton radioBtnPremium;

    //Pagar
    @FXML
    private GridPane labelPrecio;
    @FXML
    private Button btnPagar;
    @FXML
    private Label etqTitulo;
    @FXML
    private ComboBox<Integer> comboBoxAsiento;

    //DatosExtra (usase para o css)
    @FXML
    private VBox vBoxDatosExtra;
    @FXML
    private Label etqDatosExtra;
    @FXML
    private Button btnEliminar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<Integer> opcionesMaleta = FXCollections.observableArrayList(0, 1, 2, 3, 4);
        comboBoxNumMaletas.setItems(opcionesMaleta);
        comboBoxNumMaletas.getSelectionModel().selectFirst();
        pasajeros = FXCollections.observableArrayList(new ArrayList<Usuario>());
        columnaDNI.setCellValueFactory(new PropertyValueFactory<>("dni"));
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tablaPasajeros.setItems(pasajeros);
    }

    public void inicializarVComprar(Vuelo vuelo, Usuario usuario) {
        this.vuelo = vuelo;
        Modelo.getInstanceModelo().obtenerDatosAvionVuelo(vuelo);
        Modelo.getInstanceModelo().obtenerAsientos(vuelo);
        datosVuelo();
        usuario.comprarVuelo(vuelo.getNumVuelo());
        if (comprobarAsientos(usuario)) {
            pasajeros.add(usuario);
            tablaPasajeros.getSelectionModel().selectFirst();
            actualizarDatosUsu();
            actualizarPrecio();
        } else {
            getVenta().close();
        }
    }

    //Función pone los asientos disponibles en el comboBox
    public void asientosDisponibles() {
        ArrayList<Integer> asientosDisponibles = new ArrayList<Integer>();
        Usuario usuario = tablaPasajeros.getSelectionModel().getSelectedItem();
        if (usuario.getVueloEnEspera().getAsiento() != null) {
            asientosDisponibles.add(tablaPasajeros.getSelectionModel().getSelectedItem().getVueloEnEspera().getAsiento());
        }
        if (!usuario.getVueloEnEspera().getPremium()) {
            for (int i = 1; i <= vuelo.getAvion().getAsientosNormales(); i++) {
                if (vuelo.getAsientosNormalesDisponibles().get(i)) {
                    asientosDisponibles.add(i);
                }
            }
        } else {
            for (int i = vuelo.getAvion().getAsientosNormales() + 1; i <= vuelo.getAvion().getAsientosNormales() + vuelo.getAvion().getAsientosPremium(); i++) {
                if (vuelo.getAsientosPremiumDisponibles().get(i)) {
                    asientosDisponibles.add(i);
                }
            }
        }
        ObservableList<Integer> asientos = FXCollections.observableArrayList(asientosDisponibles);
        comboBoxAsiento.setItems(asientos);
        comboBoxAsiento.getSelectionModel().selectFirst();
        if (usuario.getVueloEnEspera().getAsiento() == null) {
            usuario.getVueloEnEspera().setAsiento(comboBoxAsiento.getSelectionModel().getSelectedItem());
            if (!usuario.getVueloEnEspera().getPremium()) {
                vuelo.getAsientosNormalesDisponibles().replace(usuario.getVueloEnEspera().getAsiento(), false);
            } else {
                vuelo.getAsientosPremiumDisponibles().replace(usuario.getVueloEnEspera().getAsiento(), false);
            }
        }
    }

    public void datosVuelo() {
        //Ponemos los datos del vuelo
        textFieldNumero.setText(this.vuelo.getNumVuelo());
        textFieldCodAvion.setText(this.vuelo.getAvion().getCodigo());
        textFieldOrigen.setText(this.vuelo.getOrigen());
        textFieldSalida.setText(this.vuelo.getFechasalidaTeo().toString());
        textFieldTerminal.setText(this.vuelo.getTerminal().toString());
        textFieldAerolinea.setText(this.vuelo.getAerolinea().getNombre());
        textFieldDestino.setText(this.vuelo.getDestino());
        textFieldLlegada.setText(this.vuelo.getFechallegadaTeo().toString());
        textFieldPrecioNormal.setText(this.vuelo.getPrecioActual().toString());
        textFieldPrecioPremium.setText(vuelo.getPrecioPremium().toString());
        textFieldPrecioMaleta.setText(vuelo.getAerolinea().getPrecioBaseMaleta().toString());
        textFieldPesoMaleta.setText(vuelo.getAerolinea().getPesoBaseMaleta().toString());
        plazasNormalesEnEspera = vuelo.getPlazasNormal();
        plazasPremiumEnEspera = vuelo.getPlazasPremium();
    }

    @FXML
    private void anhadirPasajero(ActionEvent event) {
        Usuario us = Modelo.getInstanceModelo().obtenerUsuario(textFieldDNI.getText());
        if (us != null) {
            us.comprarVuelo(vuelo.getNumVuelo());
            if (pasajeros.contains(us)) {
                Modelo.getInstanceModelo().mostrarError("El usuario ya figura como pasajero", getVenta());
            } else if (Modelo.getInstanceModelo().obtenerVuelosUsuario(us.getDni()).contains(vuelo)) {
                Modelo.getInstanceModelo().mostrarError("El usuario ya dispone un billete para este vuelo", getVenta());
            } else if (comprobarAsientos(us)) {
                pasajeros.add(us);
                if (pasajeros.size() == 1) {
                    radioBtnPremium.setDisable(true);
                    radioBtnAcompanhante.setDisable(true);
                    comboBoxAsiento.setDisable(true);
                    comboBoxNumMaletas.setDisable(true);
                    btnPagar.setDisable(false);
                }
                tablaPasajeros.getSelectionModel().selectLast();
                actualizarDatosUsu();
                actualizarPrecio();
            }
        } else {
            Modelo.getInstanceModelo().mostrarError("Usuario no registrado.\n"
                    + "Debe registrarse antes de volar con nostros.", getVenta());
        }
    }

    @FXML
    private void cambioUsuario(MouseEvent event) {
        actualizarDatosUsu();
        actualizarPrecio();
    }

    private void actualizarDatosUsu() {
        Usuario us = tablaPasajeros.getSelectionModel().getSelectedItem();

        //Comprobamos como activar el btn de Premium en función de la disponibilidad
        if (plazasNormalesEnEspera == 0 && plazasPremiumEnEspera == 0) {
            if (us.getVueloEnEspera().getPremium()) {
                asientosDisponibles();
                radioBtnPremium.setSelected(true);
                radioBtnPremium.setDisable(true);
                radioBtnPremium.setVisible(true);
            } else {
                asientosDisponibles();
                radioBtnPremium.setSelected(false);
                radioBtnPremium.setVisible(false);
                radioBtnPremium.setDisable(true);
            }
        } else if (plazasNormalesEnEspera == 0) {
            if (us.getVueloEnEspera().getPremium()) {
                asientosDisponibles();
                radioBtnPremium.setSelected(true);
                radioBtnPremium.setDisable(true);
                radioBtnPremium.setVisible(true);
            } else {
                asientosDisponibles();
                radioBtnPremium.setSelected(false);
                radioBtnPremium.setDisable(false);
                radioBtnPremium.setVisible(true);
            }
        } else if (plazasPremiumEnEspera == 0) {
            if (us.getVueloEnEspera().getPremium()) {
                asientosDisponibles();
                radioBtnPremium.setSelected(true);
                radioBtnPremium.setDisable(false);
                radioBtnPremium.setVisible(true);
            } else {
                asientosDisponibles();
                radioBtnPremium.setSelected(false);
                radioBtnPremium.setVisible(false);
                radioBtnPremium.setDisable(true);
            }
        } else {
            if (us.getVueloEnEspera().getPremium()) {
                asientosDisponibles();
                radioBtnPremium.setSelected(true);
                radioBtnPremium.setDisable(false);
                radioBtnPremium.setVisible(true);
            } else {
                asientosDisponibles();
                radioBtnPremium.setSelected(false);
                radioBtnPremium.setVisible(true);
                radioBtnPremium.setDisable(false);
            }

        }
        if (us.getVueloEnEspera().getAcompanante()) {
            radioBtnAcompanhante.setSelected(true);
        } else {
            radioBtnAcompanhante.setSelected(false);
        }
        comboBoxNumMaletas.getSelectionModel().select(us.getVueloEnEspera().getNumMaletas());

    }

    @FXML
    private void selectAcompanhante(ActionEvent event) {
        if (radioBtnAcompanhante.isSelected()) {
            tablaPasajeros.getSelectionModel().getSelectedItem().getVueloEnEspera().setAcompanante(true);
        } else {
            tablaPasajeros.getSelectionModel().getSelectedItem().getVueloEnEspera().setAcompanante(false);
        }
        actualizarPrecio();
    }

    @FXML
    private void selectPremium(ActionEvent event) {
        Usuario usuario = tablaPasajeros.getSelectionModel().getSelectedItem();
        if (radioBtnPremium.isSelected()) {
            vuelo.getAsientosNormalesDisponibles().replace(usuario.getVueloEnEspera().getAsiento(), true);
            usuario.getVueloEnEspera().setAsiento(null);
            usuario.getVueloEnEspera().setPremium(true);
            asientosDisponibles();
            plazasNormalesEnEspera += 1;
            plazasPremiumEnEspera -= 1;
        } else {
            vuelo.getAsientosPremiumDisponibles().replace(usuario.getVueloEnEspera().getAsiento(), true);
            usuario.getVueloEnEspera().setAsiento(null);
            usuario.getVueloEnEspera().setPremium(false);
            asientosDisponibles();
            plazasNormalesEnEspera += 1;
            plazasPremiumEnEspera -= 1;
        }
        actualizarPrecio();
    }

    private Float actualizarPrecio() {
        Float precio = (float) 0;
        Float precioUsuario = (float) 0;
        Usuario usuario = tablaPasajeros.getSelectionModel().getSelectedItem();

        if (usuario.getVueloEnEspera().getPremium()) {
            precioUsuario += vuelo.getPrecioPremium();
        } else {
            precioUsuario += vuelo.getPrecioActual();
        }
        if (usuario.getVueloEnEspera().getAcompanante()) {
            precioUsuario += CARGO_EXTRA_ACOMPANHANTE;
        }
        precioUsuario += usuario.getVueloEnEspera().getNumMaletas() * vuelo.getAerolinea().getPrecioBaseMaleta();
        precioUsuario = (float) (Math.round(precioUsuario * 100d) / 100d);
        usuario.getVueloEnEspera().setPrecio(precioUsuario);

        for (Usuario pas : pasajeros) {
            precio += pas.getVueloEnEspera().getPrecio();
        }
        precio = (float) (Math.round(precio * 100d) / 100d);
        txtFieldPrecioTotal.setText(precio.toString());
        return precio;
    }

    //Función que comprueba disponibilidad de asientos 
    public boolean comprobarAsientos(Usuario usuario) {
        if (plazasPremiumEnEspera == 0 && plazasNormalesEnEspera == 0) {
            Modelo.getInstanceModelo().mostrarNotificacion("Lo sentimos, no quedan "
                    + "más plazas para este vuelo", getVenta());
            return false;
        } else if (plazasPremiumEnEspera == 0) {
            radioBtnPremium.setSelected(false);
            radioBtnPremium.setVisible(false);
            radioBtnPremium.setDisable(true);
            usuario.getVueloEnEspera().setPremium(false);
            plazasNormalesEnEspera -= 1;
            textFieldPlazasNormales.setText(plazasNormalesEnEspera.toString());
            textFieldPlazasPremium.setText(plazasPremiumEnEspera.toString());
            return true;
        } else if (plazasNormalesEnEspera == 0) {
            radioBtnPremium.setSelected(true);
            radioBtnPremium.setDisable(true);
            radioBtnPremium.setVisible(true);
            usuario.getVueloEnEspera().setPremium(true);
            plazasPremiumEnEspera -= 1;
            textFieldPlazasNormales.setText(plazasNormalesEnEspera.toString());
            textFieldPlazasPremium.setText(plazasPremiumEnEspera.toString());
            return true;
        } else {
            usuario.getVueloEnEspera().setPremium(false);
            plazasNormalesEnEspera -= 1;
            textFieldPlazasNormales.setText(plazasNormalesEnEspera.toString());
            textFieldPlazasPremium.setText(plazasPremiumEnEspera.toString());
            return true;
        }
    }

    public Vuelo getVuelo() {
        return vuelo;
    }

    public void setVuelo(Vuelo vuelo) {
        this.vuelo = vuelo;
    }

    @FXML
    private void pagar(MouseEvent event) {
        if (Modelo.getInstanceModelo().comprarBilletes(pasajeros)) {
            getVenta().close();
            String dniUsuarios = "";
            Iterator<Usuario> it = pasajeros.iterator();
            while (it.hasNext()) {
                Usuario usuario = it.next();
                if (it.hasNext()) {
                    dniUsuarios += usuario.getDni() + ", ";
                } else {
                    dniUsuarios += usuario.getDni();
                }
            }
            Modelo.getInstanceModelo().mostrarNotificacion("Compra realizada con éxito.\n"
                    + "- Dni de los pasajeros: " + dniUsuarios + "\n"
                    + "- Origen: " + vuelo.getOrigen() + "\n"
                    + "- Destino: " + vuelo.getDestino() + "\n"
                    + "- Fecha salida: " + vuelo.getFechasalidaTeo().toString() + "\n"
                    + "- Precio total: " + actualizarPrecio().toString() + " €.",
                    getVenta());
        } else {
            Modelo.getInstanceModelo().mostrarError("Hubo un error en la compra, lo sentimos.\n"
                    + "Trataremos de arreglarlo lo antes posible.", getVenta());
            getVenta().close();
        }
    }

    @FXML
    private void cambiarNumMaletas(ActionEvent event) {
        tablaPasajeros.getSelectionModel().getSelectedItem().getVueloEnEspera().setNumMaletas(comboBoxNumMaletas.getSelectionModel().getSelectedItem());
        actualizarPrecio();
    }

    @FXML
    private void cambiarAsiento(ActionEvent event) {
        Usuario usuario = tablaPasajeros.getSelectionModel().getSelectedItem();
        if (usuario.getVueloEnEspera().getPremium()) {
            vuelo.getAsientosPremiumDisponibles().replace(usuario.getVueloEnEspera().getAsiento(), true);
            usuario.getVueloEnEspera().setAsiento(comboBoxAsiento.getSelectionModel().getSelectedItem());
            vuelo.getAsientosPremiumDisponibles().replace(usuario.getVueloEnEspera().getAsiento(), false);
        } else {
            vuelo.getAsientosNormalesDisponibles().replace(usuario.getVueloEnEspera().getAsiento(), true);
            usuario.getVueloEnEspera().setAsiento(comboBoxAsiento.getSelectionModel().getSelectedItem());
            vuelo.getAsientosNormalesDisponibles().replace(usuario.getVueloEnEspera().getAsiento(), false);
        }
    }

    @FXML
    private void eliminarPasajero(ActionEvent event) {
        Usuario pasajeroSelect = tablaPasajeros.getSelectionModel().getSelectedItem();
        if (pasajeroSelect != null) {
            if (pasajeroSelect.getVueloEnEspera().getPremium()) {
                vuelo.getAsientosPremiumDisponibles().replace(pasajeroSelect.getVueloEnEspera().getAsiento(), true);
            } else {
                vuelo.getAsientosNormalesDisponibles().replace(pasajeroSelect.getVueloEnEspera().getAsiento(), true);
            }
            pasajeros.remove(pasajeroSelect);
            if (pasajeros.size() > 0) {
                tablaPasajeros.getSelectionModel().selectFirst();
                actualizarDatosUsu();
                actualizarPrecio();
            } else {
                Float precio = (float) (Math.round((float) 0 * 100d) / 100d);
                txtFieldPrecioTotal.setText("0");
                radioBtnPremium.setDisable(true);
                radioBtnAcompanhante.setDisable(true);
                comboBoxAsiento.setDisable(true);
                comboBoxNumMaletas.setDisable(true);
                btnPagar.setDisable(true);
            }
        }

    }

}
