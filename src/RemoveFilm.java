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



public class RemoveFilm extends Application {
    Button back;
    Button ok;

    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox layout =new VBox(5);
        HBox commands=new HBox(5);

        ComboBox<String> comboBox=new ComboBox();
        for(int i=0;i<BackupReader.films.size();i++) {
            comboBox.getItems().add(BackupReader.films.get(i).filmname);

        }

        comboBox.getSelectionModel().selectFirst();

        back=Main.backButton();
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Welcome welcome=new Welcome();
                try {
                    welcome.start(primaryStage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });



        ok=new Button("OK");
        ok.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                for(int i=0;i<BackupReader.films.size();i++) {
                    if(BackupReader.films.get(i).filmname.equals(comboBox.getValue())) {
                        BackupReader.films.remove(i);
                        try {
                            start(primaryStage);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    }

                }
            }
        });


        commands.getChildren().addAll(back,ok);
        commands.setAlignment(Pos.CENTER);

        Label label=new Label("Select the film that you desire to remove and then click OK");
        layout.getChildren().addAll(label,comboBox,commands);
        layout.setAlignment(Pos.CENTER);

        Scene scene=new Scene(layout,500,250);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
