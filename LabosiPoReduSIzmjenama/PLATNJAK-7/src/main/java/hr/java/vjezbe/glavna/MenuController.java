package hr.java.vjezbe.glavna;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public class MenuController {

    public void ShowStudentSearchScreen(){
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("search/StudentSearch.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 600, 600);
        } catch (IOException e) {
            e.printStackTrace();
        }
        HelloApplication.getStage().setTitle("Student search!");
        HelloApplication.getStage().setScene(scene);
        HelloApplication.getStage().show();
    }
    public void ShowProfesorSearchScreen(){
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("search/ProfesorSearch.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 600, 600);
        } catch (IOException e) {
            e.printStackTrace();
        }
        HelloApplication.getStage().setTitle("Profesor search!");
        HelloApplication.getStage().setScene(scene);
        HelloApplication.getStage().show();
    }
    public void ShowIspitSearchScreen(){
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("search/IspitSearch.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 600, 600);
        } catch (IOException e) {
            e.printStackTrace();
        }
        HelloApplication.getStage().setTitle("Ispit search!");
        HelloApplication.getStage().setScene(scene);
        HelloApplication.getStage().show();
    }
    public void ShowPredmetSearchScreen(){
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("search/PredmetSearch.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 600, 600);
        } catch (IOException e) {
            e.printStackTrace();
        }
        HelloApplication.getStage().setTitle("Predmet search!");
        HelloApplication.getStage().setScene(scene);
        HelloApplication.getStage().show();
    }


}
