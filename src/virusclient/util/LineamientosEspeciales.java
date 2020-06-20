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
import virusclient.model.Jugador;
import virusclient.model.MarcoCarta;

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
        long numeroMedicinas = cartaList.stream().filter(carta -> "Medicina".equals(carta.getTipo())).count();
        return (cartaBase.getColor().equals(cartaJugada.getColor()) || "Multicolor".equals(cartaJugada.getColor())) && numeroMedicinas < 2;
    }

    public static List<MarcoCarta> jugarEnCampoEnemigo(VBox puñoCartas, Carta cartaJuego, Jugador jugadorRecidente, Jugador enemigo) {
        jugadorRecidente.removerCartaDeMano(cartaJuego);
        cartaJuego.setContainerId(Integer.valueOf(puñoCartas.getId()));
        enemigo.recibirCartaEnemiga(cartaJuego);
        cartaJuego.toSlowFormat();
        puñoCartas.getChildren().add(cartaJuego);
        List<Carta> cartaList = new ArrayList(puñoCartas.getChildren());
        long numeroVirus = cartaList.stream().filter(carta -> "Virus".equals(carta.getTipo())).count();
        long numeroMedicinas = cartaList.stream().filter(carta -> "Medicina".equals(carta.getTipo())).count();
        cartaList.clear();
        if (numeroVirus == 2) {
            cartaList.add((Carta) puñoCartas.getChildren().remove(puñoCartas.getChildren().size() - 1));
            cartaList.add((Carta) puñoCartas.getChildren().remove(puñoCartas.getChildren().size() - 1));
            cartaList.add((Carta) puñoCartas.getChildren().remove(puñoCartas.getChildren().size() - 1));
        } else if (numeroMedicinas == 1) {
            cartaList.add((Carta) puñoCartas.getChildren().remove(puñoCartas.getChildren().size() - 1));
            cartaList.add((Carta) puñoCartas.getChildren().remove(puñoCartas.getChildren().size() - 1));
        }
        enemigo.removeCartasJugadas(cartaList);
        List<MarcoCarta> toReturn = new ArrayList();
        cartaList.forEach(cart -> toReturn.add(new MarcoCarta(cart.getTipo(), cart.getColor(), cart.getContainerId(), cart.getPosicion())));
        return toReturn;
    }

    public static boolean puedeUsarMedicina(Carta cartaJugada, VBox puñoCartas) {
        List<Carta> cartaList = new ArrayList(puñoCartas.getChildren());
        long numeroMedicinas = cartaList.stream().filter(carta -> "Medicina".equals(carta.getTipo())).count();
        Carta cartaBase = cartaList.get(0);
        return (cartaBase.getColor().equals(cartaJugada.getColor()) || "Multicolor".equals(cartaJugada.getColor())) && numeroMedicinas < 2;
    }
}
