/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aeropuerto.util;

import java.sql.Timestamp;

/**
 *
 * @author Esther
 */
public class Reserva {
    
    private Time inicio;
    private Time fin;
    private String tipo;
    private String matricula;

    public Reserva(String tipo, Timestamp inicio, Timestamp fin, String matricula) {
        this.inicio = new Time(inicio);
        this.fin = new Time(fin);
        this.tipo=tipo;
        this.matricula=matricula;
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
    public String getTipo(){
        return tipo;
    }
    
    
    
}
