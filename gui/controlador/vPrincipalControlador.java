package gui.controlador;

import aeropuerto.elementos.Usuario;
import aeropuerto.elementos.Vuelo;
import aeropuerto.util.EstadisticasUsuario;
import aeropuerto.util.Reserva;
import aeropuerto.util.Time;
import gui.modelo.Modelo;
import static gui.modelo.Modelo.getInstanceModelo;
import static java.lang.Integer.parseInt;
import java.net.URL;
import java.security.Timestamp;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SortEvent;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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

    //Estadisticas
    @FXML
    private RadioButton btnMes;
    @FXML
    private RadioButton btnAno;
    @FXML
    private RadioButton btnEstacion;
    @FXML
    private ComboBox<String> comboBoxEstUsu;
    @FXML
    private Label etqAerolineaFav;
    @FXML
    private Label etqDestinoFav;
    @FXML
    private Label etqTarifaFav;

    //Reservas
    @FXML
    private TableView<Reserva> tablaMisReservas;
    @FXML
    private TableColumn<Reserva, Time> colInicioReserva;
    @FXML
    private TableColumn<Reserva, Time> colFinReserva;
    @FXML
    private TableColumn<Reserva, String> colTipoReserva;
    @FXML
    private Button btnCancelarReserva;
    @FXML
    private TableColumn<Reserva, String> colMatricula;
    @FXML
    private Pane panelInfoParking;
    @FXML
    private Label etqPlaza;
    @FXML
    private Label etqPiso;
    @FXML
    private Label etqTerminal;
    
    

    @FXML
    private Label etqFLC;
    @FXML
    private GridPane gridPane;

    @FXML
    private TextArea txtAreaNumViajes;

    @FXML
    private TabPane panelServicios;
    @FXML
    private AnchorPane checkBoxEstacion;
    
    

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
        ObservableList<Vuelo> vuelos = FXCollections.observableArrayList(
                getInstanceModelo().buscarVuelos("", "", "", Time.diaActual(), Time.diaActual()));
        tablaProximosVuelos.setItems(vuelos);

        //Definimos el tipo de dato de cada columna de la TablaMisVuelos
        columnaNumMiVuelo.setCellValueFactory(new PropertyValueFactory<>("numVuelo"));
        columnaOrigenMiVuelo.setCellValueFactory(new PropertyValueFactory<>("origen"));
        columnaDestinoMiVuelo.setCellValueFactory(new PropertyValueFactory<>("destino"));
        columnaSalidaMiVuelo.setCellValueFactory(new PropertyValueFactory<>("fechasalidaReal"));
        columnaLlegadaMiVuelo.setCellValueFactory(new PropertyValueFactory<>("fechallegadaReal"));
        columnaPrecioMiVuelo.setCellValueFactory(new PropertyValueFactory<>("precioActual"));

        //Definimos el tipo de dato de cada columna de la tablaMisReservas
        colInicioReserva.setCellValueFactory(new PropertyValueFactory<>("inicio"));
        colFinReserva.setCellValueFactory(new PropertyValueFactory<>("fin"));
        colTipoReserva.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        panelInfoParking.setVisible(false);
        btnCancelarReserva.setDisable(true);

        //Definimos el panel de estadísticas
        btnMes.setSelected(true);
        ObservableList<String> meses = FXCollections.observableArrayList("Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre");
        comboBoxEstUsu.setItems(meses);
        comboBoxEstUsu.getSelectionModel().selectFirst();

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
        textFieldNombre.setText(usuario.getNombre());
        textFieldAp1.setText(usuario.getAp1());
        textFieldAp2.setText(usuario.getAp2());
        textFieldTlf.setText(usuario.getTelefono().toString());

        ObservableList<String> opcionesPais = FXCollections.observableArrayList("Espanha", "Portugal", "Alemania", "Francia", "Marruecos", "Etiopia", "Estados Unidos", "Colombia", "China", "Rusia", "Australia", "Noruega");
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
        Time salida, llegada;

        if (dataPickSalida.getValue() != null) {
            salida = new Time(dataPickSalida.getValue());
        } else {
            salida = Time.diaActual();
        }
        if (dataPickLlegada.getValue() != null) {
            llegada = new Time(dataPickLlegada.getValue());
        } else {
            llegada = Time.diaActual();
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
        //Creamos unha venta filla da princiapl
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.initOwner(getVenta());
        //Seleccionamos o voo que se vai comprar
        Vuelo vuelo = tablaProximosVuelos.getSelectionModel().getSelectedItem();
        vComprarControlador controlador = ((vComprarControlador) loadWindow(getClass().getResource("/gui/vista/vComprar.fxml"), "AeroETSE", stage));

        controlador.setVuelo(vuelo);
    }

    @FXML
    private void accionBtnDarseBaja(ActionEvent event) {
        if (Modelo.getInstanceModelo().eliminarUsuario(usuario.getDni()) == true) {
            Modelo.getInstanceModelo().mostrarNotificacion("Usuario dado de baja correctamente");
        }
        super.getVenta().close();
    }

    @FXML
    private void accionBtnGuardar(ActionEvent event) {
        if (!textFieldContrasenha.getText().equals(textFieldRepetirContrasenha.getText())) {
            Modelo.getInstanceModelo().mostrarError("Las contraseñas no coinciden!");
        } else {
            Usuario us = new Usuario(usuario.getDni(), textFieldID.getText(), textFieldEmail.getText(), textFieldNombre.getText(),
                    textFieldAp1.getText(), textFieldAp2.getText(), comboBoxPais.getSelectionModel().getSelectedItem(),
                    Integer.parseInt(textFieldTlf.getText()), comboBoxSexo.getSelectionModel().getSelectedItem());
            try {
                if (Modelo.getInstanceModelo().modificarUsuario(us) == true) {  //comprobamos si cambio los datos correctamente
                    Modelo.getInstanceModelo().mostrarNotificacion("Usuario modificado correctamente");
                    //No cambiamos los datos del usuario asociado a esta clase hasta que se cambien en la base
                    usuario.setId(us.getId());
                    usuario.setEmail(us.getEmail());
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
            if (!textFieldContrasenha.getText().isEmpty()) {
                Modelo.getInstanceModelo().modificarContrasenha(usuario.getId(), textFieldContrasenha.getText());
            }
        }
    }

    @FXML
    void accionEstacion(ActionEvent event) {
        //comboBoxEstUsu.setVisible(true);
        ObservableList<String> estaciones = FXCollections.observableArrayList("Primavera", "Verano", "Otoño", "Invierno");
        comboBoxEstUsu.setItems(estaciones);
        comboBoxEstUsu.getSelectionModel().selectFirst();
    }

    @FXML
    void accionMes(ActionEvent event) {
        //comboBoxEstUsu.setVisible(true);
        ObservableList<String> meses = FXCollections.observableArrayList("Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre");
        comboBoxEstUsu.setItems(meses);
        comboBoxEstUsu.getSelectionModel().selectFirst();
    }

    @FXML
    void accionAnho(ActionEvent event) {

        ArrayList<String> anos = new ArrayList<>();
        for (Vuelo v : Modelo.getInstanceModelo().obtenerVuelosUsuario(usuario.getDni())) {
            String v_ano = v.getFechasalidaReal().getAno().toString();
            if (!anos.contains(v_ano)) {
                anos.add(v_ano);
            }
        }
        ObservableList<String> anos1 = FXCollections.observableArrayList(anos);
        comboBoxEstUsu.setItems(anos1);
        if (!comboBoxEstUsu.getItems().isEmpty()) {
            comboBoxEstUsu.getSelectionModel().selectFirst();
        }

    }

    private void mostrarEstadisticas() {
        String tipo;
        Integer num;
        EstadisticasUsuario est;

        if (btnEstacion.isSelected()) {
            tipo = "estacion";
        } else if (btnMes.isSelected()) {
            tipo = "mes";
        } else {
            tipo = "anho";
        }
        if (tipo.equals("anho")) {
            num = parseInt(comboBoxEstUsu.getSelectionModel().getSelectedItem());
        } else {
            num = comboBoxEstUsu.getSelectionModel().getSelectedIndex() + 1;
        }

        est = Modelo.getInstanceModelo().mostrarEstadisticasUsuario(usuario.getDni(), tipo, num);
        txtAreaNumViajes.setText("Has viajado " + est.getVecesViajadas()
                + " veces en " + comboBoxEstUsu.getSelectionModel().getSelectedItem().toLowerCase() + "!");
        etqAerolineaFav.setText("");
        etqDestinoFav.setText("");
        for (String aer : est.getAerolineasFav()) {
            etqAerolineaFav.setText(etqAerolineaFav.getText() + "  " + aer);
        }
        for (String de : est.getDestinosFav()) {
            etqDestinoFav.setText(etqDestinoFav.getText() + "  " + de);
        }

        etqTarifaFav.setText("  " + est.getTarifaFav());

    }

    @FXML
    private void accionAbrirEstadisticas(Event event) {
        mostrarEstadisticas();
    }

    @FXML
    private void accionComboBox(ActionEvent event) {

        if (!comboBoxEstUsu.getSelectionModel().isEmpty()) {
            mostrarEstadisticas();
        }
    }

    @FXML
    private void accionAbrirMisReservas(Event event) {
        ObservableList<Reserva> res = FXCollections.observableArrayList(
                getInstanceModelo().obtenerReservasUsuario(usuario.getDni()));
        tablaMisReservas.setItems(res);
        
    }

    @FXML
    private void accionBtnCancelarReserva(ActionEvent event) {
        Reserva resSelect=tablaMisReservas.getSelectionModel().getSelectedItem();
        Modelo.getInstanceModelo().cancelarReserva(resSelect, usuario.getDni());
        
        btnCancelarReserva.setDisable(true);
        panelInfoParking.setVisible(false);
        ObservableList<Reserva> res = FXCollections.observableArrayList(
               getInstanceModelo().obtenerReservasUsuario(usuario.getDni()));
        tablaMisReservas.setItems(res);
        Modelo.getInstanceModelo().mostrarNotificacion("Su reserva ha sido cancelada con éxito");

    }

    @FXML
    private void seleccionarVuelo(MouseEvent event) {
        this.btnComprar.setDisable(false);
    }

    @FXML
    private void seleccionarReserva(MouseEvent event) {
        
        Reserva resSelect=tablaMisReservas.getSelectionModel().getSelectedItem();//reserva seleccionada
        btnCancelarReserva.setDisable(false);
        if(resSelect.getTipo().equals("Parking")){
           panelInfoParking.setVisible(true);
           etqTerminal.setText(resSelect.getTerminal().toString());
           etqPiso.setText(resSelect.getPiso().toString());
           etqPlaza.setText(resSelect.getNumPlaza().toString());
        }
        else{
            panelInfoParking.setVisible(false);
        }
    }

}
