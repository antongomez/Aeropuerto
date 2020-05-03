/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.controlador;

import aeropuerto.elementos.PersonalLaboral;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Esther
 */
public class VMaletaController extends Controlador implements Initializable {
    
    private PersonalLaboral trabajador;

    @FXML
    private TextField txtFieldNumVuelo;
    @FXML
    private TextField txtFieldDni;
    @FXML
    private TextField txtFieldPeso;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    public void setTrabajador(PersonalLaboral trab){
        this.trabajador=trab;
    }
    
}
