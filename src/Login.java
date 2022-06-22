import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Timer;
import java.util.TimerTask;

public class Login extends Application {
    Button signupbutton;
    Button loginbutton;
    TextField usernamefield;
    PasswordField passwordfield;
    Label errorlabel;
    int error=0;


    public void timerTask() {
        error=0;

    }


    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("HUCS Cinema Reservation System ");

        signupbutton=new Button();
        signupbutton.setText("sign up");
        signupbutton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                Signup signup=new Signup();

                try {

                    signup.start(stage);
                } catch (Exception e) {

                    e.printStackTrace();
                }
            }

        });


        loginbutton=new Button();
        loginbutton.setText("Log in");
        loginbutton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                boolean isUser=false;
                if (error==5) {
                    passwordfield.clear();
                    Main.errorMusic();
                    errorlabel.setText("You are banned for 5 seconds.");
                    timerTask();
                    System.out.println("hata");
                }


                else if(usernamefield.getText().equals("")) {
                    Main.errorMusic();
                    errorlabel.setText("Username can't be empty!");
                }
                else {
                for(int i=0;i<BackupReader.users.size();i++) {

                    if(BackupReader.users.get(i).username.equals(usernamefield.getText())) {
                        isUser=true;
                        try {
                            if(BackupReader.users.get(i).hashedpass.equals(BackupReader.hashPassword(passwordfield.getText()))) {
                                Main.currentUser=BackupReader.users.get(i);

                                Welcome welcome=new Welcome();
                                welcome.start(stage);

                            }
                            else {
                                Main.errorMusic();
                                errorlabel.setText("Wrong password!");
                                passwordfield.clear();
                                error++;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }




                }
                if(!isUser) {
                    String path = "assets\\effects\\error.mp3";


                    Main.errorMusic();
                    errorlabel.setText("Wrong username");}

                }

            }
        });




        VBox layout=new VBox(20);
        VBox textLayout=new VBox();
        HBox usernameline=new HBox(5);
        HBox passwordline=new HBox(10);
        HBox signupline=new HBox(120);


        Label usernamelabel=new Label("Username:");
        usernamefield=new TextField();
        usernameline.getChildren().add(usernamelabel);
        usernameline.getChildren().add(usernamefield);
        usernameline.setAlignment(Pos.CENTER);



        Label passwordlabel=new Label("Password:");

        passwordfield=new PasswordField();
        passwordline.getChildren().add(passwordlabel);
        passwordline.getChildren().add(passwordfield);
        passwordline.setAlignment(Pos.CENTER);


        loginbutton.setText("Log In ");
        signupline.getChildren().add(signupbutton);
        signupline.getChildren().add(loginbutton);
        signupline.setAlignment(Pos.CENTER);







        Label mainlabel1=new Label("Welcome to HUCS Cinema Reservation System!");
        Label mainlabel2=new Label("Please enter your credentials below and click LOG IN.");
        Label mainlabel3=new Label("You can create a new account by clicking SIGN UP button");

        textLayout.getChildren().add(mainlabel1);
        textLayout.getChildren().add(mainlabel2);
        textLayout.getChildren().add(mainlabel3);
        textLayout.setAlignment(Pos.CENTER);


        errorlabel=new Label("");



        layout.getChildren().add(textLayout);
        layout.getChildren().add(usernameline);
        layout.getChildren().add(passwordline);
        layout.getChildren().add(signupline);
        layout.getChildren().add(errorlabel);
        layout.setAlignment(Pos.CENTER);

        stage.setX(700);
        stage.setY(400);

        Scene scene=new Scene(layout,500,300);
        stage.setScene(scene);
        stage.show();



    }
}
