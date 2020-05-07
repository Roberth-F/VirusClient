/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package virusclient.model;

import com.google.gson.Gson;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import virusclient.util.AppContext;

/**
 *
 * @author Roberth
 */
public class Escuchador {

    private ServerSocket serverSocket;    //Socket que utiliza para la comunicación.
    private DataInputStream informacion;  //Información que recibe el esperador de parte del cliente.
    private Socket canalComunicacion;

    public Escuchador() {
        try {
            this.serverSocket = new ServerSocket(0);
            AppContext.getInstance().set("mainPort", serverSocket.getLocalPort());
        } catch (IOException IO) {
            System.err.println("NO SE PUDO HABILITAR PUERTO INICIAL DE ESCUCHA");
            Logger.getLogger(Escuchador.class.getName()).log(Level.SEVERE, IO.getMessage(), IO);
        }
    }

    public Actualizacion escuchar() {
        try {
            canalComunicacion = serverSocket.accept();
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

    public void detener() {
        try {
            Socket sock = new Socket(InetAddress.getLocalHost().getHostAddress(), (int) AppContext.getInstance().get("mainPort"));
            DataOutputStream datos = new DataOutputStream(sock.getOutputStream());
            Actualizacion actStop = new Actualizacion();
            actStop.toStop();
            String Json = new Gson().toJson(actStop);
            datos.writeUTF(Json);
            sock.getOutputStream().close();
            datos.close();
            sock.close();
        } catch (IOException ex) {
            Logger.getLogger(Escuchador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
