/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.controlador;

import aeropuerto.elementos.Usuario;
import aeropuerto.elementos.Vuelo;
import gui.modelo.Modelo;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author eliseopitavilarino
 */
public class vComprarControlador extends Controlador implements Initializable {
    
    Vuelo vuelo;
    Usuario usuario;
    
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
    private TextField textFieldDestino;
    @FXML
    private TextField textFieldLlegada;
    @FXML
    private TextField textFieldCodAvion;
    @FXML
    private TextField textFieldPlazasNormales;
    @FXML
    private TextField textFieldPlazasPremium;
    @FXML
    private TextField textFieldAerolinea;
    @FXML
    private TextField textFieldPrecioNormal;
    @FXML
    private TextField textFieldPrecioPremium;
    @FXML
    private TextField txtFieldPrecioTotal;
     @FXML
    private TextField textFieldPrecioMaleta;
    @FXML
    private TextField textFieldPesoMaleta;
    
    //Tabla de pasajeros
    @FXML
    private TableView<Usuario> tablaPasajeros;
    @FXML
    private TableColumn<Usuario, String> columnaDNI;
    @FXML
    private TableColumn<Usuario, String> columnaNombre;
    
    //Añadir pasajero
    @FXML
    private TextField textFieldDNI;
    @FXML
    private Button btnAnhadir;
    
    //Personalizar billete
    @FXML
    private RadioButton radioBtnAcompanhante;
    @FXML
    private ComboBox<Integer> comboBoxNumMaletas;
    @FXML
    private RadioButton radioBtnPremium;
    
    //Pagar
    @FXML
    private GridPane labelPrecio;
    @FXML
    private Button btnPagar;
    @FXML
    private Label etqTitulo;
   
    
    
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        ObservableList<Integer> opcionesMaleta = FXCollections.observableArrayList(0,1,2,3,4);
        comboBoxNumMaletas.setItems(opcionesMaleta);
        
        
    }

    public void setVuelo(Vuelo vuelo) {
        this.vuelo = vuelo;
        Modelo.getInstanceModelo().obtenerDatosAvionVuelo(vuelo);
        datosVuelo();
    }
    public void setUsuario(Usuario usuario){
        this.usuario=usuario;
        columnaDNI.setCellValueFactory(new PropertyValueFactory<>("dni"));
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        ArrayList<Usuario> usu=new ArrayList<>();
        usu.add(usuario);
        ObservableList<Usuario> usuTabla = FXCollections.observableArrayList(usu);
        tablaPasajeros.setItems(usuTabla);
        tablaPasajeros.getSelectionModel().selectFirst();
        usuario.comprarVuelo();
        actualizarDatosUsu();
        actualizarPrecio();
        
        
        
    }
    
    public void datosVuelo(){
        //Ponemos los datos del vuelo
        textFieldNumero.setText(this.vuelo.getNumVuelo());
        textFieldCodAvion.setText(this.vuelo.getAvion());
        textFieldOrigen.setText(this.vuelo.getOrigen());
        textFieldSalida.setText(this.vuelo.getFechasalidaTeo().toString());
        textFieldTerminal.setText(this.vuelo.getTerminal().toString()); 
        textFieldAerolinea.setText(this.vuelo.getAerolinea().getNombre());
        textFieldDestino.setText(this.vuelo.getDestino());
        textFieldLlegada.setText(this.vuelo.getFechallegadaTeo().toString());
        textFieldPlazasNormales.setText(this.vuelo.getPlazasNormal().toString());
        textFieldPlazasPremium.setText(this.vuelo.getPlazasPremium().toString());
        textFieldPrecioNormal.setText(this.vuelo.getPrecioActual().toString());
        textFieldPrecioPremium.setText(vuelo.getPrecioPremium().toString());
        textFieldPrecioMaleta.setText(vuelo.getAerolinea().getPrecioBaseMaleta().toString());
        textFieldPesoMaleta.setText(vuelo.getAerolinea().getPesoBaseMaleta().toString());
        //Calcular plazas disponibles
    }

    @FXML
    private void anhadirPasajero(ActionEvent event) {
        Usuario us=Modelo.getInstanceModelo().obtenerUsuario(textFieldDNI.getText());
        if(us!=null){
            
            tablaPasajeros.getItems().add(us);
            us.comprarVuelo();
            //us.comprarVuelo();
            
        }
        else{
            Modelo.getInstanceModelo().mostrarError("Usuario no registrado.\nDebe registrarse antes de volar con nostros");
        }
    }

    @FXML
    private void cambioUsuario(MouseEvent event) {
        actualizarDatosUsu();
        actualizarPrecio();
    }
    private void actualizarDatosUsu(){
        Usuario us=tablaPasajeros.getSelectionModel().getSelectedItem();
        if(us.getVueloEnEspera().getPremium()){
            radioBtnPremium.setSelected(true);
        }
        else{
            radioBtnPremium.setSelected(false);
        }
        if(us.getVueloEnEspera().getAcompanante()){
            radioBtnAcompanhante.setSelected(true);
        }
        else{
            radioBtnAcompanhante.setSelected(false);
        }
        comboBoxNumMaletas.getSelectionModel().select(us.getVueloEnEspera().getNumMaletas());
        
    }

    @FXML
    private void selectAcompanhante(ActionEvent event) {
        if(radioBtnAcompanhante.isSelected()){
            tablaPasajeros.getSelectionModel().getSelectedItem().getVueloEnEspera().setAcompanante(true);
        }
        else{
            tablaPasajeros.getSelectionModel().getSelectedItem().getVueloEnEspera().setAcompanante(false);
        }
        actualizarPrecio();
    }

    @FXML
    private void selectPremium(ActionEvent event) {
        if(radioBtnPremium.isSelected()){
            tablaPasajeros.getSelectionModel().getSelectedItem().getVueloEnEspera().setPremium(true);
        }
        else{
            tablaPasajeros.getSelectionModel().getSelectedItem().getVueloEnEspera().setPremium(false);
        }
        actualizarPrecio();
    }

    private void cambioNumMaletas(MouseEvent event) {
        
     
        tablaPasajeros.getSelectionModel().getSelectedItem().getVueloEnEspera().setNumMaletas(comboBoxNumMaletas.getSelectionModel().getSelectedItem());
        actualizarPrecio();
       
    }
    private Float actualizarPrecio(){
        Float precio;
        precio=(float)0;
        
        for(Usuario pas: tablaPasajeros.getItems()){
            if(pas.getVueloEnEspera().getPremium()){
                precio+=vuelo.getPrecioPremium();
            }
            else{
                precio+=vuelo.getPrecioActual();
            }
            if(pas.getVueloEnEspera().getAcompanante()) precio+=30;
            precio+=pas.getVueloEnEspera().getNumMaletas()*vuelo.getAerolinea().getPrecioBaseMaleta();
            
            
        }
        precio=(float)(Math.round(precio * 100d) / 100d);
        txtFieldPrecioTotal.setText(precio.toString());
        return precio;
    }

    @FXML
    private void cambioNumMaletas(ActionEvent event) {
    }

    
         
}
