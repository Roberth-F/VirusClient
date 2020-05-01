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
import javafx.scene.layout.AnchorPane;
import virusclient.util.TbxControl;

/**
 * FXML Controller class
 *
 * @author Lalo
 */
public class MenuIncioController extends Rechargeable implements Initializable {

    @FXML
    private Button btnCrearPartida;
    @FXML
    private Button btnUnirsePartida;
    @FXML
    private AnchorPane root;
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
        fondo.fitHeightProperty().bind(root.heightProperty());
        fondo.fitWidthProperty().bind(root.widthProperty());
    }    


    @Override
    public void reOpen() {
    }

    @FXML
    private void crearPartida(ActionEvent event) {
        TbxControl.getInstance().view("DatosJugador");
    }
    
}
