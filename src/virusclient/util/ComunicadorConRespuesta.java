/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package virusclient.util;

import com.google.gson.Gson;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import virusclient.model.Peticion;

/**
 * Una instancia de esta clase permite al juego cominicarse por medio de
 * peticiones con el servidor. La comunicación es bidireccional, es decir que se
 * recibirá una respuesta inmediata a la peticion.
 *
 * @author Roberth
 */
public class ComunicadorConRespuesta {

    /**
     * Intenta unir al jugador a la partida actual en caso de existir una en el
     * servidor y el juego no halla iniciado aun.
     *
     * @param puertoEscucha Integer con el puerto <b>PERMANENTE</b> de escucha
     * del jugador.
     * @param NombJugador String que represente el nombre con el que el jugador
     * se identificará frente a sus oponentes.
     * @return Respuerta del servidor a la petición hecha.
     */
    public Respuesta unirApartExistente(int puertoEscucha, String NombJugador) {
        try {
            ServerSocket servSock = new ServerSocket();
            Thread enviardor = new Thread(() -> {  //Envia petición a servidor.
                try {
                    Thread.sleep(10);              //Pausa imperseptible para dar tiempo al otro hilo.
                    Socket sock = new Socket("192.168.1.5", 7777);
                    DataOutputStream datos = new DataOutputStream(sock.getOutputStream());
                    Peticion pet = new Peticion();
                    pet.toStartGame("unirsePertida", puertoEscucha, NombJugador, servSock.getLocalPort());
                    String Json = new Gson().toJson(pet);
                    datos.writeUTF(Json);
                    sock.getOutputStream().close();
                    datos.close();
                    sock.close();
                } catch (UnknownHostException UHE) {
                    Logger.getLogger(ComunicadorConRespuesta.class.getName()).log(Level.SEVERE, null, UHE);
                } catch (IOException | InterruptedException EX) {
                    Logger.getLogger(ComunicadorConRespuesta.class.getName()).log(Level.SEVERE, null, EX);
                }
            });
            enviardor.start();                                  //Arranca hilo enviador mientras el otro se pone en espera.
            Socket canalComunic = servSock.accept();
            DataInputStream informacion = new DataInputStream(canalComunic.getInputStream());
            String Json = informacion.readUTF();
            Respuesta resp = new Gson().fromJson(Json, Respuesta.class);
            canalComunic.getInputStream().close();
            informacion.close();
            canalComunic.close();
            return resp;
        } catch (UnknownHostException UE) {
            Logger.getLogger(ComunicadorConRespuesta.class.getName()).log(Level.SEVERE, null, UE);
            return new Respuesta(false, "Ha ocurrido un error inesperado");
        } catch (IOException IO) {
            Logger.getLogger(ComunicadorConRespuesta.class.getName()).log(Level.SEVERE, null, IO);
            return new Respuesta(false, "Ha ocurrido un error inesperado");
        }
    }

    /**
     * En caso de que en el servidor no exista ya una partida abierta, permite
     * abrir una.
     *
     * @param puertoEscucha Integer con el puerto <b>PERMANENTE</b> de escucha
     * del jugador.
     * @param NombJugador String que represente el nombre con el que el jugador
     * se identificará frente a sus oponentes.
     * @return Respuerta del servidor a la petición hecha.
     */
    public Respuesta crearNuevaPartida(int puertoEscucha, String NombJugador) {
        try {
            ServerSocket servSock = new ServerSocket(0);
            Thread enviardor = new Thread(() -> {  //Envia petición a servidor.
                try {
                    Thread.sleep(10);              //Pausa imperseptible para dar tiempo al otro hilo.
                    Socket sock = new Socket("192.168.1.5", 7777);
                    DataOutputStream datos = new DataOutputStream(sock.getOutputStream());
                    Peticion pet = new Peticion();
                    pet.toStartGame("iniciarPartida", puertoEscucha, NombJugador, servSock.getLocalPort());
                    String Json = new Gson().toJson(pet);
                    datos.writeUTF(Json);
                    sock.getOutputStream().close();
                    datos.close();
                    sock.close();
                } catch (UnknownHostException UHE) {
                    Logger.getLogger(ComunicadorConRespuesta.class.getName()).log(Level.SEVERE, null, UHE);
                } catch (IOException | InterruptedException EX) {
                    Logger.getLogger(ComunicadorConRespuesta.class.getName()).log(Level.SEVERE, null, EX);
                }
            });
            enviardor.start();                                  //Arranca hilo enviador mientras el otro se pone en espera.
            Socket canalComunic = servSock.accept();
            DataInputStream informacion = new DataInputStream(canalComunic.getInputStream());
            String Json = informacion.readUTF();
            Respuesta resp = new Gson().fromJson(Json, Respuesta.class);
            canalComunic.getInputStream().close();
            informacion.close();
            canalComunic.close();
            return resp;
        } catch (UnknownHostException UE) {
            Logger.getLogger(ComunicadorConRespuesta.class.getName()).log(Level.SEVERE, null, UE);
            return new Respuesta(false, "Ha ocurrido un error inesperado");
        } catch (IOException IO) {
            Logger.getLogger(ComunicadorConRespuesta.class.getName()).log(Level.SEVERE, null, IO);
            return new Respuesta(false, "Ha ocurrido un error inesperado");
        }
    }

}
