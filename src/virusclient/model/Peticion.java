/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package virusclient.model;

import com.google.gson.annotations.SerializedName;

/**
 *
 * @author Roberth
 */
public class Peticion {
    
    @SerializedName("content")
    private Object contenido;    //Deberá ser cambiado por tipo de objeto personalizado (POJO) que se puede convertir en Json
    @SerializedName("action")
    private String accion;
    

    public Peticion(Object dato) {
        this.contenido = dato;
    }

    public Object getContenido() {
        return contenido;
    }

    public void setContenido(Object contenido) {
        this.contenido = contenido;
    }

}
