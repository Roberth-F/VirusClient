/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package virusclient.model;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    public ArrayList<List<MarcoCarta>> cartasLogicasJugadas = new ArrayList();
    public transient ArrayList<Carta> cartasActuales = new ArrayList();         //trancient evita que Gson pueda ver estos campos
    public transient ArrayList<List<Carta>> cartasJugadas = new ArrayList();

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
            cartasActuales.add(new Carta(act.getTipo(), act.getColor(), cartasTamañoCompleto, act.getContainerId(), act.getPosicion()));
        });
        cartasLogicasJugadas.forEach(list -> {
            List<Carta> toAdd = new ArrayList();
            list.forEach(cart -> {
                toAdd.add(new Carta(cart.getTipo(), cart.getColor(), cartasTamañoCompleto, cart.getContainerId(), cart.getPosicion()));
            });
            cartasJugadas.add(toAdd);
        });
    }

    public void refrescarCartasLogicas() {
        cartasLogicasActuales.clear();
        cartasLogicasJugadas.clear();
        cartasActuales.forEach(cart -> {
            cartasLogicasActuales.add(new MarcoCarta(cart.getTipo(), cart.getColor(), cart.getContainerId(), cart.getPosicion()));
        });
        cartasJugadas.forEach(list -> {
            List<MarcoCarta> toAdd = new ArrayList();
            list.forEach(cart -> {
                toAdd.add(new MarcoCarta(cart.getTipo(), cart.getColor(), cart.getContainerId(), cart.getPosicion()));
            });
            cartasLogicasJugadas.add(toAdd);
        });
    }

    public void ponerCartaEnLaMesa(Carta cartaBuscada) {
        cartasActuales.remove(cartaBuscada);
        for (List<Carta> lisC : cartasJugadas) {
            if (lisC.get(0).getContainerId() == cartaBuscada.getContainerId()) {
                lisC.add(cartaBuscada);
                return;
            }
        }
        List<Carta> listC = new ArrayList(Arrays.asList(cartaBuscada));
        cartasJugadas.add(listC);
        refrescarCartasLogicas();
    }

    public void removerCartaDeMano(Carta carta) {
        cartasActuales.remove(carta);
        refrescarCartasLogicas();
    }

    public void recibirCartaEnemiga(Carta carta) {
        for (List<Carta> lisC : cartasJugadas) {
            if (lisC.get(0).getContainerId() == carta.getContainerId()) {
                lisC.add(carta);
                refrescarCartasLogicas();
                return;
            }
        }
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

    public ArrayList<MarcoCarta> getCartasLogicasActuales() {
        return cartasLogicasActuales;
    }

    public void setCartasLogicasActuales(ArrayList<MarcoCarta> cartasLogicasActuales) {
        this.cartasLogicasActuales = cartasLogicasActuales;
    }

    public ArrayList<List<MarcoCarta>> getCartasLogicasJugadas() {
        return cartasLogicasJugadas;
    }

    public void setCartasLogicasJugadas(ArrayList<List<MarcoCarta>> cartasLogicasJugadas) {
        this.cartasLogicasJugadas = cartasLogicasJugadas;
    }

    public ArrayList<List<Carta>> getCartasJugadas() {
        return cartasJugadas;
    }

    public void setCartasJugadas(ArrayList<List<Carta>> cartasJugadas) {
        this.cartasJugadas = cartasJugadas;
    }

    public ArrayList<Carta> getCartasActuales() {
        return cartasActuales;
    }

    public void setCartasActuales(ArrayList<Carta> cartasActuales) {
        this.cartasActuales = cartasActuales;
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

    public static Jugador extraerDeLista(List<Jugador> jug, String nombre) {
        Jugador toReturn = null;
        for (Jugador jugador : jug) {
            if (jugador.getNombre().equals(nombre)) {
                toReturn = jugador;
                break;
            }
        }
        jug.remove(toReturn);
        return toReturn;
    }

    public void copyCarts(Jugador jug) {
        if (jug.getNombre().equals(this.getNombre())) {
            this.cartasLogicasActuales.clear();
            this.cartasLogicasJugadas.clear();
            this.cartasLogicasActuales.addAll(jug.getCartasLogicasActuales());
            this.cartasLogicasJugadas.addAll(jug.getCartasLogicasJugadas());
        }
    }

    public void removeCartasJugadas(List<Carta> cartas) {
        cartasJugadas.forEach(cList -> {
            cartas.forEach(carta -> {
                cList.removeIf(cart -> cart.getTipo().equals(carta.getTipo()) && cart.getContainerId() == carta.getContainerId());
            });
        });
        refrescarCartasLogicas();
    }

}
