package merge_sevral_image;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Merge_Sevral_Image extends Application{
    
    @Override
    public void start(Stage stage) throws Exception{
        Parent root=FXMLLoader.load(getClass().getResource("MainPage.fxml"));
        
        Scene scene=new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }
    
    public static void main(String[] args)
    {
        launch(args);
    }
    
}
