/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.controlador;

import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author anton
 */
public abstract class Controlador { //Abstracta ya que no se instancia

    private Stage venta;
    public static final String ICON_IMAGE_LOC = "/recursos/avion.png";

    //Funcion que aigna o icono da venta
    public static void setStageIcon(Stage stage) {
        stage.getIcons().add(new Image(ICON_IMAGE_LOC));
    }

    /**
     *
     * @param loc localizacion da venta (tipo URL)
     * @param title
     * @param parentStage utilizase no caso de querer pasarlle un stage
     * existente, por exemplo, para non decoralo (StageStyle.UNDECORATED)
     * @return devolve o controlador da venta que se abriu
     */
    public static Controlador loadWindow(URL loc, String title, Stage parentStage) {
        Controlador controller = null;
        try {
            FXMLLoader loader = new FXMLLoader(loc);
            Parent parent = loader.load();
            controller = loader.getController();
            Stage stage = null;
            if (parentStage != null) {
                stage = parentStage;
            } else {
                stage = new Stage(StageStyle.DECORATED);
            }
            stage.setTitle(title);
            stage.setScene(new Scene(parent));
            stage.show();
            
            //Ponhemoslle un icono
            setStageIcon(stage);
            
            //Asignamoslle a venta ao controlador
            controller.setVenta(stage);

        } catch (IOException ex) {
            System.out.println("Error en la apertura de la ventana");
        }
        return controller;
    }

    //Getters
    public Stage getVenta() {
        return venta;
    }

    public void setVenta(Stage venta) {
        this.venta = venta;
    }
}
