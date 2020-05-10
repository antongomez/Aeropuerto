package gui.controlador;

import aeropuerto.elementos.ElemHistorial;
import aeropuerto.elementos.PersonalLaboral;
import aeropuerto.util.Time;
import gui.modelo.Modelo;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class VHistorialController extends Controlador implements Initializable {

    PersonalLaboral trabajador;
    @FXML
    private TableView<ElemHistorial> tablaHistorial;
    @FXML
    private TableColumn<ElemHistorial, Time> colEntrada;
    @FXML
    private TableColumn<ElemHistorial, Time> colSalida;
    @FXML
    private DatePicker datePickerFechaInicio;
    @FXML
    private DatePicker datePickerFechaFin;
    @FXML
    private Button btnBuscar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void setTrabajador(PersonalLaboral trab) {
        this.trabajador = trab;
        colEntrada.setCellValueFactory(new PropertyValueFactory<>("fechaEntrada"));
        colSalida.setCellValueFactory(new PropertyValueFactory<>("fechaSalida"));
        trabajador.borrarHistorial();
        if (Modelo.getInstanceModelo().obtenerHistorialPersLaboral(trabajador, Time.diaActual(), Time.diaActual()) == true) {

            ObservableList<ElemHistorial> hist = FXCollections.observableArrayList(trabajador.getHistorialTrabajo());
            tablaHistorial.setItems(hist);
            //Engadimos a data actual nos datePickers
            datePickerFechaInicio.setValue(LocalDate.now());
            datePickerFechaFin.setValue(LocalDate.now());
        }
    }

    @FXML
    private void buscarHistorial(ActionEvent event) {
        Time inicio, fin;

        if (datePickerFechaInicio.getValue() != null) {
            inicio = new Time(datePickerFechaInicio.getValue());
        } else {
            inicio = Time.diaActual();
        }
        if (datePickerFechaFin.getValue() != null) {
            fin = new Time(datePickerFechaFin.getValue());
        } else {
            fin = Time.diaActual();
        }
        trabajador.borrarHistorial();
        if (Modelo.getInstanceModelo().obtenerHistorialPersLaboral(trabajador, inicio, fin) == true) {

            ObservableList<ElemHistorial> hist = FXCollections.observableArrayList(trabajador.getHistorialTrabajo());
            tablaHistorial.setItems(hist);
        }
    }
}
