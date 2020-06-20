/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package virusclient.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import virusclient.model.Jugador;
import virusclient.util.AppContext;
import virusclient.util.TbxControl;

/**
 * FXML Controller class
 *
 * @author rober
 */
public class SalaDeGanadorController extends Rechargeable implements Initializable {

    @FXML
    private Label lblNombre;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Jugador ganador = (Jugador) AppContext.getInstance().get("ganador");
        lblNombre.setText(ganador.getNombre());
        lblNombre.setGraphic(new ImageView(new Image("virusclient/resources/imagenesAvatar/" + ganador.getNombAvatar())));
    }

    @Override
    public void reOpen() {
    }

    @FXML
    private void goHome(ActionEvent event) {
        TbxControl.getInstance().resetCache();
        AppContext.getInstance().delete("ganador");
        AppContext.getInstance().delete("unirse");
        AppContext.getInstance().delete("nuevosJugadores");
        AppContext.getInstance().delete("cargarPartida");
        AppContext.getInstance().delete("DataGame");
        TbxControl.getInstance().view("MenuIncio");
    }

}
