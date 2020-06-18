/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package virusclient.util;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

public class MensajePopUp {

    public MensajePopUp() {

    }

    public void notifyMensajeInformacion(String titulo, String contenido) {
        Notifications construirNotify = Notifications.create()
                .title(titulo)
                .text(contenido)
                .position(Pos.BOTTOM_RIGHT)
                .hideAfter(Duration.seconds(5));
        construirNotify.darkStyle();
        construirNotify.showInformation();
    }

    public void notifyMensajeError(String titulo, String contenido) {
        Notifications construirNotify = Notifications.create()
                .title(titulo)
                .text(contenido)
                .position(Pos.BOTTOM_RIGHT)
                .hideAfter(Duration.seconds(7))
                .onAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        System.out.println("notificacion pulsada");
                    }
                });
        construirNotify.darkStyle();
        construirNotify.showError();
    }

    public void notifyMensajeConfirmacion(String titulo, String contenido) {
        Notifications construirNotify = Notifications.create()
                .title(titulo)
                .text(contenido)
                .position(Pos.BOTTOM_RIGHT)
                .hideAfter(Duration.seconds(5));
        construirNotify.darkStyle();
        construirNotify.showConfirm();
    }

    public void notifyMensajeImagenPersonalizada(String titulo, String contenido, String rutaImg) {
        //   Ejemplo de ruta :: "/tarea/resources/NombreImagen.extension"
        Image imagen = new Image(rutaImg);
        ImageView img = new ImageView(imagen);
        img.setFitWidth(130);
        img.setFitHeight(100);
        Notifications construirNotify = Notifications.create()
                .title(titulo)
                .text(contenido)
                .graphic(img)
                .position(Pos.BOTTOM_RIGHT)
                .hideAfter(Duration.seconds(5));
        construirNotify.darkStyle();
        construirNotify.show();
    }

    public void enviarReaccion(String titulo, String contenido, String img) {
        //   Ejemplo de ruta :: "/tarea/resources/NombreImagen.extension"
        ImageView m;
        if (img != null) {
            m = new ImageView(new Image("virus_cliente/resources/reacciones/" + img + ".png"));
        } else {
            m = new ImageView(new Image("virus_cliente/resources/tenor.gif"));
        }

        m.setFitWidth(130);
        m.setFitHeight(100);
        Notifications construirNotify = Notifications.create()
                .title(titulo)
                .text(contenido)
                .graphic(m)
                .position(Pos.BOTTOM_RIGHT)
                .hideAfter(Duration.seconds(5));
        construirNotify.darkStyle();
        construirNotify.show();
    }

    public void notifyMensajeCheck(String titulo, String contenido) {
        //   Ejemplo de ruta :: "/tarea/resources/NombreImagen.extension"
        Image imagen = new Image("/mapsgps/resources/check.png");//mapsgps\resources
        Notifications construirNotify = Notifications.create()
                .title(titulo)
                .text(contenido)
                .graphic(new ImageView(imagen))
                .position(Pos.BOTTOM_RIGHT)
                .hideAfter(Duration.seconds(5));
        construirNotify.darkStyle();
        construirNotify.show();
    }
}
