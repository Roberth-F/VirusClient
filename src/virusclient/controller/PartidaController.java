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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import virusclient.model.Actualizacion;
import virusclient.model.Carta;
import virusclient.util.LineamientosGenerales;
import virusclient.model.MarcoCarta;
import virusclient.model.Jugador;
import virusclient.util.AppContext;
import virusclient.util.ComunicadorConRespuesta;
import virusclient.util.ComunicadorSinRespuesta;
import virusclient.util.GraficacionDeCampoJuego;
import virusclient.util.LineamientosEspeciales;
import virusclient.util.MensajePopUp;
import virusclient.util.TbxControl;

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
    @FXML
    private Button btnCambiarTurno;
    @FXML
    private VBox vbCartaEnemiga1;
    @FXML
    private VBox vbCartaEnemiga2;
    @FXML
    private VBox vbCartaEnemiga4;
    @FXML
    private VBox vbCartaEnemiga5;
    @FXML
    private VBox vbCartaEnemiga3;
    private List<Jugador> listJugadores;
    private Carta cartaJugadaActual;
    private final List<VBox> campoJuego = new ArrayList();
    private final List<VBox> campoEnemigo = new ArrayList();
    private final Jugador jugadorResidente = (Jugador) AppContext.getInstance().get("jugador");
    private Jugador enemigoSeleccionado;
    @FXML
    private Label lblIdentificadorEnJuego;

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
        campoEnemigo.addAll(Arrays.asList(vbCartaEnemiga1, vbCartaEnemiga2, vbCartaEnemiga3, vbCartaEnemiga4, vbCartaEnemiga5));
        campoJuego.forEach(container -> container.setSpacing(-120));
        campoEnemigo.forEach(container -> container.setSpacing(-100));
        activarEventosDragDropCampoEnemigo();
        this.cargarDatosJugador();
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

    public void refrescarDatosDeJuego(Actualizacion act) {
        listJugadores.forEach(jugador -> {
            act.getRefresherList().forEach(refresher -> {
                jugador.copyCarts(refresher);
            });
        });
        jugadorResidente.copyCarts(Jugador.extraerDeLista(act.getRefresherList(), jugadorResidente.getNombre()));
        actualizarGraficos();
    }

    private void actualizarGraficos() {
        jugadorResidente.refrescarCartasVisuales(true);
        listJugadores.forEach(jug -> jug.refrescarCartasVisuales(false));
        refrescarBarraDeCartasPropias();
        refrescarBarraDeContrincantes();
        GraficacionDeCampoJuego.graficarEnCampoCaliente(campoJuego, jugadorResidente, true);
    }

    public void refrescarBarraDeCartasPropias() {
        vBoxCartas.getChildren().clear();
        jugadorResidente.getCartasActuales().forEach(carta -> {
            vBoxCartas.getChildren().add(carta);
        });
        vBoxCartas.getChildren().forEach(cart -> {
            cart.setOnDragDetected((event) -> {
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
                if (LineamientosGenerales.puedeJugar(cartaJugadaActual, act, jugadorResidente.getCartasJugadas())) {
                    event.acceptTransferModes(TransferMode.MOVE);
                }
                event.consume();
            });
        });
        campoJuego.forEach(act -> {
            act.setOnDragDropped(event -> {
                Dragboard db = event.getDragboard();
                LineamientosGenerales.setJugando(true);
                if (db.hasImage()) {
                    event.setDropCompleted(true);
                    btnCambiarTurno.setDisable(true);
                    cartaJugadaActual.setOnDragDetected(null);
                    List<MarcoCarta> cartasDesecho = LineamientosGenerales.jugarEnEspacioPropio(act, cartaJugadaActual, jugadorResidente);
                    desecharCartas(cartasDesecho);
                    enviarActualizacionDeJuego();
                }
                event.consume();
            });
        });
    }

    public void activarEventosDragDropCampoEnemigo() {
        campoEnemigo.forEach(vbox -> {
            vbox.setOnDragOver(event -> {
                if (LineamientosEspeciales.puedeJugarAqui(cartaJugadaActual, vbox)) {
                    event.acceptTransferModes(TransferMode.MOVE);
                }
                event.consume();
            });
        });
        campoEnemigo.forEach(vbox -> {
            vbox.setOnDragDropped(event -> {
                Dragboard db = event.getDragboard();
                if (db.hasImage()) {
                    LineamientosGenerales.setJugando(true);
                    event.setDropCompleted(true);
                    btnCambiarTurno.setDisable(true);
                    cartaJugadaActual.setOnDragDetected(null);
                    List<MarcoCarta> cartasDesecho = LineamientosEspeciales.jugarEnCampoEnemigo(vbox, cartaJugadaActual, jugadorResidente, enemigoSeleccionado);
                    desecharCartas(cartasDesecho);
                    enviarActualizacionDeJuego();
                    event.consume();
                }
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
                hBoxJugadores.getChildren().forEach(label -> label.setStyle("-fx-background-color: #455A64"));
                labelPerfilContricante.setStyle("-fx-background-color: #521200");
                labelContricante.setText(jug.getNombre() + "(Enemigo)");
                labelContricante.setGraphic(new ImageView(new Image("virusclient/resources/imagenesAvatar/" + jug.getNombAvatar())));
                enemigoSeleccionado = jug;
                GraficacionDeCampoJuego.graficarEnCampoCaliente(campoEnemigo, jug, false);
            });
            hBoxJugadores.getChildren().add(labelPerfilContricante);
        });
    }

    public void cargarDatosJugador() {
        ImageView perfilJugador = new ImageView();
        lblIdentificadorEnJuego.setText(jugadorResidente.getNombre());
        perfilJugador.setImage(new Image("virusclient/resources/imagenesAvatar/" + jugadorResidente.getNombAvatar()));
        lblIdentificadorEnJuego.setGraphic(perfilJugador);
    }

    @FXML
    private void onClickMazo(MouseEvent event) {
        if (LineamientosGenerales.puedeTomarCartas(jugadorResidente)) {
            MarcoCarta resp = new ComunicadorConRespuesta().solicitarCarta();
            jugadorResidente.getCartasLogicasActuales().add(resp);
            jugadorResidente.refrescarCartasVisuales(true);
            refrescarBarraDeCartasPropias();
            enviarActualizacionDeJuego();
            if (LineamientosGenerales.isJugando() && LineamientosGenerales.esMomentoDeCambiarTurno(jugadorResidente)) {
                onClickCambiarTurno(new ActionEvent());
            }
        }
    }

    @FXML
    private void onDragOverDesecho(DragEvent event) {
        if (LineamientosGenerales.puedeDesecharCartas()) {
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }
        event.consume();
    }

    @FXML
    private void onDragDropedDesecho(DragEvent event) {
        Dragboard db = event.getDragboard();
        if (db.hasImage()) {
            LineamientosGenerales.setBotoCartas(true);
            event.setDropCompleted(true);
            jugadorResidente.getCartasActuales().remove(cartaJugadaActual);
            MarcoCarta toDesecho = cartaJugadaActual.toLogicCart();
            jugadorResidente.refrescarCartasLogicas();
            desecharCartas(Arrays.asList(toDesecho));
            refrescarBarraDeCartasPropias();
            enviarActualizacionDeJuego();
        }
        event.consume();
    }

    public void turnoDeJugar(Actualizacion act) {
        LineamientosGenerales.enTurno(true);
        btnCambiarTurno.setDisable(false);
        new MensajePopUp().notifyMensajeInformacion("Atencion", "Es tu turno de jugar");
    }

    public void desecharCartas(List<MarcoCarta> cartaList) {
        if (!cartaList.isEmpty()) {
            new ComunicadorSinRespuesta().desecharCartas(cartaList);
        }
    }

    @FXML
    private void onClickCambiarTurno(ActionEvent event) {
        btnCambiarTurno.setDisable(true);
        LineamientosGenerales.enTurno(false);
        new MensajePopUp().notifyMensajeInformacion("Atencion", "Tu turno ha acabado");
        new ComunicadorSinRespuesta().cambiarTurno();
    }

    private void enviarActualizacionDeJuego() {
        List<Jugador> updateLis = new ArrayList();
        updateLis.addAll(listJugadores);
        updateLis.add(jugadorResidente);
        new ComunicadorSinRespuesta().actualizarContrincantes(updateLis);
    }

    public void declararGanador(Actualizacion act) {
        AppContext.getInstance().set("ganador", act.getGanador());
        AppContext.getInstance().delete("jugador");
        TbxControl.getInstance().view("SalaDeGanador");
    }
}
