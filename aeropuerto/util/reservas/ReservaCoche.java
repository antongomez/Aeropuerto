package aeropuerto.util.reservas;

import aeropuerto.util.Time;
import java.sql.Timestamp;

public class ReservaCoche extends Reserva {

    private Float precio;
    private String estado;
    private Float precioDia;
    private String modelo;
    private Integer retraso;
    private Boolean devRetraso;

    public ReservaCoche(Timestamp inicio, Timestamp fin, String matricula, Boolean enCurso, Boolean devRetraso) {
        super(inicio, fin, matricula, enCurso);
        this.devRetraso=devRetraso;
    }
    
    public ReservaCoche(Time inicio, Time fin, String matricula) {
        super(inicio, fin, matricula);
    }

    /*Constructor para alquiler de coche con reserva*/
    public ReservaCoche(Timestamp inicio, Timestamp fin, String matricula, String modelo, Float precioDia, String estado) {
        super(inicio, fin, matricula);

        Integer duracionAlquiler = Time.obtenerDias(getInicio().toLocalDate(), getFin().toLocalDate()) + 1;
        this.precio = (float) (Math.round((duracionAlquiler * precioDia) * 100d) / 100d);
        this.modelo = modelo;
        this.precioDia = precioDia;
        this.estado = estado;
    }

    /*Constructor de alquiler de coche sin reserva y devolución*/
    public ReservaCoche(Time inicio, Time fin, String matricula, Float precioDia, String usuario) {
        super(inicio, fin, matricula, usuario);

        this.precioDia = precioDia;
        if (!Time.fechaMayorIgualActual(fin)) {
            this.retraso = Time.obtenerDias(this.getFin().toLocalDate(), Time.diaActual().toLocalDate());
        } else {
            this.retraso = 0;
        }
        Integer duracionAlquiler = Time.obtenerDias(getInicio().toLocalDate(), getFin().toLocalDate()) + 1;
        this.precio = duracionAlquiler * this.precioDia + retraso * (this.precioDia + CARGO_EXTRA_RETRASO);
    }

    public Float getPrecio() {
        return precio;
    }

    public void setPrecio(Float precio) {
        this.precio = precio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Float getPrecioDia() {
        return precioDia;
    }

    public void setPrecioDia(Float precioDia) {
        this.precioDia = precioDia;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Integer getRetraso() {
        return retraso;
    }

    public void setRetraso(Integer retraso) {
        this.retraso = retraso;
    }

    public Boolean getDevRetraso() {
        return devRetraso;
    }
    
    
}
