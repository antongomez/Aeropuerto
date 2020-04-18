/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aeropuerto;

import java.util.Date;

/**
 *
 * @author Esther
 */
public class Administrador extends Usuario{
    
    private Date fechaInicio;
    private String curriculum;

    public Administrador(String dni, String id, String email, String contrasenha, String nombre, String ap1, String ap2, String paisProcedencia, Integer telefono, String sexo, String curriculum) {
        super(dni, id, email, contrasenha, nombre, ap1, ap2, paisProcedencia, telefono, sexo);
        this.curriculum = curriculum;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public void setCurriculum(String curriculum) {
        this.curriculum = curriculum;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public String getCurriculum() {
        return curriculum;
    }
    
    
    
}
