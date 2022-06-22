import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RemoveHall extends Application {
    ComboBox<String> halllist;
    Button back;
    Button ok;

    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox layout=new VBox(10);
        layout.setAlignment(Pos.CENTER);
        HBox command=new HBox(5);


        Label toplabel=new Label("Select the hall you desire to remove from "+Main.currentFilm.filmname+" and then click OK");

        halllist=new ComboBox<>();
        for(int i=0;i<BackupReader.halls.size();i++) {
            if(BackupReader.halls.get(i).currentfilm.equals(Main.currentFilm.filmname)) {
                halllist.getItems().add(BackupReader.halls.get(i).hallname);

            }

        }
        halllist.getSelectionModel().selectFirst();

        back=Main.backButton();
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Trailer trailer=new Trailer();
                try {
                    trailer.start(primaryStage );
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
                    if(BackupReader.halls.get(i).currentfilm.equals(Main.currentFilm.filmname) && BackupReader.halls.get(i).hallname.equals(halllist.getSelectionModel().getSelectedItem())) {
                        BackupReader.halls.remove(i);
                        break;

                    }

                }

                RemoveHall removeHall=new RemoveHall();
                try {
                    removeHall.start(primaryStage);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });


        command.getChildren().addAll(back,ok);
        command.setAlignment(Pos.CENTER);

        layout.getChildren().addAll(toplabel,halllist,command);

        Scene scene=new Scene(layout,200,200);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
