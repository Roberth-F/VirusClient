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
    Jugador actual = (Jugador) AppContext.getInstance().get("jugador");
    ImageView ima = new ImageView();
    MarcoCarta cartaJugadaActual;
    List<Jugador> listaJ;
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
    private final List<VBox> campoJuego = new ArrayList();
    @FXML
    private Button btnSolicitar;

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
        this.CargarJugadores();
        this.eventoColocarCartasDeJugador();
    }

    @Override
    public void reOpen() {

    }

    public void eventoColocarCartasDeJugador() {
        campoJuego.forEach(act -> {
            act.setOnDragOver((DragEvent event) -> {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                event.consume();
            });
        });
        campoJuego.forEach(act -> {
            act.setOnDragDropped(value -> {
                Dragboard db = value.getDragboard();
                if (db.hasImage()) {
                    value.setDropCompleted(true);
                    ImageView cartaColocada = new ImageView(db.getImage());
                    cartaColocada.setFitHeight(100);
                    cartaColocada.setFitWidth(100);
                    act.getChildren().add(cartaColocada);
                    vBoxCartas.getChildren().clear();
                    eliminarCartaDeMazo();
                   new ComunicadorSinRespuesta().ActualizarCartas(listaJ);
                    //actualizarListasDeJuegoActualizada();
                }
                value.consume();
            });
        });
    }
//Eliminar las cartas despues de la lista de cartas de jugador

 public void eliminarCartaDeMazo() {
        listaJ.forEach(jugador -> {
            if (jugador.getNombre().equals(actual.getNombre())) {
                for (int x = 0; x < jugador.verLista().size(); x++) {
                    if (jugador.verLista().get(x).getTipo().equals(cartaJugadaActual.getTipo()) && jugador.verLista().get(x).getColor().equals(cartaJugadaActual.getColor())) {
                        jugador.verCartasTablero().add(cartaJugadaActual);
                        jugador.verLista().remove(x);
                        x=jugador.verLista().size();
                    }

                }
            }
        });
    }
//Actuliza la lista de cartas de jugador

    public void actualizarListasDeJuego(Actualizacion act) {
vBoxCartas.getChildren().clear();
hBoxJugadores.getChildren().clear();
        listaJ = act.getlistaJugador();
        listaJ.forEach(jugadorD -> {
            if (actual.getNombre().equals(jugadorD.getNombre())) {
                jugadorD.verLista().forEach((MarcoCarta misCartas) -> {
                    ImageView carta = new ImageView();
                    carta.setFitHeight(100);
                    carta.setFitWidth(100);
                    if(misCartas.getColor().equals("Sincolor")){
                     carta.setImage(new Image("virusclient/resources/cartas/" + misCartas.getTipo()+".png"));
                    }else{
                        carta.setImage(new Image("virusclient/resources/cartas/" + misCartas.getTipo()+misCartas.getColor()+".png"));
                    }

                    carta.setOnDragDetected((MouseEvent event) -> {
                        ima = carta;
                        cartaJugadaActual = misCartas;
                        Dragboard db = carta.startDragAndDrop(TransferMode.COPY);
                        ClipboardContent content = new ClipboardContent();
                        content.putImage(carta.getImage());
                        content.putString("");

                        db.setContent(content);
                        event.consume();
                    });
                    vBoxCartas.getChildren().add(carta);
                });
            } else {
//Crea las perfiles
                ImageView perfilJugador1 = new ImageView();
                Label labelPerfilContricante = new Label();
                labelPerfilContricante.setText(jugadorD.getNombre());
                perfilJugador1.setImage(new Image("virusclient/resources/imagenesAvatar/" + jugadorD.getNombAvatar()));
                labelPerfilContricante.setGraphic(perfilJugador1);
                //Evento de click para los  perfiles de jugador
                labelPerfilContricante.setOnMouseClicked((MouseEvent t) -> {
                    labelContricante.setText(jugadorD.getNombre());
                    labelContricante.setGraphic(new ImageView(new Image("virusclient/resources/imagenesAvatar/" + jugadorD.getNombAvatar())));
                    labelCartasContricantes.setText("Cartas Actuales:" + jugadorD.verLista().size());
                });
                hBoxJugadores.getChildren().add(labelPerfilContricante);
            }
        });
    }

    public void actualizarListasDeJuegoActualizada() {
//        List<Jugador> 
//        listaJ = act.getlistaJugador();
        listaJ.forEach(jugadorD -> {
            if (actual.getNombre().equals(jugadorD.getNombre())) {
                jugadorD.verLista().forEach((MarcoCarta misCartas) -> {
                    ImageView carta = new ImageView();
                    carta.setFitHeight(100);
                    carta.setFitWidth(100);
                    carta.setOnDragDetected((MouseEvent event) -> {
                        ima = carta;
                        cartaJugadaActual = misCartas;
                        Dragboard db = carta.startDragAndDrop(TransferMode.COPY);
                        ClipboardContent content = new ClipboardContent();
                        content.putImage(carta.getImage());
                        content.putString("");

                        db.setContent(content);
                        event.consume();
                    });
                    vBoxCartas.getChildren().add(carta);
                });
            }
        });
    }

    public void CargarJugadores() {
        ImageView perfilJugador = new ImageView();
        Label nombre = new Label();
        nombre.setText(actual.getNombre());
        perfilJugador.setImage(new Image("virusclient/resources/imagenesAvatar/" + actual.getNombAvatar()));
        nombre.setGraphic(perfilJugador);
        panelPropio.getChildren().add(nombre);
    }

    @FXML
    private void OnActionSolicitarCarta(ActionEvent event) {
       MarcoCarta resp = new ComunicadorConRespuesta().solicitarCarta();
        listaJ.forEach(jugador -> {
            if (jugador.getNombre().equals(actual.getNombre())) {
                jugador.misCartas(resp);

            }
        });

        new ComunicadorSinRespuesta().ActualizarCartas(listaJ);   
    }

}
