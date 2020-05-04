/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author eliseopitavilarino
 */
public class VCocheControlador implements Initializable {
    
    //Apartados con reserva
    @FXML
    private TextField textFieldDNIConReserva;
    @FXML
    private Button btnBuscarConReserva;
    @FXML
    private TableView<?> tablaConReservatablaConReserva;
    @FXML
    private Button btnAlquilarConReserva;
    @FXML
    private RadioButton cambiarFechaConReserva;
    @FXML
    private DatePicker datePickerConReserva;
    @FXML
    private TableColumn<?, ?> columnaMatriculaConReserva;
    @FXML
    private TableColumn<?, ?> columnaFechaVueltaConReserva;
    @FXML
    private TableColumn<?, ?> columnaPrecioConReserva;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cambiarFechaConReserva.setSelected(false);
        datePickerConReserva.setDisable(true);
        btnAlquilarConReserva.setDisable(true);
    }    

    @FXML
    private void obtenerReservasUsuario(ActionEvent event) {
        
    }

    @FXML
    private void seleccionarConReserva(MouseEvent event) {
    }

    @FXML
    private void alquilarConReserva(ActionEvent event) {
    }

    @FXML
    private void activarDatePicker(ActionEvent event) {
        if(cambiarFechaConReserva.isSelected()){
            datePickerConReserva.setDisable(false);
        }
        else{
            datePickerConReserva.setDisable(true);
        }
    }
    
}
