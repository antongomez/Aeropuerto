
package gui.controlador;

import aeropuerto.Usuario;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;


public class vPrincipalControlador extends Controlador{

    @FXML
    private AnchorPane panelTitulo;
    @FXML
    private HBox boxTitulo;
    @FXML
    private Label etqAeroEtse;
    @FXML
    private Label etqTitulo;
    @FXML
    private AnchorPane panelLateral;
    @FXML
    private ToggleGroup BtnMenu;
    @FXML
    private ToggleGroup opVerVuelo;
    @FXML
    private ToggleGroup opVerVuelo2;
    @FXML
    private ToggleGroup opVerVuelo21;
    
    private Usuario usuario;//usuario que está usando la ventana

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    
    
}
