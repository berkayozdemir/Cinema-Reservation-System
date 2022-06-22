import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class AddFilm extends Application {

    TextField nametext;
    TextField trailertext;
    TextField duratiotext;
    Button back;
    Button ok;
    Label errorlabel;


    private boolean filmnameIsExists(String filmname) {
        boolean check=false;

        for(int i=0;i<BackupReader.films.size();i++) {
            if(BackupReader.films.get(i).filmname.equals(filmname)) {
                check=true;
            }


        }

        return check;

    }

    private Integer isPositive(String duration) {

        try{
            int _duration=Integer.parseInt(duration);
        return _duration;}
        catch (NumberFormatException e) {
            return -1;
        }


    }

    private boolean isPathExists(String path) {
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();

        String _path=s+"\\assets\\trailers\\"+path;
        boolean check=true;

        File file=new File(_path);

        if(file.exists()) {
            check=false;

        }
        return check;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox layout=new VBox(15);
        HBox nameline=new HBox(25);
        HBox trailerline=new HBox(20);
        HBox durationline=new HBox(10);
        HBox commandline=new HBox(170);


        Label namelabel=new Label("Name:          ");
        Label trailabel=new Label("Trailer (Path):");
        Label duralabel=new Label("Duration (m):  ");


        nametext=new TextField();
        nameline.getChildren().addAll(namelabel,nametext);
        nameline.setAlignment(Pos.CENTER);

        trailertext=new TextField();
        trailerline.getChildren().addAll(trailabel,trailertext);
        trailerline.setAlignment(Pos.CENTER);

        duratiotext=new TextField();
        durationline.getChildren().addAll(duralabel,duratiotext);
        durationline.setAlignment(Pos.CENTER);

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




        ok=new Button(" OK ");
        ok.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            if(nametext.getText().equals("")) {
                Main.errorMusic();
                errorlabel.setText("ERROR: Film name could not be empty!");

            }
            else if(filmnameIsExists(nametext.getText())) {
                Main.errorMusic();
                errorlabel.setText("ERROR: There is a film named "+nametext.getText()+"!");

            }

            else if(trailertext.getText().equals("")) {
                Main.errorMusic();
                errorlabel.setText("ERROR: Trailer (path) could not be empty!");

            }

            else if(duratiotext.getText().equals(""))
            {
                Main.errorMusic();
                errorlabel.setText("ERROR: Duration (m) could not be empty!");

            }
            else if(isPositive(duratiotext.getText())<=0) {
                Main.errorMusic();
                errorlabel.setText("ERROR: Duration has to be positive integer");
            }
            else if(isPathExists(trailertext.getText())) {
                Main.errorMusic();
                errorlabel.setText("ERROR: There is no such a trailer!");

            }

            else {

                Film film=new Film(nametext.getText(),trailertext.getText(),isPositive(duratiotext.getText()));
                BackupReader.films.add(film);
                errorlabel.setText("Film added succesfully!");

            }




            }
        });




        commandline.getChildren().addAll(back,ok);
        commandline.setAlignment(Pos.CENTER);

        errorlabel=new Label("");
        Label label=new Label("Please give name, relative path of the trailer and duration of the film.");
        layout.getChildren().addAll(label,nameline,trailerline,durationline,commandline,errorlabel);
        layout.setAlignment(Pos.CENTER);

        primaryStage.setX(700);
        primaryStage.setY(400);


        Scene scene=new Scene(layout,400,300);
        primaryStage.setScene(scene);
        primaryStage.show();











    }
}
