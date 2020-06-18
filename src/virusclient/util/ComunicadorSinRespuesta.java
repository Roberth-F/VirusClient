/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package virusclient.util;

import com.google.gson.Gson;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;
import virusclient.model.Jugador;
import virusclient.model.MarcoCarta;

import virusclient.model.Peticion;

/**
 * Las instancias de esta clase envían peticiones al servidor, con la salvedad
 * de que no reciben ninguna resppuesta por parte del servidor.
 *
 * @author Roberth 😊
 */
public class ComunicadorSinRespuesta {

    private final String serverIp = (String) AppContext.getInstance().get("ServerIP");

    public void votarPorInicioDeJuego() {
        try {
            Socket sock = new Socket(serverIp, 7777);
            DataOutputStream datos = new DataOutputStream(sock.getOutputStream());
            Peticion pet = new Peticion();
            pet.yoEstoyListo();
            String Json = new Gson().toJson(pet);
            datos.writeUTF(Json);
            sock.getOutputStream().close();
            datos.close();
            sock.close();
        } catch (UnknownHostException UHE) {
            Logger.getLogger(ComunicadorConRespuesta.class.getName()).log(Level.SEVERE, null, UHE);
        } catch (IOException ex) {
            Logger.getLogger(ComunicadorSinRespuesta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void solicitarSalida() {
        if (AppContext.getInstance().get("jugador") != null) {
            try {
                Socket sock = new Socket(serverIp, 7777);
                DataOutputStream datos = new DataOutputStream(sock.getOutputStream());
                Peticion pet = new Peticion();
                pet.peticionSalida();
                String Json = new Gson().toJson(pet);
                datos.writeUTF(Json);
                sock.getOutputStream().close();
                datos.close();
                sock.close();
            } catch (UnknownHostException UHE) {
                Logger.getLogger(ComunicadorConRespuesta.class.getName()).log(Level.SEVERE, null, UHE);
            } catch (IOException ex) {
                Logger.getLogger(ComunicadorSinRespuesta.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Pide al servidor que inidique a todos los equipos conetados que deben
     * pasar al modo de juego.
     */
    public void forzarInicioDeJuego() {
        try {
            Socket sock = new Socket(serverIp, 7777);
            DataOutputStream datos = new DataOutputStream(sock.getOutputStream());
            Peticion pet = new Peticion();
            pet.peticionDeJuego();
            String Json = new Gson().toJson(pet);
            datos.writeUTF(Json);
            sock.getOutputStream().close();
            datos.close();
            sock.close();
        } catch (UnknownHostException UHE) {
            Logger.getLogger(ComunicadorConRespuesta.class.getName()).log(Level.SEVERE, null, UHE);
        } catch (IOException io) {
            Logger.getLogger(ComunicadorConRespuesta.class.getName()).log(Level.SEVERE, null, io);
        }
    }

    public void actualizarContrincantes(List<Jugador> jugadores) {
        try {
            Socket sock = new Socket(serverIp, 7777);
            DataOutputStream datos = new DataOutputStream(sock.getOutputStream());
            Peticion pet = new Peticion();
            pet.addActualizacion(jugadores);
            String Json = new Gson().toJson(pet);
            datos.writeUTF(Json);
            sock.getOutputStream().close();
            datos.close();
            sock.close();
        } catch (UnknownHostException UHE) {
            Logger.getLogger(ComunicadorConRespuesta.class.getName()).log(Level.SEVERE, null, UHE);
        } catch (IOException ex) {
            Logger.getLogger(ComunicadorSinRespuesta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void desecharCartas(List<MarcoCarta> desecho) {
        try {
            Socket sock = new Socket(serverIp, 7777);
            DataOutputStream datos = new DataOutputStream(sock.getOutputStream());
            Peticion pet = new Peticion();
            pet.desecharCartas(desecho);
            String Json = new Gson().toJson(pet);
            datos.writeUTF(Json);
            sock.getOutputStream().close();
            datos.close();
            sock.close();
        } catch (UnknownHostException UHE) {
            Logger.getLogger(ComunicadorConRespuesta.class.getName()).log(Level.SEVERE, null, UHE);
        } catch (IOException ex) {
            Logger.getLogger(ComunicadorSinRespuesta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void cambiarTurno() {
        try {
            Socket sock = new Socket(serverIp, 7777);
            DataOutputStream datos = new DataOutputStream(sock.getOutputStream());
            Peticion pet = new Peticion();
            pet.toCambioDeTurno();
            String Json = new Gson().toJson(pet);
            datos.writeUTF(Json);
            sock.getOutputStream().close();
            datos.close();
            sock.close();
        } catch (UnknownHostException UHE) {
            Logger.getLogger(ComunicadorConRespuesta.class.getName()).log(Level.SEVERE, null, UHE);
        } catch (IOException ex) {
            Logger.getLogger(ComunicadorSinRespuesta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
