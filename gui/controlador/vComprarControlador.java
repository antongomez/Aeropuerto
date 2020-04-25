/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.controlador;

import aeropuerto.elementos.Vuelo;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author eliseopitavilarino
 */
public class vComprarControlador extends Controlador implements Initializable {
    
    Vuelo vuelo;
    
    //Campos del vuelo
    @FXML
    private TextField textFieldNumero;
    @FXML
    private TextField textFieldOrigen;
    @FXML
    private TextField textFieldSalida;
    @FXML
    private TextField textFieldTerminal;
    @FXML
    private TextField textFieldCompanhia;
    @FXML
    private TextField textFieldDestino;
    @FXML
    private TextField textFieldLlegada;
    @FXML
    private TextField textFieldCodAvion;
    @FXML
    private TextField textFieldPlazasNormales;
    @FXML
    private TextField textFieldPlazasPremium;
    
    //Tabla de pasajeros
    @FXML
    private TableView<?> tablaPasajeros;
    @FXML
    private TableColumn<?, ?> columnaDNI;
    @FXML
    private TableColumn<?, ?> columnaNombre;
    
    //Añadir pasajero
    @FXML
    private TextField textFieldDNI;
    @FXML
    private Button btnAnhadir;
    
    //Personalizar billete
    @FXML
    private RadioButton radioBtnAcompanhante;
    @FXML
    private ComboBox<?> comboBoxNumMaletas;
    @FXML
    private RadioButton radioBtnPremium;
    
    //Pagar
    @FXML
    private GridPane labelPrecio;
    @FXML
    private Button btnPagar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //Aqui habría que poner al usuario que compra el vuelo en la tabla
        
    }

    public void setVuelo(Vuelo vuelo) {
        this.vuelo = vuelo;
        datosVuelo();
    }
    
    public void datosVuelo(){
        //Ponemos los datos del vuelo
        textFieldNumero.setText(this.vuelo.getNumVuelo());
        textFieldCodAvion.setText(this.vuelo.getAvion().toString());
        textFieldOrigen.setText(this.vuelo.getOrigen());
        textFieldSalida.setText(this.vuelo.getFechasalidaTeo().toString());
        textFieldTerminal.setText(this.vuelo.getTerminal().toString()); 
        //textFieldCompanhia.setText(this.vuelo.getAerolinea());
        textFieldDestino.setText(this.vuelo.getDestino());
        textFieldLlegada.setText(this.vuelo.getFechallegadaTeo().toString());
        //Calcular plazas disponibles
    }
         
}
