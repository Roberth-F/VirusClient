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
            jug.getCartasJugadas().forEach(listaC -> {
                if (listaC.get(0).getContainerId() == Integer.valueOf(vbox.getId())) {
                    if (!tamannoCompleto) {
                        listaC.forEach(carta -> {
                            carta.toSlowFormat();
                            carta.setRotate(carta.getPosicion());
                        });
                    }
                    vbox.getChildren().addAll(listaC);
                }
            });
        });
    }
}
