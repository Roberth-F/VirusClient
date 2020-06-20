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
    private int containerId;
    @SerializedName("color")
    String color;
    @SerializedName("posicion")
    private int posicion;

    public String getColor() {
        return color;
    }

    public MarcoCarta(String tipo, String color, int containerId, int poscion) {
        this.color = color;
        this.tipo = tipo;
        this.containerId = containerId;
        this.posicion = poscion;
    }

    public void setContainerId(int numeroCarta) {
        this.containerId = numeroCarta;
    }

    public int getContainerId() {
        return containerId;
    }

    public void setTipo(String tipoCarta) {//Organo=1/virus=2/medicina=3/tratamiento=4
        this.tipo = tipoCarta;

    }

    public String getTipo() {
        return tipo;
    }

    public Carta toVisualCart(boolean tamannoCompleto) {
        return new Carta(this.tipo, this.color, tamannoCompleto, this.containerId, this.posicion);
    }

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }
    
}
