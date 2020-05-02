/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aeropuerto.util;

import aeropuerto.elementos.Aerolinea;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Esther
 */
public class EstadisticasAerolinea {
    
    private Aerolinea aerolinea;
    private Float porcVuelosRetraso;
    private String tiempoMedioRetraso;
    private Float porcOcupNormal;
    private Float porcOcupPremium;
    private Float anoFabricMedioAvion;
    private Float plazasMediasAvion;
    private ArrayList<String> nacionalidadPred;

    public EstadisticasAerolinea(Aerolinea aerolinea,Float porcVuelosRetraso, String tiempoMedioRetraso, Float porcOcupNormal,
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

    public String getTiempoMedioRetraso() {
        String retraso = "";

        if (retraso.equals("00:00:00")) {
            retraso = "0";
            /*Es una condición que no es normal que se de si la compañía funciona correctamente.
            No nos interesa especificar cuántos días se retrasa*/
        } else if (retraso.contains("day")) {
            retraso = "Varios días";
        } else {

            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            try {
                Calendar fecha = Calendar.getInstance();
                Date tiempo = sdf.parse(retraso);
                fecha.setTime(tiempo);
                if (fecha.get(Calendar.HOUR) <= 1) {
                    int minutos = fecha.get(Calendar.MINUTE) + fecha.get(Calendar.HOUR) * 60;
                    retraso = minutos + " min";
                } else {

                    retraso = fecha.get(Calendar.HOUR) + " h " + fecha.get(Calendar.MINUTE) + " min";
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }

        return retraso;
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
