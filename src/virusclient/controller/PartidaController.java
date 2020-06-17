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
    private final Jugador actual = (Jugador) AppContext.getInstance().get("jugador");
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

    public void eventoColocarCartasDeJugador() {
//        campoJuego.forEach(act -> {
//            act.setOnDragOver((DragEvent event) -> {
//                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
//                event.consume();
//            });
//        });
//        
    }
//Eliminar las cartas despues de la lista de cartas de jugador

    public void eliminarCartaDeMazo() {
//        listJugadores.forEach(jugador -> {
//            if (jugador.getNombre().equals(actual.getNombre())) {
//                for (int x = 0; x < jugador.verLista().size(); x++) {
//                    if (jugador.verLista().get(x).getTipo().equals(cartaJugadaActual.getTipo()) && jugador.verLista().get(x).getColor().equals(cartaJugadaActual.getColor())) {
//                        jugador.verCartasTablero().add(cartaJugadaActual);
//                        jugador.verLista().remove(x);
//                        x = jugador.verLista().size();
//                    }
//                }
//            }
//        });
    }

    public void cargarDatosInicioJuego(Actualizacion act) {
        listJugadores = new ArrayList();
        List<Jugador> jugadores = act.getlistaJugador();
        jugadores.forEach(jug -> {
            if (jug.getNombre().equals(actual.getNombre())) {
                actual.setCartasLogicasActuales(jug.getCartasLogicasActuales());
            } else {
                jug.activate();                              //Inicializa litas visuales de cartas
                listJugadores.add(jug);
            }
        });
        actualizarGraficos();
    }

    private void actualizarGraficos() {
        vBoxCartas.getChildren().clear();
        hBoxJugadores.getChildren().clear();
        actual.cargarCartasVisuales(true);
        listJugadores.forEach(jug -> jug.cargarCartasVisuales(false));
        refrescarBarraDeCartasPropias();
        refrescarBarraDeContrincantes();
    }

    public void refrescarBarraDeCartasPropias() {
        actual.getCartasActuales().forEach(carta -> {
            vBoxCartas.getChildren().add(carta);
        });
        vBoxCartas.getChildren().forEach(cart -> {
            cart.setOnDragDetected((MouseEvent event) -> {
                cartaJugadaActual = (Carta)cart;
                Dragboard db = cart.startDragAndDrop(TransferMode.MOVE);
                ClipboardContent content = new ClipboardContent();
                content.putImage(((Carta)cart).getImage());
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
                    actual.ponerCartaEnLaMesa(cartaJugadaActual);
                    act.getChildren().add(cartaJugadaActual);
                }
                event.consume();
            });
        });
    }

    public void refrescarBarraDeContrincantes() {
        listJugadores.forEach(jug -> {
            ImageView perfilJugador1 = new ImageView(new Image("virusclient/resources/imagenesAvatar/" + jug.getNombAvatar()));
            Label labelPerfilContricante = new Label(jug.getNombre());
            labelPerfilContricante.setGraphic(perfilJugador1);
            labelPerfilContricante.setOnMouseClicked((MouseEvent event) -> {
                labelContricante.setText(jug.getNombre());
                labelContricante.setGraphic(new ImageView(new Image("virusclient/resources/imagenesAvatar/" + jug.getNombAvatar())));
                //labelCartasContricantes.setText("Cartas Actuales:" + jug.verLista().size());
                flowMesaContrincante.getChildren().clear();
                flowMesaContrincante.getChildren().addAll(jug.getCartasJugadas());
            });
            hBoxJugadores.getChildren().add(labelPerfilContricante);
        });
    }

    private void OnActionSolicitarCarta(ActionEvent event) {
        MarcoCarta resp = new ComunicadorConRespuesta().solicitarCarta();
        listJugadores.forEach(jugador -> {
            if (jugador.getNombre().equals(actual.getNombre())) {
                jugador.misCartas(resp);
            }
        });
        new ComunicadorSinRespuesta().ActualizarCartas(listJugadores);
    }

    public void activarFuncuinesDeArrastreDeCartas(Carta carta) {

    }

    public void cargarDatosJugador() {
        ImageView perfilJugador = new ImageView();
        Label nombre = new Label();
        nombre.setText(actual.getNombre());
        perfilJugador.setImage(new Image("virusclient/resources/imagenesAvatar/" + actual.getNombAvatar()));
        nombre.setGraphic(perfilJugador);
        panelPropio.getChildren().add(nombre);
    }

}
