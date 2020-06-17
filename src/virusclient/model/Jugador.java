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
    public ArrayList<MarcoCarta> cartasLogicasActuales = new ArrayList();
    public ArrayList<MarcoCarta> cartasLogicasJugadas = new ArrayList();
    public transient ArrayList<Carta> cartasActuales = new ArrayList();         //trancient evita que Gson pueda ver estos campos
    public transient ArrayList<Carta> cartasJugadas = new ArrayList();

    public Jugador(String Nombre, String nombAvatar, boolean host) {
        this.Nombre = Nombre;
        this.nombAvatar = nombAvatar;
        this.host = host;
    }

    /**
     * Combierte los marcos de las cartas en cartas reales y graficables.
     *
     * @param cartasTamañoCompleto Indica si las cartas son de tamaño completo o
     * reducido
     */
    public void refrescarCartasVisuales(boolean cartasTamañoCompleto) {
        cartasActuales.clear();
        cartasJugadas.clear();
        cartasLogicasActuales.forEach(act -> {
            cartasActuales.add(new Carta(act.getTipo(), act.getColor(), cartasTamañoCompleto));
        });
        cartasLogicasJugadas.forEach(cart -> {
            cartasJugadas.add(new Carta(cart.getTipo(), cart.getColor(), cartasTamañoCompleto));
        });
    }

    public void ponerCartaEnLaMesa(Carta cartaBuscada) {
        cartasActuales.remove(cartaBuscada);
        cartasJugadas.add(cartaBuscada);
        refrescarCartasLogias();
    }

    public void refrescarCartasLogias() {
        cartasLogicasActuales.clear();
        cartasLogicasJugadas.clear();
        cartasActuales.forEach(cart -> {
            cartasLogicasActuales.add(new MarcoCarta(cart.getTipo(), cart.getColor()));
        });
        cartasJugadas.forEach(cart -> {
            cartasLogicasJugadas.add(new MarcoCarta(cart.getTipo(), cart.getColor()));
        });
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

    public void misCartas(MarcoCarta cartas) {
        cartasLogicasActuales.add(cartas);
    }

    public ArrayList<MarcoCarta> verLista() {

        return cartasLogicasActuales;
    }

    public void CartasTablero(MarcoCarta cartas) {
        cartasLogicasJugadas.add(cartas);
    }

    public ArrayList<MarcoCarta> verCartasTablero() {

        return cartasLogicasJugadas;
    }

    public ArrayList<MarcoCarta> getCartasLogicasActuales() {
        return cartasLogicasActuales;
    }

    public void setCartasLogicasActuales(ArrayList<MarcoCarta> cartasLogicasActuales) {
        this.cartasLogicasActuales = cartasLogicasActuales;
    }

    public ArrayList<MarcoCarta> getCartasLogicasJugadas() {
        return cartasLogicasJugadas;
    }

    public void setCartasLogicasJugadas(ArrayList<MarcoCarta> cartasLogicasJugadas) {
        this.cartasLogicasJugadas = cartasLogicasJugadas;
    }

    public ArrayList<Carta> getCartasActuales() {
        return cartasActuales;
    }

    public void setCartasActuales(ArrayList<Carta> cartasActuales) {
        this.cartasActuales = cartasActuales;
    }

    public ArrayList<Carta> getCartasJugadas() {
        return cartasJugadas;
    }

    public void setCartasJugadas(ArrayList<Carta> cartasJugadas) {
        this.cartasJugadas = cartasJugadas;
    }

    /**
     * Dado que cartasActuales y cartasJugadas son de tipo trancient entonces
     * Gson no la inicializa, por eso la necesidad de este método. (Inicializa
     * dichas listas)
     */
    public void activate() {
        this.cartasActuales = new ArrayList();
        this.cartasJugadas = new ArrayList();
    }

}
