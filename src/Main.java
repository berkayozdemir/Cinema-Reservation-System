import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

public class Main extends Application implements EventHandler<ActionEvent> {

public static User currentUser;
public static Film currentFilm;
public static Hall currentHall;


    public static void main(String[] args) throws FileNotFoundException {
        BackupReader backupReader=new BackupReader();

        launch(args);

    }


    @Override
    public void stop() throws FileNotFoundException, UnsupportedEncodingException {

        BackupReader.writeBackup();
    }

    public static void errorMusic() {
        String filename="assets\\effects\\error.mp3";
        Media sound= new Media(new File(filename).toURI().toString());
        MediaPlayer mediaPlayer=new MediaPlayer(sound);
        mediaPlayer.play();
    }

    public static Button backButton() {
        Image starticon = new Image(new File("assets\\icons\\img.png").toURI().toString());
        ImageView startView=new ImageView(starticon);
        startView.setFitHeight(10);
        startView.setFitWidth(10);
        Button button=new Button("BACK",startView);
        return button;


    }

    @Override
    public void start(Stage stage) throws Exception {
        Login login=new Login();
        login.start(stage);
    }

    @Override
    public void handle(ActionEvent actionEvent) {

    }
}
