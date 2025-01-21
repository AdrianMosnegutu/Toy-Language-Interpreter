import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Interpreter extends Application {
    public static void main(String[] args) {
        Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> {
            System.err.println("Uncaught exception: " + throwable.getMessage());
            throwable.printStackTrace();

            Platform.runLater(() -> {
                Platform.exit();
                System.exit(1);
            });
        });

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Thread.currentThread().setUncaughtExceptionHandler((thread, throwable) -> {
            System.err.println("Uncaught exception: " + throwable.getMessage());
            throwable.printStackTrace();

            Platform.runLater(() -> {
                Platform.exit();
                System.exit(1);
            });
        });

        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
        Parent root = loader.load();

        primaryStage.setTitle("Toy Language Interpreter");
        primaryStage.setScene(new Scene(root));

        primaryStage.show();
    }
}
