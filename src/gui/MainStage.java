package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by Matthew on 5/5/2017.
 */
public class MainStage extends Application {
    public MainStage(){ }

    /**
     * The required start method for JavaFX in the class for the main stage.
     * @param primaryStage
     * @throws Exception
     */
    @Override public void start(Stage primaryStage) throws Exception {
        TollScene ttc = new TollScene();
        Scene mainScene = new Scene(ttc);
        primaryStage.setScene(mainScene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
