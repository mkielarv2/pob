import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        stage.setOnCloseRequest(event ->
                System.exit(0)
        );
        stage.setTitle("Uber Commander");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Utils.getLocation("main.fxml", getClass()));
        Parent root = loader.load();
        Scene scene = new Scene(root, 1000, 600);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
