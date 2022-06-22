import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AddHall extends Application {
    ComboBox<Integer> row;
    ComboBox<Integer> column;
    TextField hallname;
    TextField hallprice;
    Button back;
    Button ok;
    Label errorLabel;


    private boolean isHallExists(String hall) {
        boolean check=false;
        for(int i=0;i<BackupReader.halls.size();i++) {
        if(BackupReader.halls.get(i).hallname.equals(hall) && BackupReader.halls.get(i).currentfilm.equals(Main.currentFilm.filmname)) {
            check=true;
        }
        }
        return  check;
    }

    private Integer isPositive(String duration) {

        try{
            return Integer.parseInt(duration);}
        catch (NumberFormatException e) {
            return -1;
        }


    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox layout=new VBox(20);
        HBox rowlayout=new HBox(105);
        HBox columnlayout=new HBox(85);
        HBox namelayout=new HBox(5);
        HBox pricelayout=new HBox(8);
        HBox commandLayout=new HBox(30);


        Label rowlabel= new Label("Row:");
        Label columnlabel= new Label("Column");
        Label namelabel=new Label("Name:");
        Label pricelabel=new Label("Price:");

        row=new ComboBox<>();
        row.getItems().addAll(3,4,5,6,7,8,9,10);
        row.getSelectionModel().selectFirst();

        column=new ComboBox<>();
        column.getItems().addAll(3,4,5,6,7,8,9,10);
        column.getSelectionModel().selectFirst();

        hallname=new TextField();
        hallprice=new TextField();

        errorLabel=new Label("");
        Label toplabel=new Label(Main.currentFilm.filmname+" ("+Main.currentFilm.duration+" minutes)");

        ok=new Button("OK");
        ok.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(hallname.getText().equals("")) {
                    Main.errorMusic();
                    errorLabel.setText("ERROR: Hall name could not be empty!");
                }
                else if(isHallExists(hallname.getText())) {
                    Main.errorMusic();
                    errorLabel.setText("ERROR: This hall name already exists!");
                    hallname.clear();
                }

                else if(hallprice.getText().equals("")) {
                    Main.errorMusic();
                    errorLabel.setText("ERROR: Price could not be empty!");
                }
                else if(isPositive(hallprice.getText())<=0) {
                    Main.errorMusic();
                    errorLabel.setText("ERROR: Price has to be a positive integer!");
                }
                else {
                    Hall hall=new Hall(Main.currentFilm.filmname,hallname.getText(),isPositive(hallprice.getText()),row.getSelectionModel().getSelectedItem(),column.getSelectionModel().getSelectedItem());
                    hall.createSeats();
                    BackupReader.halls.add(hall);
                    row.getSelectionModel().selectFirst();
                    column.getSelectionModel().selectFirst();
                    hallname.clear();
                    hallprice.clear();
                    errorLabel.setText("SUCCESS: Hall  succesfully created");

                }
            }
        });


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



        rowlayout.getChildren().addAll(rowlabel,row);
        rowlayout.setAlignment(Pos.CENTER);

        columnlayout.getChildren().addAll(columnlabel,column);
        columnlayout.setAlignment(Pos.CENTER);

        namelayout.getChildren().addAll(namelabel,hallname);
        namelayout.setAlignment(Pos.CENTER);

        pricelayout.getChildren().addAll(pricelabel,hallprice);
        pricelayout.setAlignment(Pos.CENTER);

        commandLayout.getChildren().addAll(back,ok);
        commandLayout.setAlignment(Pos.CENTER);

        layout.getChildren().addAll(toplabel,rowlayout,columnlayout,namelayout,pricelayout,commandLayout,errorLabel);
        layout.setAlignment(Pos.CENTER);

        Scene scene=new Scene(layout,350,350);

        primaryStage.setScene(scene);
        primaryStage.show();







    }
}
