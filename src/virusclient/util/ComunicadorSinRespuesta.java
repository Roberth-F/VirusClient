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
import java.util.logging.Level;
import java.util.logging.Logger;
import virusclient.model.Peticion;

/**
 * Las instancias de esta clase envían peticiones al servidor, con la salvedad
 * de que no reciben ninguna resppuesta por parte del servidor.
 *
 * @author Roberth 😊
 */
public class ComunicadorSinRespuesta {

    public void votarPorInicioDeJuego() {
        try {
            Socket sock = new Socket("192.168.1.2", 7777);
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
}