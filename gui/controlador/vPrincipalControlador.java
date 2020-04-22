package gui.controlador;

import aeropuerto.elementos.Usuario;
import aeropuerto.elementos.Vuelo;
import aeropuerto.util.Time;
import gui.modelo.Modelo;
import static gui.modelo.Modelo.getInstanceModelo;
import java.net.URL;
import java.security.Timestamp;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class vPrincipalControlador extends Controlador implements Initializable {

    private final static String TITULO_VUELOS = "PRÓXIMOS VUELOS";
    private final static String TITULO_AREAP = "ÁREA PERSONAL";
    private Usuario usuario;//usuario que está usando la ventana

    //Encabezado
    @FXML
    private AnchorPane panelTitulo;
    @FXML
    private HBox boxTitulo;
    @FXML
    private Label etqTitulo;

    //MenuLateral
    @FXML
    private AnchorPane panelLateral;
    @FXML
    private ToggleGroup BtnMenu;

    //Botons do menu
    @FXML
    private ToggleButton btnVuelos;
    @FXML
    private ToggleButton btnAreaP;
    @FXML
    private ToggleButton btnServicios;
    @FXML
    private ToggleButton btnInfo;
    @FXML
    private ToggleButton btnAdmin;

    //Ventas
    @FXML
    private TabPane panelAreaP;
    @FXML
    private AnchorPane panelVuelos;

    //Venta Vuelo
    @FXML
    private TextField txtNumVuelo;
    @FXML
    private TextField txtOrigen;
    @FXML
    private DatePicker dataPickSalida;
    @FXML
    private TextField txtDestino;
    @FXML
    private DatePicker dataPickLlegada;
    @FXML
    private Button btnBuscar;
    @FXML
    private Button btnComprar;

    //Venta Area Personal
    @FXML
    private Button btnDarseBaja;
    @FXML
    private Button btnGuardar;
    @FXML
    private ToggleGroup opVerVuelo;
    
    //Campos modificar datos
    @FXML
    private TextField textFieldID;
    @FXML
    private TextField textFieldEmail;
    @FXML
    private PasswordField textFieldContrasenha;
    @FXML
    private PasswordField textFieldRepetirContrasenha;
    @FXML
    private TextField textFieldAp1;
    @FXML
    private TextField textFieldAp2;
    @FXML
    private ComboBox<String> comboBoxPais;
    @FXML
    private TextField textFieldTlf;
    @FXML
    private ComboBox<String> comboBoxSexo;
    @FXML
    private TextField textFieldDni;
    @FXML
    private TextField textFieldFIngreso;

    //TaboaProximosVoos
    @FXML
    private TableView<Vuelo> tablaProximosVuelos;
    @FXML
    private TableColumn<Vuelo, Integer> columnaNumVuelo;
    @FXML
    private TableColumn<Vuelo, String> columnaOrigen;
    @FXML
    private TableColumn<Vuelo, String> columnaDestino;
    @FXML
    private TableColumn<Vuelo, Timestamp> columnaSalida;
    @FXML
    private TableColumn<Vuelo, Timestamp> columnaLlegada;
    @FXML
    private TableColumn<Vuelo, Float> columnaPrecio;
    @FXML
    private TableColumn<Vuelo, Float> columnaPrecioPremium;
    @FXML
    private TextField textFieldNombre;
    
    //TablaMisVuelos
    @FXML
    private TableView<Vuelo> tablaMisVuelos;
    @FXML
    private TableColumn<Vuelo, Integer> columnaNumMiVuelo;
    @FXML
    private TableColumn<Vuelo, String> columnaOrigenMiVuelo;
    @FXML
    private TableColumn<Vuelo, String> columnaDestinoMiVuelo;
    @FXML
    private TableColumn<Vuelo, Timestamp> columnaSalidaMiVuelo;
    @FXML
    private TableColumn<Vuelo, Timestamp> columnaLlegadaMiVuelo;
    @FXML
    private TableColumn<Vuelo, Float> columnaPrecioMiVuelo;
    
    @FXML
    private Label etqFLC;
    @FXML
    private GridPane gridPane;
    @FXML
    private RadioButton btnMes;
    @FXML
    private RadioButton btnAno;
    @FXML
    private RadioButton btnEstacion;
    @FXML
    private TextArea txtAreaNumViajes;
    @FXML
    private Label etqAerolineaFav;
    @FXML
    private Label etqDestinoFav;
    @FXML
    private Label etqTarifaFav;
    @FXML
    private TabPane panelServicios;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        BtnMenu.selectToggle(btnVuelos);
        panelVuelos.toFront();

        etqTitulo.setText(TITULO_VUELOS);

        btnComprar.setDisable(true);

        //Definimos o tipo de dato de cada columna da TaboaProximosVoos
        columnaNumVuelo.setCellValueFactory(new PropertyValueFactory<>("numVuelo"));
        columnaOrigen.setCellValueFactory(new PropertyValueFactory<>("origen"));
        columnaDestino.setCellValueFactory(new PropertyValueFactory<>("destino"));
        columnaSalida.setCellValueFactory(new PropertyValueFactory<>("fechasalidaReal"));
        columnaLlegada.setCellValueFactory(new PropertyValueFactory<>("fechallegadaReal"));
        columnaPrecio.setCellValueFactory(new PropertyValueFactory<>("precioActual"));
        columnaPrecioPremium.setCellValueFactory(new PropertyValueFactory<>("precioPremium"));
        
        //Definimos el tipo de dato de cada columna de la TablaMisVuelos
        columnaNumMiVuelo.setCellValueFactory(new PropertyValueFactory<>("numVuelo"));
        columnaOrigenMiVuelo.setCellValueFactory(new PropertyValueFactory<>("origen"));
        columnaDestinoMiVuelo.setCellValueFactory(new PropertyValueFactory<>("destino"));
        columnaSalidaMiVuelo.setCellValueFactory(new PropertyValueFactory<>("fechasalidaReal"));
        columnaLlegadaMiVuelo.setCellValueFactory(new PropertyValueFactory<>("fechallegadaReal"));
        columnaPrecioMiVuelo.setCellValueFactory(new PropertyValueFactory<>("precioActual"));
        

        ObservableList<Vuelo> vuelos = FXCollections.observableArrayList(
                getInstanceModelo().buscarVuelos("", "", "", Time.diaActual(), Time.diaActual()));
        tablaProximosVuelos.setItems(vuelos);
      
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @FXML
    private void accionBtnVuelos(ActionEvent event) {
        panelVuelos.toFront();
        etqTitulo.setText(TITULO_VUELOS);
    }

    @FXML
    private void accionBtnAreaP(ActionEvent event) {
        panelAreaP.toFront();
        etqTitulo.setText(TITULO_AREAP);
        
        //Ponemos los datos del usuario
        textFieldDni.setText(usuario.getDni());
        textFieldID.setText(usuario.getId());
        textFieldEmail.setText(usuario.getEmail());
        textFieldContrasenha.setText(usuario.getContrasenha());
        textFieldRepetirContrasenha.setText(usuario.getContrasenha());
        textFieldNombre.setText(usuario.getNombre());
        textFieldAp1.setText(usuario.getAp1());
        textFieldAp2.setText(usuario.getAp2());
        textFieldTlf.setText(usuario.getTelefono().toString());
        
        ObservableList<String> opcionesPais = FXCollections.observableArrayList("Espanha", "Portugal", "Alemania", "Francia", "Marruecos", "Etiopia", "Estados Unidos", "Colombia", "China", "Rusia", "Australia");
        comboBoxPais.setItems(opcionesPais);
        comboBoxPais.getSelectionModel().select(usuario.getPaisProcedencia());
        
        ObservableList<String> opcionesSexo = FXCollections.observableArrayList("Mujer", "Hombre", "Prefiero no contestar");
        comboBoxSexo.setItems(opcionesSexo);
        comboBoxSexo.getSelectionModel().select(usuario.getSexo());
        
        ObservableList<Vuelo> vuelosUsuario = FXCollections.observableArrayList(getInstanceModelo().obtenerVuelosUsuario("48116361Q"));
        tablaMisVuelos.setItems(vuelosUsuario);
    }

    @FXML
    private void accionBtnServicios(ActionEvent event) {
    }

    @FXML
    private void accionBtnInfo(ActionEvent event) {
    }

    @FXML
    private void accionBtnAdmin(ActionEvent event) {
    }

    @FXML
    private void accionBtnBuscar(ActionEvent event) {
        Time salida = null, llegada = null;

        if (dataPickSalida.getValue() != null) {
            salida = new Time(dataPickSalida.getValue());
        }
        if (dataPickLlegada.getValue() != null) {
            llegada = new Time(dataPickLlegada.getValue());
        }

        if (((salida != null) && (!Time.fechaMayorActual(salida)))
                || ((llegada != null) && (!Time.fechaMayorActual(llegada)))) {
            getInstanceModelo().mostrarError("Las fechas de salida y llegada deben ser mayores que la fecha actual");
        } else {

            ObservableList<Vuelo> vuelos = FXCollections.observableArrayList(
                    getInstanceModelo().buscarVuelos(txtNumVuelo.getText(), txtOrigen.getText(),
                            txtDestino.getText(), salida, llegada));

            tablaProximosVuelos.setItems(vuelos);
        }

    }

    @FXML
    private void accionBtnComprar(ActionEvent event) {
    }

    @FXML
    private void accionBtnDarseBaja(ActionEvent event) {
        if(Modelo.getInstanceModelo().eliminarUsuario(usuario.getDni()) == true){
            Modelo.getInstanceModelo().mostrarNotificacion("Usuario dado de baja correctamente");
        }
        super.getVenta().close();
    }

    @FXML
    private void accionBtnGuardar(ActionEvent event) {
       if (!textFieldContrasenha.getText().equals(textFieldRepetirContrasenha.getText())) {
            Modelo.getInstanceModelo().mostrarError("Las contraseñas no coinciden!");
        } else {
           Usuario us = new Usuario(usuario.getDni(), textFieldID.getText(), textFieldEmail.getText(),
                        textFieldContrasenha.getText(), textFieldNombre.getText(),
                        textFieldAp1.getText(), textFieldAp2.getText(), comboBoxPais.getSelectionModel().getSelectedItem(),
                        Integer.parseInt(textFieldTlf.getText()), comboBoxSexo.getSelectionModel().getSelectedItem());
            try {
                if (Modelo.getInstanceModelo().modificarUsuario(us) == true) {  //comprobamos si cambio los datos correctamente
                    Modelo.getInstanceModelo().mostrarNotificacion("Usuario modificado correctamente");
                    //No cambiamos los datos del usuario asociado a esta clase hasta que se cambien en la base
                    usuario.setId(us.getId());
                    usuario.setEmail(us.getEmail());
                    usuario.setContrasenha(us.getContrasenha());
                    usuario.setNombre(us.getNombre());
                    usuario.setAp1(us.getAp1());
                    usuario.setAp2(us.getAp2());
                    usuario.setPaisProcedencia(us.getPaisProcedencia());
                    usuario.setTelefono(us.getTelefono());
                    usuario.setSexo(us.getSexo());
                }
            //Este error no llega aquí, el programa se para
            } catch (NumberFormatException e) {
                Modelo.getInstanceModelo().mostrarError("Número de teléfono incorrecto");
            }
        }
    }

}
