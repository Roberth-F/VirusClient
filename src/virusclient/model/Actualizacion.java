/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package virusclient.model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Una instancia de esta clase permite crear y enviar una actualizació al los
 * clientes.
 *
 * @author Roberth 😊
 */
public class Actualizacion {

    @SerializedName("method")
    private String metodo;          //Metodo que se va a ejecutar
    @SerializedName("class")
    private String modulo;           //Módulo donde está el método
    @SerializedName("nuevosJugadores")
    private String listaJugadores;
    @SerializedName("refresher")
    private List<Jugador> refresherList;
    @SerializedName("Ganador")
    private Jugador ganador;
    @SerializedName("nuevosMensajes")
    private String listaMensajes;

    /**
     * Prepara una actualización de lista de jugadores.
     *
     * @param jugList Lista de jugadores convertida al formato <b>Json</b>
     */
    public void actualizarListaJugadores(String jugList) {
        metodo = "nuevosJugadores";
        listaJugadores = jugList;
    }

    public List<Jugador> getlistaJugador() {
        Type typeListJug = new TypeToken<List<Jugador>>() {
        }.getType();
        List listaJugadoresUnidos = new Gson().fromJson(listaJugadores, typeListJug);
        return listaJugadoresUnidos;
    }

    public List<ChatGlobal> getlistaMensajes() {
        Type typeListJug = new TypeToken<List<ChatGlobal>>() {
        }.getType();
        List listachat = new Gson().fromJson(listaMensajes, typeListJug);
        return listachat;
    }

    /**
     * Genera actualizacion con orden de detener el escuchador del juego.
     */
    public void toStop() {
        this.metodo = "stopAll";
    }

    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }

    public String getModulo() {
        return modulo;
    }

    public void setModulo(String clase) {
        this.modulo = clase;
    }

    public List<Jugador> getRefresherList() {
        return refresherList;
    }

    public void setRefresherList(List<Jugador> refresherList) {
        this.refresherList = refresherList;
    }

    public Jugador getGanador() {
        return ganador;
    }
}
