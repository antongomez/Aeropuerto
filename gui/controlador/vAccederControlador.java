package gui.controlador;

import aeropuerto.Usuario;
import gui.modelo.Modelo;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class vAccederControlador extends Controlador implements Initializable {

    @FXML
    private Label labTitulo;
    @FXML
    private Label labSubrayado;
    @FXML
    private Label labErro;
    @FXML
    private Button btnRegistrar;
    @FXML
    private Button btnCancelar;
    @FXML
    private TextField textFieldId;
    @FXML
    private PasswordField textFieldContrasenha;
    @FXML
    private Button btnAcceder;

    /**
     * Inicializa o controlador.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        labErro.setVisible(false);
        btnAcceder.setDisable(true);
    }

    @FXML
    private void accionBtnAcceder(ActionEvent event) {
        //Abrese a venta. Falta a comprobacion das credenciais
        Usuario usuario = Modelo.getInstanceModelo().credencialesCorrectos(textFieldId.getText(), textFieldContrasenha.getText());
        if (usuario != null) {
            ((vPrincipalControlador) loadWindow(getClass().getResource("/gui/vista/vPrincipal.fxml"), "AeroETSE", null)).setUsuario(usuario);
            //Pechase a venta de rexistro
            getVenta().close();
        } else {
            labErro.setVisible(true);
            //Modelo.getInstanceModelo().mostrarError("Usuario o contraseña incorrectos");
        }

    }

    @FXML
    private void accionCancelar(ActionEvent event) {
        if (getVenta() != null) {
            getVenta().close();
        } else {
            System.out.println("Erro, a venta esta a null.");
        }

    }

    @FXML
    private void accionRegistrar(ActionEvent event) {
        loadWindow(getClass().getResource("/gui/vista/vRegistrarse.fxml"), "Registrarse", null);
        if (labErro.isVisible()) {
            labErro.setVisible(false);
        }
    }

    //Key Pressed sobre os txt
    @FXML
    private void cambioCredenciais(KeyEvent event) {
        if (labErro.isVisible()) {
            labErro.setVisible(false);
        }
    }

    //Key released sobre os txt
    @FXML
    private void activarAcceder(KeyEvent event) {
        if (textFieldId.getText().isBlank() || textFieldContrasenha.getText().isBlank()) {
            btnAcceder.setDisable(true);
        } else {
            btnAcceder.setDisable(false);
        }
    }

}
