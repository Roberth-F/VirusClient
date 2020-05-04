/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package virusclient.model;

import com.google.gson.Gson;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Roberth
 */
public class Escuchador {

    private final int port;               //puerto de escucha.
    private ServerSocket serverSocket;    //Socket que utiliza para la comunicación.
    private DataInputStream informacion;  //Información que recibe el esperador de parte del cliente.

    public Escuchador(int port) {
        this.port = port;
        try {
            this.serverSocket = new ServerSocket(9999);
        } catch (IOException IO) {
            System.err.println("NO SE PUDO HABILITAR PUERTO INICIAL DE ESCUCHA");
            Logger.getLogger(Escuchador.class.getName()).log(Level.SEVERE, IO.getMessage(), IO);
        }
    }
    
       public Actualizacion escuchar() {
        try {
            Socket canalComunicacion = serverSocket.accept();
            informacion = new DataInputStream(canalComunicacion.getInputStream());
            String Json = informacion.readUTF();
            Actualizacion actualizar = new Gson().fromJson(Json, Actualizacion.class);
            System.out.println("Recibida petición -->" + actualizar.getAction() + "<--");
            canalComunicacion.getInputStream().close();
            informacion.close();
            canalComunicacion.close();
            return actualizar;
        } catch (IOException IO) {
            System.err.println("ERROR AL RECIBIR INFORMACIÓN");
            Logger.getLogger(Escuchador.class.getName()).log(Level.SEVERE, IO.getMessage(), IO);
            return null;
        }
    }

}
