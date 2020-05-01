/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package virusclient;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import virusclient.util.ComunicadorConRespuesta;
import virusclient.util.FlowController;
import virusclient.util.Respuesta;

/**
 *
 * @author Roberth
 */
public class VirusClient extends Application {
    @Override
        public void start(Stage stage) throws Exception {
        Parent root = (Parent)FlowController.getLoader("MenuIncio").getRoot();
        Scene scene = new Scene(root,700,600);       
        stage.setScene(scene);
        stage.show();
     FlowController.InitializeFlow(stage,null);
    }
//    @Override
//    @SuppressWarnings("Convert2Lambda")
//    public void start(Stage primaryStage) {
//        Button btn = new Button();
//        btn.setText("Say 'Hello World'");
//        btn.setOnAction(new EventHandler<ActionEvent>() {
//            
//            @Override
//            public void handle(ActionEvent event) {
//                //System.out.println("Hello World!");
//                ComunicadorConRespuesta serv = new ComunicadorConRespuesta();
//                Respuesta resp = serv.crearNuevaPartida(9999, "Roberth");
//                System.out.println(resp.getEstado());
//            }
//        });
//        StackPane root = new StackPane();
//        root.getChildren().add(btn);
//        
//        Scene scene = new Scene(root, 300, 250);
//        
//        primaryStage.setTitle("Hello World!");
//        primaryStage.setScene(scene);
//        primaryStage.show();
  //  }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
