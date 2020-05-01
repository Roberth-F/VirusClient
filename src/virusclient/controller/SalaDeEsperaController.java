/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package virusclient.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Lalo
 */
public class SalaDeEsperaController implements Initializable {

    @FXML
    private StackPane root;
    @FXML
    private VBox vbContenedor;
    @FXML
    private HBox hbTitulo;
    @FXML
    private Label labTituloEsp;
    @FXML
    private HBox hbUnirse;
    private String nombreJugador;

    /**
     * Initializes the controller class.
     */

    public void setNombre(String nom) {
        System.out.println(nom);
        this.nombreJugador = nom;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        ImageView iv1 = new ImageView("virusclient/resources/imagenesAvatar/avatar1.png");
        hbUnirse.getChildren().add(iv1);
        Label btn1 = new Label();
        System.out.println(nombreJugador);
        btn1.setText("LALO");
        btn1.setGraphic(iv1);
        btn1.setContentDisplay(ContentDisplay.TOP);
        hbUnirse.getChildren().add(btn1);

    }

}
