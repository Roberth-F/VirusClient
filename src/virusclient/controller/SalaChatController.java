/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package virusclient.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import virusclient.util.Mensaje;
import virusclient.model.Actualizacion;
import virusclient.model.ChatGlobal;
import virusclient.model.Jugador;
import virusclient.util.AppContext;
import virusclient.util.ComunicadorSinRespuesta;

/**
 * FXML Controller class
 *
 * @author rober
 */
public class SalaChatController implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private Button btnEnviar;
    @FXML
    private TextField txtMensaje;
    @FXML
    private VBox vboxContenedor;
    private List<ChatGlobal> chat = new ArrayList();

    @FXML
    private ImageView fo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        AppContext.getInstance().set("SalaChat", this);

        root.setOnMouseClicked(event -> event.consume());

        new ComunicadorSinRespuesta().ActualizarMensajes(chat);
    }

    @FXML
    private void enviarOnAction(ActionEvent event) {
        Jugador actual = (Jugador) AppContext.getInstance().get("jugador");
        if (txtMensaje.getText().length() < 40) {
            if (txtMensaje.getText().length() != 0) {
                ChatGlobal CHA = new ChatGlobal(actual.getNombre(), txtMensaje.getText());
                chat.add(CHA);
                if (chat.size() == 20) {
                    chat.remove(0);
                }
                new ComunicadorSinRespuesta().ActualizarMensajes(chat);
                txtMensaje.clear();

            }
        } else {
            Mensaje m = new Mensaje();
            m.show(Alert.AlertType.WARNING, "Mensaje", "Cantidad de caracteres sobrepasada");
        }
    }

    public void actualizarListaMensaje(Actualizacion act) {
        vboxContenedor.getChildren().clear();
        chat = act.getlistaMensajes();
        chat.forEach(chatN -> {
            HBox contenedorMsj = new HBox();
            contenedorMsj.setAlignment(Pos.CENTER);
            contenedorMsj.setStyle("     -fx-text-fill:white;\n"
                    + "    -fx-background-color: #2E9AFE;\n"
                    + "    -fx-border-radius:10;\n"
                    + "    -fx-background-radius: 10px;\n"
                    + "    -fx-font: bold 9pt \"Bahnschrift\" ;\n"
                    + "    -fx-border-color: black;");
            Label emisor = new Label();
            emisor.setStyle("     -fx-text-fill:white;");
            emisor.setText(chatN.getEmisor() + ":");
            contenedorMsj.getChildren().add(emisor);
            Label msj = new Label();
            msj.setStyle("     -fx-text-fill:black;");
            msj.setText(chatN.getMensaje());
            contenedorMsj.getChildren().add(msj);

            vboxContenedor.getChildren().add(contenedorMsj);
        });

    }

}
