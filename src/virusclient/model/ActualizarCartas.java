/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package virusclient.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;

/**
 *
 * @author LordLalo
 */

public class ActualizarCartas {
     private String listaJugadores;
    public void actualizarListaJugadores(String jugList) {
       // metodo = "nuevosJugadores";
        listaJugadores = jugList;
    }

    public List<Jugador> getlistaJugador() {
        Type typeListJug = new TypeToken<List<Jugador>>() {
        }.getType();
        List listaJugadoresUnidos = new Gson().fromJson(listaJugadores, typeListJug);
        return listaJugadoresUnidos;
    }
}
