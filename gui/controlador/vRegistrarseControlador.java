package gui.controlador;

import aeropuerto.Usuario;
import gui.modelo.Modelo;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class vRegistrarseControlador extends Controlador implements Initializable {

    @FXML
    private Label labTitulo;
    @FXML
    private Label labDniErro;
    @FXML
    private TextField textFieldDni;
    @FXML
    private TextField textFieldId;
    @FXML
    private TextField textFieldEmail;
    @FXML
    private PasswordField textFieldContrasenha;
    @FXML
    private PasswordField textFieldRepetirContrasenha;
    @FXML
    private TextField textFieldNombre;
    @FXML
    private TextField textFieldAp1;
    @FXML
    private TextField textFieldAp2;
    @FXML
    private ComboBox<String> comboBoxPais;
    @FXML
    private TextField textFieldTelefono;
    @FXML
    private ComboBox<String> comboBoxSexo;
    @FXML
    private Button btnRegistrar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        ObservableList<String> opcionesSexo = FXCollections.observableArrayList("Mujer", "Hombre", "Prefiero no contestar");
        comboBoxSexo.setItems(opcionesSexo);
        comboBoxSexo.getSelectionModel().selectFirst();

        //muestra representativa de países
        ObservableList<String> opcionesPais = FXCollections.observableArrayList("Espanha", "Portugal", "Alemania", "Francia", "Marruecos", "Etiopia", "Estados Unidos", "Colombia", "China", "Rusia", "Australia");
        comboBoxPais.setItems(opcionesPais);
        comboBoxPais.getSelectionModel().selectFirst();

        labDniErro.setVisible(false);

        btnRegistrar.setDisable(true);

    }

    @FXML
    private void accionBtnRegistrarse(ActionEvent event) {

        //Habría que poner una excepcion por si el dni es incorrecto?
        if (!textFieldContrasenha.getText().equals(textFieldRepetirContrasenha.getText())) {
            Modelo.getInstanceModelo().mostrarError("Las contraseñas no coinciden!");
        } else {
            try {
                Usuario us = new Usuario(textFieldDni.getText(), textFieldId.getText(), textFieldEmail.getText(),
                        textFieldContrasenha.getText(), textFieldNombre.getText(),
                        textFieldAp1.getText(), textFieldAp2.getText(), comboBoxPais.getSelectionModel().getSelectedItem(),
                        Integer.parseInt(textFieldTelefono.getText()), comboBoxSexo.getSelectionModel().getSelectedItem());

                if (Modelo.getInstanceModelo().registrarUsuario(us) == true) {  //comprobamos si se registró correctamente
                    Modelo.getInstanceModelo().mostrarNotificacion("Usuario registrado correctamente");

                    //se cierra la ventana de registrar
                    getVenta().close();
                }
            } catch (NumberFormatException e) {
                Modelo.getInstanceModelo().mostrarError("Número de teléfono incorrecto");
            }
        }
    }

    @FXML
    private void comprobarTxtBaleiros(KeyEvent event) {
        if (camposCompletos()) {
            btnRegistrar.setDisable(false);
        } else {
            btnRegistrar.setDisable(true);
        }
    }

    private Boolean camposCompletos() {
        return !(textFieldDni.getText().isEmpty()
                || textFieldId.getText().isEmpty()
                || textFieldEmail.getText().isEmpty()
                || textFieldContrasenha.getText().isEmpty()
                || textFieldRepetirContrasenha.getText().isEmpty()
                || textFieldNombre.getText().isEmpty()
                || textFieldAp1.getText().isEmpty()
                || textFieldAp2.getText().isEmpty()
                || comboBoxPais.getSelectionModel().getSelectedItem().isEmpty() //O pais pode ser null na base de datos
                || comboBoxSexo.getSelectionModel().getSelectedItem().isEmpty());
    }
}
