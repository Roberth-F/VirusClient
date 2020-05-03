/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package virusclient.model;

/**
 *
 * @author Roberth
 */
public class Jugador {

    private String Nombre;
    private String nombAvatar;

    public Jugador(String Nombre, String nombAvatar) {
        this.Nombre = Nombre;
        this.nombAvatar = nombAvatar;
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

}
