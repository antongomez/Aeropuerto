/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aeropuerto.util.reservas;

import aeropuerto.util.Time;
import java.sql.Timestamp;

public abstract class Reserva {

    private Time inicio;
    private Time fin;
    private String matricula;
    private String usuario;
    private Boolean enCurso;

    final static Integer CARGO_EXTRA_RETRASO = 20;

    public Reserva(Timestamp inicio, Timestamp fin, String matricula, String usuario) {
        this.inicio = new Time(inicio);
        this.fin = new Time(fin);
        this.matricula = matricula;
        this.usuario = usuario;
    }

    public Reserva(Time inicio, Time fin, String matricula, String usuario) {
        this.inicio = inicio;
        this.fin = fin;
        this.matricula = matricula;
        this.usuario = usuario;
    }

    public Reserva(Timestamp inicio, Timestamp fin, String matricula, Boolean enCurso) {
        this.inicio = new Time(inicio);
        this.fin = new Time(fin);
        this.matricula = matricula;
        this.enCurso = enCurso;
    }
    
    public Reserva(Timestamp inicio, Timestamp fin, String matricula) {
        this.inicio = new Time(inicio);
        this.fin = new Time(fin);
        this.matricula = matricula;
        this.usuario = null;
    }

    public Reserva(Time inicio, Time fin, String matricula) {
        this.inicio = inicio;
        this.fin = fin;
        this.matricula = matricula;
        this.usuario = null;
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

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Boolean getEnCurso() {
        return enCurso;
    }
    
    public String getFindate() {
        return fin.toStringFecha();
    }
    
    public String getIniciodate() {
        return inicio.toStringFecha();
    }
}
