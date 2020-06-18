/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package virusclient.util;

import java.util.List;
import javafx.scene.layout.VBox;
import virusclient.model.Jugador;

/**
 *
 * @author roberth 😊
 */
public class GraficacionDeCampoJuego {

    public static void graficarEnCampoCaliente(List<VBox> campoJuego, Jugador jug, boolean tamannoCompleto) {
        campoJuego.forEach(vbox -> {
            vbox.getChildren().clear();
            jug.getCartasJugadas().forEach(carta -> {
                if (carta.getContainerId() == Integer.valueOf(vbox.getId())) {
                    if (!tamannoCompleto) {
                        carta.toSlowFormat();
                    }
                    vbox.getChildren().add(carta);
                }
            });
        });
    }
}
