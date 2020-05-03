/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.controlador;

import aeropuerto.elementos.PersonalLaboral;
import gui.modelo.Modelo;
import static java.lang.Float.parseFloat;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
    @FXML
    private Button btnFacturar;

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
    private void facturar(ActionEvent event) {
        
        if(txtFieldDni.getText().isEmpty()|| txtFieldNumVuelo.getText().isEmpty() || txtFieldPeso.getText().isEmpty()){
          Modelo.getInstanceModelo().mostrarError("Debes rellenar todos los campos", this.getVenta());
        }
        else{
            Float peso;
            try{
                Float extra=null;
                peso=parseFloat(txtFieldPeso.getText());
                if(Modelo.getInstanceModelo().facturarMaleta(txtFieldDni.getText(), txtFieldNumVuelo.getText(), peso, extra)==true){
                    if(extra.equals((float)0)){
                        Modelo.getInstanceModelo().mostrarNotificacion("Operación realizada con éxito", this.getVenta());
                    }
                    else{
                        Modelo.getInstanceModelo().mostrarNotificacion("Operación realizada con éxito.\n Debes pagar "+extra+" € extra", this.getVenta());
                    }
                }
                else{
                    Modelo.getInstanceModelo().mostrarError("Datos incorrectos", this.getVenta());
                }
                
            }
            catch(NumberFormatException e){
                Modelo.getInstanceModelo().mostrarError("Valor incorrecto para peso", this.getVenta());
            }
        }
        
        
    }
    
}
