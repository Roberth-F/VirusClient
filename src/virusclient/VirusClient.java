/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package virusclient;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import virusclient.controller.PartidaController;
import virusclient.controller.SalaChatController;
import virusclient.model.Actualizacion;
import virusclient.model.Escuchador;
import virusclient.model.Jugador;
import virusclient.util.AppContext;
import virusclient.util.ComunicadorSinRespuesta;
import virusclient.util.TbxControl;
import virusclient.util.ThreadCollector;

/**
 *
 * @author Roberth
 */
public class VirusClient extends Application {

    private final Escuchador escucharSV = new Escuchador();

    @Override
    public void start(Stage stage) {
        stage.setTitle("Covid-18");
        TbxControl.getInstance().startControl(stage, null, null);
        TbxControl.getInstance().onAppClosing(event -> {
            new ComunicadorSinRespuesta().solicitarSalida();
            escucharSV.detener();
            ThreadCollector.getInstance().stopThreads();
            ThreadCollector.getInstance().stopTimers();
        });
        TbxControl.getInstance().viewBase(true, null);
        TbxControl.getInstance().view("MenuIncio");
        actualizarFondo();
    }

    public void actualizarFondo() {
        Thread actulizarHilo = new Thread(() -> {
            CICLO:
            while (true) {
                Actualizacion actuali = escucharSV.escuchar();
                switch (actuali.getMetodo()) {
                    case "stopAll":
                        break CICLO;
                    case "nuevosJugadores":
                        synchronized (AppContext.getInstance()) {
                            AppContext.getInstance().set("nuevosJugadores", actuali.getlistaJugador());
                        }
                        break;
                    case "volverHost":
                        ((Jugador) AppContext.getInstance().get("jugador")).setHost(true);
                        break;
                    case "modoJuego":
                        AppContext.getInstance().set("cargarPartida", true);
                        break;
                    default:
                        this.execute(actuali);
                        break;
                }
            }
            System.err.println("App se ha desconectado del servidor");
        });
        actulizarHilo.start();
        ThreadCollector.getInstance().addThread(actulizarHilo);
    }

    /**
     * Ejecuta la actualización entrante.
     *
     * @param act Actualización por ejecutar
     */
    public void execute(Actualizacion act) {

        if ("SalaChat".equals(act.getModulo())) {
            SalaChatController salaChat = (SalaChatController) AppContext.getInstance().get("SalaChat");
            Method met = getClassMethod(salaChat, act.getMetodo());
            Platform.runLater(() -> {
                try {
                    met.invoke(salaChat, act);
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                    Logger.getLogger(VirusClient.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        } else if ("SalaDeJuego".equals(act.getModulo())) {
            PartidaController partController = (PartidaController) AppContext.getInstance().get("SalaDeJuego");
            Method met = getClassMethod(partController, act.getMetodo());
            Platform.runLater(() -> {
                try {
                    met.invoke(partController, act);
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                    Logger.getLogger(VirusClient.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
    }

    /**
     * Recibe una intsnacia de una clase y un nombre, basado en eso devuelve un
     * método.
     *
     * @param objeto Instancia de la clase de la cual se desean obtener el
     * método. Si la instancia es de un tipo diferente a
     * <b>SalaChatController</b> o "LA CLASE DE LA PANTALLA DE JUEGO" el método
     * no retornará método alguno.
     * @param nombreMethod Nombre del método que se desea obtener.
     * @return Método deseado, null en caso de fallar al obtener el método.
     */
    public Method getClassMethod(Object objeto, String nombreMethod) {
        try {
            if (objeto instanceof SalaChatController) {
                return ((SalaChatController) objeto).getClass().getDeclaredMethod(nombreMethod, Actualizacion.class);
            } else {
                if (objeto instanceof PartidaController) {
                    return ((PartidaController) objeto).getClass().getDeclaredMethod(nombreMethod, Actualizacion.class);
                } else {
                    return null;
                }
            }
        } catch (NoSuchMethodException | SecurityException ex) {
            Logger.getLogger(VirusClient.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
