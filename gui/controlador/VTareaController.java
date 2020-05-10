package gui.controlador;

import aeropuerto.elementos.PersonalLaboral;
import gui.modelo.Modelo;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Esther
 */
public class VTareaController extends Controlador implements Initializable {

    /**
     * Initializes the controller class.
     */
    private PersonalLaboral trabajador;
    @FXML
    private TextField txtFieldTarea;
    @FXML
    private TextArea txtAreaDescripcion;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setTrabajador(PersonalLaboral trab) {
        this.trabajador = trab;
        if (Modelo.getInstanceModelo().obtenerDatosPersLab(trab) == true) {
            txtFieldTarea.setText(trabajador.getLabor());
            txtAreaDescripcion.setText(trabajador.getDescripcionTarea());
        }
    }

}
