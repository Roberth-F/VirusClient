/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package virusclient.util;

import virusclient.VirusClient;
import virusclient.controller.Controller;
import java.io.IOException;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

//import unaplanilla2.UnaPlanilla2;
//import unaplanilla2.controller.Controller;

/**
 *
 * @author esanchez
 */
public class FlowController {

    private static FlowController INSTANCE = null;
    public static Stage mainStage;
    private static ResourceBundle idioma;
    private static HashMap<String, FXMLLoader> loaders = new HashMap<>();

    private FlowController() {
    }
    public  static void mostrarView(String pantalla){
 
    mainStage.getScene().setRoot(getLoader(pantalla).getRoot());
    }
    public static Stage getInstance(){
      return  mainStage;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    public static void InitializeFlow(Stage stage, ResourceBundle idioma) {
        mainStage = stage;
        idioma = idioma;
    }

    public static FXMLLoader getLoader(String name) {
        FXMLLoader loader = loaders.get(name);
        if (loader == null) {
            synchronized (FlowController.class) {
                if (loader == null) {
                    try {
                        loader = new FXMLLoader(VirusClient.class.getResource("view/" + name + ".fxml"), idioma);
                        loader.load();
                        loaders.put(name, loader);
                    } catch (Exception ex) {
                        loader = null;
                        java.util.logging.Logger.getLogger(FlowController.class.getName()).log(Level.SEVERE, "Creando loader [" + name + "].", ex);
                    }
                }
            }
        }
        return loader;
    }

    public static void goMain() {
        try {
            mainStage.setScene(new Scene(FXMLLoader.load(VirusClient.class.getResource("view/ViewPrincipal.fxml"), idioma),640,480));
            mainStage.show();
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(FlowController.class.getName()).log(Level.SEVERE, "Error inicializando la vista base.", ex);
        }
    }

    public static void goView(String viewName) {
        goView(viewName, "Center", null);
    }

    public static void goView(String viewName, String accion) {
        goView(viewName, "Center", accion);
    }

    public static  void goView(String viewName, String location, String accion) {
        FXMLLoader loader = getLoader(viewName);
        Controller controller = loader.getController();
        controller.setAccion(accion);
        controller.initialize();
        Stage stage = controller.getStage();
        if (stage == null) {
            stage = mainStage;
            controller.setStage(stage);
        }
        switch (location) {
            case "Center":
                ((VBox) ((BorderPane) stage.getScene().getRoot()).getCenter()).getChildren().clear();
                ((VBox) ((BorderPane) stage.getScene().getRoot()).getCenter()).getChildren().add(loader.getRoot());
                break;
            case "Top":
                break;
            case "Bottom":
                break;
            case "Right":
                break;
            case "Left":
                break;
            default:
                break;
        }
    }

    public static void goViewInStage(String viewName, Stage stage) {
        FXMLLoader loader = getLoader(viewName);
        Controller controller = loader.getController();
        controller.setStage(stage);
        stage.getScene().setRoot(loader.getRoot());
    }

    public static void goViewInWindow(String viewName) {
        FXMLLoader loader = getLoader(viewName);
        Controller controller = loader.getController();
        controller.initialize();
        Stage stage = new Stage();
        stage.getIcons().add(new Image("unaplanilla2/resources/Agregar-48.png"));
        stage.setTitle("UNA PLANILLA");
        stage.setOnHidden((WindowEvent event) -> {
            controller.getStage().getScene().setRoot(new Pane());
            controller.setStage(null);
        });
        controller.setStage(stage);
        Parent root = loader.getRoot();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();

    }

    public static void goViewInWindowModal(String viewName, Stage parentStage, Boolean resizable) {
        FXMLLoader loader = getLoader(viewName);
        //Controller controller = loader.getController();
        //controller.initialize();
        Stage stage = new Stage();
        
        stage.setHeight(600);
        stage.setWidth(600);
        //stage.getIcons().add(new Image("unaplanilla2/resources/Agregar-48.png"));
        //stage.setTitle("UNA PLANILLA");
        stage.setResizable(resizable);
        //stage.setOnHidden((WindowEvent event) -> {
          //  controller.getStage().getScene().setRoot(new Pane());
            //controller.setStage(null);
        //});
        //controller.setStage(stage);
        Parent root = loader.getRoot();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(parentStage);
        stage.centerOnScreen();
        stage.showAndWait();

    }

    public static Controller getController(String viewName) {
        return getLoader(viewName).getController();
    }

    public static void setIdioma(ResourceBundle idioma) {
        FlowController.idioma = idioma;
    }
    
    public static void initialize() {
        loaders.clear();
    }

    public static void salir() {
        mainStage.close();
    }

}
