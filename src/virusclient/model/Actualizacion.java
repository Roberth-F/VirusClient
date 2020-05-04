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

    @SerializedName("action")
    private String action;
    @SerializedName("nuevosJugadores")
    private String listaJugadores;

    /**
     * Prepara una actualización de lista de jugadores.
     *
     * @param json Lista de jugadores convertida al formato <b>Json</b>
     */
    public void actualizarListaJugadores(String json) {
        action = "nuevosJugadores";
        listaJugadores = json;
    }

    public List<Jugador> getlistaJugador() {
        Type typeListJug = new TypeToken<List<Jugador>>() {
        }.getType();
        List listaJugadoresUnidos = new Gson().fromJson(listaJugadores, typeListJug);
        return listaJugadoresUnidos;
    }

    public String getAction() {
        return action;
    }

    public String getListaJugadores() {
        return listaJugadores;
    }
}
