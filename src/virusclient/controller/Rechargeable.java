package virusclient.controller;

import javafx.stage.Stage;

public abstract class Rechargeable{

    private Stage stage;

    /**
    * Se ejecuta con cada apertura o reapertura de una pantalla.
    */
    public abstract void reOpen();


    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
}