package me.houdhakker2.plug;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Scene;
import javafx.scene.web.*;
public class PlugMain extends Application {
	
	public static PlugMain plugMain;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent t) {
                System.exit(0);
            }
        });
		WebView web = new WebView();
		WebEngine engine = web.getEngine();
		
		engine.load("https://plugapi4py-houdhakker2-2.c9.io/song");
		
		Scene scene = new Scene(web);
        stage.setScene(scene);
        
        stage.show();
	}
}
