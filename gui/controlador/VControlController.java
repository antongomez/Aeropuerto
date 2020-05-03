/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.controlador;

import aeropuerto.elementos.PersonalLaboral;
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

/**
 * FXML Controller class
 *
 * @author Esther
 */
public class VControlController extends Controlador implements Initializable {

    private PersonalLaboral trabajador;
    
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
    public void setTrabajador(PersonalLaboral trab){
        this.trabajador=trab;
    }

    @FXML
    private void entrarControl(ActionEvent event) {
        
        if(checkBoxPersExterno.isSelected()){
            if(!txtFieldDni.getText().isEmpty()){
                if(Modelo.getInstanceModelo().pasarControlPersExt(txtFieldDni.getText())==true){
                    Modelo.getInstanceModelo().mostrarConfirmacion("Usuario registrado correctamente", this.getVenta());
                }
                else{
                    Modelo.getInstanceModelo().mostrarConfirmacion("Este usuario no está registrado como personal externo", this.getVenta());
                }
            }
            else{
                Modelo.getInstanceModelo().mostrarError("Debes introducir el dni", this.getVenta());
            }
        }
        else{
            if(!(txtFieldDni.getText().isEmpty()||txtFieldNumVuelo.getText().isEmpty())){
                if(Modelo.getInstanceModelo().pasarControlBillete(txtFieldDni.getText(),txtFieldNumVuelo.getText())==true){
                    Modelo.getInstanceModelo().mostrarConfirmacion("Usuario registrado correctamente", this.getVenta());
                }
                else{
                    Modelo.getInstanceModelo().mostrarConfirmacion("Estos datos no corresponden con ningún billete", this.getVenta());
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
                    Modelo.getInstanceModelo().mostrarConfirmacion("Usuario registrado correctamente", this.getVenta());
                }
                else{
                    Modelo.getInstanceModelo().mostrarConfirmacion("Este usuario no está registrado como personal externo", this.getVenta());
                }
            }
            else{
                Modelo.getInstanceModelo().mostrarError("Debes introducir el dni", this.getVenta());
            }
        }
        else{
            if(!(txtFieldDni.getText().isEmpty()||txtFieldNumVuelo.getText().isEmpty())){
                if(Modelo.getInstanceModelo().salirControlBillete(txtFieldDni.getText(),txtFieldNumVuelo.getText())==true){
                    Modelo.getInstanceModelo().mostrarConfirmacion("Usuario registrado correctamente", this.getVenta());
                }
                else{
                    Modelo.getInstanceModelo().mostrarConfirmacion("Estos datos no corresponden con ningún billete", this.getVenta());
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
            etqNumVuelo.setVisible(true);
        txtFieldNumVuelo.setVisible(true);
        }
    }
}
