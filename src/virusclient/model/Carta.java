/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package virusclient.model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author rober
 */
public class Carta extends ImageView {

    String tipoCarta;
    String color;

    public Carta(String tipo, String color, boolean cartasTamañoCompleto) {
        this.color = color;
        this.tipoCarta = tipo;
        if ("Sincolor".equals(color)) {
            this.setImage(new Image("virusclient/resources/cartas/" + tipo + ".png"));
        } else {
            this.setImage(new Image("virusclient/resources/cartas/" + tipo + color + ".png"));
        }
        this.setFitHeight(cartasTamañoCompleto ? 140 : 110);
        this.setFitWidth(cartasTamañoCompleto ? 110 : 85);
    }

    public String getColor() {
        return color;
    }

    public String getTipo() {
        return tipoCarta;
    }

    public MarcoCarta toLogicCart() {
        return new MarcoCarta(this.tipoCarta, this.color);
    }
}
