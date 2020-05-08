/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Esther
 */
public class VTareaController extends Controlador implements Initializable {

    /**
     * Initializes the controller class.
     */
    private PersonalLaboral trabajador;
    @FXML
    private TextField txtFieldTarea;
    @FXML
    private TextArea txtAreaDescripcion;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    public void setTrabajador(PersonalLaboral trab) {
        this.trabajador = trab;
        if(Modelo.getInstanceModelo().obtenerDatosPersLab(trab)==true){
        txtFieldTarea.setText(trabajador.getLabor());
        txtAreaDescripcion.setText(trabajador.getDescripcionTarea());
        }
    }

    
}
