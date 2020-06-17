/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package virusclient.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import virusclient.model.Actualizacion;
import virusclient.model.Carta;
import virusclient.model.MarcoCarta;
import virusclient.model.Jugador;
import virusclient.util.AppContext;
import virusclient.util.ComunicadorConRespuesta;
import virusclient.util.ComunicadorSinRespuesta;
import virusclient.util.Respuesta;

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
    @FXML
    private Label labelContricante;
    @FXML
    private Label labelCartasContricantes;
    @FXML
    private VBox vbCartaMesa1;
    @FXML
    private VBox vbCartaMesa2;
    @FXML
    private VBox vbCartaMesa4;
    @FXML
    private VBox vbCartaMesa3;
    @FXML
    private VBox vbCartaMesa5;
    private List<Jugador> listJugadores;
    private Carta cartaJugadaActual;
    private final List<VBox> campoJuego = new ArrayList();
    private final Jugador jugadorResidente = (Jugador) AppContext.getInstance().get("jugador");
    @FXML
    private FlowPane flowMesaContrincante;
    @FXML
    private ImageView ImgMazo;
    @FXML
    private ImageView imgDescarte;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        AppContext.getInstance().set("SalaDeJuego", this);
        campoJuego.addAll(Arrays.asList(vbCartaMesa1, vbCartaMesa2, vbCartaMesa3, vbCartaMesa4, vbCartaMesa5));
        this.cargarDatosJugador();
        //this.eventoColocarCartasDeJugador();
    }

    @Override
    public void reOpen() {

    }

    public void cargarDatosInicioJuego(Actualizacion act) {
        listJugadores = new ArrayList();
        List<Jugador> jugadores = act.getlistaJugador();
        jugadores.forEach(jug -> {
            if (jug.getNombre().equals(jugadorResidente.getNombre())) {
                jugadorResidente.setCartasLogicasActuales(jug.getCartasLogicasActuales());
            } else {
                jug.activate();                              //Inicializa litas visuales de cartas
                listJugadores.add(jug);
            }
        });
        actualizarGraficos();
    }

    private void actualizarGraficos() {
        jugadorResidente.refrescarCartasVisuales(true);
        listJugadores.forEach(jug -> jug.refrescarCartasVisuales(false));
        refrescarBarraDeCartasPropias();
        refrescarBarraDeContrincantes();
    }

    public void refrescarBarraDeCartasPropias() {
        vBoxCartas.getChildren().clear();
        jugadorResidente.getCartasActuales().forEach(carta -> {
            vBoxCartas.getChildren().add(carta);
        });
        vBoxCartas.getChildren().forEach(cart -> {
            cart.setOnDragDetected((MouseEvent event) -> {
                cartaJugadaActual = (Carta) cart;
                Dragboard db = cart.startDragAndDrop(TransferMode.MOVE);
                ClipboardContent content = new ClipboardContent();
                content.putImage(((Carta) cart).getImage());
                content.putString("");
                db.setContent(content);
                event.consume();
            });
        });
        campoJuego.forEach(act -> {
            act.setOnDragOver(event -> {
                event.acceptTransferModes(TransferMode.MOVE);
                event.consume();
            });
        });
        campoJuego.forEach(act -> {
            act.setOnDragDropped(event -> {
                Dragboard db = event.getDragboard();
                if (db.hasImage()) {
                    event.setDropCompleted(true);
                    jugadorResidente.ponerCartaEnLaMesa(cartaJugadaActual);
                    act.getChildren().add(cartaJugadaActual);
                }
                event.consume();
            });
        });
    }

    public void refrescarBarraDeContrincantes() {
        hBoxJugadores.getChildren().clear();
        listJugadores.forEach(jug -> {
            ImageView perfilJugador1 = new ImageView(new Image("virusclient/resources/imagenesAvatar/" + jug.getNombAvatar()));
            Label labelPerfilContricante = new Label(jug.getNombre());
            labelPerfilContricante.setGraphic(perfilJugador1);
            labelPerfilContricante.setOnMouseClicked((MouseEvent event) -> {
                labelContricante.setText(jug.getNombre());
                labelContricante.setGraphic(new ImageView(new Image("virusclient/resources/imagenesAvatar/" + jug.getNombAvatar())));
                flowMesaContrincante.getChildren().clear();
                flowMesaContrincante.getChildren().addAll(jug.getCartasJugadas());
            });
            hBoxJugadores.getChildren().add(labelPerfilContricante);
        });
    }

    private void OnActionSolicitarCarta(ActionEvent event) {
        MarcoCarta resp = new ComunicadorConRespuesta().solicitarCarta();
        listJugadores.forEach(jugador -> {
            if (jugador.getNombre().equals(jugadorResidente.getNombre())) {
                jugador.misCartas(resp);
            }
        });
        new ComunicadorSinRespuesta().ActualizarCartas(listJugadores);
    }

    public void cargarDatosJugador() {
        ImageView perfilJugador = new ImageView();
        Label nombre = new Label();
        nombre.setText(jugadorResidente.getNombre());
        perfilJugador.setImage(new Image("virusclient/resources/imagenesAvatar/" + jugadorResidente.getNombAvatar()));
        nombre.setGraphic(perfilJugador);
        panelPropio.getChildren().add(nombre);
    }

    @FXML
    private void onClickMazo(MouseEvent event) {
        MarcoCarta resp = new ComunicadorConRespuesta().solicitarCarta();
        jugadorResidente.getCartasLogicasActuales().add(resp);
        jugadorResidente.refrescarCartasVisuales(true);
        refrescarBarraDeCartasPropias();
    }
}
