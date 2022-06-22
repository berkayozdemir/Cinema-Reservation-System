import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


import java.io.File;

public class HallScreen extends Application {

    Button back;
    Seat[][] arr=Main.currentHall.seatsHall;
    ComboBox<String> comboBox;
    Label feeLabel;


    private User getUser() {
        User user=null;
        for(int i=0;i<BackupReader.users.size();i++) {
            if(comboBox.getSelectionModel().getSelectedItem().equals(BackupReader.users.get(i).username)) {
                user=BackupReader.users.get(i);
            }
        }
        return user;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox layout=new VBox(10);
        HBox backlayout=new HBox();
        layout.setAlignment(Pos.CENTER);
        Label toplabel;

        VBox halls=new VBox(10);
        for(int i=0;i<Main.currentHall.row;i++) {
            HBox hBox=new HBox(10);
            for(int j=0;j<Main.currentHall.column;j++) {



                    //SET ICON
                Image  icon=new Image(new File("assets\\icons\\empty_seat.png").toURI().toString());
                if( ! Main.currentHall.seatsHall[i][j].owner.equals("null")) {

                    icon=new Image(new File("assets\\icons\\reserved_seat.png").toURI().toString());
                }
                ImageView iconview=new ImageView(icon);
                iconview.setFitWidth(55);
                iconview.setFitHeight(55);
                Button button=new Button(i+" "+j,iconview);
                if(!Main.currentUser.admin && ! Main.currentHall.seatsHall[i][j].owner.equals("null") && !Main.currentHall.seatsHall[i][j].owner.equals(Main.currentUser.username))
                {button.setDisable(true);}
                button.setPrefSize(60,60);
                button.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);



                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        String[] arr=button.getText().split(" ");
                        int x=Integer.parseInt(arr[0]);
                        int y=Integer.parseInt(arr[1]);
                        if(Main.currentUser.admin) {
                        User user=getUser();
                            if(Main.currentHall.seatsHall[x][y].owner.equals("null")) {
                                Main.currentHall.seatsHall[x][y].owner=user.username;
                                if(user.clubmember) { Main.currentHall.seatsHall[x][y].bought_price=Main.currentHall.price/100*BackupReader.discount;}
                               else {Main.currentHall.seatsHall[x][y].bought_price=Main.currentHall.price;}
                            }
                            else { Main.currentHall.seatsHall[x][y].owner="null";
                                Main.currentHall.seatsHall[x][y].bought_price=-1;

                            }
                        }

                        else{
                            if(Main.currentHall.seatsHall[x][y].owner.equals("null")) {
                                Main.currentHall.seatsHall[x][y].owner=Main.currentUser.username;
                                if(Main.currentUser.clubmember) { Main.currentHall.seatsHall[x][y].bought_price=Main.currentHall.price/100*BackupReader.discount;}
                                else {Main.currentHall.seatsHall[x][y].bought_price=Main.currentHall.price;}
                            }
                            else { Main.currentHall.seatsHall[x][y].owner="null";
                                Main.currentHall.seatsHall[x][y].bought_price=-1;

                            }






                        }
                        try {
                            start(primaryStage);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                button.setOnMouseMoved(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        String[] arr=button.getText().split(" ");
                        int x=Integer.parseInt(arr[0]);
                        int y=Integer.parseInt(arr[1]);
                        if(Main.currentHall.seatsHall[x][y].owner.equals("null")) {
                            feeLabel.setText("");
                        }
                        else{feeLabel.setText("Seat at"+(x+1)+" "+(y+1)+"bought for "+Main.currentHall.seatsHall[x][y].owner+" for "+Main.currentHall.seatsHall[x][y].bought_price+" TL!");}


                    }
                });

                hBox.getChildren().add(button);

            }
            halls.getChildren().add(hBox);
        }



        comboBox=new ComboBox<>();
        for(int i=0;i<BackupReader.users.size();i++) {
            comboBox.getItems().add(BackupReader.users.get(i).username);
        }
        comboBox.getSelectionModel().selectFirst();


        back=Main.backButton();
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Trailer trailer=new Trailer();
                try {
                    trailer.start(primaryStage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        backlayout.getChildren().add(back);
        backlayout.setAlignment(Pos.BOTTOM_LEFT);


        toplabel=new Label(Main.currentFilm.filmname+ " ("+Main.currentFilm.duration+" Minutes)  Hall:"+Main.currentHall.hallname);

        feeLabel=new Label("");


        if(Main.currentUser.admin) { layout.getChildren().addAll(toplabel,halls,comboBox,feeLabel,backlayout);}
        else { layout.getChildren().addAll(toplabel,halls,feeLabel,backlayout);}


        Scene scene=new Scene(layout);
        primaryStage.setScene(scene);
        primaryStage.show();



    }
}
