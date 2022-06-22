import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;


import java.awt.*;
import java.security.NoSuchAlgorithmException;

public class Signup extends Application{
    TextField username;
    PasswordField password1;
    PasswordField password2;
    Button login;
    Button signup;
    Label errorLabel;


    private boolean checkUsernameExists(String username) {
        boolean check=false;
        for(int i=0;i<BackupReader.users.size();i++) {
            if(username.equals(BackupReader.users.get(i).username)) {
                check=true;
            }

        }

        return check;
    }


    private void clear() {username.clear();password1.clear();password2.clear();}

    @Override
    public void start(Stage stage) throws Exception {

        errorLabel=new Label("");
        stage.setTitle("HUCS Cinema Reservation System ");
        VBox layout=new VBox(20);
        VBox textLayout=new VBox();
        HBox usernameline=new HBox(5);
        HBox passwordline=new HBox(10);
        HBox passwordline2=new HBox(10);
        HBox signupline=new HBox(120);


        javafx.scene.control.Label mainlabel1=new javafx.scene.control.Label("Welcome to HUCS Cinema Reservation System!");
        javafx.scene.control.Label mainlabel2=new javafx.scene.control.Label("Please enter your credentials below and click LOG IN.");
        javafx.scene.control.Label mainlabel3=new javafx.scene.control.Label("You can create a new account by clicking SIGN UP button");

        textLayout.getChildren().add(mainlabel1);
        textLayout.getChildren().add(mainlabel2);
        textLayout.getChildren().add(mainlabel3);
        textLayout.setAlignment(Pos.CENTER);


        Label usernamelabel= new Label("Username:");
        username=new TextField();
        usernameline.getChildren().add(usernamelabel);
        usernameline.getChildren().add(username);
        usernameline.setAlignment(Pos.CENTER);

        Label pass1= new Label("Password:");
        password1=new PasswordField();
        passwordline.getChildren().add(pass1);
        passwordline.getChildren().add(password1);
        passwordline.setAlignment(Pos.CENTER);

        Label pass2= new Label("Password:");
        password2=new PasswordField();
        passwordline2.getChildren().add(pass2);
        passwordline2.getChildren().add(password2);
        passwordline2.setAlignment(Pos.CENTER);

        login=new Button("LOG IN");
        login.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent actionEvent) {
                Login login=new Login();
                try {
                    login.start(stage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        signup=new Button("SIGN UP");
        signup.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(checkUsernameExists(username.getText())) {
                    errorLabel.setText("Username already exists");
                    clear();
                    Main.errorMusic();
                }
                else if(username.getText().equals("")) {
                    errorLabel.setText("Username cant be empty");
                    clear();
                    Main.errorMusic();
                }
                else if(password1.getText().equals("")||password2.getText().equals("")) {
                    errorLabel.setText("Password cant be empty");
                    clear();
                    Main.errorMusic();
                }

                else if(!password1.getText().equals(password2.getText())) {

                    errorLabel.setText("Passwords doesn't match!");
                    clear();
                    Main.errorMusic();
                }

                else {
                    try {
                        User user=new User(username.getText(),BackupReader.hashPassword(password1.getText()),false,false);
                        BackupReader.users.add(user);
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }

                    errorLabel.setText("You succesfully registered with your new credentials!");
                    clear();
                }
            }
        });

        signupline.getChildren().add(login);
        signupline.getChildren().add(signup);
        signupline.setAlignment(Pos.CENTER);

        layout.getChildren().addAll(textLayout,usernameline,passwordline,passwordline2,signupline,errorLabel);
        layout.setAlignment(Pos.CENTER);


        stage.setX(700);
        stage.setY(400);
        Scene scene=new Scene(layout,500,350);
        stage.setScene(scene);
        stage.show();


    }
}
