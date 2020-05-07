/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package virusclient;

import javafx.application.Application;
import javafx.stage.Stage;
import virusclient.model.Actualizacion;
import virusclient.model.Escuchador;
import virusclient.util.AppContext;
import virusclient.util.TbxControl;
import virusclient.util.ThreadCollerctor;

/**
 *
 * @author Roberth
 */
public class VirusClient extends Application {

    private final Escuchador escucharSV = new Escuchador();

    @Override
    public void start(Stage stage) {
        TbxControl.getInstance().startControl(stage, null, null);
        TbxControl.getInstance().onAppClosing(event -> {
            escucharSV.detener();
            ThreadCollerctor.getInstance().stopThreads();
            ThreadCollerctor.getInstance().stopTimers();
        });
        TbxControl.getInstance().viewBase(true, null);
        TbxControl.getInstance().view("MenuIncio");
        actualizarFondo();
    }

    public void actualizarFondo() {
        Thread actulizarHilo = new Thread(() -> {
            while (true) {
                Actualizacion actuali = escucharSV.escuchar();
                if ("stopAll".equals(actuali.getAction())) {
                    break;
                }
                if ("nuevosJugadores".equals(actuali.getAction())) {
                    synchronized (AppContext.getInstance()) {
                        AppContext.getInstance().set("nuevosJugadores", actuali.getlistaJugador());
                    }
                }
            }
            System.out.println("App se ha desconectado del servidor");
        });
        actulizarHilo.start();
        ThreadCollerctor.getInstance().addThread(actulizarHilo);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
