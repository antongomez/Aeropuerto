/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.controlador;

import aeropuerto.util.Reserva;
import aeropuerto.util.Time;
import gui.modelo.Modelo;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author eliseopitavilarino
 */
public class VCocheControlador extends Controlador implements Initializable {

    private String dniUsuarioActual;

    //Apartados con reserva
    @FXML
    private TextField textFieldDNIConReserva;
    @FXML
    private Button btnBuscarConReserva;
    @FXML
    private Button btnAlquilarConReserva;
    @FXML
    private RadioButton cambiarFechaConReserva;
    @FXML
    private DatePicker datePickerConReserva;
    @FXML
    private TableView<Reserva> tablaConReserva;
    @FXML
    private TableColumn<Reserva, String> columnaMatriculaConReserva;
    @FXML
    private TableColumn<Reserva, Time> columnaFechaVueltaConReserva;
    @FXML
    private TableColumn<Reserva, Float> columnaPrecioConReserva;
    @FXML
    private TableColumn<Reserva, Time> columnaFechaRecogidaConReserva;
    @FXML
    private TableColumn<Reserva, String> columnaEstadoConReserva;
    @FXML
    private TableColumn<Reserva, String> columnaModeloConReserva;
    @FXML
    private TextField textFieldPrecioFinalConReserva;
    
    @FXML
    private TextField textFieldModeloSinReserva;
    @FXML
    private TextField textFieldMatriculaSinReserva;
    @FXML
    private ComboBox<?> comboBoxPlazasSinReserva;
    @FXML
    private DatePicker datePickerFechaVueltaSinSalida;
    @FXML
    private Button btnBuscarSinReserva;
    @FXML
    private TableView<?> tablaSinReservas;
    @FXML
    private TableColumn<?, ?> columnaMatriculaSinReserva;
    @FXML
    private TableColumn<?, ?> columnaModeloSinReserva;
    @FXML
    private TableColumn<?, ?> columnaCaballosSinReserva;
    @FXML
    private TableColumn<?, ?> columnaPrecioDiaSinReserva;
    @FXML
    private TableColumn<?, ?> columnaCombustibleSinReserva;
    @FXML
    private TableColumn<?, ?> columnaPlazasSinReserva;
    @FXML
    private TextField textFieldDniSinReserva;
    @FXML
    private Button btnAlquilarSinReserva;
    @FXML
    private TextField textFieldPrecioSinReserva;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cambiarFechaConReserva.setSelected(false);
        cambiarFechaConReserva.setDisable(true);
        datePickerConReserva.setDisable(true);
        btnAlquilarConReserva.setDisable(true);
        columnaMatriculaConReserva.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        columnaFechaVueltaConReserva.setCellValueFactory(new PropertyValueFactory<>("fin"));
        columnaPrecioConReserva.setCellValueFactory(new PropertyValueFactory<>("precio"));
        columnaModeloConReserva.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        columnaFechaRecogidaConReserva.setCellValueFactory(new PropertyValueFactory<>("inicio"));
        columnaEstadoConReserva.setCellValueFactory(new PropertyValueFactory<>("estado"));
    }

    @FXML
    private void obtenerReservasUsuario(ActionEvent event) {
        dniUsuarioActual = textFieldDNIConReserva.getText();
        ObservableList<Reserva> reservas = FXCollections.observableList(Modelo.getInstanceModelo().obtenerReservasCocheUsuario(dniUsuarioActual));
        tablaConReserva.setItems(reservas);
    }

    @FXML
    private void seleccionarConReserva(MouseEvent event) {
        Reserva reserva = tablaConReserva.getSelectionModel().getSelectedItem();
        if (reserva.getEstado().equals("sin alquilar")) {
            cambiarFechaConReserva.setDisable(false);
            datePickerConReserva.setValue(reserva.getFin().toLocalDate());
            btnAlquilarConReserva.setDisable(false);
            textFieldPrecioFinalConReserva.setText(reserva.getPrecio().toString());
        } else {
            cambiarFechaConReserva.setDisable(true);
            datePickerConReserva.setValue(reserva.getFin().toLocalDate());
            btnAlquilarConReserva.setDisable(true);
        }
    }

    @FXML
    private void alquilarConReserva(ActionEvent event) {
        Reserva reserva = tablaConReserva.getSelectionModel().getSelectedItem();
        if (cambiarFechaConReserva.isSelected()) {
            reserva.setFin(new Time(datePickerConReserva.getValue()));
            Modelo.getInstanceModelo().introducirAlquiler(reserva, dniUsuarioActual);
        } else {
            Modelo.getInstanceModelo().introducirAlquiler(reserva, dniUsuarioActual);
        }
    }

    @FXML
    private void activarDatePicker(ActionEvent event) {
        if (cambiarFechaConReserva.isSelected()) {
            datePickerConReserva.setDisable(false);
        } else {
            datePickerConReserva.setDisable(true);
            datePickerConReserva.setValue(tablaConReserva.getSelectionModel().getSelectedItem().getFin().toLocalDate());
        }
    }
    
    @FXML
    private void actualizarPrecioConReserva(ActionEvent event) {
        Reserva reserva = tablaConReserva.getSelectionModel().getSelectedItem();
        Float precio=Time.obtenerDias(reserva.getInicio().toLocalDate(), datePickerConReserva.getValue())*reserva.getPrecioDia();
        textFieldPrecioFinalConReserva.setText(precio.toString());
    }
    
    @FXML
    private void buscarCochesDisponibles(ActionEvent event) {
    }

    @FXML
    private void seleccionarSinReserva(MouseEvent event) {
    }

    @FXML
    private void alquilarSinReserva(ActionEvent event) {
    }

}
