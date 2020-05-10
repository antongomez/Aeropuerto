package gui.controlador;

import aeropuerto.elementos.Coche;
import aeropuerto.util.Time;
import aeropuerto.util.reservas.ReservaCoche;
import gui.modelo.Modelo;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class VCocheControlador extends Controlador implements Initializable {

    //Alquiler con reserva
    private String dniUsuarioActual;

    //Alquiler sin reserva
    private Coche cocheActual;

    //Devolucion
    private ReservaCoche devolucion;

    //Apartados con reserva
    @FXML
    private TextField textFieldDNIConReserva;
    @FXML
    private Button btnBuscarConReserva;
    @FXML
    private Button btnAlquilarConReserva;
    @FXML
    private RadioButton cambiarFechaConReserva;
    @FXML
    private DatePicker datePickerConReserva;
    @FXML
    private TableView<ReservaCoche> tablaConReserva;
    @FXML
    private TableColumn<ReservaCoche, String> columnaMatriculaConReserva;
    @FXML
    private TableColumn<ReservaCoche, Time> columnaFechaVueltaConReserva;
    @FXML
    private TableColumn<ReservaCoche, Float> columnaPrecioConReserva;
    @FXML
    private TableColumn<ReservaCoche, Time> columnaFechaRecogidaConReserva;
    @FXML
    private TableColumn<ReservaCoche, String> columnaEstadoConReserva;
    @FXML
    private TableColumn<ReservaCoche, String> columnaModeloConReserva;
    @FXML
    private TextField textFieldPrecioFinalConReserva;

    @FXML
    private TextField textFieldModeloSinReserva;
    @FXML
    private TextField textFieldMatriculaSinReserva;
    @FXML
    private Button btnBuscarSinReserva;
    @FXML
    private TableView<Coche> tablaSinReservas;
    @FXML
    private TableColumn<Coche, String> columnaMatriculaSinReserva;
    @FXML
    private TableColumn<Coche, String> columnaModeloSinReserva;
    @FXML
    private TableColumn<Coche, Integer> columnaCaballosSinReserva;
    @FXML
    private TableColumn<Coche, Float> columnaPrecioDiaSinReserva;
    @FXML
    private TableColumn<Coche, String> columnaCombustibleSinReserva;
    @FXML
    private TableColumn<Coche, Integer> columnaPlazasSinReserva;
    @FXML
    private TableColumn<Coche, Integer> columnaPuertasSinReserva;
    @FXML
    private TextField textFieldDniSinReserva;
    @FXML
    private Button btnAlquilarSinReserva;
    @FXML
    private TextField textFieldPrecioSinReserva;
    @FXML
    private TextField textFieldNPlazas;
    @FXML
    private DatePicker datePickerFechaVueltaSinReserva;

    //Devolucion
    @FXML
    private TextField textMatriculaD;
    @FXML
    private Button btnBuscarD;
    @FXML
    private TextField textDniD;
    @FXML
    private TextField textFinD;
    @FXML
    private TextField textRetrasoD;
    @FXML
    private TextField textInicioD;
    @FXML
    private TextField textActualD;
    @FXML
    private TextField textPrecioDiaD;
    @FXML
    private TextField textPrecioTotalD;
    @FXML
    private Button btnDevolver;
    @FXML
    private Label etqErrorFechaCR;
    @FXML
    private Label etqErrorFechaSR;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Apartado con reserva
        cambiarFechaConReserva.setSelected(false);
        cambiarFechaConReserva.setDisable(true);
        datePickerConReserva.setDisable(true);
        btnAlquilarConReserva.setDisable(true);

        columnaMatriculaConReserva.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        columnaFechaVueltaConReserva.setCellValueFactory(new PropertyValueFactory<>("findate"));
        columnaPrecioConReserva.setCellValueFactory(new PropertyValueFactory<>("precio"));
        columnaModeloConReserva.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        columnaFechaRecogidaConReserva.setCellValueFactory(new PropertyValueFactory<>("iniciodate"));
        columnaEstadoConReserva.setCellValueFactory(new PropertyValueFactory<>("estado"));

        //Apartado sin reserva
        btnBuscarSinReserva.setDisable(true);
        btnAlquilarSinReserva.setDisable(true);
        columnaMatriculaSinReserva.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        columnaModeloSinReserva.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        columnaCaballosSinReserva.setCellValueFactory(new PropertyValueFactory<>("caballos"));
        columnaPrecioDiaSinReserva.setCellValueFactory(new PropertyValueFactory<>("precioDia"));
        columnaCombustibleSinReserva.setCellValueFactory(new PropertyValueFactory<>("tipoCombustible"));
        columnaPlazasSinReserva.setCellValueFactory(new PropertyValueFactory<>("nPrazas"));
        columnaPuertasSinReserva.setCellValueFactory(new PropertyValueFactory<>("nPuertas"));

        //Apartado devolucion
        this.btnDevolver.setDisable(true);
    }

    @FXML
    private void obtenerReservasUsuario(ActionEvent event) {
        dniUsuarioActual = textFieldDNIConReserva.getText();
        if (Modelo.getInstanceModelo().comprobarRegistrado(dniUsuarioActual)) {
            ObservableList<ReservaCoche> reservas = FXCollections.observableList(Modelo.getInstanceModelo().obtenerReservasCocheUsuario(dniUsuarioActual));
            tablaConReserva.setItems(reservas);
        } else {
            Modelo.getInstanceModelo().mostrarError("Usuario no registrado.\n"
                    + "Debe registrarse antes de alquilar coches con nosotros.", getVenta());
            tablaConReserva.getItems().clear();
        }
    }

    @FXML
    private void seleccionarConReserva(MouseEvent event) {
        ReservaCoche reserva = tablaConReserva.getSelectionModel().getSelectedItem();
        if (reserva != null) {
            if (reserva.getEstado().equals("sin alquilar")) {
                cambiarFechaConReserva.setDisable(false);
                datePickerConReserva.setValue(reserva.getFin().toLocalDate());
                btnAlquilarConReserva.setDisable(false);
                textFieldPrecioFinalConReserva.setText(reserva.getPrecio().toString() + " €");
            } else {
                cambiarFechaConReserva.setDisable(true);
                datePickerConReserva.setValue(reserva.getFin().toLocalDate());
                btnAlquilarConReserva.setDisable(true);
            }
        }
    }

    @FXML
    private void alquilarConReserva(ActionEvent event) {
        ReservaCoche reserva = tablaConReserva.getSelectionModel().getSelectedItem();
        if (reserva != null) {
            reserva.setUsuario(dniUsuarioActual);
            if (cambiarFechaConReserva.isSelected()) {
                reserva.setFin(new Time(datePickerConReserva.getValue()));
            }
            if (Modelo.getInstanceModelo().introducirAlquiler(reserva.getMatricula(), reserva.getFin(), reserva.getUsuario())) {
                Modelo.getInstanceModelo().mostrarNotificacion("Alquiler realizado con éxito.\n"
                        + "- Dni del cliente: " + reserva.getUsuario() + "\n"
                        + "- Matricula del coche: " + reserva.getMatricula() + "\n"
                        + "- Fecha de inicio: " + reserva.getInicio().toStringFecha() + "\n"
                        + "- Fecha de abandono: " + reserva.getFin().toStringFecha() + "\n"
                        + "- Precio: " + reserva.getPrecio().toString() + " €.",
                        getVenta());
            }
        }
        btnBuscarConReserva.fire();
        btnAlquilarConReserva.setDisable(true);
        textFieldDNIConReserva.clear();
        textFieldPrecioFinalConReserva.clear();
    }

    @FXML
    private void activarDatePicker(ActionEvent event) {
        if (cambiarFechaConReserva.isSelected()) {
            datePickerConReserva.setDisable(false);
        } else {
            datePickerConReserva.setDisable(true);
            datePickerConReserva.setValue(tablaConReserva.getSelectionModel().getSelectedItem().getFin().toLocalDate());
        }
    }

    //Cada vez que cambia la fecha se debe actualizar el precio, si la fecha está disponible
    @FXML
    private void actualizarPrecioConReserva(ActionEvent event) {

        ReservaCoche reserva = tablaConReserva.getSelectionModel().getSelectedItem();

        if (reserva != null) {
            Time fechaFin = new Time(datePickerConReserva.getValue());
            if (Modelo.getInstanceModelo().sePuedeAmpliarReservaCoche(reserva.getFin(), fechaFin, reserva.getMatricula())) {
                Integer duracionAlquiler = Time.obtenerDias(reserva.getInicio().toLocalDate(), datePickerConReserva.getValue()) + 1;
                if (!Time.compararMayor(reserva.getInicio(), fechaFin)) {
                    Float precio = (float) (Math.round(duracionAlquiler * reserva.getPrecioDia() * 100d) / 100d);
                    reserva.setPrecio(precio);
                    textFieldPrecioFinalConReserva.setText(reserva.getPrecio().toString() + " €");
                    etqErrorFechaCR.setVisible(false);
                    btnAlquilarConReserva.setDisable(false);
                } else {
                    etqErrorFechaCR.setText("La fecha debe ser igual o superior a la actual");
                    etqErrorFechaCR.setVisible(true);
                    btnAlquilarConReserva.setDisable(true);
                }
            } else {
                etqErrorFechaCR.setText("Este coche no está disponible para estas fechas");
                etqErrorFechaCR.setVisible(true);
                btnAlquilarConReserva.setDisable(true);
            }
        }
    }

    @FXML
    private void buscarCochesDisponibles(ActionEvent event) {
        if (!comprobarNumeroValido()) {
            Modelo.getInstanceModelo().mostrarError("El número de plazas debe ser un número entre 1 y 12.", getVenta());
        } else {
            Integer numPlazas;
            if (!textFieldNPlazas.getText().isEmpty()) {
                numPlazas = Integer.parseInt(textFieldNPlazas.getText());
            } else {
                numPlazas = null;
            }
            ObservableList<Coche> coches = FXCollections.observableList(Modelo.getInstanceModelo().buscarCoches(Time.diaActual(), new Time(datePickerFechaVueltaSinReserva.getValue()),
                    numPlazas, textFieldModeloSinReserva.getText(), textFieldMatriculaSinReserva.getText()));
            tablaSinReservas.setItems(coches);
        }

    }

    @FXML
    private void seleccionarSinReserva(MouseEvent event) {
        btnAlquilarSinReserva.setDisable(false);
        cocheActual = tablaSinReservas.getSelectionModel().getSelectedItem();
        if (cocheActual != null) {
            Integer duracionAlquiler = Time.obtenerDias(Time.diaActual().toLocalDate(),
                    datePickerFechaVueltaSinReserva.getValue()) + 1;
            Float precio = (float) (Math.round(duracionAlquiler * cocheActual.getPrecioDia() * 100d) / 100d);
            textFieldPrecioSinReserva.setText(precio.toString() + " €");
        }
    }

    @FXML
    private void alquilarSinReserva(ActionEvent event) {
        String dni = textFieldDniSinReserva.getText();
        if (Modelo.getInstanceModelo().comprobarRegistrado(dni)) {
            if (cocheActual != null) {
                ReservaCoche reserva = new ReservaCoche(Time.diaActual(), new Time(datePickerFechaVueltaSinReserva.getValue()),
                        cocheActual.getMatricula(), cocheActual.getPrecioDia(), dni);
                if (Modelo.getInstanceModelo().introducirAlquiler(reserva.getMatricula(),
                        reserva.getFin(), dni)) {
                    Modelo.getInstanceModelo().mostrarNotificacion("Alquiler realizado con éxito.\n"
                            + "- Dni del cliente: " + reserva.getUsuario() + "\n"
                            + "- Matricula del coche: " + reserva.getMatricula() + "\n"
                            + "- Fecha de inicio: " + reserva.getInicio().toStringFecha() + "\n"
                            + "- Fecha de abandono: " + reserva.getFin().toStringFecha() + "\n"
                            + "- Precio: " + reserva.getPrecio().toString() + " €.",
                            getVenta());
                }
            }
        } else {
            Modelo.getInstanceModelo().mostrarError("Usuario no registrado.\n"
                    + "Debe registrarse antes de alquilar coches con nosotros.", getVenta());
        }
        btnBuscarSinReserva.fire();
        textFieldPrecioSinReserva.clear();
        textFieldDniSinReserva.clear();
    }

    private Boolean comprobarNumeroValido() {
        try {
            int num = Integer.parseInt(textFieldNPlazas.getText());
            return (num <= 12) && (num >= 1);
        } catch (NumberFormatException ex) {
            return textFieldNPlazas.getText().isEmpty();
        }
    }

    @FXML
    private void activarBuscar(ActionEvent event) {
        if (!Time.compararMayor(Time.diaActual(), new Time(datePickerFechaVueltaSinReserva.getValue()))) {
            btnBuscarSinReserva.setDisable(false);
            etqErrorFechaSR.setVisible(false);
        }
        else{
            btnBuscarSinReserva.setDisable(true);
            etqErrorFechaSR.setText("La fecha de vuelta debe ser mayor o igual a la actual.");
            etqErrorFechaSR.setVisible(true);
        }
        tablaSinReservas.setItems(null);
        btnAlquilarSinReserva.setDisable(true);
        textFieldPrecioSinReserva.clear();
    }

    @FXML
    private void buscarAlquilerDevolucion(ActionEvent event) {
        devolucion = Modelo.getInstanceModelo().buscarAlquilerDevolucion(textMatriculaD.getText());
        if (devolucion != null) {
            this.textDniD.setText(devolucion.getUsuario());
            this.textInicioD.setText(devolucion.getInicio().toStringFecha());
            this.textFinD.setText(devolucion.getFin().toStringFecha());
            this.textRetrasoD.setText(devolucion.getRetraso().toString());
            this.textPrecioDiaD.setText(devolucion.getPrecioDia().toString());
            this.textPrecioTotalD.setText(devolucion.getPrecio().toString() + " €");
            this.textActualD.setText(Time.diaActual().toStringFecha());
        } else {
            Modelo.getInstanceModelo().mostrarError("No se ha encontrado ninguna devolución pendiente para esa matrícula.");
        }
        this.btnDevolver.setDisable(false);
    }

    @FXML
    private void devolverCoche(ActionEvent event) {
        if (Modelo.getInstanceModelo().devolucionCoche(devolucion)) {
            Modelo.getInstanceModelo().mostrarNotificacion("Devolución realizada con éxito.\n"
                    + "- Dni del cliente: " + devolucion.getUsuario() + "\n"
                    + "- Matricula del coche: " + devolucion.getMatricula() + "\n"
                    + "- Fecha de inicio: " + devolucion.getInicio().toStringFecha() + "\n"
                    + "- Fecha de fin: " + devolucion.getFin().toStringFecha() + "\n"
                    + "- Fecha de devolucion: " + Time.diaActual().toStringFecha() + "\n"
                    + "- Precio: " + devolucion.getPrecio().toString() + " €.",
                    getVenta());
        } else {
            Modelo.getInstanceModelo().mostrarError("No se ha podido realizar la devolución de forma correcta.");
        }
        this.textDniD.clear();
        this.textInicioD.clear();
        this.textFinD.clear();
        this.textRetrasoD.clear();
        this.textPrecioDiaD.clear();
        this.textPrecioTotalD.clear();
        this.textActualD.clear();
        this.textMatriculaD.clear();
    }

}
