/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package virusclient.util;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.VBox;
import virusclient.model.Carta;

;

/**
 *
 * @author roberth 😊
 */
public class LineamientosEspeciales {

    public static boolean puedeJugarAqui(Carta cartaJugada, VBox puñoCartas) {
        if (LineamientosGenerales.puedeAtacarAlEnemigo() && !puñoCartas.getChildren().isEmpty()) {
            switch (cartaJugada.getTipo()) {
                case "Virus":
                    return puedeLanzarVirus(cartaJugada, puñoCartas);
                default:
                    return false;
            }
        } else {
            return false;
        }
    }

    private static boolean puedeLanzarVirus(Carta cartaJugada, VBox puñoCartas) {
        List<Carta> cartaList = new ArrayList(puñoCartas.getChildren());
        Carta cartaBase = cartaList.get(0);
        return cartaBase.getColor().equals(cartaJugada.getColor()) || "Multicolor".equals(cartaJugada.getColor());
    }

    public static boolean puedeUsarMedicina(Carta cartaJugada, VBox puñoCartas) {
        List<Carta> cartaList = new ArrayList(puñoCartas.getChildren());
        Carta cartaBase = cartaList.get(0);
        return cartaBase.getColor().equals(cartaJugada.getColor()) || "Multicolor".equals(cartaJugada.getColor());
    }
}
