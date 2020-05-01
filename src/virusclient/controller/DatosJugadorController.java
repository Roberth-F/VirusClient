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
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import virusclient.util.ComunicadorConRespuesta;
import virusclient.util.FlowController;
import virusclient.util.Respuesta;

/**
 * FXML Controller class
 *
 * @author Lalo
 */
public class DatosJugadorController implements Initializable {

    @FXML
    private TextField txtNombreJugador;
    @FXML
    private Button btnCancelar;
    String nombreJugador;
    @FXML
    private HBox root;
    @FXML
    private ImageView imgAvatar;
    @FXML
    private ImageView imgFondoRegistro;
    @FXML
    private StackPane StackP;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        imgFondoRegistro.fitHeightProperty().bind(root.heightProperty());
        imgFondoRegistro.fitWidthProperty().bind(root.widthProperty());
        StackP.prefWidthProperty().bind(root.heightProperty());
    }    

    @FXML
    private void OnActionNombreJugador(ActionEvent event) {
       nombreJugador=txtNombreJugador.getText();
       System.out.print(nombreJugador);
    }

    @FXML
    private void onActionCancelar(ActionEvent event) {
    }

    @FXML
    private void OnActionSiguiente(ActionEvent event) {
        if(txtNombreJugador.getText().length()!=0){
         ComunicadorConRespuesta serv = new ComunicadorConRespuesta();
              Respuesta resp = serv.crearNuevaPartida(9999,nombreJugador);
              System.out.println(resp.getEstado());
              if(resp.getEstado()==true){
                  System.out.print(nombreJugador+"enre");
             ((SalaDeEsperaController)FlowController.getLoader("SalaDeEspera").getController()).setNombre("Lalo");
             Stage s=(Stage)root.getScene().getWindow();
             FlowController.mostrarView("SalaDeEspera");
              }
              
              
        }
    }
    
}
