/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aeropuerto.util;

import aeropuerto.elementos.Coche;
import java.sql.Timestamp;
import java.time.LocalDate;

public class Reserva {

    private Time inicio;
    private Time fin;
    private String tipo;
    private String matricula;
    private String usuario;
    /*Atributos solo válidos para reserva de parking*/
    private Integer terminal;
    private Integer piso;
    private Integer numPlaza;
    /*Atributo solo válido para reserva de coche*/
    private Float precio;
    private String estado;
    private Float precioDia;
    private String modelo;
    private Integer retraso;
    private final static Integer CARGO_EXTRA_RETRASO = 20;

    /*Constructor reserva coche*/
    public Reserva(String tipo, Timestamp inicio, Timestamp fin, String matricula) {
        this.inicio = new Time(inicio);
        this.fin = new Time(fin);
        this.tipo = tipo;
        this.matricula = matricula;
    }

    /*Constructor reserva parking*/
    public Reserva(String tipo, Timestamp inicio, Timestamp fin, String matricula, Integer terminal, Integer piso, Integer numPlaza) {
        this.inicio = new Time(inicio);
        this.fin = new Time(fin);
        this.tipo = tipo;
        this.matricula = matricula;
        this.terminal = terminal;
        this.piso = piso;
        this.numPlaza = numPlaza;
    }

    public Reserva(Time inicio, Time fin, String tipo, String matricula, Integer terminal, Integer piso, Integer numPlaza) {
        this.inicio = inicio;
        this.fin = fin;
        this.tipo = tipo;
        this.matricula = matricula;
        this.terminal = terminal;
        this.piso = piso;
        this.numPlaza = numPlaza;
    }

    public Reserva(Time inicio, Time fin, String tipo, String matricula) {
        this.inicio = inicio;
        this.fin = fin;
        this.tipo = tipo;
        this.matricula = matricula;
    }
    
    /*Constructor para alquiler de coche con reserva*/
    public Reserva(Timestamp inicio, Timestamp fin, String matricula, String modelo, Float precioDia, String estado) {
        this.inicio = new Time(inicio);
        this.fin = new Time(fin);
        this.tipo = "coche";
        Integer duracionAlquiler=Time.obtenerDias(this.inicio.toLocalDate(), this.fin.toLocalDate())+1;
        this.precio = (float)(Math.round((duracionAlquiler*precioDia) * 100d) / 100d);
        this.modelo=modelo;
        this.precioDia=precioDia;
        this.estado=estado;
        this.matricula=matricula;
    }
    
    /*Constructor de alquiler de coche sin reserva y devolución*/
    public Reserva(Time inicio, Time fin, String matricula, Float precioDia, String usuario){
        this.inicio = inicio;
        this.fin = fin;
        this.usuario = usuario;
        this.precioDia = precioDia;
        this.matricula=matricula;
        if(!Time.fechaMayorIgualActual(fin)){
            this.retraso=Time.obtenerDias(this.fin.toLocalDate(), Time.diaActual().toLocalDate());
        }
        else{
            this.retraso=0;
        }
        Integer duracionAlquiler=Time.obtenerDias(this.inicio.toLocalDate(), this.fin.toLocalDate())+1;
        this.precio=duracionAlquiler*this.precioDia+retraso*(this.precioDia+CARGO_EXTRA_RETRASO);
    }
    
    public Integer getTerminal() {
        return terminal;
    }

    public Integer getPiso() {
        return piso;
    }

    public Integer getNumPlaza() {
        return numPlaza;
    }

    public String getMatricula() {
        return matricula;
    }

    public Time getInicio() {
        return inicio;
    }

    public Time getFin() {
        return fin;
    }

    public void setFin(Time fin) {
        this.fin = fin;
    }
    
    public String getTipo() {
        return tipo;
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

    public String getModelo() {
        return modelo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Integer getRetraso() {
        return retraso;
    }
    
    
    
    
       
}
