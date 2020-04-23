/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package virusclient.util;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/**
 *
 * @author Roberth
 */
public class clasePrueba implements Serializable{
    @SerializedName("nombre")
    private String Nombre;
    @SerializedName("edad")
    private Integer Edad;
    
    
    public clasePrueba() {
        this.Edad = 22;
        this.Nombre = "Roberth";
    }
    
}
