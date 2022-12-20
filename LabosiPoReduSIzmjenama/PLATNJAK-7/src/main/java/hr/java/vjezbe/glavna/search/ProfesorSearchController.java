package hr.java.vjezbe.glavna.search;

import hr.java.vjezbe.entitet.Profesor;
import hr.java.vjezbe.glavna.GlavnaDatoteke;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class ProfesorSearchController implements OsobaFilter{

    private static List<Profesor> profesorList = new ArrayList<>();

    @FXML
    private TextField nameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField sifraTextField;
    @FXML
    private TextField titulaTextField;

    @FXML
    private TableView<Profesor> profesorTableView;
    @FXML
    private TableColumn<Profesor, String> nameOfProfesor;
    @FXML
    private TableColumn<Profesor, String> LastNameOfProfesor;
    @FXML
    private TableColumn<Profesor, String> sifraOfProfesor;
    @FXML
    private TableColumn<Profesor, String> titulaOfProfesor;


    @FXML
    public void initialize(){
        System.out.println("\nKreiranje Profesora...");
        profesorList = new ArrayList<>(GlavnaDatoteke.dohvatiProfesore());
        ObservableList<Profesor> profesorObservableList = FXCollections.observableList(profesorList);
        profesorTableView.setItems(profesorObservableList);

        nameOfProfesor.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getIme()));
        LastNameOfProfesor.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getPrezime()));
        sifraOfProfesor.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getSifra()));
        titulaOfProfesor.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getTitula()));
    }
    @FXML
    protected void onSearchButtonClick() {
        String enteredName = nameTextField.getText();
        String enteredLastName = lastNameTextField.getText();
        String enteredSifra = sifraTextField.getText();
        String enteredTitula = titulaTextField.getText();
        List<Profesor> filteredList = profesorList;

        filteredList = imeFilter(enteredName, filteredList);
        filteredList = prezimeFilter(enteredLastName, filteredList);

        if(!enteredSifra.isEmpty()){
            filteredList = filteredList.stream()
                    .filter(s -> s.getSifra().toLowerCase().contains(enteredSifra.toLowerCase(Locale.ROOT)))
                    .collect(Collectors.toList());
        }

        if(!enteredTitula.isEmpty()){
            filteredList= filteredList.stream()
                    .filter(s->s.getTitula().toLowerCase().contains(enteredTitula.toLowerCase(Locale.ROOT)))
                    .collect(Collectors.toList());
        }
        profesorTableView.setItems(FXCollections.observableList(filteredList));
    }
}


