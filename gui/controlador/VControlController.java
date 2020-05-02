/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Esther
 */
public class VControlController implements Initializable {

    @FXML
    private Label etqNumVuelo;
    @FXML
    private CheckBox checkBoxPersExterno;
    @FXML
    private TextField txtFieldDni;
    @FXML
    private TextField txtFieldNumVuelo;
    @FXML
    private Button btnEntrarSalir;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
