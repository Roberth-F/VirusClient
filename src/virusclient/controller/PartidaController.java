/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package virusclient.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import virusclient.model.Jugador;
import virusclient.util.AppContext;

/**
 * FXML Controller class
 *
 * @author LordLalo
 */
public class PartidaController extends Rechargeable implements Initializable {

    @FXML
    private AnchorPane panelJugadorSeleccionado;
    @FXML
    private HBox hBoxJugadores;
    @FXML
    private VBox vBoxCartas;
    @FXML
    private AnchorPane panelPropio;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
      this.CargarJudarPropio();
   
    }    

    @Override
    public void reOpen() {
        
    }
    public  void CargarJudarPropio(){
        ImageView perfilJugador=new ImageView();
         Jugador actual = (Jugador) AppContext.getInstance().get("jugador");
         Label nombre = new Label();
            nombre.setText(actual.getNombre());
         perfilJugador.setImage(new Image("virusclient/resources/imagenesAvatar/" + actual.getNombAvatar()));
         nombre.setGraphic(perfilJugador);
         hBoxJugadores.getChildren().add(nombre);
    }
    }
