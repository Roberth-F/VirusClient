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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import unaplanilla2.util.Mensaje;
import virusclient.model.Jugador;
import virusclient.util.AppContext;
import virusclient.util.TbxControl;
import virusclient.util.ComunicadorConRespuesta;
import virusclient.util.Respuesta;

/**
 * FXML Controller class
 *
 * @author Lalo
 */
public class DatosJugadorController extends Rechargeable implements Initializable {

    @FXML
    private TextField txtNombreJugador;
    @FXML
    private Button btnCancelar;
    String nombreJugador;
    @FXML
    private AnchorPane root;
    @FXML
    private ImageView imgAvatar;
    @FXML
    private ImageView imgFondoRegistro;
    String NombreAvatar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        NombreAvatar = "avt14.png";
        imgFondoRegistro.fitHeightProperty().bind(root.heightProperty());
        imgFondoRegistro.fitWidthProperty().bind(root.widthProperty());
        imgAvatar.setOnMouseClicked((event) -> {
            AppContext.getInstance().set("DataGame", this);
            TbxControl.getInstance().viewInWindow("AvatarSelector", null, "Selector", false);
        });
    }

    @Override
    public void reOpen() {
    }

    @FXML
    private void OnActionNombreJugador(ActionEvent event) {
        nombreJugador = txtNombreJugador.getText();
        System.out.print(nombreJugador);
    }

    @FXML
    private void onActionCancelar(ActionEvent event) {
        TbxControl.getInstance().goBack();
    }

    @FXML
    private void OnActionSiguiente(ActionEvent event) {
        if (txtNombreJugador.getText().length() != 0) {
            ComunicadorConRespuesta serv = new ComunicadorConRespuesta();
            Respuesta resp;
            boolean host = false;
            if ((Boolean) AppContext.getInstance().get("unirse")) {
                resp = serv.unirApartExistente(9999, txtNombreJugador.getText(), getNombreAvatar());
            } else {
                resp = serv.crearNuevaPartida(9999, txtNombreJugador.getText(), getNombreAvatar());
                host = true;
            }
            if (resp.getEstado() == true) {
                AppContext.getInstance().set("jugador", new Jugador(txtNombreJugador.getText(), NombreAvatar, host));
                TbxControl.getInstance().view("SalaDeEspera");
            } else {
                new Mensaje().show(Alert.AlertType.WARNING, "Atención", resp.getMensaje());
            }
        }
    }

    public void colocarNuevaImagenJugador(Image img, String nombre) {
        imgAvatar.setImage(img);
        NombreAvatar = nombre;
    }

    public String getNombreAvatar() {
        return NombreAvatar;
    }

}
