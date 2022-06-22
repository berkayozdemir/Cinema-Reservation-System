import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class EditUsers extends Application {
    TableView<User> tableView;
    Button back;
    Button adminmember;
    Button clubmember;


    private void promoteordemoteadmin(User user) {
        for(int i=0;i<BackupReader.users.size();i++) {
            if(BackupReader.users.get(i).username.equals(user.username)) {
                if(!BackupReader.users.get(i).admin) {
                    BackupReader.users.get(i).admin=true;
                }
                else {
                    BackupReader.users.get(i).admin=false;
                }

            }

        }
    }


    private void promoteordemoteclubmember(User user) {
        for(int i=0;i<BackupReader.users.size();i++) {
            if(BackupReader.users.get(i).username.equals(user.username)) {
                if(!BackupReader.users.get(i).clubmember) {
                    BackupReader.users.get(i).clubmember=true;
                }
                else {
                    BackupReader.users.get(i).clubmember=false;
                }

            }

        }
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox layout=new VBox(10);
        HBox command=new HBox(10);

        ObservableList<User> userObservableList= FXCollections.observableArrayList();


        userObservableList.addAll(BackupReader.users);


        tableView=new TableView<User>();
        tableView.setEditable(true);
        tableView.setLayoutY(300);

        TableColumn<User,String> column1 =new TableColumn<>("Username");
        column1.setCellValueFactory(new PropertyValueFactory<>("username"));




        TableColumn<User,Boolean> column3 =new TableColumn<>("Club Member");
        column3.setCellValueFactory(new PropertyValueFactory<>("clubmember"));

        TableColumn<User,Boolean> column4 =new TableColumn<>("Admin");
        column4.setCellValueFactory(new PropertyValueFactory<>("admin"));



        tableView.getColumns().add(column1);

        tableView.getColumns().add(column3);
        tableView.getColumns().add(column4);

        tableView.setItems(userObservableList);

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

        adminmember=new Button("Promote/Demote Admin");
        adminmember.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                User user=tableView.getSelectionModel().getSelectedItem();
                promoteordemoteadmin(user);
                EditUsers editUsers=new EditUsers();
                try {
                    editUsers.start(primaryStage);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });


        clubmember=new Button("Promote/Demote Clup Member");
        clubmember.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                User user=tableView.getSelectionModel().getSelectedItem();
                promoteordemoteclubmember(user);
                EditUsers editUsers=new EditUsers();
                try {
                    editUsers.start(primaryStage);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        command.getChildren().addAll(back,clubmember,adminmember);
        command.setAlignment(Pos.CENTER);

        layout.getChildren().add(tableView);
        layout.getChildren().add(command);
        Scene scene =new Scene(layout,600,450);
        primaryStage.setY(150);

        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
