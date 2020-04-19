/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.controlador;

import aeropuerto.FachadaAplicacion;
import gui.FachadaGui;
import gui.modelo.Modelo;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author anton
 */
public abstract class Controlador { //Abstracta ya que no se instancia

    private Stage venta;
    protected Modelo modelo;
    

    public void abrirVRegistrar(Modelo modelo) { //prueba de eli
        try {
            Stage vR = new Stage();
            FXMLLoader loader = new FXMLLoader(FachadaGui.class.getResource("/gui/vista/vRegistrarse.fxml"));
            Scene root = (Scene) loader.load();

            vR.setTitle("Aeropuerto");
            vR.setScene(root);
            ((vRegistrarseControlador)loader.getController()).setModelo(modelo);
            vR.show();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    //Getters
    public Stage getVenta() {
        return venta;
    }

    public void setVenta(Stage venta) {
        this.venta = venta;
    }

    public Modelo getModelo() {
        return modelo;
    }

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }    
}
