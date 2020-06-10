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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javax.swing.text.html.CSS;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        AppContext.getInstance().set("SalaDeJuego", this);
        //this.CargarJugadores();

    }

    @Override
    public void reOpen() {

    }

    public void actualizarListasDeJuego(Actualizacion act) {
        List<Jugador> listaJ = act.getlistaJugador();
        listaJ.forEach(jugadorD -> {
            if (actual.getNombre().equals(jugadorD.getNombre())) {
                jugadorD.verLista().forEach(misCartas -> {
                    if (misCartas.getTipo() == 1) {
                        ImageView cart = new ImageView();
                        cart.setImage(new Image("virusclient/resources/cartas/" + "cerebro.jpg"));
                        vBoxCartas.getChildren().add(cart);
                    } else {
                        Label car = new Label();
                        car.setText(misCartas.getNombreCarta() + " " + misCartas.getTipo() + " " + misCartas.getNumeroCarta());
                        vBoxCartas.getChildren().add(car);
                    }
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

    public void CargarJugadores() {
        ImageView perfilJugador = new ImageView();

        Label nombre = new Label();
        nombre.setText(actual.getNombre());
        perfilJugador.setImage(new Image("virusclient/resources/imagenesAvatar/" + actual.getNombAvatar()));
        nombre.setGraphic(perfilJugador);
        panelPropio.getChildren().add(nombre);

    }

}
