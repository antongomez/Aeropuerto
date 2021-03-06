
package gui.controlador;

import gui.modelo.Modelo;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class VControlController extends Controlador implements Initializable {


    
    @FXML
    private Label etqNumVuelo;
    @FXML
    private CheckBox checkBoxPersExterno;
    @FXML
    private TextField txtFieldDni;
    @FXML
    private TextField txtFieldNumVuelo;
    @FXML
    private Button btnEntrar;
    @FXML
    private Button btnSalir;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
  
    @FXML
    private void entrarControl(ActionEvent event) {
        
        if(checkBoxPersExterno.isSelected()){
            if(!txtFieldDni.getText().isEmpty()){
                if(Modelo.getInstanceModelo().pasarControlPersExt(txtFieldDni.getText())==true){
                    Modelo.getInstanceModelo().mostrarNotificacion("Usuario registrado correctamente", this.getVenta());
                    txtFieldDni.setText("");
                }
                else{
                    Modelo.getInstanceModelo().mostrarError("Este usuario no está registrado como personal externo", this.getVenta());
                }
            }
            else{
                Modelo.getInstanceModelo().mostrarError("Debes introducir el dni", this.getVenta());
            }
        }
        else{
            if(!(txtFieldDni.getText().isEmpty()||txtFieldNumVuelo.getText().isEmpty())){
                if(Modelo.getInstanceModelo().pasarControlBillete(txtFieldDni.getText(),txtFieldNumVuelo.getText())==true){
                    Modelo.getInstanceModelo().mostrarNotificacion("Usuario registrado correctamente", this.getVenta());
                    txtFieldDni.setText("");
                    txtFieldNumVuelo.setText("");
                }
                
            }
            else{
                Modelo.getInstanceModelo().mostrarError("Debes cubrir todos los campos", this.getVenta());
            }
        }
        
    }

    @FXML
    private void salirControl(ActionEvent event) {
        if(checkBoxPersExterno.isSelected()){
            if(!txtFieldDni.getText().isEmpty()){
                if(Modelo.getInstanceModelo().salirControlPersExt(txtFieldDni.getText())==true){
                    Modelo.getInstanceModelo().mostrarNotificacion("Usuario registrado correctamente", this.getVenta());
                    txtFieldDni.setText("");
                }
                else{
                    Modelo.getInstanceModelo().mostrarError("Este usuario no está registrado como personal externo", this.getVenta());
                }
            }
            else{
                Modelo.getInstanceModelo().mostrarError("Debes introducir el dni", this.getVenta());
            }
        }
        else{
            if(!(txtFieldDni.getText().isEmpty()||txtFieldNumVuelo.getText().isEmpty())){
                if(Modelo.getInstanceModelo().salirControlBillete(txtFieldDni.getText(),txtFieldNumVuelo.getText())==true){
                    Modelo.getInstanceModelo().mostrarNotificacion("Usuario registrado correctamente", this.getVenta());
                    txtFieldDni.setText("");
                    txtFieldNumVuelo.setText("");
                }
                
            }
            else{
                Modelo.getInstanceModelo().mostrarError("Debes cubrir todos los campos", this.getVenta());
            }
        }
        
    }
    @FXML
    private void esPersonalExterno(ActionEvent event) {
        if(checkBoxPersExterno.isSelected()){
        etqNumVuelo.setVisible(false);
        txtFieldNumVuelo.setVisible(false);
        }
        else{
            
        txtFieldNumVuelo.setText("");
        etqNumVuelo.setVisible(true);
        txtFieldNumVuelo.setVisible(true);
        }
    }
}
