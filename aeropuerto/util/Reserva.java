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
    /*Atributos solo válidos para reserva de parking*/
    private Integer terminal;
    private Integer piso;
    private Integer numPlaza;
    /*Atributo solo válido para reserva de coche*/
    private Float precio;
    private String estado;
    private Float precioDia;
    private String modelo;

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
    
    public Reserva(Timestamp inicio, Timestamp fin, String tipo, String matricula, String modelo, Float precioDia, String estado) {
        this.inicio = new Time(inicio);
        this.fin = new Time(fin);
        this.tipo = tipo;
        this.precio = (float)(Math.round(((Time.obtenerDias(this.inicio.toLocalDate(), this.fin.toLocalDate()))*precioDia) * 100d) / 100d);
        this.modelo=modelo;
        this.precioDia=precioDia;
        this.estado=estado;
        this.matricula=matricula;
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

    
    
}
