/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package virusclient.model;

import com.google.gson.annotations.SerializedName;

/**
 *
 * @author LordLalo
 */
public class MarcoCarta {

    @SerializedName("tipoCarta")
    private String tipo;
    @SerializedName("numeroCarta")
    private int cantidad;
    @SerializedName("color")
    String color;

    public String getColor() {
        return color;
    }

    public MarcoCarta(String tipo, String color) {
        this.color = color;
        this.tipo = tipo;
    }

    public void setNumeroDeCarta(int numeroCarta) {
        this.cantidad = numeroCarta;
    }

    public int getNumeroCarta() {
        return cantidad;
    }

    public void setTipo(String tipoCarta) {//Organo=1/virus=2/medicina=3/tratamiento=4
        this.tipo = tipoCarta;

    }

    public String getTipo() {
        return tipo;
    }

    public Carta toVisualCart(boolean tamannoCompleto) {
        return new Carta(this.tipo, this.color, tamannoCompleto);
    }

}
