/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package virusclient;

import javafx.application.Application;
import javafx.stage.Stage;
import virusclient.util.TbxControl;

/**
 *
 * @author Roberth
 */
public class VirusClient extends Application {

    @Override
    public void start(Stage stage) {
        TbxControl.getInstance().startControl(stage, null, null);
        TbxControl.getInstance().viewBase(true, null);
        TbxControl.getInstance().view("MenuIncio");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
