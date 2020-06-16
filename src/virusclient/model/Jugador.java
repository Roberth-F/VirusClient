/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package virusclient.model;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

/**
 *
 * @author Roberth
 */
public class Jugador {

    @SerializedName("nombreJug")
    private String Nombre;
    @SerializedName("nombreAvt")
    private String nombAvatar;
    @SerializedName("host:")
    private boolean host;
    public ArrayList<MarcoCarta>cartasActuales=new ArrayList<>();
    public  ArrayList<MarcoCarta>cartasJugadas=new ArrayList<>();
    
    public Jugador(String Nombre, String nombAvatar, boolean host) {
        this.Nombre = Nombre;
        this.nombAvatar = nombAvatar;
        this.host = host;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getNombAvatar() {
        return nombAvatar;
    }

    public void setNombAvatar(String nombAvatar) {
        this.nombAvatar = nombAvatar;
    }

    public boolean isHost() {
        return host;
    }

    public void setHost(boolean host) {
        this.host = host;
    }
     public void misCartas(MarcoCarta cartas){
      cartasActuales.add(cartas);
    }    
    public ArrayList<MarcoCarta> verLista(){

      return cartasActuales;
    }
     public  void CartasTablero(MarcoCarta cartas ){
     cartasJugadas.add(cartas);
    }
     public ArrayList<MarcoCarta> verCartasTablero(){

      return cartasJugadas;
    }
}
