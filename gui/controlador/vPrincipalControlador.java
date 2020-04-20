package gui.controlador;

import aeropuerto.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class vPrincipalControlador extends Controlador {

    private Usuario usuario;//usuario que está usando la ventana

    //Encabezado
    @FXML
    private AnchorPane panelTitulo;
    @FXML
    private HBox boxTitulo;
    @FXML
    private Label etqAeroEtse;
    @FXML
    private Label etqTitulo;

    //MenuLateral
    @FXML
    private AnchorPane panelLateral;
    @FXML
    private ToggleGroup BtnMenu;

    //Botons do menu
    @FXML
    private ToggleButton btnVuelos;
    @FXML
    private ToggleButton btnAreaP;
    @FXML
    private ToggleButton btnServicios;
    @FXML
    private ToggleButton btnInfo;
    @FXML
    private ToggleButton btnAdmin;

    //Ventas
    @FXML
    private TabPane panelAreaP;
    @FXML
    private AnchorPane panelVuelos;

    //Venta Vuelo
    @FXML
    private TextField txtNumVuelo;
    @FXML
    private TextField txtOrigen;
    @FXML
    private DatePicker dataPickSalida;
    @FXML
    private TextField txtDestino;
    @FXML
    private DatePicker dataPickLlegada;
    @FXML
    private Button btnBuscar;
    @FXML
    private Button btnComprar;

    //Venta Area Personal
    @FXML
    private Button btnDarseBaja;
    @FXML
    private Button btnGuardar;
    @FXML
    private ToggleGroup opVerVuelo;
    @FXML
    private ToggleGroup opVerVuelo2;
    @FXML
    private ToggleGroup opVerVuelo21;

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @FXML
    private void accionBtnVuelos(ActionEvent event) {
        panelVuelos.toFront();
    }

    @FXML
    private void accionBtnAreaP(ActionEvent event) {
        panelAreaP.toFront();
    }

    @FXML
    private void accionBtnServicios(ActionEvent event) {
    }

    @FXML
    private void accionBtnInfo(ActionEvent event) {
    }

    @FXML
    private void accionBtnAdmin(ActionEvent event) {
    }

    @FXML
    private void accionBtnBuscar(ActionEvent event) {
    }

    @FXML
    private void accionBtnComprar(ActionEvent event) {
    }

    @FXML
    private void accionBtnDarseBaja(ActionEvent event) {
    }

    @FXML
    private void aacionBtnGuardar(ActionEvent event) {
    }

}
