/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package virusclient.controller;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import virusclient.util.AppContext;

/**
 * FXML Controller class
 *
 * @author Roberth 😊
 */
public class AvatarSelectorController extends Rechargeable implements Initializable {

    @FXML
    private FlowPane charger;
    private DatosJugadorController pantallaJugador;
    private Map<Image, String> map;

    public AvatarSelectorController() {
        this.map = new HashMap();
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            this.getStage().setOnCloseRequest(event -> close());
        });
    }

    @Override
    public void reOpen() {
        pantallaJugador = (DatosJugadorController) AppContext.getInstance().get("DataGame");
        AppContext.getInstance().set("DataGame", null);
        map = new HashMap<>();
        imgcherger();
    }

    public void imgcherger() {
        for (int i = 1; i < 15; i++) {
            Image img = new Image("virusclient/resources/imagenesAvatar/avt" + String.valueOf(i) + ".png");
            ImageView cuadro = new ImageView(img);
            map.put(img, "avt" + String.valueOf(i) + ".png");
            charger.getChildren().add(cuadro);

        }
        charger.getChildren().forEach(actual -> {
            if (actual instanceof ImageView) {
                actual.setOnMouseClicked(event -> {
                    pantallaJugador.colocarNuevaImagenJugador(((ImageView) actual).getImage(), map.get(((ImageView) actual).getImage()));
                    close();
                });
            }
        });
    }

    private void close() {
        pantallaJugador = null;
        charger.getChildren().clear();
        map.clear();
        this.getStage().close();
    }
}
