/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.controlador;

import aeropuerto.elementos.PersonalLaboral;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author Esther
 */
public class VHistorialController extends Controlador implements Initializable {

    /**
     * Initializes the controller class.
     */
    PersonalLaboral trabajador;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   
     public void setTrabajador(PersonalLaboral trab){
        this.trabajador=trab;
    }
    
}
