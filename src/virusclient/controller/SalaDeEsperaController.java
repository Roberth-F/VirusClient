/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package virusclient.controller;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.events.JFXDrawerEvent;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import unaplanilla2.util.Mensaje;
import virusclient.model.Jugador;
import virusclient.util.AppContext;
import virusclient.util.ComunicadorConRespuesta;
import virusclient.util.ComunicadorSinRespuesta;
import virusclient.util.Respuesta;
import virusclient.util.TbxControl;
import virusclient.util.ThreadCollector;

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
    @FXML
    private JFXDrawer DrawLContMenu;
    @FXML
    private JFXHamburger HamburgerLMBtn;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
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
                Platform.runLater(() -> {
                    if (((Jugador) AppContext.getInstance().get("jugador")).isHost()) {
                        btnIniciarJuego.setText("Iniciar juego      ");
                        btnIniciarJuego.setDisable(false);
                    }
                });
                if (AppContext.getInstance().get("nuevosJugadores") != null) {
                    synchronized (AppContext.getInstance()) {
                        cargarUsuarios();
                        AppContext.getInstance().set("nuevosJugadores", null);
                    }
                }
                //if extra para cambio de pantalla
                //TbxControl.getInstance().view("");
                if ((AppContext.getInstance().get("cargarPartida")) != null) {

                    System.out.println("entre");
                    Platform.runLater(() -> {
                        TbxControl.getInstance().view("Partida");
                        tiempoActualizar.cancel();
                    });
                }
            }
        };
        tiempoActualizar = new Timer();
        tiempoActualizar.schedule(ejecutor, 0, 2000);
        ThreadCollector.getInstance().addTimer(tiempoActualizar);
        jugadores.getChildren().add(new Label("Jugadores conectados..."));
        activarSalaChat();

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
        btnIniciarJuego.setDisable(true);
        if (((Jugador) AppContext.getInstance().get("jugador")).isHost()) {
            Respuesta resp = new ComunicadorConRespuesta().iniciarJuego();
            if (!resp.getEstado()) {
                new Mensaje().show(Alert.AlertType.WARNING, "Atención", resp.getMensaje());
            } else {

                new ComunicadorSinRespuesta().forzarInicioDeJuego();
            }
        } else {
            new ComunicadorSinRespuesta().votarPorInicioDeJuego();
        }
    }

    private void activarSalaChat() {
        HamburgerSlideCloseTransition HamTrans = new HamburgerSlideCloseTransition(HamburgerLMBtn);
        HamTrans.setRate(-1);
        try {
            AnchorPane anchorP = (AnchorPane) FXMLLoader.load(getClass().getResource("/virusclient/view/SalaChat.fxml"));
            DrawLContMenu.setSidePane(anchorP);
        } catch (IOException ex) {
            Logger.getLogger(SalaDeEsperaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        EventHandler<JFXDrawerEvent> amburgerEvent = (jfxae) -> {
            HamTrans.setRate(HamTrans.getRate() * -1);
            HamTrans.play();
        };
        EventHandler<MouseEvent> LateralMenuLauncher = (MouseEvent event) -> {
            if (this.DrawLContMenu.isShown()) {
                this.DrawLContMenu.close();
            } else if (!(event.getSource() instanceof AnchorPane)) {
                this.DrawLContMenu.open();
            }
        };
        root.setOnMouseClicked(LateralMenuLauncher);
        HamburgerLMBtn.setOnMouseClicked(LateralMenuLauncher);
        DrawLContMenu.setOnDrawerOpening(amburgerEvent);
        DrawLContMenu.setOnDrawerClosing(amburgerEvent);
    }
}
