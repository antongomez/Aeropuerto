package gui.controlador;

import gui.modelo.Modelo;
import static java.lang.Float.parseFloat;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class VMaletaController extends Controlador implements Initializable {

    @FXML
    private TextField txtFieldNumVuelo;
    @FXML
    private TextField txtFieldDni;
    @FXML
    private TextField txtFieldPeso;
    @FXML
    private Button btnFacturar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void facturar(ActionEvent event) {

        if (txtFieldDni.getText().isEmpty() || txtFieldNumVuelo.getText().isEmpty() || txtFieldPeso.getText().isEmpty()) {
            Modelo.getInstanceModelo().mostrarError("Debes rellenar todos los campos", this.getVenta());
        } else {
            Float peso;
            try {

                peso = parseFloat(txtFieldPeso.getText());
                if (Modelo.getInstanceModelo().facturarMaleta(txtFieldDni.getText(), txtFieldNumVuelo.getText(), peso) == true) {
                    Float extra = Modelo.getInstanceModelo().getPrecioExtraDespuesFacturar(txtFieldDni.getText(), txtFieldNumVuelo.getText(), peso);
                    if (extra == (float) 0) {
                        Modelo.getInstanceModelo().mostrarNotificacion("Operación realizada con éxito", this.getVenta());
                    } else {
                        Modelo.getInstanceModelo().mostrarNotificacion("Operación realizada con éxito.\n Debes pagar " + extra + " € extra", this.getVenta());
                    }
                    txtFieldDni.setText("");
                    txtFieldNumVuelo.setText("");
                    txtFieldPeso.setText("");
                }

            } catch (NumberFormatException e) {
                Modelo.getInstanceModelo().mostrarError("Valor incorrecto para peso", this.getVenta());
            }
        }

    }

}
