/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package virusclient.model;

import com.google.gson.annotations.SerializedName;

/**
 *
 * @author LordLalo
 */
public class ChatGlobal {

    @SerializedName("emisor")
    String emisor;
    @SerializedName("mensaje")
    String mensaje;

    public String getEmisor() {
        return emisor;
    }

    public void setEmisor(String emisor) {
        this.emisor = emisor;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public ChatGlobal(String emisor, String mensaje) {
        this.emisor = emisor;
        this.mensaje = mensaje;
    }
}
