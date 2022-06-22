import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.util.Duration;


import java.io.File;
import java.util.Objects;

public class Trailer  extends Application {
    Button startStop;
    Button fiveUp;
    Button fiveDown;
    Button toBegin;
    Button back;
    ComboBox<String> halllist;
    Button ok;
    Button addHall;
    Button removeHall;
    Slider slider;
    boolean resume=true;


    @Override
    public void start(Stage stage) throws Exception {

        VBox layout=new VBox(50);
        HBox ekran=new HBox(20);
        VBox ekranV=new VBox(10);
        VBox kumanda=new VBox(5);
        HBox kumanda2=new HBox(5);




        File path=new File("assets\\trailers\\"+Main.currentFilm.filmpath);
        Media media=new Media(path.toURI().toString());

        MediaPlayer player = new MediaPlayer(media);
        MediaView mediaView = new MediaView(player);
        mediaView.setFitHeight(768);
        mediaView.setFitWidth(1024);


        startStop=new Button();
        startStop.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                resume=!resume;
                if(resume) { player.play();
                    Image starticon = new Image(new File("assets\\icons\\start.png").toURI().toString());
                    ImageView startView=new ImageView(starticon);
                    startView.setFitHeight(10);
                    startView.setFitWidth(10);
                    startStop.setGraphic(startView);
                    startStop.setPrefSize(70,20);





                }
                else {player.pause();
                    Image starticon = new Image(new File("assets\\icons\\pause.png").toURI().toString());
                    ImageView startView=new ImageView(starticon);
                    startView.setFitHeight(10);
                    startView.setFitWidth(10);
                    startStop.setGraphic(startView);
                    startStop.setPrefSize(70,20);
                }
            }
        });
        Image starticon = new Image(new File("assets\\icons\\start.png").toURI().toString());
        ImageView startView=new ImageView(starticon);
        startView.setFitHeight(10);
        startView.setFitWidth(10);
        startStop.setGraphic(startView);
        startStop.setPrefSize(70,20);


        fiveDown=new Button("<<");
        fiveDown.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {


                player.seek(new Duration(player.getCurrentTime().toMillis()-5000));
            }
        });
        fiveDown.setPrefSize(70,20);


        fiveUp=new Button(">>");
        fiveUp.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                player.seek(new Duration(player.getCurrentTime().toMillis()+5000));
            }
        });
        fiveUp.setPrefSize(70,20);




        toBegin=new Button("|<<");
        toBegin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                player.stop();
                player.play();
            }
        });
        toBegin.setPrefSize(70,20);

        slider=new Slider(0,player.getVolume(),player.getVolume()/2);


        slider.setOrientation(Orientation.VERTICAL);

        slider.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                player.setVolume(slider.getValue());
            }
        });

        kumanda.getChildren().addAll(startStop,fiveDown,fiveUp,toBegin,slider);

        kumanda.setAlignment(Pos.CENTER);







        back=Main.backButton();
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Welcome welcome=new Welcome();
                try {
                    player.stop();
                    welcome.start(stage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        halllist=new ComboBox<>();
        for(int i=0;i<BackupReader.halls.size();i++) {
            if(BackupReader.halls.get(i).currentfilm.equals(Main.currentFilm.filmname)) {
                halllist.getItems().add(BackupReader.halls.get(i).hallname);

            }

        }
        halllist.getSelectionModel().selectFirst();


        addHall=new Button("Add Hall");
        addHall.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            AddHall addHall=new AddHall();
                try {
                    player.stop();
                    addHall.start(stage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });



        removeHall=new Button("Remove Hall");
        removeHall.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                RemoveHall removeHall=new RemoveHall();
                try {
                    player.stop();
                    removeHall.start(stage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        ok=new Button("OK");
        ok.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                for(int i=0;i<BackupReader.halls.size();i++) {
                    if(Main.currentFilm.filmname.equals(BackupReader.halls.get(i).currentfilm)
                            && halllist.getSelectionModel().getSelectedItem().equals(BackupReader.halls.get(i).hallname))
                    { Main.currentHall=BackupReader.halls.get(i);}


                }
                player.stop();
                HallScreen hallScreen=new HallScreen();
                try {
                    hallScreen.start(stage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        if(Main.currentUser.admin) {
            kumanda2.getChildren().addAll(back,addHall,removeHall,halllist,ok);
        }
        else { kumanda2.getChildren().addAll(back,halllist,ok);}

        kumanda2.setAlignment(Pos.CENTER);

        Label label=new Label(Main.currentFilm.filmname+"("+Main.currentFilm.duration+" minutes)");


        ekranV.getChildren().add(label);
        ekranV.getChildren().add( mediaView);
        ekranV.getChildren().add(kumanda2);
        ekranV.setAlignment(Pos.CENTER);


        ekran.getChildren().add(ekranV);
        ekran.getChildren().add(kumanda);
        ekran.setAlignment(Pos.CENTER);


        layout.getChildren().addAll(ekran);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout, 1400, 900);
        stage.setX(300);
        stage.setY(50);

        stage.setScene(scene);
        stage.show();


        player.play();
    }
}
