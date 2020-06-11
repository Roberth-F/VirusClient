/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package virusclient.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javax.swing.text.html.CSS;
import static jdk.nashorn.internal.objects.NativeRegExp.source;
import unaplanilla2.util.Mensaje;
import virusclient.model.Actualizacion;
import virusclient.model.Cartas;
import virusclient.model.Jugador;
import virusclient.util.AppContext;

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
    @FXML
    private FlowPane cartasJugadas;
    ImageView ima = new ImageView();
    Cartas cartaJugadaActual;
    List<Jugador> listaJ;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        AppContext.getInstance().set("SalaDeJuego", this);
        this.CargarJugadores();
        this.evento();
    }

    @Override
    public void reOpen() {

    }

    public void evento() {
        cartasJugadas.setOnDragOver(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {

                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                event.consume();

            }
        });
// cartasJugadas.setOnDragOver(value->{
//          Dragboard db = value.getDragboard();
//            if (db.hasImage()) {
//                value.acceptTransferModes(TransferMode.COPY);
//            }
//
//            value.consume();
//          });
        cartasJugadas.setOnDragDropped(value -> {
            Dragboard db = value.getDragboard();
            if (db.hasImage()) {
                value.setDropCompleted(true);
                ImageView cartaColocada = new ImageView(db.getImage());
                cartaColocada.setFitHeight(100);
                cartaColocada.setFitWidth(100);
                cartasJugadas.getChildren().add(cartaColocada);
                //    vBoxCartas.getChildren().remove(ima);
                vBoxCartas.getChildren().clear();
                eliminarCartaDeMazo();
                actualizarListasDeJuego2();

            }
            value.consume();

        });

    }

    public void eliminarCartaDeMazo() {
        listaJ.forEach(jugador -> {
            if (jugador.getNombre().equals(actual.getNombre())) {
//        jugador.verLista().forEach(cartas->{
//           
//         if(cartas.equals(cartaJugadaActual)){
//            jugador.verLista().remove(cartasJugadas);
//             new Mensaje().show(Alert.AlertType.WARNING, "Atención","Elimine a:"+cartas.getNombreCarta());
//         }
//        
//        });
//           
                for (int x = 0; x < jugador.verLista().size(); x++) {
                    if (jugador.verLista().get(x).getNombreCarta().equals(cartaJugadaActual.getNombreCarta()) && jugador.verLista().get(x).getNumeroCarta() == cartaJugadaActual.getNumeroCarta()) {
                    jugador.verLista().remove(x);
                    }

                }
            }
        });
    }

    public void actualizarListasDeJuego(Actualizacion act) {
//        List<Jugador> 
        listaJ = act.getlistaJugador();
        listaJ.forEach(jugadorD -> {
            if (actual.getNombre().equals(jugadorD.getNombre())) {
                jugadorD.verLista().forEach((Cartas misCartas) -> {
                    ImageView carta = new ImageView();
                    carta.setFitHeight(100);
                    carta.setFitWidth(100);
                    if (misCartas.getTipo() == 1) {
                        carta.setImage(new Image("virusclient/resources/cartas/" + misCartas.getNombreCarta() + ".png"));
                    } else {
                        if (misCartas.getTipo() == 2) {
                            carta.setImage(new Image("virusclient/resources/cartas/" + misCartas.getNombreCarta() + "2.png"));
                        } else {
                            if (misCartas.getTipo() == 3) {
                                carta.setImage(new Image("virusclient/resources/cartas/" + misCartas.getNombreCarta() + "3.png"));
                            } else {
                                carta.setImage(new Image("virusclient/resources/cartas/" + "tratamiento" + ".png"));
                            }
                        }
                    }
                    carta.setOnDragDetected(new EventHandler<MouseEvent>() {
                        public void handle(MouseEvent event) {
                            ima = carta;
                            cartaJugadaActual = misCartas;
                            Dragboard db = carta.startDragAndDrop(TransferMode.COPY);
                            ClipboardContent content = new ClipboardContent();
                            content.putImage(carta.getImage());
                            content.putString("");

                            db.setContent(content);
                            event.consume();
                        }
                    });
                    vBoxCartas.getChildren().add(carta);
                });
            } else {
//Crea las perfiles
                ImageView perfilJugador1 = new ImageView();
                Label lab = new Label();
                lab.setText(jugadorD.getNombre());
                perfilJugador1.setImage(new Image("virusclient/resources/imagenesAvatar/" + jugadorD.getNombAvatar()));

                lab.setGraphic(perfilJugador1);
                hBoxJugadores.getChildren().add(lab);
            }
        });
    }

    public void actualizarListasDeJuego2() {
//        List<Jugador> 
//        listaJ = act.getlistaJugador();
        listaJ.forEach(jugadorD -> {
            if (actual.getNombre().equals(jugadorD.getNombre())) {
                jugadorD.verLista().forEach((Cartas misCartas) -> {
                    ImageView carta = new ImageView();
                    carta.setFitHeight(100);
                    carta.setFitWidth(100);
                    if (misCartas.getTipo() == 1) {
                        carta.setImage(new Image("virusclient/resources/cartas/" + misCartas.getNombreCarta() + ".png"));
                    } else {
                        if (misCartas.getTipo() == 2) {
                            carta.setImage(new Image("virusclient/resources/cartas/" + misCartas.getNombreCarta() + "2.png"));
                        } else {
                            if (misCartas.getTipo() == 3) {
                                carta.setImage(new Image("virusclient/resources/cartas/" + misCartas.getNombreCarta() + "3.png"));
                            } else {
                                carta.setImage(new Image("virusclient/resources/cartas/" + "tratamiento" + ".png"));
                            }
                        }
                    }
                    carta.setOnDragDetected(new EventHandler<MouseEvent>() {
                        public void handle(MouseEvent event) {
                            ima = carta;
                            cartaJugadaActual = misCartas;
                            Dragboard db = carta.startDragAndDrop(TransferMode.COPY);
                            ClipboardContent content = new ClipboardContent();
                            content.putImage(carta.getImage());
                            content.putString("");

                            db.setContent(content);
                            event.consume();
                        }
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

}
