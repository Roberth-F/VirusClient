/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package virusclient.model;

import com.google.gson.annotations.SerializedName;
import javafx.scene.image.ImageView;

/**
 *
 * @author rober
 */
public class Carta extends ImageView {
 
    @SerializedName("tipoCarta")
    String tipoCarta;
    @SerializedName("color")
    String color;
      private String getColor(){
    return  color;
   }
    public Carta(String tipo,String color){
     this.color=color;
     this.tipoCarta=tipo; 
    }

   private String getTipo(){
    return  tipoCarta;
   }

    
    
    
    //AQUI ES LALO
   
}
