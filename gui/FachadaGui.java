/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Esther
 */
public class FachadaGui {
    
    aeropuerto.FachadaAplicacion fa;

    public FachadaGui(aeropuerto.FachadaAplicacion fa) {
        this.fa = fa;
    }

    public void iniciarVista(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/vista/vAcceder.fxml"));
        primaryStage.setTitle("Aeroporto");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

}
