/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class vConfirmacionControlador extends Controlador implements Initializable {

    private Boolean confirmado;
    @FXML
    private Label mensaje;
    @FXML
    private Button btnConfirmar;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        confirmado=false;
    }

    public void mostrarMensaje(String men) {
        mensaje.setText(men);
    }

    @FXML
    private void accionBtnConfirmar(ActionEvent event) {
        confirmado=true;
        getVenta().close();
    }

    @FXML
    private void accionBtnCancelar(ActionEvent event) {
        confirmado=false;
        getVenta().close();
    }

    public Boolean getConfirmado() {
        return confirmado;
    }
    
    

}
