/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package virusclient.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import virusclient.util.FlowController;

/**
 * FXML Controller class
 *
 * @author Lalo
 */
public class MenuIncioController implements Initializable {

    @FXML
    private Button btnCrearPartida;
    @FXML
    private Button btnUnirsePartida;
    @FXML
    private HBox root;
    @FXML
    private Label labelTitulo;
    @FXML
    private Button btnAccercaDe;
    @FXML
    private ImageView fondo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        fondo.fitHeightProperty().bind(root.heightProperty());
        fondo.fitWidthProperty().bind(root.widthProperty());
    }    

    @FXML
    private void OnActionCrearPartida(ActionEvent event) {
 
         FlowController.mostrarView("DatosJugador");
        
    }
    
}
