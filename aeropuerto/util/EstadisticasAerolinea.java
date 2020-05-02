/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aeropuerto.util;

import aeropuerto.elementos.Aerolinea;
import java.util.ArrayList;

/**
 *
 * @author Esther
 */
public class EstadisticasAerolinea {
    
    private Aerolinea aerolinea;
    private Float porcVuelosRetraso;
    private Float tiempoMedioRetraso;
    private Float porcOcupNormal;
    private Float porcOcupPremium;
    private Float anoFabricMedioAvion;
    private Float plazasMediasAvion;
    private ArrayList<String> nacionalidadPred;

    public EstadisticasAerolinea(Aerolinea aerolinea,Float porcVuelosRetraso, Float tiempoMedioRetraso, Float porcOcupNormal,
            Float porcOcupPremium, Float anoFabricMedioAvion, Float plazasMediasAvion) {
        this.porcVuelosRetraso = porcVuelosRetraso;
        this.tiempoMedioRetraso = tiempoMedioRetraso;
        this.porcOcupNormal = porcOcupNormal;
        this.porcOcupPremium = porcOcupPremium;
        this.anoFabricMedioAvion = anoFabricMedioAvion;
        this.plazasMediasAvion = plazasMediasAvion;
        this.aerolinea=aerolinea;
        nacionalidadPred=new ArrayList<>();
    }
public void addNacionalidad(String nac){
    nacionalidadPred.add(nac);
}
   

    public Aerolinea getAerolinea() {
        return aerolinea;
    }

    public Float getPorcVuelosRetraso() {
        return porcVuelosRetraso;
    }

    public Float getTiempoMedioRetraso() {
        return tiempoMedioRetraso;
    }

    public Float getPorcOcupNormal() {
        return porcOcupNormal;
    }

    public Float getPorcOcupPremium() {
        return porcOcupPremium;
    }

    public Float getAnoFabricMedioAvion() {
        return anoFabricMedioAvion;
    }

    public Float getPlazasMediasAvion() {
        return plazasMediasAvion;
    }

    public ArrayList<String> getNacionalidadPred() {
        return nacionalidadPred;
    }
    
    
    
    
    
}
