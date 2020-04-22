/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package virusclient.util;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Roberth
 */
public class ServerComunicator {

    public void enviar(String dato) {

        OutputStream bufferSalida;
        try {
            Socket canalComunicacion = new Socket("192.168.1.5", 7777);
            DataOutputStream datos = new DataOutputStream(canalComunicacion.getOutputStream());
            String mensaje = "Hola Mundo!\n";
            datos.writeUTF(mensaje);
            canalComunicacion.getOutputStream().close();
            datos.close();
            canalComunicacion.close();
        } catch (UnknownHostException ex) {
            Logger.getLogger(ServerComunicator.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        } catch (IOException ex) {

        }
    }
}
