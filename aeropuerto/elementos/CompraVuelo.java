/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aeropuerto.elementos;

/**
 *
 * @author Esther
 */
public class CompraVuelo {
    
    Boolean acompanante;
    Integer numMaletas;
    Boolean premium;

    public CompraVuelo() {
        acompanante=false;
        numMaletas=0;
        premium=false;
    }

    public void setAcompanante(Boolean acompanante) {
        this.acompanante = acompanante;
    }

    public void setNumMaletas(Integer numMaletas) {
        this.numMaletas = numMaletas;
    }

    public void setPremium(Boolean premium) {
        this.premium = premium;
    }

    public Boolean getAcompanante() {
        return acompanante;
    }

    public Integer getNumMaletas() {
        return numMaletas;
    }

    public Boolean getPremium() {
        return premium;
    }
    
    
}
