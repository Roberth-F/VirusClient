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
public class Cartas {
     @SerializedName("nombreCarta")
    private String nombre;
    @SerializedName("TipoCarta:Organo=1,virus=2..etc")
    private int tipo;
    @SerializedName("Cantidad numero de carta de ese tipo cartas")
    private  int cantidad;
    public Cartas(String nombreCarta, int tipoCarta,int numeroCarta) {
        this.nombre = nombreCarta;
        this.tipo = tipoCarta;
        this.cantidad=numeroCarta;
    }
     public void setNombre(String nombre){//Corazon
   this.nombre=nombre;
   
   }
   public  void setNumeroDeCarta(int numeroCarta){
   this.cantidad=numeroCarta;
   }
   public   int getNumeroCarta(){
   return cantidad;
   }
   public void setTipo(int tipoCarta){//Organo=1/virus=2/medicina=3/tratamiento=4
    this.tipo=tipoCarta;
   
   }
   public String  getNombreCarta(){
    return nombre;
   }
   public int getTipo(){
    return tipo;
   }
}
