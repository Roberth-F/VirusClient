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
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import virusclient.model.Jugador;
import virusclient.util.AppContext;

/**
 * FXML Controller class
 *
 * @author Lalo
 */
public class SalaDeEsperaController extends Rechargeable implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private ImageView fondoEspera;
    @FXML
    private Label nombreJugador;
    @FXML
    private ImageView perfilJugador;
    @FXML
    private VBox jugadores;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fondoEspera.fitHeightProperty().bind(root.heightProperty());
        fondoEspera.fitWidthProperty().bind(root.widthProperty());
        Jugador actual = (Jugador)AppContext.getInstance().get("jugador");
        nombreJugador.setText(actual.getNombre());
        perfilJugador.setImage(new Image("virusclient/resources/imagenesAvatar/" + actual.getNombAvatar()));
    }

    @Override
    public void reOpen() {

    }

}
