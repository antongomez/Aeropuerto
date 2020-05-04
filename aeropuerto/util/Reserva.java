/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aeropuerto.util;

import java.sql.Timestamp;

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

    public String getTipo() {
        return tipo;
    }

}
