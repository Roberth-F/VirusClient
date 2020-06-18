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
 * @author roberth 😊
 */
public class Carta extends ImageView {

    private String tipoCarta;
    private String color;
    private int containerId;

    public Carta(String tipo, String color, boolean cartasTamañoCompleto, int containerId) {
        this.color = color;
        this.tipoCarta = tipo;
        this.containerId = containerId;
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

    public void setTipoCarta(String tipoCarta) {
        this.tipoCarta = tipoCarta;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public MarcoCarta toLogicCart() {
        return new MarcoCarta(this.tipoCarta, this.color, this.containerId);
    }

    public int getContainerId() {
        return containerId;
    }

    public void setContainerId(int containerId) {
        this.containerId = containerId;
    }

}
