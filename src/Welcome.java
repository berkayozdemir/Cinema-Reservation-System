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

public class Welcome extends Application implements EventHandler<ActionEvent> {

 static Scene scene;
 Button ok;
 Button logout;
 Button addfilm;
 Button removefilm;
 Button editusers;


    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("HUCS Cinema Reservation System ");

        VBox layout=new VBox(20);
        VBox textLine=new VBox(10);
        HBox dropdownLine=new HBox(5);
        HBox adminPanelLine=new HBox(7);
        HBox logoutline=new HBox();

        String unvan="";
        if(Main.currentUser.admin && Main.currentUser.clubmember) {
            unvan=" (Admin - Club Member)!";
        }
        else if(Main.currentUser.admin && !Main.currentUser.clubmember) {
            unvan=" (Admin)!";
        }
        else if(!Main.currentUser.admin && Main.currentUser.clubmember) {
            unvan=" (Club Member)!";
        }
        else unvan="!";

        Label label1=new Label("Welcome "+Main.currentUser.username+unvan);
        Label label2=new Label("Select a film and then click OK and continue");
        textLine.getChildren().addAll(label1,label2);
        textLine.setAlignment(Pos.CENTER);




        ComboBox<String> filmsComboBox=new ComboBox<>();
        for(int i=0;i<BackupReader.films.size();i++) {
            filmsComboBox.getItems().add(BackupReader.films.get(i).filmname);
        }
        filmsComboBox.getSelectionModel().selectFirst();

        ok=new Button("OK");
        ok.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Trailer trailer=new Trailer();
                Main.currentFilm=BackupReader.films.get(filmsComboBox.getSelectionModel().getSelectedIndex());

            try {
                    trailer.start(stage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        dropdownLine.getChildren().addAll(filmsComboBox,ok);
        dropdownLine.setAlignment(Pos.CENTER);



        addfilm=new Button("Add Film");
        addfilm.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AddFilm addFilm=new AddFilm();
                try {
                    addFilm.start(stage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        removefilm=new Button("Remove Film");
        removefilm.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            RemoveFilm removeFilm=new RemoveFilm();
                try {
                    removeFilm.start(stage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        editusers=new Button("Edit Users");
        editusers.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                EditUsers editUsers=new EditUsers();
                try {
                    editUsers.start(stage);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });
        adminPanelLine.getChildren().addAll(addfilm,removefilm,editusers);
        adminPanelLine.setAlignment(Pos.CENTER);





        logout=new Button("LOG OUT");
        logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Login login=new Login();
                try {
                    login.start(stage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        logoutline.getChildren().add(logout);
        logoutline.setAlignment(Pos.CENTER_RIGHT);

        if(Main.currentUser.admin) {layout.getChildren().addAll(textLine,dropdownLine,adminPanelLine,logoutline);}
        else {layout.getChildren().addAll(textLine,dropdownLine,logoutline);}

        layout.setAlignment(Pos.CENTER);

            stage.setX(700);
            stage.setY(400);
         scene=new Scene(layout,500,200);
         stage.setScene(scene);




    }



    @Override
    public void handle(ActionEvent actionEvent) {

    }
}
