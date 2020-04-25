/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aeropuerto.util;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Esther
 */
/*Clase que recoge el formato de las estadísticas de los usuarios*/
public class EstadisticasUsuario {
    
    ArrayList<String> aerolineasFav;
    ArrayList<String> destinosFav;
    String tarifaFav; //premium/normal/premium y normal
    Integer vecesViajadas;

    /*Cuando se crean las estadísticas solo se introduce las veces que se viajó*/
    public EstadisticasUsuario(Integer vecesViajadas) {
        this.vecesViajadas=vecesViajadas;
        aerolineasFav=new ArrayList<>();
        destinosFav=new ArrayList<>(); 
        tarifaFav="";
    }
    
    public void anadirAerolinea(String a){
        
        /*Solo se añade si no estaba ya*/
        if(!aerolineasFav.contains(a)){
            aerolineasFav.add(a);
        }
    }
    
    public void anadirDestino(String d){
        if(!destinosFav.contains(d)){
            destinosFav.add(d);
        }
    }
    
    public void anadirTarifa(String t){
        
        if(tarifaFav.isEmpty() || tarifaFav.equals(t)){
            tarifaFav=t;
        }
        else{
            tarifaFav="normal y premium";
        }
    }

    public ArrayList<String> getAerolineasFav() {
        return aerolineasFav;
    }

    public ArrayList<String> getDestinosFav() {
        return destinosFav;
    }

    public String getTarifaFav() {
        return tarifaFav;
    }

    public Integer getVecesViajadas() {
        return vecesViajadas;
    }
    
    
    
}
