/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package virusclient.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import unaplanilla2.util.Mensaje;
import virusclient.model.Jugador;
import virusclient.util.AppContext;
import virusclient.util.ComunicadorConRespuesta;
import virusclient.util.ComunicadorSinRespuesta;
import virusclient.util.Respuesta;
import virusclient.util.ThreadCollerctor;

/**
 * FXML Controller class
 *
 * @author Lalo
 */
public class SalaDeEsperaController extends Rechargeable implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private ImageView fondoEspera;
    @FXML
    private Label nombreJugador;
    @FXML
    private ImageView perfilJugador;
    @FXML
    private VBox jugadores;
    private Timer tiempoActualizar;
    private TimerTask ejecutor;
    @FXML
    private Button btnIniciarJuego;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnIniciarJuego.setText((((Jugador) AppContext.getInstance().get("jugador")).isHost()) ? "Iniciar juego      " : "Estoy listo para jugar      ");
        fondoEspera.fitHeightProperty().bind(root.heightProperty());
        fondoEspera.fitWidthProperty().bind(root.widthProperty());
        Jugador actual = (Jugador) AppContext.getInstance().get("jugador");
        nombreJugador.setText(actual.getNombre());
        perfilJugador.setImage(new Image("virusclient/resources/imagenesAvatar/" + actual.getNombAvatar()));
        ejecutor = new TimerTask() {
            @Override
            public void run() {
                if (AppContext.getInstance().get("nuevosJugadores") != null) {
                    synchronized (AppContext.getInstance()) {
                        cargarUsuarios();
                        AppContext.getInstance().set("nuevosJugadores", null);
                    }
                }
            }
        };
        tiempoActualizar = new Timer();
        tiempoActualizar.schedule(ejecutor, 0, 2000);
        ThreadCollerctor.getInstance().addTimer(tiempoActualizar);
        jugadores.getChildren().add(new Label("Jugadores conectados..."));
    }

    public void cargarUsuarios() {
        Platform.runLater(() -> {
            jugadores.getChildren().removeIf(nodo -> ((Label) nodo).getGraphic() != null);
        });
        List<Jugador> lisJugadores = (List<Jugador>) AppContext.getInstance().get("nuevosJugadores");
        lisJugadores.forEach(actual -> {
            Label nombre = new Label();
            nombre.setText(actual.getNombre());
            ImageView avatar = new ImageView(new Image("virusclient/resources/imagenesAvatar/" + actual.getNombAvatar()));
            nombre.setGraphic(avatar);
            Platform.runLater(() -> {
                jugadores.getChildren().add(nombre);
            });
        });
        Platform.runLater(() -> {
            jugadores.getChildren().removeIf(titulo -> ((Label) titulo).getText().equals(((Jugador) AppContext.getInstance().get("jugador")).getNombre()));
        });
    }

    @Override
    public void reOpen() {

    }

    @FXML
    private void OnAcionEsotyListo(ActionEvent event) {
        if (((Jugador) AppContext.getInstance().get("jugador")).isHost()) {
            Respuesta resp = new ComunicadorConRespuesta().iniciarJuego();
            if (!resp.getEstado()) {
                new Mensaje().show(Alert.AlertType.WARNING, "Atención", resp.getMensaje());
            }
        } else {
            new ComunicadorSinRespuesta().votarPorInicioDeJuego();
            btnIniciarJuego.setDisable(true);
        }
    }
}
