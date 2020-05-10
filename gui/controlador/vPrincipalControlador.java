package gui.controlador;

import aeropuerto.elementos.Administrador;
import aeropuerto.elementos.Coche;
import aeropuerto.elementos.Parking;
import aeropuerto.elementos.PersonalLaboral;
import aeropuerto.elementos.Tienda;
import aeropuerto.elementos.Usuario;
import aeropuerto.elementos.Vuelo;
import aeropuerto.util.EstadisticasAerolinea;
import aeropuerto.util.EstadisticasUsuario;
import aeropuerto.util.PorcentajeDisponibilidad;
import aeropuerto.util.Time;
import aeropuerto.util.reservas.ReservaCoche;
import aeropuerto.util.reservas.ReservaParking;
import gui.modelo.Modelo;
import static gui.modelo.Modelo.getInstanceModelo;
import static java.lang.Integer.parseInt;
import java.net.URL;
import java.security.Timestamp;
import java.time.LocalDate;
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
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
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
    private final static String TITULO_SERV = "SERVICIOS";
    private final static String TITULO_INFO = "INFORMACIÓN";
    private final static String TITULO_PERSLAB = "PERSONAL LABORAL";

    private final static String TXT_BTN_ADMIN = "Administrador";
    private final static String TXT_BTN_PL = "Personal";

    private final static String TEXTO_ERROR_FECHA = "La fecha de salida debe ser mayor que la fecha actual";
    private final static String TEXTO_INFO_PARKING_COCHES = "Introduce los datos de tu vuelo o de tu estancia";
    private final static String TEXTO_ERROR_PARKING_COCHES = "La fecha de regreso debe ser mayor o igual que la de llegada";
    private final static String TEXTO_ERROR_COCHES_NUMERO = "El numero de plazas no es válido";

    private final static Float PRECIO_DIA_PARKING = 10.0f;

    private Usuario usuario;//usuario que está usando la ventana
    private Parking parking;//parking a reservar
    private ObservableList<Vuelo> vuelosFuturos;//Vuelos usuario

    //Encabezado
    @FXML
    private AnchorPane panelTitulo;
    @FXML
    private HBox boxTitulo;
    @FXML
    private Label etqTitulo;
    @FXML
    private Label etqFLC;

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
    private ToggleButton btnPersonal;

    //Ventas
    @FXML
    private TabPane panelAreaP;
    @FXML
    private AnchorPane panelVuelos;
    @FXML
    private AnchorPane panelAdministrador;

    //Proximos vuelos
    @FXML
    private TextField txtNumVuelo;
    @FXML
    private TextField txtOrigen;
    @FXML
    private DatePicker dataPickSalida;
    @FXML
    private TextField txtDestino;
    @FXML
    private Button btnBuscar;
    @FXML
    private Button btnComprar;
    @FXML
    private Label etqInfoPV;

    //Venta Area Personal
    @FXML
    private Button btnDarseBaja;
    @FXML
    private Button btnGuardar;
    @FXML
    private ToggleGroup opVerVuelo;

    //Campos modificar datos
    @FXML
    private AnchorPane paneModificarDatos;
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
    @FXML
    private GridPane gridPaneModificarDatos;
    @FXML
    private Label etqFechaIngreso;
    @FXML
    private Pane paneCurriculum;
    @FXML
    private TextArea txtAreaCurriculum;

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
    private TableColumn<Vuelo, Time> columnaSalidaMiVuelo;
    @FXML
    private TableColumn<Vuelo, Time> columnaLlegadaMiVuelo;
    @FXML
    private TableColumn<Vuelo, Float> columnaPrecioMiVuelo;

    //Tabla Futuros Vuelos
    @FXML
    private TableView<Vuelo> tablaFuturosVuelos;
    @FXML
    private TableColumn<Vuelo, Integer> columnaNumeroFV;
    @FXML
    private TableColumn<Vuelo, String> columnaOrigenFV;
    @FXML
    private TableColumn<Vuelo, String> columnaDestinoFV;
    @FXML
    private TableColumn<Vuelo, Time> columnaSalidaFV;
    @FXML
    private TableColumn<Vuelo, Time> columnaLlegadaFV;
    @FXML
    private TableColumn<Vuelo, Float> columnaPrecioFV;

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
    private Label etqAerolineaFavEspecifica;
    @FXML
    private Label etqDestinoFavEspecifico;
    @FXML
    private Label etqTarifaFavEspecifico;
    @FXML
    private TextArea txtAreaNumViajes;
    @FXML
    private Label etqInfoEstadisticasEspecificas;
    @FXML
    private Label etqInfoEstadisticasGlobales;
    @FXML
    private Label etqAerolineaFavGlobal;
    @FXML
    private Label etqDestinoFavGlobal;
    @FXML
    private Label etqTarifaFavGlobal;
    @FXML
    private Label etqInfoEstadisticas1;
    @FXML
    private Label etqInfoEstadisticas2;

    //Reservas
    @FXML
    private TableView<ReservaParking> tablaReservasParking;
    @FXML
    private TableColumn<ReservaParking, Time> colInicioReservaParking;
    @FXML
    private TableColumn<ReservaParking, Time> colFinReservaParking;
    @FXML
    private TableColumn<ReservaParking, String> colMatriculaParking;
    @FXML
    private TableColumn<ReservaParking, Integer> colTerminalParking;
    @FXML
    private TableColumn<ReservaParking, Integer> colPisoParking;
    @FXML
    private TableColumn<ReservaParking, Integer> colPlazaParking;
    @FXML
    private Button btnCancelarReservaParking;

    @FXML
    private TableView<ReservaCoche> tablaReservasCoche;
    @FXML
    private TableColumn<ReservaCoche, Time> colInicioReservaCoche;
    @FXML
    private TableColumn<ReservaCoche, Time> colFinReservaCoche;
    @FXML
    private TableColumn<ReservaCoche, String> colMatriculaCoche;
    @FXML
    private Button btnCancelarReservaCoche;

    //Servicios
    @FXML
    private TabPane panelServicios;
    @FXML
    private AnchorPane checkBoxEstacion;
    @FXML
    private Button btnBuscarParking;
    @FXML
    private Button btnReservarParking;
    @FXML
    private ComboBox<Integer> cboxTerminalParking;
    @FXML
    private DatePicker dataFLlegadaParking;
    @FXML
    private DatePicker dataFRetornoParking;
    @FXML
    private Label etqInfoParking;
    @FXML
    private TextField txtPlazasParking;
    @FXML
    private TextField txtPrecioParking;
    @FXML
    private TextField txtMatriculaParking;
    @FXML
    private Label etqErroMatricula;
    @FXML
    private ToggleGroup infoVuelos;
    @FXML
    private HBox hBoxInfoDisponhibilidadeParking;
    @FXML
    private Label etqInfoDisponhibilidadeParking;

    //Informacion
    @FXML
    private TableView<Vuelo> tablaSalidasLlegadas;
    @FXML
    private TableColumn<Vuelo, String> colVueloSL;
    @FXML
    private TableColumn<Vuelo, String> colOrigenSL;
    @FXML
    private TableColumn<Vuelo, String> colDestinoSL;
    @FXML
    private TableColumn<Vuelo, Integer> colTerminalSL;
    @FXML
    private TableColumn<Vuelo, String> colPuertaSL;
    @FXML
    private TableColumn<Vuelo, Time> colFechaSL;
    @FXML
    private TableColumn<Vuelo, String> colEstadoSL;
    @FXML
    private TableColumn<Vuelo, String> colTRestanteSL;
    @FXML
    private TabPane panelInfo;
    @FXML
    private RadioButton radioBtnSalidas;
    @FXML
    private RadioButton radioBtnLlegadas;
    @FXML
    private Button btnActualizarSL;

    //Reservar Coches
    @FXML
    private DatePicker dataFLlegadaCoches;
    @FXML
    private DatePicker dataFRetornoCoches;
    @FXML
    private TextField textNPrazas;
    @FXML
    private Label etqInfoCoches;
    @FXML
    private Button btnBuscarCoches;
    @FXML
    private TableView<Coche> taboaReservarCoche;
    @FXML
    private TableColumn<Coche, String> columnaMatriculaCoche;
    @FXML
    private TableColumn<Coche, String> columnaModeloCoche;
    @FXML
    private TableColumn<Coche, Integer> columnaPlazasCoche;
    @FXML
    private TableColumn<Coche, Integer> columnaPuertasCoche;
    @FXML
    private TableColumn<Coche, String> columnaCombustibleCoche;
    @FXML
    private TableColumn<Coche, Integer> columnaCaballosCoche;
    @FXML
    private TableColumn<Coche, Float> columnaPrecioDiaCoche;
    @FXML
    private TextField txtPrecioTotalCoches;
    @FXML
    private Button btnReservarCoches;

    //Tendas
    @FXML
    private TextField txtNomeTendas;
    @FXML
    private ComboBox<String> cBoxTerminalTendas;
    @FXML
    private ComboBox<String> cBoxTipoTendas;
    @FXML
    private Button btnBuscarTendas;
    @FXML
    private TableView<Tienda> taboaTendas;
    @FXML
    private TableColumn<Tienda, String> columnaNomeTendas;
    @FXML
    private TableColumn<Tienda, String> columnaTipoTendas;
    @FXML
    private TableColumn<Tienda, Integer> columnaTerminalTendas;

    //Estadisticas aerolinea
    @FXML
    private ComboBox<String> comboBoxEstAer;
    @FXML
    private TextField txtFieldVuelosRetraso;
    @FXML
    private TextField txtFieldTiempoRetraso;
    @FXML
    private TextField txtFieldOcNormal;
    @FXML
    private TextField txtFieldOcPremium;
    @FXML
    private TextField txtFieldPlazasAvion;
    @FXML
    private TextField txtFieldAnoAvion;
    @FXML
    private TextField txtFieldNacionalidad;
    @FXML
    private TextField txtFieldPrecioMaleta;
    @FXML
    private TextField txtFieldPesoMaleta;
    @FXML
    private TextField txtFieldPaisSede;
    @FXML
    //Personal laboral
    private Button btnControl;
    @FXML
    private Button btnEntrarSalir;
    @FXML
    private Button btnTarea;
    @FXML
    private Button btnCoches;
    @FXML
    private Button btnMaletas;
    @FXML
    private Button btnHistorial;
    @FXML
    private AnchorPane panelPersLab;

    @FXML
    private Button btnDevolver;
    @FXML
    private Button btnCancelar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnComprar.setDisable(true);
        btnComprar.toFront();
        btnDevolver.setDisable(true);
        btnDevolver.setVisible(false);

        //Definimos o tipo de dato de cada columna da TaboaProximosVoos
        columnaNumVuelo.setCellValueFactory(new PropertyValueFactory<>("numVuelo"));
        columnaOrigen.setCellValueFactory(new PropertyValueFactory<>("origen"));
        columnaDestino.setCellValueFactory(new PropertyValueFactory<>("destino"));
        columnaSalida.setCellValueFactory(new PropertyValueFactory<>("fechasalidaReal"));
        columnaLlegada.setCellValueFactory(new PropertyValueFactory<>("fechallegadaReal"));
        columnaPrecio.setCellValueFactory(new PropertyValueFactory<>("precioActual"));
        columnaPrecioPremium.setCellValueFactory(new PropertyValueFactory<>("precioPremium"));
        ObservableList<Vuelo> vuelos = FXCollections.observableArrayList(
                getInstanceModelo().buscarVuelos("", "", "", Time.diaActual()));
        tablaProximosVuelos.setItems(vuelos);
        //A fila ponse en vermello en caso de estar cancelado o vuelo
        tablaProximosVuelos.setRowFactory(row -> new TableRow<Vuelo>() {
            @Override
            public void updateItem(Vuelo item, boolean empty) {
                super.updateItem(item, empty);

                if ((item != null) && (!empty)) {
                    if (item.getCancelado()) {
                        setStyle("-fx-background-color: #b80c00;");
                    } else if (item.getEnCurso()) {
                        setStyle("-fx-background-color: #11ad00;");
                    } else {
                        setStyle("");
                    }
                }
            }
        });
        //Etiqueta que informa dos erros
        etqInfoPV.setVisible(false);

        //Engadimos a data actual nos datePickers
        dataPickSalida.setValue(LocalDate.now());

        //Area Persoal Modificar Datos
        ObservableList<String> opcionesPais = FXCollections.observableArrayList("Espanha", "Portugal", "Alemania", "Francia", "Marruecos", "Etiopia", "Estados Unidos", "Colombia", "China", "Rusia", "Australia", "Noruega", "Galicia");
        comboBoxPais.setItems(opcionesPais);
        ObservableList<String> opcionesSexo = FXCollections.observableArrayList("Mujer", "Hombre", "Prefiero no contestar");
        comboBoxSexo.setItems(opcionesSexo);

        //Definimos el tipo de dato de cada columna de la TablaMisVuelos
        columnaNumMiVuelo.setCellValueFactory(new PropertyValueFactory<>("numVuelo"));
        columnaOrigenMiVuelo.setCellValueFactory(new PropertyValueFactory<>("origen"));
        columnaDestinoMiVuelo.setCellValueFactory(new PropertyValueFactory<>("destino"));
        columnaSalidaMiVuelo.setCellValueFactory(new PropertyValueFactory<>("fechasalidaReal"));
        columnaLlegadaMiVuelo.setCellValueFactory(new PropertyValueFactory<>("fechallegadaReal"));
        columnaPrecioMiVuelo.setCellValueFactory(new PropertyValueFactory<>("precioActual"));
        //A fila ponse en vermello en caso de estar cancelado o vuelo
        tablaMisVuelos.setRowFactory(row -> new TableRow<Vuelo>() {
            @Override
            public void updateItem(Vuelo item, boolean empty) {
                super.updateItem(item, empty);

                if ((item != null) && (!empty)) {
                    if (item.getCancelado()) {
                        setStyle("-fx-background-color: #b80c00");
                    } else {
                        setStyle("");
                    }
                }
            }
        });

        columnaNumeroFV.setCellValueFactory(new PropertyValueFactory<>("numVuelo"));
        columnaOrigenFV.setCellValueFactory(new PropertyValueFactory<>("origen"));
        columnaDestinoFV.setCellValueFactory(new PropertyValueFactory<>("destino"));
        columnaSalidaFV.setCellValueFactory(new PropertyValueFactory<>("fechasalidaReal"));
        columnaLlegadaFV.setCellValueFactory(new PropertyValueFactory<>("fechallegadaReal"));
        columnaPrecioFV.setCellValueFactory(new PropertyValueFactory<>("precioActual"));
        //A fila ponse en vermello en caso de estar cancelado o vuelo
        tablaFuturosVuelos.setRowFactory(row -> new TableRow<Vuelo>() {
            @Override
            public void updateItem(Vuelo item, boolean empty) {
                super.updateItem(item, empty);

                if ((item != null) && (!empty)) {
                    if (item.getCancelado()) {
                        setStyle("-fx-background-color: #b80c00;");
                    } else if (item.getEnCurso()) {
                        setStyle("-fx-background-color: #11ad00;");
                    } else {
                        setStyle("");
                    }
                }
            }
        });

        //Definimos el tipo de dato de cada columna de la tablaReservasParking y tablaReservasCoche
        colInicioReservaParking.setCellValueFactory(new PropertyValueFactory<>("iniciodate"));
        colFinReservaParking.setCellValueFactory(new PropertyValueFactory<>("findate"));
        colMatriculaParking.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        colTerminalParking.setCellValueFactory(new PropertyValueFactory<>("terminal"));
        colPisoParking.setCellValueFactory(new PropertyValueFactory<>("piso"));
        colPlazaParking.setCellValueFactory(new PropertyValueFactory<>("numPlaza"));
        tablaReservasParking.setRowFactory(row -> new TableRow<ReservaParking>() {
            @Override
            public void updateItem(ReservaParking item, boolean empty) {
                super.updateItem(item, empty);

                if ((item != null) && (!empty)) {
                    if (item.getEnCurso()) {
                        setStyle("-fx-background-color: #11ad00;");
                    } else {
                        setStyle("");
                    }
                }
            }
        });

        colInicioReservaCoche.setCellValueFactory(new PropertyValueFactory<>("iniciodate"));
        colFinReservaCoche.setCellValueFactory(new PropertyValueFactory<>("findate"));
        colMatriculaCoche.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        tablaReservasCoche.setRowFactory(row -> new TableRow<ReservaCoche>() {
            @Override
            public void updateItem(ReservaCoche item, boolean empty) {
                super.updateItem(item, empty);

                if ((item != null) && (!empty)) {
                    if (item.getEnCurso()) {
                        setStyle("-fx-background-color: #11ad00;");
                    } if (item.getDevRetraso()) {
                        setStyle("-fx-background-color: #b80c00;");
                    } else {
                        setStyle("");
                    }
                }
            }
        });

        btnBuscarParking.setDisable(true);
        btnBuscarCoches.setDisable(true);
        btnReservarParking.setDisable(true);
        btnReservarCoches.setDisable(true);
        hBoxInfoDisponhibilidadeParking.setVisible(false);
        btnCancelarReservaParking.setDisable(true);
        btnCancelarReservaCoche.setDisable(true);

        //Estadisticas
        opVerVuelo.selectToggle(btnEstacion);
        ObservableList<String> estaciones = FXCollections.observableArrayList("Primavera", "Verano", "Otoño", "Invierno");
        comboBoxEstUsu.setItems(estaciones);
        comboBoxEstUsu.getSelectionModel().selectFirst();

        //Definimos tabla salidas-llegadas
        colVueloSL.setCellValueFactory(new PropertyValueFactory<>("numVuelo"));
        colOrigenSL.setCellValueFactory(new PropertyValueFactory<>("origen"));
        colDestinoSL.setCellValueFactory(new PropertyValueFactory<>("destino"));
        colTerminalSL.setCellValueFactory(new PropertyValueFactory<>("terminal"));
        colPuertaSL.setCellValueFactory(new PropertyValueFactory<>("puerta"));
        colFechaSL.setCellValueFactory(new PropertyValueFactory<>("fechasalidaReal"));
        colEstadoSL.setCellValueFactory(new PropertyValueFactory<>("estado"));
        colTRestanteSL.setCellValueFactory(new PropertyValueFactory<>("tiempoRestante"));

        //Lista de terminais para mostrar no comboBox da venta reservar parking
        ObservableList<Integer> terminais = FXCollections.observableArrayList(getInstanceModelo().buscarTerminais());
        cboxTerminalParking.setItems(terminais);

        //Definimos combobox estadísticas aerolíneas
        ObservableList<String> aerolineas = FXCollections.observableArrayList(getInstanceModelo().obtenerAerolineasConVuelos());
        comboBoxEstAer.setItems(aerolineas);
        comboBoxEstAer.getSelectionModel().selectFirst();

        //Servicios, taboa Coches, etqInfo
        columnaMatriculaCoche.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        columnaModeloCoche.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        columnaPlazasCoche.setCellValueFactory(new PropertyValueFactory<>("nPrazas"));
        columnaPuertasCoche.setCellValueFactory(new PropertyValueFactory<>("nPuertas"));
        columnaCombustibleCoche.setCellValueFactory(new PropertyValueFactory<>("tipoCombustible"));
        columnaCaballosCoche.setCellValueFactory(new PropertyValueFactory<>("caballos"));
        columnaPrecioDiaCoche.setCellValueFactory(new PropertyValueFactory<>("precioDia"));

        //Tendas, taboa, comboBox terminais, comboBox tipoVentas
        columnaNomeTendas.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnaTipoTendas.setCellValueFactory(new PropertyValueFactory<>("tipoVentas"));
        columnaTerminalTendas.setCellValueFactory(new PropertyValueFactory<>("terminal"));

        ObservableList<String> terminais2 = FXCollections.observableArrayList("Todas");
        //Usamos a lista de terminais obtida de antes
        terminais.forEach((enteiro) -> {
            terminais2.add(enteiro.toString());
        });
        cBoxTerminalTendas.setItems(terminais2);
        cBoxTerminalTendas.getSelectionModel().selectFirst();

        ObservableList<String> tipoVentas = FXCollections.observableArrayList("Todos");
        tipoVentas.addAll(getInstanceModelo().obterTipoVentas());
        cBoxTipoTendas.setItems(tipoVentas);
        cBoxTipoTendas.getSelectionModel().selectFirst();

        ObservableList<Tienda> tiendas = FXCollections.observableList(getInstanceModelo().buscarTiendas(txtNomeTendas.getText(),
                cBoxTipoTendas.getSelectionModel().getSelectedItem(),
                cBoxTerminalTendas.getSelectionModel().getSelectedItem()));
        taboaTendas.setItems(tiendas);

        //Informacion
        infoVuelos.selectToggle(radioBtnSalidas);

        //Configuracion inicial
        etqTitulo.setText(TITULO_VUELOS);
        BtnMenu.selectToggle(btnVuelos);
        panelVuelos.toFront();
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;

        if (usuario instanceof Administrador) {
            btnPersonal.setText(TXT_BTN_ADMIN);
        } else if (usuario instanceof PersonalLaboral) {
            btnPersonal.setText(TXT_BTN_PL);
        } else {
            btnPersonal.setVisible(false);
        }
    }

    /*
    
    
        Compra de vuelos
    
     */
    @FXML
    private void accionBtnVuelos(ActionEvent event) {
        panelVuelos.toFront();
        etqTitulo.setText(TITULO_VUELOS);

        btnComprar.setDisable(true);
        btnComprar.toFront();
    }

    @FXML
    private void comprobarData(Event event) {
        Time salida = new Time(dataPickSalida.getValue());

        if (!Time.fechaMayorIgualActual(salida)) {
            btnBuscar.setDisable(true);
            etqInfoPV.setText(TEXTO_ERROR_FECHA);
            etqInfoPV.setVisible(true);
        } else {
            btnBuscar.setDisable(false);
            etqInfoPV.setVisible(false);
        }
    }

    @FXML
    private void accionBtnBuscar(ActionEvent event) {
        Time salida;

        if (dataPickSalida.getValue() != null) {
            salida = new Time(dataPickSalida.getValue());
        } else {
            salida = Time.diaActual();
        }

        if ((salida != null) && (!Time.fechaMayorIgualActual(salida))) {
            getInstanceModelo().mostrarError(TEXTO_ERROR_FECHA, getVenta());
        } else {

            ObservableList<Vuelo> vuelos = FXCollections.observableArrayList(
                    getInstanceModelo().buscarVuelos(txtNumVuelo.getText(), txtOrigen.getText(),
                            txtDestino.getText(), salida));
            tablaProximosVuelos.setItems(vuelos);
        }

    }

    @FXML
    private void seleccionarVuelo(MouseEvent event) {
        Vuelo vueloSelect = tablaProximosVuelos.getSelectionModel().getSelectedItem();
        if ((vueloSelect != null) && (!vueloSelect.getCancelado())) {
            if (Modelo.getInstanceModelo().obtenerVuelosUsuario(usuario.getDni()).contains(vueloSelect)) {
                btnComprar.setDisable(true);
                btnComprar.setVisible(false);
                btnDevolver.toFront();
                btnDevolver.setVisible(true);
                btnDevolver.setDisable(false);
            } else if (vueloSelect.getEnCurso()) {
                btnComprar.setDisable(true);
                btnComprar.toFront();
            } else {
                btnComprar.setDisable(false);
                btnComprar.setVisible(true);
                btnComprar.toFront();
                btnDevolver.setVisible(false);
                btnDevolver.setDisable(true);
            }
        }
    }

    @FXML
    private void accionBtnComprar(ActionEvent event) {
        //Creamos unha venta filla da princiapal
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.initOwner(getVenta());
        //Seleccionamos o voo que se vai comprar
        Vuelo vuelo = tablaProximosVuelos.getSelectionModel().getSelectedItem();
        vComprarControlador controlador = ((vComprarControlador) loadWindow(getClass().getResource("/gui/vista/vComprar.fxml"), "Folgoso do Courel", stage));
        controlador.inicializarVComprar(vuelo, usuario);
        btnComprar.setDisable(true);

    }

    /*


        AREA PERSONAL


     */
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
        textFieldContrasenha.setText("");
        textFieldRepetirContrasenha.setText("");
        textFieldTlf.setText(usuario.getTelefono().toString());
        if (usuario instanceof Administrador) {
            textFieldFIngreso.setText(((Administrador) usuario).getFechaInicio().toString());
            txtAreaCurriculum.setText(((Administrador) usuario).getCurriculum());
        } else if (usuario instanceof PersonalLaboral) {
            textFieldFIngreso.setText(((PersonalLaboral) usuario).getFechaInicio().toString());
            paneCurriculum.setVisible(false);
        } else {
            textFieldFIngreso.setVisible(false);
            etqFechaIngreso.setVisible(false);
            paneCurriculum.setVisible(false);
        }
        comboBoxPais.getSelectionModel().select(usuario.getPaisProcedencia());
        comboBoxSexo.getSelectionModel().select(usuario.getSexo());

        //MisVuelos
        ObservableList<Vuelo> vuelosRealizados = FXCollections.observableArrayList(getInstanceModelo()
                .obtenerVuelosRealizadosUsuario(usuario.getDni()));
        tablaMisVuelos.setItems(vuelosRealizados);
        vuelosFuturos = FXCollections.observableArrayList(getInstanceModelo()
                .obtenerVuelosFuturosUsuario(usuario.getDni()));
        tablaFuturosVuelos.setItems(vuelosFuturos);

        //RESERVAS
        ObservableList<ReservaParking> resParking = FXCollections.observableArrayList(
                getInstanceModelo().obterResParkingUsuario(usuario.getDni()));
        tablaReservasParking.setItems(resParking);

        ObservableList<ReservaCoche> resCoche = FXCollections.observableArrayList(
                getInstanceModelo().obterResCocheUsuario(usuario.getDni()));
        tablaReservasCoche.setItems(resCoche);

        //Estadisticas
        mostrarEstadisticas();
    }

    //MODIFICAR DATOS
    @FXML
    private void accionAbrirModificarDatos(Event event) {
        textFieldContrasenha.setText("");
        textFieldRepetirContrasenha.setText("");
    }

    @FXML
    private void accionBtnDarseBaja(ActionEvent event) {
        if (getInstanceModelo().mostrarConfirmacion("¿Estás seguro de que quieres darte de baja?", getVenta()) == true) {
            if (Modelo.getInstanceModelo().eliminarUsuario(usuario.getDni()) == true) {
                super.getVenta().close();
                Modelo.getInstanceModelo().mostrarNotificacion("Usuario dado de baja correctamente", getVenta());

            }
        } else {
            Modelo.getInstanceModelo().mostrarNotificacion("Operación cancelada.", getVenta());
        }

    }

    @FXML
    private void accionBtnGuardar(ActionEvent event) {

        if (Modelo.getInstanceModelo().mostrarConfirmacion("¿Estás seguro de que quieres modificar tus datos?", getVenta())) {
            if (!textFieldContrasenha.getText().equals(textFieldRepetirContrasenha.getText())) {
                Modelo.getInstanceModelo().mostrarError("Las contraseñas no coinciden!", getVenta());
            } else {
                Usuario us;
                try {

                    if (usuario instanceof Administrador) {
                        us = new Administrador(usuario.getDni(), textFieldID.getText(), textFieldEmail.getText(), textFieldNombre.getText(),
                                textFieldAp1.getText(), textFieldAp2.getText(), comboBoxPais.getSelectionModel().getSelectedItem(),
                                Integer.parseInt(textFieldTlf.getText()), comboBoxSexo.getSelectionModel().getSelectedItem(), txtAreaCurriculum.getText());
                    } else {
                        us = new Usuario(usuario.getDni(), textFieldID.getText(), textFieldEmail.getText(), textFieldNombre.getText(),
                                textFieldAp1.getText(), textFieldAp2.getText(), comboBoxPais.getSelectionModel().getSelectedItem(),
                                Integer.parseInt(textFieldTlf.getText()), comboBoxSexo.getSelectionModel().getSelectedItem());
                    }

                    if (Modelo.getInstanceModelo().modificarUsuario(us) == true) {  //comprobamos si cambio los datos correctamente
                        Modelo.getInstanceModelo().mostrarNotificacion("Usuario modificado correctamente", getVenta());
                        //No cambiamos los datos del usuario asociado a esta clase hasta que se cambien en la base
                        usuario.setId(us.getId());
                        usuario.setEmail(us.getEmail());
                        usuario.setNombre(us.getNombre());
                        usuario.setAp1(us.getAp1());
                        usuario.setAp2(us.getAp2());
                        usuario.setPaisProcedencia(us.getPaisProcedencia());
                        usuario.setTelefono(us.getTelefono());
                        usuario.setSexo(us.getSexo());
                        if (usuario instanceof Administrador) {
                            ((Administrador) usuario).setCurriculum(((Administrador) us).getCurriculum());
                        }
                        if (!textFieldContrasenha.getText().isEmpty()) {
                            Modelo.getInstanceModelo().modificarContrasenha(usuario.getId(), textFieldContrasenha.getText());
                        }
                    }

                } catch (NumberFormatException e) {
                    Modelo.getInstanceModelo().mostrarError("Número de teléfono incorrecto", getVenta());
                }
            }

        } else {
            Modelo.getInstanceModelo().mostrarNotificacion("Operación cancelada.", getVenta());
        }

        textFieldContrasenha.setText("");
        textFieldRepetirContrasenha.setText("");
    }

    /*Para comprobar que todos los campos estén cubiertos*/
    @FXML
    private void comprobarTxtBaleiros(KeyEvent event) {
        if (camposCompletos()) {
            btnGuardar.setDisable(false);
        } else {
            btnGuardar.setDisable(true);
        }
    }

    private Boolean camposCompletos() {
        return !(textFieldID.getText().isEmpty()
                || textFieldEmail.getText().isEmpty()
                || textFieldNombre.getText().isEmpty()
                || textFieldAp1.getText().isEmpty()
                || textFieldAp2.getText().isEmpty()
                || comboBoxPais.getSelectionModel().getSelectedItem().isEmpty()
                || comboBoxSexo.getSelectionModel().getSelectedItem().isEmpty()
                || textFieldTlf.getText().isEmpty());
    }

    //MisVuelos
    @FXML
    private void accionAbrirMisVuelos(Event event) {
        ObservableList<Vuelo> vuelosRealizados
                = FXCollections.observableArrayList(getInstanceModelo().obtenerVuelosRealizadosUsuario(usuario.getDni()));
        tablaMisVuelos.setItems(vuelosRealizados);
        vuelosFuturos
                = FXCollections.observableArrayList(getInstanceModelo().obtenerVuelosFuturosUsuario(usuario.getDni()));
        tablaFuturosVuelos.setItems(vuelosFuturos);
    }

    @FXML
    private void cancelarViaje(ActionEvent event) {
        if (Modelo.getInstanceModelo().mostrarConfirmacion("¿Estás seguro de que quieres devolver el vuelo?", getVenta())) {
            Vuelo vueloSelect = tablaFuturosVuelos.getSelectionModel().getSelectedItem();
            if (vueloSelect != null) {
                if (Time.obtenerDias(Time.diaActual().toLocalDate(), vueloSelect.getFechasalidaReal().toLocalDate()) >= 15
                        || vueloSelect.getCancelado()) {
                    if (Modelo.getInstanceModelo().devolverBillete(vueloSelect.getNumVuelo(), usuario.getDni())) {
                        vuelosFuturos.remove(vueloSelect);
                        Modelo.getInstanceModelo().mostrarNotificacion("El billete se ha devuelto con éxito", getVenta());
                    } else {
                        Modelo.getInstanceModelo().mostrarError("No se ha podido completar la devolución del billete. Vuelva a intentarlo", getVenta());
                    }
                } else {
                    Modelo.getInstanceModelo().mostrarNotificacion("Nuestra política de devolución no "
                            + "nos permite realizar el reembolso de "
                            + "un billete en un plazo inferior a 15 días de la salida del vuelo. "
                            + "Para más información contacte con " + getInstanceModelo().obtenerDatosAerolinea(vueloSelect.getNumVuelo()).getNombre() + ", la aerolínea encargada "
                            + "de operar este vuelo.", getVenta());
                }
            }
        } else {
            Modelo.getInstanceModelo().mostrarNotificacion("Operación cancelada.", getVenta());
        }
    }

    @FXML
    private void seleccionarMiVuelo(MouseEvent event) {
        Vuelo vueloSelect = tablaFuturosVuelos.getSelectionModel().getSelectedItem();
        if (vueloSelect != null) {
            if (Modelo.getInstanceModelo().vueloRealizado(vueloSelect.getNumVuelo())) {
                btnCancelar.setDisable(true);
            } else {
                btnCancelar.setDisable(false);
            }
        }
    }

    //Estadisticas
    @FXML
    private void accionAbrirEstadisticas(Event event) {
        etqInfoEstadisticas2.setVisible(false);
        //Ao entrar poñemos as estatisticas dos anos e seleccionamos o mais recente

        mostrarEstadisticas();
        //Estadisticas globais
        EstadisticasUsuario estadisticasUsuario = getInstanceModelo()
                .obtenerEstadisticasGlobalesUsuario(usuario.getDni());

        //Aerolinea
        if (estadisticasUsuario.getAerolineasFav().isEmpty()) {
            etqAerolineaFavGlobal.setText(" - ");
        } else {
            String aerolineasFav = estadisticasUsuario.getAerolineasFav().get(0);
            for (int i = 1; i < estadisticasUsuario.getAerolineasFav().size(); i++) {
                aerolineasFav += ", " + estadisticasUsuario.getAerolineasFav().get(i);
            }
            etqAerolineaFavGlobal.setText(aerolineasFav);
        }
        //Destino
        if (estadisticasUsuario.getDestinosFav().isEmpty()) {
            etqDestinoFavGlobal.setText(" - ");
            //Controlamos no caso de que so fixera viaxes de volta ao folgoso do courel
            if ((getInstanceModelo().usuarioViajado(usuario.getDni()))
                    && (!etqAerolineaFavGlobal.getText().equals(" - "))) {
                etqInfoEstadisticas2.setVisible(true);
            }
        } else {
            String destinosFav = estadisticasUsuario.getDestinosFav().get(0);
            for (int i = 1; i < estadisticasUsuario.getDestinosFav().size(); i++) {
                destinosFav += ", " + estadisticasUsuario.getDestinosFav().get(i);
            }
            etqDestinoFavGlobal.setText(destinosFav);
        }
        //Tarifa
        if (!estadisticasUsuario.getTarifaFav().isEmpty()) {
            etqTarifaFavGlobal.setText(estadisticasUsuario.getTarifaFav());
        } else {
            etqTarifaFavGlobal.setText(" - ");
        }
        //Comprobase se o usuario viaxou
        if (!getInstanceModelo().usuarioViajado(usuario.getDni())) {
            txtAreaNumViajes.setText("¡" + usuario.getNombre() + ","
                    + " aún no has viajado con nosotros! Dirígete al panel y "
                    + "déjate llevar por los destinos más increíbles.");
        }

    }

    private void mostrarEstadisticas() {
        String tipo;
        Integer num;
        EstadisticasUsuario estadisticasUsuario;
        String titulo = "Estadísticas ";
        etqInfoEstadisticas1.setVisible(false);

        if (getInstanceModelo().usuarioViajado(usuario.getDni())) {
            if (btnEstacion.isSelected()) {
                tipo = "estacion";
                titulo += "de ";
            } else if (btnMes.isSelected()) {
                tipo = "mes";
                titulo += "de ";
            } else {
                tipo = "anho";
                titulo += "del ";
            }
            if (tipo.equals("anho")) {
                num = parseInt(comboBoxEstUsu.getSelectionModel().getSelectedItem());
                etqInfoEstadisticas1.setText("En el " + num + " no has realizado vuelos que despegaran desde nuestro aeropuerto");
            } else {
                num = comboBoxEstUsu.getSelectionModel().getSelectedIndex() + 1;
                String mes_estacion = comboBoxEstUsu.getSelectionModel().getSelectedItem().toLowerCase();
                etqInfoEstadisticas1.setText("En " + mes_estacion + " no has realizado vuelos que despegaran desde nuestro aeropuerto");
            }

            estadisticasUsuario = Modelo.getInstanceModelo().mostrarEstadisticasUsuario(usuario.getDni(), tipo, num);

            txtAreaNumViajes.setText("Has viajado " + estadisticasUsuario.getVecesViajadas()
                    + " veces en " + comboBoxEstUsu.getSelectionModel().getSelectedItem().toLowerCase() + "!");

            titulo += comboBoxEstUsu.getSelectionModel().getSelectedItem().toLowerCase();
            etqInfoEstadisticasEspecificas.setText(titulo);

            if (estadisticasUsuario.getAerolineasFav().isEmpty()) {
                etqAerolineaFavEspecifica.setText(" - ");
            } else {
                String aerolineasFav = estadisticasUsuario.getAerolineasFav().get(0);
                for (int i = 1; i < estadisticasUsuario.getAerolineasFav().size(); i++) {
                    aerolineasFav += ", " + estadisticasUsuario.getAerolineasFav().get(i);
                }
                etqAerolineaFavEspecifica.setText(aerolineasFav);
            }
            if (estadisticasUsuario.getDestinosFav().isEmpty()) {
                etqDestinoFavEspecifico.setText(" - ");
                if ((getInstanceModelo().usuarioViajado(usuario.getDni()))
                        && (!etqAerolineaFavEspecifica.getText().equals(" - "))) {
                    etqInfoEstadisticas1.setVisible(true);
                }
            } else {
                String destinosFav = estadisticasUsuario.getDestinosFav().get(0);
                for (int i = 1; i < estadisticasUsuario.getDestinosFav().size(); i++) {
                    destinosFav += ", " + estadisticasUsuario.getDestinosFav().get(i);
                }
                etqDestinoFavEspecifico.setText(destinosFav);
            }

            if (!estadisticasUsuario.getTarifaFav().isEmpty()) {
                etqTarifaFavEspecifico.setText(estadisticasUsuario.getTarifaFav());
            } else {
                etqTarifaFavEspecifico.setText(" - ");
            }
        } else {
            etqAerolineaFavEspecifica.setText(" - ");
            etqDestinoFavEspecifico.setText(" - ");
            etqTarifaFavEspecifico.setText(" - ");
        }

    }

    @FXML
    void accionEstacion(ActionEvent event) {
        ObservableList<String> estaciones = FXCollections.observableArrayList("Primavera", "Verano", "Otoño", "Invierno");
        comboBoxEstUsu.setItems(estaciones);
        comboBoxEstUsu.getSelectionModel().selectFirst();
        mostrarEstadisticas();
    }

    @FXML
    void accionMes(ActionEvent event) {
        ObservableList<String> meses = FXCollections.observableArrayList("Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre");
        comboBoxEstUsu.setItems(meses);
        comboBoxEstUsu.getSelectionModel().selectFirst();
        mostrarEstadisticas();
    }

    @FXML
    void accionAnho(ActionEvent event) {
        buscarAnos();
        mostrarEstadisticas();
    }

    private void buscarAnos() {
        opVerVuelo.selectToggle(btnAno);
        ObservableList<String> anhos = FXCollections.observableList(getInstanceModelo().obtenerAnhosViajados(usuario.getDni()));
        comboBoxEstUsu.setItems(anhos);
        if (!comboBoxEstUsu.getItems().isEmpty()) {
            comboBoxEstUsu.getSelectionModel().selectFirst();
        }
        String ano = comboBoxEstUsu.getSelectionModel().getSelectedItem();
        if (ano != null) {
            etqInfoEstadisticasEspecificas.setText("Estadísticas del " + ano);
        } else {
            etqInfoEstadisticasEspecificas.setText("Estadísticas del " + Time.diaActual().getAno());
        }
    }

    @FXML
    private void accionComboBox(ActionEvent event) {
        if (comboBoxEstUsu.getSelectionModel().getSelectedItem() != null) {
            mostrarEstadisticas();
        }
    }

    @FXML
    private void cambiarAerolinea(ActionEvent event) {
        if (!comboBoxEstAer.getItems().isEmpty()) {
            accionCalculoEstAerolinea();
        }
    }

    @FXML
    private void abrirPestanaEstAerolineas(Event event) {
        if (!comboBoxEstAer.getItems().isEmpty()) {
            if (comboBoxEstAer.getSelectionModel().getSelectedItem() == null) {
                comboBoxEstAer.getSelectionModel().selectFirst();
            }
            accionCalculoEstAerolinea();
        }

    }

    private void accionCalculoEstAerolinea() {
        EstadisticasAerolinea est = getInstanceModelo().obtenerEstadisticasAerolinea(comboBoxEstAer.getSelectionModel().getSelectedItem());
        txtFieldVuelosRetraso.setText(String.format("%.2f", est.getPorcVuelosRetraso()) + "%");
        txtFieldOcNormal.setText(String.format("%.2f", (float) (Math.round(est.getPorcOcupNormal() * 100f)) / 100f) + "%");
        txtFieldOcPremium.setText(String.format("%.2f", (float) (Math.round(est.getPorcOcupPremium() * 100f)) / 100f) + "%");
        txtFieldTiempoRetraso.setText(est.getTiempoMedioRetraso());
        txtFieldPesoMaleta.setText(est.getAerolinea().getPesoBaseMaleta().toString());
        txtFieldPrecioMaleta.setText(est.getAerolinea().getPrecioBaseMaleta().toString());
        txtFieldPaisSede.setText(est.getAerolinea().getPais());
        txtFieldPlazasAvion.setText(String.format("%.1f", (float) (Math.round(est.getPlazasMediasAvion() * 100f)) / 100f));
        txtFieldAnoAvion.setText(String.format("%.0f", (float) (Math.round(est.getAnoFabricMedioAvion() * 100f)) / 100f));

        String paises = "";
        for (String pais : est.getNacionalidadPred()) {
            paises += pais + "  ";
        }
        txtFieldNacionalidad.setText(paises);
    }

    //Reservas
    @FXML
    private void accionAbrirMisReservas(Event event) {
        ObservableList<ReservaParking> resParking = FXCollections.observableArrayList(
                getInstanceModelo().obterResParkingUsuario(usuario.getDni()));
        tablaReservasParking.setItems(resParking);

        ObservableList<ReservaCoche> resCoche = FXCollections.observableArrayList(
                getInstanceModelo().obterResCocheUsuario(usuario.getDni()));
        tablaReservasCoche.setItems(resCoche);

    }

    @FXML
    private void accionBtnCancelarReservaParking(ActionEvent event) {

        if (Modelo.getInstanceModelo().mostrarConfirmacion("¿Estás seguro de que quieres cancelar la reserva?", getVenta())) {
            ReservaParking resSelect = tablaReservasParking.getSelectionModel().getSelectedItem();
            if (resSelect != null) {
                Modelo.getInstanceModelo().cancelarReserva(resSelect, usuario.getDni());

                btnCancelarReservaParking.setDisable(true);

                ObservableList<ReservaParking> res = FXCollections.observableArrayList(
                        getInstanceModelo().obterResParkingUsuario(usuario.getDni()));
                tablaReservasParking.setItems(res);
                Modelo.getInstanceModelo().mostrarNotificacion("Su reserva ha sido cancelada "
                        + "con éxito", getVenta());
            }
        } else {
            Modelo.getInstanceModelo().mostrarNotificacion("Operación cancelada.", getVenta());
        }

    }

    @FXML
    private void accionBtnCancelarReservaCoche(ActionEvent event) {

        if (Modelo.getInstanceModelo().mostrarConfirmacion("¿Estás seguro de que quieres cancelar la reserva?", getVenta())) {
            ReservaCoche resSelect = tablaReservasCoche.getSelectionModel().getSelectedItem();
            if (resSelect != null) {
                Modelo.getInstanceModelo().cancelarReserva(resSelect, usuario.getDni());

                btnCancelarReservaCoche.setDisable(true);

                ObservableList<ReservaCoche> res = FXCollections.observableArrayList(
                        getInstanceModelo().obterResCocheUsuario(usuario.getDni()));
                tablaReservasCoche.setItems(res);
                Modelo.getInstanceModelo().mostrarNotificacion("Su reserva ha sido cancelada "
                        + "con éxito", getVenta());
            }
        } else {
            Modelo.getInstanceModelo().mostrarNotificacion("Operación cancelada.", getVenta());
        }

    }

    @FXML
    private void seleccionarReservaParking(MouseEvent event) {
        ReservaParking resSelect = tablaReservasParking.getSelectionModel().getSelectedItem();
        if (resSelect != null) {
            if (resSelect.getEnCurso()) {
                btnCancelarReservaParking.setDisable(true);
            } else {
                btnCancelarReservaParking.setDisable(false);
            }
        } else {
            btnCancelarReservaParking.setDisable(true);
        }
    }

    @FXML
    private void seleccionarReservaCoche(MouseEvent event) {
        ReservaCoche resSelect = tablaReservasCoche.getSelectionModel().getSelectedItem();
        if (resSelect != null) {
            if (resSelect.getEnCurso()) {
                btnCancelarReservaCoche.setDisable(true);
            } else {
                btnCancelarReservaCoche.setDisable(false);
            }
        } else {
            btnCancelarReservaCoche.setDisable(true);
        }
    }

    /*

        SERVICIOS

     */
    @FXML
    private void accionBtnServicios(ActionEvent event) {
        //Poñemos o panel diante
        etqTitulo.setText(TITULO_SERV);
        panelServicios.toFront();
    }

    //PARKING
    @FXML
    private void abrirParking(Event event) {
    }

    @FXML
    private void accionBtnBuscarParking(ActionEvent event) {
        parking = getInstanceModelo().buscarParking(
                cboxTerminalParking.getSelectionModel().getSelectedItem(),
                new Time(dataFLlegadaParking.getValue()),
                new Time(dataFRetornoParking.getValue()));
        PorcentajeDisponibilidad pd = getInstanceModelo()
                .obterPrazasRestantesParkingTerminal(
                        cboxTerminalParking.getSelectionModel().getSelectedItem(),
                        new Time(dataFLlegadaParking.getValue()),
                        new Time(dataFRetornoParking.getValue()));
        if (pd != null) {
            txtPlazasParking.setText(pd.getPlazasLibres().toString());
            txtPrecioParking.setText(obterPrecioParking());
            etqInfoDisponhibilidadeParking.setText(String.format("%.2f", pd.calcularPorcentajeDisp()) + "%.");
            hBoxInfoDisponhibilidadeParking.setVisible(true);
            if (pd.getPlazasLibres() > 0) {
                if (comprobarMatricula()) {
                    btnReservarParking.setDisable(false);
                }
            }
        } else {
            hBoxInfoDisponhibilidadeParking.setVisible(false);
            txtPrecioParking.clear();
            txtPlazasParking.clear();
            getInstanceModelo().mostrarError("No se pudo obtener el número de "
                    + "plazas disponibles en el parking de la terminal. "
                    + "Inténtelo en otro momento.", getVenta());
        }

    }

    private String obterPrecioParking() {
        return String.format("%.2f", ((Time.obtenerDias(dataFLlegadaParking.getValue(),
                dataFRetornoParking.getValue()) + 1) * PRECIO_DIA_PARKING));
    }

    private Integer asignarPlazaParking(Time llegada, Time retorno) {
        return getInstanceModelo().obterPrazaLibre(parking.getTerminal(),
                parking.getPiso(), llegada, retorno);
    }

    @FXML
    private void accionBtnReservarParking(ActionEvent event) {
        Time llegada = new Time(dataFLlegadaParking.getValue());
        Time retorno = new Time(dataFRetornoParking.getValue());
        Integer numPraza = asignarPlazaParking(llegada, retorno);
        ReservaParking reserva = new ReservaParking(llegada,
                retorno,
                txtMatriculaParking.getText(),
                parking.getTerminal(),
                parking.getPiso(),
                numPraza);
        if (getInstanceModelo().reservarParking(reserva, usuario.getDni())) {
            getInstanceModelo().mostrarNotificacion("Reserva realizada con éxito.\n"
                    + "- Dni del cliente: " + usuario.getDni() + "\n"
                    + "- Terminal: " + parking.getTerminal() + "\n"
                    + "- Piso: " + parking.getPiso() + "\n"
                    + "- Número de plaza: " + numPraza + "\n"
                    + "- Fecha de inicio: " + llegada.toStringFecha() + "\n"
                    + "- Fecha de abandono: " + retorno.toStringFecha() + "\n"
                    + "- Precio: " + obterPrecioParking() + " €.",
                    getVenta());
        }
        btnReservarParking.setDisable(true);
        txtMatriculaParking.clear();
        etqErroMatricula.setVisible(false);
        btnBuscarParking.fire();
    }

    @FXML
    private void comprobarBuscarParking(Event event) {
        if ((cboxTerminalParking.getSelectionModel().getSelectedItem() != null)
                && (dataFLlegadaParking.getValue() != null)
                && (dataFRetornoParking.getValue() != null)) {

            if ((!Time.fechaMayorIgualActual(new Time(dataFLlegadaParking.getValue())))
                    || (!Time.fechaMayorIgualActual(new Time(dataFRetornoParking.getValue())))
                    || (Time.compararMayor(new Time(dataFLlegadaParking.getValue()), new Time(dataFRetornoParking.getValue())))) {
                btnBuscarParking.setDisable(true);

                //Ao inicializarse ten dúas clases css: label e etqInfo
                if (etqInfoParking.getStyleClass().size() == 2) {
                    etqInfoParking.getStyleClass().add("etqErro");
                }

                //A data de chegada e maior que a de retorno
                if (Time.compararMayor(new Time(dataFLlegadaParking.getValue()), new Time(dataFRetornoParking.getValue()))) {
                    etqInfoParking.setText(TEXTO_ERROR_PARKING_COCHES);
                } else { //As datas son menores que a actual
                    etqInfoParking.setText(TEXTO_ERROR_FECHA);
                }

            } else {
                etqInfoParking.setText(TEXTO_INFO_PARKING_COCHES);
                btnBuscarParking.setDisable(false);
                etqInfoParking.getStyleClass().remove("etqErro");
            }

        } else if ((dataFLlegadaParking.getValue() != null)
                && (dataFRetornoParking.getValue() != null)) {

            if ((!Time.fechaMayorIgualActual(new Time(dataFLlegadaParking.getValue())))
                    || (!Time.fechaMayorIgualActual(new Time(dataFRetornoParking.getValue())))
                    || (Time.compararMayor(new Time(dataFLlegadaParking.getValue()), new Time(dataFRetornoParking.getValue())))) {
                btnBuscarParking.setDisable(true);
                if (etqInfoParking.getStyleClass().size() == 2) {
                    etqInfoParking.getStyleClass().add("etqErro");
                }

                //A data de chegada e maior que a de retorno
                if (Time.compararMayor(new Time(dataFLlegadaParking.getValue()), new Time(dataFRetornoParking.getValue()))) {
                    etqInfoParking.setText(TEXTO_ERROR_PARKING_COCHES);
                } else { //As datas son menores que a actual
                    etqInfoParking.setText(TEXTO_ERROR_FECHA);
                }

            } else {
                etqInfoParking.setText(TEXTO_INFO_PARKING_COCHES);
                btnBuscarParking.setDisable(true);
                etqInfoParking.getStyleClass().remove("etqErro");
            }

        } else if (dataFLlegadaParking.getValue() != null) {
            if (!Time.fechaMayorIgualActual(new Time(dataFLlegadaParking.getValue()))) {
                etqInfoParking.setText(TEXTO_ERROR_FECHA);
                btnBuscarParking.setDisable(true);

                if (etqInfoParking.getStyleClass().size() == 2) {
                    etqInfoParking.getStyleClass().add("etqErro");
                }
            } else {
                etqInfoParking.setText(TEXTO_INFO_PARKING_COCHES);
                btnBuscarParking.setDisable(true);
                etqInfoParking.getStyleClass().remove("etqErro");
            }

        } else if (dataFRetornoParking.getValue() != null) {
            if (!Time.fechaMayorIgualActual(new Time(dataFRetornoParking.getValue()))) {
                etqInfoParking.setText(TEXTO_ERROR_FECHA);
                btnBuscarParking.setDisable(true);
                if (etqInfoParking.getStyleClass().size() == 2) {
                    etqInfoParking.getStyleClass().add("etqErro");
                }
            } else {
                etqInfoParking.setText(TEXTO_INFO_PARKING_COCHES);
                btnBuscarParking.setDisable(true);
                etqInfoParking.getStyleClass().remove("etqErro");
            }

        } else {
            etqInfoParking.setText(TEXTO_INFO_PARKING_COCHES);
            btnBuscarParking.setDisable(true);
            etqInfoParking.getStyleClass().remove("etqErro");
        }

        btnReservarParking.setDisable(true);
    }

    private Boolean comprobarMatricula() {
        String matricula = txtMatriculaParking.getText();

        if (matricula.length() == 7) {
            for (int i = 0; i < 4; i++) {
                if (!Character.isDigit(matricula.charAt(i))) {
                    etqErroMatricula.setVisible(true);
                    return false;
                }
            }
            for (int i = 4; i < 7; i++) {
                if (!Character.isLetter(matricula.charAt(i))) {
                    etqErroMatricula.setVisible(true);
                    return false;
                }
            }
            return true;
        }
        return false;

    }

    @FXML
    private void comprobarReservarParking(KeyEvent event) {
        String matricula = txtMatriculaParking.getText();
        if (comprobarMatricula()) {
            etqErroMatricula.setVisible(false);
            if ((cboxTerminalParking.getSelectionModel().getSelectedItem() != null)
                    && (dataFLlegadaParking.getValue() != null)
                    && (dataFRetornoParking.getValue() != null)) {
                btnReservarParking.setDisable(false);
            }
        } else if (matricula.length() > 7) {
            etqErroMatricula.setVisible(true);
            btnReservarParking.setDisable(true);
        } else {
            etqErroMatricula.setVisible(false);
            btnReservarParking.setDisable(true);
        }

    }

    //COCHES
    @FXML
    private void abrirCoches(Event event) {
    }

    private void poderBuscarCoches() {
        if ((dataFLlegadaCoches.getValue() != null)
                && (dataFRetornoCoches.getValue() != null)) {

            if ((!Time.fechaMayorIgualActual(new Time(dataFLlegadaCoches.getValue())))
                    || (!Time.fechaMayorIgualActual(new Time(dataFRetornoCoches.getValue())))
                    || (Time.compararMayor(new Time(dataFLlegadaCoches.getValue()), new Time(dataFRetornoCoches.getValue())))) {
                btnBuscarCoches.setDisable(true);
                if (etqInfoCoches.getStyleClass().size() == 2) {
                    etqInfoCoches.getStyleClass().add("etqErro");
                }

                //A data de chegada e maior que a de retorno
                if (Time.compararMayor(new Time(dataFLlegadaCoches.getValue()), new Time(dataFRetornoCoches.getValue()))) {
                    etqInfoCoches.setText(TEXTO_ERROR_PARKING_COCHES);
                } else { //As datas son menores que a actual
                    etqInfoCoches.setText(TEXTO_ERROR_FECHA);
                }

            } else if (comprobarNumeroValido()) {
                etqInfoCoches.setText(TEXTO_INFO_PARKING_COCHES);
                btnBuscarCoches.setDisable(false);
                etqInfoCoches.getStyleClass().remove("etqErro");
            } else {
                etqInfoCoches.setText(TEXTO_ERROR_COCHES_NUMERO);
            }

        } else if (dataFRetornoCoches.getValue() != null) {
            if (!Time.fechaMayorIgualActual(new Time(dataFRetornoCoches.getValue()))) {
                etqInfoCoches.setText(TEXTO_ERROR_FECHA);
                btnBuscarCoches.setDisable(true);
                if (etqInfoCoches.getStyleClass().size() == 2) {
                    etqInfoCoches.getStyleClass().add("etqErro");
                }
            } else if (comprobarNumeroValido()) {
                etqInfoCoches.setText(TEXTO_INFO_PARKING_COCHES);
                btnBuscarCoches.setDisable(true);
                etqInfoCoches.getStyleClass().remove("etqErro");
            } else {
                etqInfoCoches.setText(TEXTO_ERROR_COCHES_NUMERO);
            }

        } else if (dataFLlegadaCoches.getValue() != null) {
            if (!Time.fechaMayorIgualActual(new Time(dataFLlegadaCoches.getValue()))) {
                etqInfoCoches.setText(TEXTO_ERROR_FECHA);
                btnBuscarCoches.setDisable(true);
                if (etqInfoCoches.getStyleClass().size() == 2) {
                    etqInfoCoches.getStyleClass().add("etqErro");
                }
            } else if (comprobarNumeroValido()) {
                etqInfoCoches.setText(TEXTO_INFO_PARKING_COCHES);
                btnBuscarCoches.setDisable(true);
                etqInfoCoches.getStyleClass().remove("etqErro");
            } else {
                etqInfoCoches.setText(TEXTO_ERROR_COCHES_NUMERO);
            }

        } else if (comprobarNumeroValido()) {
            etqInfoCoches.setText(TEXTO_INFO_PARKING_COCHES);
            btnBuscarCoches.setDisable(true);
            etqInfoCoches.getStyleClass().remove("etqErro");
        } else {
            etqInfoCoches.setText(TEXTO_ERROR_COCHES_NUMERO);
        }

        btnReservarCoches.setDisable(true);
    }

    @FXML
    private void comprobarBuscarCochesDteP(Event event) {
        poderBuscarCoches();
    }

    private Boolean comprobarNumeroValido() {
        try {
            int num = Integer.parseInt(textNPrazas.getText());
            return (num <= 12) && (num >= 1);
        } catch (NumberFormatException ex) {
            return textNPrazas.getText().isEmpty();
        }
    }

    @FXML
    private void comprobarBuscarCochesTxt(KeyEvent event) {
        if (comprobarNumeroValido()) {
            poderBuscarCoches();
        } else {
            etqInfoCoches.setText(TEXTO_ERROR_COCHES_NUMERO);
            btnBuscarCoches.setDisable(true);
            if (etqInfoCoches.getStyleClass().size() == 2) {
                etqInfoCoches.getStyleClass().add("etqErro");
            }
        }
    }

    @FXML
    private void accionBtnBuscarCoches(ActionEvent event) {
        Time llegada = new Time(dataFLlegadaCoches.getValue());
        Time salida = new Time(dataFRetornoCoches.getValue());
        Integer numPrazas;
        if (!textNPrazas.getText().isEmpty()) {
            numPrazas = Integer.parseInt(textNPrazas.getText());
        } else {
            numPrazas = null;
        }
        actualizarTaboa(llegada, salida, numPrazas);
    }

    private void actualizarTaboa(Time llegada, Time salida, Integer numPrazas) {
        ObservableList<Coche> coches = FXCollections.observableList(getInstanceModelo()
                .buscarCoches(llegada, salida, numPrazas));
        taboaReservarCoche.setItems(coches);
    }

    @FXML
    private void seleccionCoche(MouseEvent event) {
        if ((taboaReservarCoche.getSelectionModel().getSelectedItem() != null)
                && (!btnBuscarCoches.isDisable())) {
            Integer dias = Time.obtenerDias(dataFRetornoCoches.getValue(), dataFLlegadaCoches.getValue()) + 1;
            Coche coche = taboaReservarCoche.getSelectionModel().getSelectedItem();
            Float precioTotal = dias * coche.getPrecioDia();
            txtPrecioTotalCoches.setText(precioTotal.toString());
            btnReservarCoches.setDisable(false);
        } else {
            btnReservarCoches.setDisable(true);
        }
    }

    @FXML
    private void accionBtnReservarCoches(ActionEvent event) {
        Coche coche = taboaReservarCoche.getSelectionModel().getSelectedItem();
        if (coche != null) {
            ReservaCoche reserva = new ReservaCoche(new Time(dataFLlegadaCoches.getValue()),
                    new Time(dataFRetornoCoches.getValue()),
                    coche.getMatricula());
            if (getInstanceModelo().reservarCoche(reserva, usuario.getDni())) {
                getInstanceModelo().mostrarNotificacion("Reserva realizada con éxito.\n"
                        + "- Dni del cliente: " + usuario.getDni() + "\n"
                        + "- Fecha de inicio: " + reserva.getInicio().toStringFecha() + "\n"
                        + "- Fecha de devolución: " + reserva.getFin().toStringFecha() + "\n"
                        + "- Matrícula del coche: " + reserva.getMatricula() + "\n"
                        + "- Precio: " + txtPrecioTotalCoches.getText() + " €.",
                        getVenta());
            } else {
                getInstanceModelo().mostrarError("Se ha producido un error en la reserva. "
                        + "Inténtelo de nuevo más tarde.\n\n"
                        + "Disculpe las molestias, trataremos de arreglarlo lo antes porsible.",
                        getVenta());
            }
        } else {
            getInstanceModelo().mostrarError("Debe seleccionar un coche antes de "
                    + "reservar.", getVenta());
        }

        Integer numPrazas;
        if (!textNPrazas.getText().isEmpty()) {
            numPrazas = Integer.parseInt(textNPrazas.getText());
        } else {
            numPrazas = null;
        }
        actualizarTaboa(new Time(dataFLlegadaCoches.getValue()),
                new Time(dataFRetornoCoches.getValue()),
                numPrazas);
        btnReservarCoches.setDisable(true);

    }

    //Tendas
    @FXML
    private void abrirTiendas(Event event) {
        btnBuscarTendas.fire();
    }

    @FXML
    private void accionBtnBuscarTendas(ActionEvent event) {
        ObservableList<Tienda> tiendas = FXCollections.observableList(getInstanceModelo().buscarTiendas(txtNomeTendas.getText(),
                cBoxTipoTendas.getSelectionModel().getSelectedItem(),
                cBoxTerminalTendas.getSelectionModel().getSelectedItem()));
        taboaTendas.setItems(tiendas);
    }

    /*

        INFROMACION

     */
    @FXML
    private void accionBtnInfo(ActionEvent event) {
        panelInfo.toFront();
        etqTitulo.setText(TITULO_INFO);
        if (radioBtnSalidas.isSelected()) {
            accionSalidas();
        } else {
            accionLlegadas();
        }
    }

    @FXML
    private void abrirPestanaSL(Event event) {
        if (radioBtnSalidas.isSelected()) {
            accionSalidas();
        } else {
            accionLlegadas();
        }
    }

    @FXML
    private void pulsarSalidas(ActionEvent event) {
        accionSalidas();
    }

    @FXML
    private void pulsarLlegadas(ActionEvent event) {
        accionLlegadas();
    }

    @FXML
    private void actualizarSL(ActionEvent event) {
        if (radioBtnSalidas.isSelected()) {
            accionSalidas();
        } else {
            accionLlegadas();
        }
    }

    private void accionSalidas() {
        colFechaSL.setText("Salida");
        colFechaSL.setCellValueFactory(new PropertyValueFactory<>("fechasalidaReal"));
        ObservableList<Vuelo> salidas = FXCollections.observableArrayList(
                getInstanceModelo().mostrarSalidas());
        tablaSalidasLlegadas.setItems(salidas);
    }

    private void accionLlegadas() {
        colFechaSL.setText("Llegada");
        colFechaSL.setCellValueFactory(new PropertyValueFactory<>("fechallegadaReal"));
        ObservableList<Vuelo> llegadas = FXCollections.observableArrayList(
                getInstanceModelo().mostrarLlegadas());
        tablaSalidasLlegadas.setItems(llegadas);
    }

    /*

        PERSONAL LABORAL

     */
    @FXML
    private void accionBtnPersonal(ActionEvent event) {
        if (usuario instanceof PersonalLaboral) {
            panelPersLab.toFront();
            etqTitulo.setText(TITULO_PERSLAB);
            PersonalLaboral trabajador = (PersonalLaboral) usuario;

            trabajador.setEstaDentro(Modelo.getInstanceModelo().estaDentroPersLab(trabajador));

            actualizarBotonesPersLab();

        } else if (usuario instanceof Administrador) {
            panelAdministrador.toFront();
        }

    }

    @FXML
    private void devolverBillete(ActionEvent event) {
        Modelo.getInstanceModelo().mostrarNotificacion("Dirigite a tu historial de vuelos, dentro de área personal, para realizar "
                + "la devolución del billete.", getVenta());
    }

    @FXML
    private void accionBtnControl(ActionEvent event) {
        //Creamos unha venta filla da princiapl
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.initOwner(getVenta());
        //Seleccionamos o voo que se vai comprar

        VControlController controlador = ((VControlController) loadWindow(getClass().getResource("/gui/vista/vControl.fxml"), "Folgoso do Courel", stage));
    }

    @FXML
    private void accionBtnEntrarSalir(ActionEvent event) {
        PersonalLaboral trab = (PersonalLaboral) usuario;
        if (btnEntrarSalir.getText().equals("Entrar")) {
            Modelo.getInstanceModelo().entrarPersLaboral(trab);
        } else {
            Modelo.getInstanceModelo().salirPersLaboral(trab);
        }
        actualizarBotonesPersLab();
    }

    private void actualizarBotonesPersLab() {

        if (!((PersonalLaboral) usuario).estaDentro()) {
            btnMaletas.setDisable(true);
            btnControl.setDisable(true);
            btnCoches.setDisable(true);
            btnEntrarSalir.setText("Entrar");
        } else {
            btnMaletas.setDisable(false);
            btnControl.setDisable(false);
            btnCoches.setDisable(false);
            btnEntrarSalir.setText("Salir");
        }
    }

    @FXML
    private void accionBtnTarea(ActionEvent event) {
        //Creamos unha venta filla da princiapl
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.initOwner(getVenta());

        VTareaController controlador = ((VTareaController) loadWindow(getClass().getResource("/gui/vista/vTarea.fxml"), "Folgoso do Courel", stage));
        controlador.setTrabajador((PersonalLaboral) usuario);
    }

    @FXML
    private void accionBtnCoches(ActionEvent event) {
        //Creamos unha venta filla da princiapl
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.initOwner(getVenta());

        VCocheControlador controlador = ((VCocheControlador) loadWindow(getClass().getResource("/gui/vista/vCoche.fxml"), "Folgoso do Courel", stage));
    }

    @FXML
    private void accionBtnMaletas(ActionEvent event) {

        //Creamos unha venta filla da princiapl
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.initOwner(getVenta());

        VMaletaController controlador = ((VMaletaController) loadWindow(getClass().getResource("/gui/vista/vMaleta.fxml"), "Folgoso do Courel", stage));

    }

    @FXML
    private void accionBtnHistorial(ActionEvent event) {
        //Creamos unha venta filla da princiapl
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.initOwner(getVenta());

        VHistorialController controlador = ((VHistorialController) loadWindow(getClass().getResource("/gui/vista/vHistorial.fxml"), "Folgoso do Courel", stage));
        controlador.setTrabajador((PersonalLaboral) usuario);
    }

}
