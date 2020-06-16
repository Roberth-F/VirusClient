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

    @SerializedName("nombreCarta")
    private String nombre;
    @SerializedName("tipoCarta")
    private String tipo;
    @SerializedName("numeroCarta")
    private int cantidad;
   @SerializedName("color")
    String color;
      public  String getColor(){
    return  color;
   }
    public MarcoCarta(String tipo,String color){
     this.color=color;
     this.tipo=tipo; 
    }

    public MarcoCarta(String nombreCarta, String tipoCarta, int numeroCarta) {
        this.nombre = nombreCarta;
        this.tipo = tipoCarta;
        this.cantidad = numeroCarta;
    }

    public void setNombre(String nombre) {//Corazon
        this.nombre = nombre;

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

    public String getNombreCarta() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }
}
