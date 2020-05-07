/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package virusclient.util;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Stack;
import java.util.logging.Level;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import virusclient.VirusClient;
import virusclient.controller.Rechargeable;

/**
 *
 * @author Roberth Fallas 😊
 */
public class TbxControl {

    private static final Stack<String> viewed = new Stack<String>();
    private static TbxControl INSTANCE = null;
    private static Stage mainStage;
    private static ResourceBundle idioma;
    private static final Tree<FXMLLoader> tree = new Tree<>();
    private static String css = null;

    private TbxControl() {
    }

    private static void createInstance() {
        synchronized (TbxControl.class) {
            if (INSTANCE == null) {
                INSTANCE = new TbxControl();
            }
        }
    }

    public static TbxControl getInstance() {
        if (INSTANCE == null) {
            createInstance();
        }
        return INSTANCE;
    }

    public void startControl(Stage stage, ResourceBundle idioma, String stylesheetName) {
        getInstance();
        TbxControl.mainStage = stage;
        mainStage.setMinHeight(600);
        mainStage.setMinWidth(800);
        TbxControl.idioma = idioma;
        if (stylesheetName != null) {
            URL url = VirusClient.class.getResource("view/" + stylesheetName + ".css");
            TbxControl.css = url.toExternalForm();
        }
    }

    private FXMLLoader searchLoader(String name) {
        FXMLLoader loader = (FXMLLoader) tree.find(name);
        if (loader == null) {
            synchronized (TbxControl.class) {
                try {
                    loader = new FXMLLoader(VirusClient.class.getResource("view/" + name + ".fxml"),
                            TbxControl.idioma);
                    loader.load();
                    tree.insert(name, loader);
                } catch (Exception ex) {
                    loader = null;
                    java.util.logging.Logger.getLogger(TbxControl.class.getName()).log(Level.SEVERE, "Creando loader [" + name + "].", ex);
                }
            }
        }
        return loader;
    }

    public void viewBase(boolean resizable, Image icon) {
        try {
            TbxControl.mainStage.setResizable(resizable);
            if (icon != null) {
                TbxControl.mainStage.getIcons().add(icon);
            }
            TbxControl.mainStage.setScene(new Scene(FXMLLoader.load(VirusClient.class.getResource("view/Base.fxml"), TbxControl.idioma)));
            TbxControl.mainStage.show();
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(TbxControl.class.getName()).log(Level.SEVERE, "Error inicializando la vista base.", ex);
        }
    }

    public Stage getMain() {
        return TbxControl.mainStage;
    }

    public void view(String nombre) {
        FXMLLoader loader = this.searchLoader(nombre);
        Rechargeable control = loader.getController();
        control.reOpen();
        Stage stage = control.getStage();
        if (stage == null) {
            stage = TbxControl.mainStage;
            control.setStage(stage);
        }
        if (!viewed.isEmpty()) {
            if (!viewed.peek().equals(nombre)) {
                viewed.push(nombre);
            }
        } else {
            viewed.push(nombre);
        }
        ((AnchorPane) stage.getScene().getRoot()).getChildren().clear();
        ((AnchorPane) stage.getScene().getRoot()).getChildren().add(loader.getRoot());
        AnchorPane.setBottomAnchor(loader.getRoot(), 0.0);
        AnchorPane.setTopAnchor(loader.getRoot(), 0.0);
        AnchorPane.setLeftAnchor(loader.getRoot(), 0.0);
        AnchorPane.setRightAnchor(loader.getRoot(), 0.0);
        if (TbxControl.css != null) {
            ((AnchorPane) ((AnchorPane) stage.getScene().getRoot()).getChildren().get(0)).getStylesheets().clear();
            ((AnchorPane) ((AnchorPane) stage.getScene().getRoot()).getChildren().get(0)).getStylesheets().add(css);
        }
    }

    public void viewInWindow(String viewName, Image icon, String titulo, boolean resizable) {
        FXMLLoader loader = this.searchLoader(viewName);
        Rechargeable control = loader.getController();
        control.reOpen();
        Stage stage = new Stage();
        if (icon != null) {
            stage.getIcons().add(icon);
        }
        stage.setTitle(titulo);
        stage.setOnHidden((WindowEvent event) -> {
            control.getStage().getScene().setRoot(new Pane());
            control.setStage(null);
        });
        control.setStage(stage);
        Parent root = loader.getRoot();
        if (css != null) {
            root.getStylesheets().clear();
            root.getStylesheets().add(css);
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setResizable(resizable);
        stage.show();
    }

    public void goViewInWindowModal(String viewName, Stage parentStage, Boolean resizable, String titulo, Image icono) {
        FXMLLoader loader = searchLoader(viewName);
        Rechargeable control = loader.getController();
        control.reOpen();
        Stage stage = new Stage();
        if (icono != null) {
            stage.getIcons().add(icono);
        }
        stage.setTitle(titulo);
        stage.setResizable(resizable);
        stage.setOnHidden((WindowEvent event) -> {
            control.getStage().getScene().setRoot(new Pane());
            control.setStage(null);
        });
        control.setStage(stage);
        Parent root = loader.getRoot();
        Scene scene = new Scene(root);
        if (css != null) {
            root.getStylesheets().clear();
            root.getStylesheets().add(css);
        }
        stage.setScene(scene);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(parentStage);
        stage.centerOnScreen();
        stage.showAndWait();
    }

    public void setIdioma(ResourceBundle idioma) {
        TbxControl.idioma = idioma;
    }

    public void reset() {
        TbxControl.tree.clear();
    }

    public void closeApp() {
        TbxControl.mainStage.close();
    }

    public void eliminarLoader(String name) {
        tree.delete(name);
    }

    public void goBack() {
        if (viewed.size() > 1) {
            viewed.pop();
            view(viewed.peek());
        }
    }

    public boolean deleteHistoryTo(String viewName) {
        int pos = viewed.search(viewName);
        boolean existe = pos != -1;
        if (existe) {
            while (!viewed.peek().equals(viewName)) {
                viewed.pop();
            }
            return true;
        }
        return false;
    }

    /**
     * @param idioma Nuevo idioma a mostrar
     * @implNote MÉTODO NO HA SIDO TESTEADO (PUEDE DAR LUGAR A COMPORTAMIENTOS
     * INESPERADOS)
     */
    public void changeLanguaje(ResourceBundle idioma) {  //--->Metodo sin probar
        TbxControl.idioma = idioma;
        tree.forEach(nodo -> nodo.setResources(idioma));
    }

    public void onAppClosing(EventHandler<WindowEvent> event) {
        mainStage.setOnCloseRequest(event);
    }

}
